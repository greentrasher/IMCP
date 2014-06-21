package net.wolfpac.cpportal.v2.clients;

public abstract class FileFormat {
	
	protected String line;
	protected String des_msisdn, msg, src_ac;
	protected String problem;
	protected String vaeRRN;
	
	protected boolean validLine ;
	protected int vaeSID ;
	
	
	
	protected boolean vae = false;
	
	public abstract void reset(String line);
	
	
	public String getSrc_ac() {
		return src_ac;
	}


	public void setSrc_ac(String src_ac) {
		this.src_ac = src_ac;
	}


	public String getProblem() {
		return problem;
	}
	
	public boolean isValidLine() {
		return validLine;
	}
	

	public String getDes_msisdn() {
		return des_msisdn;
	}

	public String getMsg() {
		return msg;
	}
	
	public String getLine() {
		return line;
	}
	
	public FileFormat(String line) {
		this.line  = line;
		validLine = true;
		parseFile();
	
	}
	
	
	public abstract void parseFile();


	public int getVaeSID() {
		return vaeSID;
	}


	public String getVaeRRN() {
		return vaeRRN;
	}


	public boolean isVae() {
		return vae;
	}
	

	
	
}
