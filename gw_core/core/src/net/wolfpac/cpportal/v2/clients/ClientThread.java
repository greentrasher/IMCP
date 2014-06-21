package net.wolfpac.cpportal.v2.clients;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.EncodingUtil;



import net.wolfpac.cpportal.v2.clients.IncomingFileFormat;
import net.wolfpac.cpportal.v2.clients.KeywordAndURL;
import net.wolfpac.cpportal.v2.clients.ClientType.TYPE;
import net.wolfpac.cpportal.v2.db.CPInfo;
import net.wolfpac.cpportal.v2.db.DBConnector;
import net.wolfpac.logger.BasicLoggerPool;


public abstract class ClientThread implements Runnable {
	
	private static BasicLoggerPool loggerPool = BasicLoggerPool.getLogger(System.getProperty("confFile"));
	
	private static final String ACKNOWLEDGE = "ACK";
	
	private Pattern pAck;	
	private Matcher mAck;
	
	protected CPInfo cp_info;
	
	protected DBConnector dbh;
	private HttpClient httpClient;
	protected TYPE type;
	private String lookUpDir;
	
	public abstract void processAck200(FileFormat ff, int kwd_id, String rrn) throws Exception ;
	
	public ClientThread(CPInfo cp_info, Properties prop, HttpClient httpclient, TYPE type) throws Exception {
		if (type == null) {
			throw new IllegalArgumentException("type is null");
		}
		
		this.cp_info = cp_info;
		this.httpClient = httpclient;
		this.type = type;
		
		this.pAck = Pattern.compile( ACKNOWLEDGE, Pattern.DOTALL );
		
			
		this.dbh = new DBConnector(prop.getProperty("DB_URL"), prop.getProperty("DB_USER"), prop.getProperty("DB_PASS"));
		
		if (type == TYPE.REQUESTER) {
			this.lookUpDir = cp_info.getRequester_dir();
		}
		else {
			this.lookUpDir = cp_info.getReporter_dir();
		}
		
	}
	
	@Override
	public void run() {
		loggerPool.logMeHum("looking up: (" + type + ") " + this.lookUpDir);
		
		
		File directory = new File(this.lookUpDir);
		while(true) {
		try {
		
			if (Thread.interrupted()) {
				throw new InterruptedException("interrupted");				
			}
			
			System.out.println("boo");
			
			File[] files = directory.listFiles();
			if (files == null) {
				loggerPool.logMeError("directory doesnt exists: ".intern() + this.lookUpDir);
				Thread.sleep(60000);
				continue;
			}
		
		
			
			FileFormat fileInHand = null;
			for(File file: files) {
				if (!file.isFile()) {
					loggerPool.logMeRaw("Not a file / Ignoring: ".intern() + file.getAbsolutePath());
					continue;
				}
					
					
				if (Thread.interrupted()) {
					throw new InterruptedException("interrupted");						
				}
					
				loggerPool.logMeRaw("Processing: ".intern() + file.getAbsolutePath());
				try {
					String line = processFile(file);
					
					if (fileInHand == null) {
						if (type == TYPE.REQUESTER) {
							fileInHand = new IncomingFileFormat(line);
						}
						else {
							fileInHand = new StatusFileFormat(line);
						}
							
					}
					else {
						fileInHand.reset(line);
					}
						
					if (!fileInHand.isValidLine()) {
						loggerPool.logMeError("file in hand is not valid : ".intern() + line );
				  		file.delete();
						continue;
					}
						
						
					KeywordAndURL ku = null;
						
					if (type == TYPE.REQUESTER) {
						ku = this.dbh.getRequesterKeywordUrl(((IncomingFileFormat)fileInHand).getKeyword(), this.cp_info.getCp_id());
					
					}
					else {
						ku = this.dbh.getRequesterKeywordUrl(((StatusFileFormat)fileInHand).getKwd_id(), this.cp_info.getCp_id());
					}
					
					
					
					if (ku == null ) {
						loggerPool.logMeError("url not found! line:  ".intern() + line);
						file.delete();
					}
					else if (ku.getSvc_status() == KeywordAndURL.ServiceStatus.DISABLED ) {
						loggerPool.logMeError("service is disabled:  ".intern() + line);
						file.delete();
					}
					else {
						String rrn = null;
						
						if (type == TYPE.REQUESTER) {
							rrn =  ((IncomingFileFormat)fileInHand).getVaeRRN();
							if (rrn == null ) {
								rrn = generateRRN();
							}
						}
							
						String url = generateURL(fileInHand, ku.getUrl(), rrn);
						GetMethod gm = new GetMethod(url);
						  							  	
						try {
							int retry = 1;
							boolean fileDeleted = false;
							
							while(retry <= 3) {
								
								loggerPool.logMeHum( rrn + " > -" + retry + "- " +url);						  		
								httpClient.executeMethod(gm);
								
								
								if ( gm.getStatusCode() == 200 ) {
								  	String responseBody = gm.getResponseBodyAsString(200);
									
								  	loggerPool.logMeHum(rrn + " < -" + retry + "- " + responseBody  );
									
									mAck = pAck.matcher(responseBody);
									
									if (responseBody.startsWith("ACK: ".intern()) || mAck.find() ){
										processAck200(fileInHand, ku.getKwd_id(), rrn);										
									}
									
									file.delete();
									
									retry = 3;
									
								  	fileDeleted = true;
								  	
								}
								else {
									loggerPool.logMeHum(rrn + " < -" + retry + "- " + gm.getStatusCode() + " " + gm.getStatusLine()  );
								}
								
							  	retry++;
							}
						  		
						  	if (! fileDeleted ) {
						  		retry--;
						  		loggerPool.logMeHum(rrn + " < -" + retry + "- gave up retry!" );
						  		file.delete();
						  	}
						 }
						 catch(Exception e) {
						 	loggerPool.logMeHum(rrn + " < request timeout!" + e.getMessage()  );
						 }
						 finally {
							 gm.releaseConnection();
						 }
						 	
					  }
						
					}catch(Exception e) {
						loggerPool.logMeError(e.getMessage());
						e.printStackTrace();
						//TODO handle exception			
						
					}
					
				}
			
			
			}
			
			catch(InterruptedException e) {
				try {
					loggerPool.logMeError("interrupted!");
					dbh.disconnect2DB();
					return;
				}
				catch(Exception ee) {
					
				}
				
			}
			catch(Exception e) {
				loggerPool.logMeError(e.getMessage());
				//TODO handle exception
				
			}
			finally{
				try {
					Thread.sleep(2000);
				}
				catch(InterruptedException ee) {
					try {
						loggerPool.logMeError("interrupted!");
						dbh.disconnect2DB();
					}
					catch(Exception eee) {} 
					System.out.println("interrupted");
					return;
					
				}
			}
			
		}
	}
	
	
	public String generateURL(FileFormat incoming1, String url, String rrn) throws Exception {
		StringBuilder sb = new StringBuilder(70).append(url).append('?');
		
		ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
		
		if (type == TYPE.REQUESTER) {		
			IncomingFileFormat incoming = (IncomingFileFormat)incoming1;
			list.add(new NameValuePair("src".intern(), incoming.getSrc_msisdn()));			
			list.add(new NameValuePair("dst".intern(), incoming.getDes_msisdn()));
			list.add(new NameValuePair("rrn".intern(), rrn));
			list.add(new NameValuePair("msg".intern(), incoming.getMsg()));
		}
		else if (type == TYPE.REPORTER){
			StatusFileFormat incoming = (StatusFileFormat)incoming1;						
			list.add(new NameValuePair("src".intern(), incoming.getDes_msisdn()));
			list.add(new NameValuePair("status".intern(), Integer.toString(incoming.getStatus())));
			list.add(new NameValuePair("uid".intern(), incoming.getUid()));			
		}
		else {
			throw new Exception("type is unknonwn! : " + this.type);
		}
			
		NameValuePair[] pairs = new NameValuePair[list.size()];
		list.toArray(pairs);
		
		sb.append(EncodingUtil.formUrlEncode(pairs, "ISO 8859-1".intern()));
		
		return sb.toString();
		
	}
		
	private String processFile(File file) throws FileNotFoundException {
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			line = br.readLine();			
		}
		catch(IOException e) {
			loggerPool.logMeError(e.getMessage() + " file: " +  file );
		}
		finally{
			if (br != null) {
				try {
					br.close();
				}
				catch(Exception e) {							}
			}
		}
		
		return line;
	}

	
	
		
    
	
		
		
	
	public static String generateRRN() {
		UUID idOne = UUID.randomUUID();
		String s = idOne.toString();
		s = s.substring(s.length()-10);
		return s;
		
	}
	
}
