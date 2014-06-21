package net.wolfpac.cpportal.v2.clients;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StatusFileFormat extends FileFormat{

	private static final Pattern pattern = Pattern.compile("^.+:(.+):(.+)");
	private Matcher matcher ;
	private int status;
	private int kwd_id;	
	private String uid;
	
	
	
	public StatusFileFormat(String line) {
		super(line);	
	}
	
	@Override
	public void parseFile() {
	

	
		
		try {
			String[] arrLine = this.line.split("\\|");
			status = Integer.parseInt(arrLine[0]);
			des_msisdn = arrLine[1];
			msg = arrLine[2];
			
			if (matcher == null) {
				matcher = pattern.matcher(msg);				
			}
			else {
				matcher.reset(msg);
			}
			
			if (matcher.matches()) {
				uid = matcher.group(1);
				kwd_id = Integer.parseInt(matcher.group(2));
				
			}
			else {
				validLine = false;
				problem = "unknown repo format: " + msg;
			}
			
			if (arrLine.length > 3 ) {
				vae =true;
				this.vaeRRN = arrLine[3];
				
				if (arrLine.length > 4) {
					if (arrLine[4].length() > 0 ) {
						this.vaeSID = Integer.parseInt(arrLine[4]);
					}
				}
				
			}
			
		}
		
		catch(Exception e) {
			validLine = false;
			problem = e.getMessage();
			e.printStackTrace();
		}
	
				
	}
	
	public void reset(String file) {
		this.line = file;
		validLine  = true;
		problem = null;	
		des_msisdn = null;
		msg = null;
		kwd_id = 0;
		status = 0;
		uid = null;
		vae = false;
		this.vaeRRN = null;
		this.vaeSID = 0;
		parseFile();
	}

	public int getStatus() {
		return status;
	}

	public int getKwd_id() {
		return kwd_id;
	}

	public String getUid() {
		return uid;
	}
	
}