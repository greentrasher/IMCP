package net.wolfpac.cpportal.v2.db;

public class CPInfo {
     private String cp_name, requester_dir, reporter_dir;
     int  cp_id;     
	public String getCp_name() {
		return cp_name;
	}
	
	public String getReporter_dir() {
		return reporter_dir;
	}
	
	public String getRequester_dir() {
		return requester_dir;
	}
	
	
	public int getCp_id() {
		return cp_id;
	}
	
	public CPInfo(String cp_name, String cp_directory, int cp_id, String reporter_dir) {
		super();
		this.cp_name = cp_name;
		this.requester_dir = cp_directory;
	
		this.cp_id = cp_id;
		this.reporter_dir = reporter_dir;
	}
     
     
}
