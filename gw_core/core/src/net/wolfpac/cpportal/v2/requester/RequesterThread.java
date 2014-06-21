package net.wolfpac.cpportal.v2.requester;
import java.util.Properties;
import java.util.UUID;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.EncodingUtil;




import net.wolfpac.cpportal.v2.clients.ClientThread;
import net.wolfpac.cpportal.v2.clients.FileFormat;
import net.wolfpac.cpportal.v2.clients.IncomingFileFormat;
import net.wolfpac.cpportal.v2.clients.KeywordAndURL;
import net.wolfpac.cpportal.v2.clients.StatusFileFormat;
import net.wolfpac.cpportal.v2.clients.ClientType.TYPE;
import net.wolfpac.cpportal.v2.db.CPInfo;
import net.wolfpac.cpportal.v2.db.DBConnector;
import net.wolfpac.logger.BasicLoggerPool;


public class RequesterThread extends ClientThread {
	
	private static BasicLoggerPool loggerPool = BasicLoggerPool.getLogger(System.getProperty("confFile"));
	
	
	public RequesterThread(CPInfo cp_info, Properties prop, HttpClient httpclient) throws Exception {
		super(cp_info, prop, httpclient, TYPE.REQUESTER);
		
		
	}
	
	
	@Override
	public void processAck200(FileFormat ff, int kwd_id, String rrn) throws Exception {
		IncomingFileFormat fileInHand = (IncomingFileFormat)ff;
		
		dbh.insertRRN(rrn, fileInHand.getSrc_msisdn().substring(fileInHand.getSrc_msisdn().length()-9), kwd_id);
		
	}
	
	

	
	public static String generateRRN() {
		UUID idOne = UUID.randomUUID();
		String s = idOne.toString();
		s = s.substring(s.length()-10);
		return s;
		
	}
	
}
