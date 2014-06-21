package net.wolfpac.cpportal.v2.db;

public class DefaultACReply {
	
	
	private String smsOut;
	private String tc;
	private String sd;
	
	private String defMsg;
	
	public DefaultACReply() {
		
	}

	public DefaultACReply(String smsOut, String tc, String sd, String defMsg) {
		super();
		this.smsOut = smsOut;
		this.tc = tc;
		this.sd = sd;
		this.defMsg = defMsg;
	}

	public String getSmsOut() {
		return smsOut;
	}

	public String getTc() {
		return tc;
	}

	public String getSd() {
		return sd;
	}

	public String getDefMsg() {
		return defMsg;
	}
	
	
	
	
	
	
	

}
