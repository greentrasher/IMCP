package net.wolfpac.cpportal.v2.clients;

public class KeywordAndURL {

	 private String keyword;
	 private String url;
	 private ServiceStatus svc_status;
	 private int kwd_id;
	 
	 public static enum ServiceStatus {
		 LIVE, LAUNCH, DISABLED;
		 
		
		 
		
		 
		 public static ServiceStatus getServiceStatus(int i) {
			 if (i == 1) {
				 return LIVE;				 
			 }
			 else if (i == 2 ) {
				 return LAUNCH;				 
			 }
			 else if (i == 3) {
				 return DISABLED;
			 }
			 return null;
		 }
		 
	 }
	 
	 
	 public int getKwd_id() {
		return kwd_id;
	}
	 
	 public ServiceStatus getSvc_status() {
		return svc_status;
	}
	 
	 public String getKeyword() {
		return keyword;
	}
	 
	 public String getUrl() {
		return url;
	}
	 
	 
	 public KeywordAndURL(String kw, String url, int svc_status, int kwd_id) {
		 this.keyword = kw;
		 this.url = url;
		 this.svc_status = ServiceStatus.getServiceStatus(svc_status);
		 this.kwd_id = kwd_id;
	}
	 
	 public KeywordAndURL(String kw, String url, ServiceStatus ss, int kwd_id) {
		 this.keyword = kw;
		 this.url = url;
		 this.svc_status = ss;
		 this.kwd_id = kwd_id;
	}
}
