package net.wolfpac.cpportal.v2.reporter;

import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;


import net.wolfpac.cpportal.v2.clients.ClientThread;
import net.wolfpac.cpportal.v2.clients.FileFormat;
import net.wolfpac.cpportal.v2.clients.ClientType.TYPE;
import net.wolfpac.cpportal.v2.db.CPInfo;

public class ReporterThread extends ClientThread{
	@Override
	public void processAck200(FileFormat ff, int kwd_id, String rrn)
			throws Exception {
		
		
		
	}
	
	public ReporterThread(CPInfo cp_info, Properties prop, HttpClient httpclient) throws Exception {
		super(cp_info, prop, httpclient, TYPE.REPORTER);
	}

}
