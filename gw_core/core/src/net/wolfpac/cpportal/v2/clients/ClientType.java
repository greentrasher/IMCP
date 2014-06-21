package net.wolfpac.cpportal.v2.clients;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.wolfpac.cpportal.v2.db.CPInfo;
import net.wolfpac.cpportal.v2.db.DBConnector;
import net.wolfpac.cpportal.v2.reporter.ReporterThread;
import net.wolfpac.cpportal.v2.requester.RequesterThread;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;




/**
 * This example demonstrates the use of the {@link ResponseHandler} to simplify 
 * the process of processing the HTTP response and releasing associated resources.
 */
public abstract class ClientType extends UnicastRemoteObject {
	
	protected Properties prop;
	protected DBConnector dbh;
	protected HttpClient httpclient;
	
	
	 
	
	public static enum TYPE {
		REQUESTER, REPORTER;
	}
	
	public ClientType(Properties prop) throws Exception {
		
		this.prop = prop;
	
		this.dbh = new  DBConnector(prop.getProperty("DB_URL"), prop.getProperty("DB_USER"), prop.getProperty("DB_PASS"));
		
		
		prepareHttpClient();
		
		
	}
	
	

	
	private void prepareHttpClient() {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams paramss = new HttpConnectionManagerParams();
		paramss.setMaxTotalConnections(Integer.parseInt(prop.getProperty("MaxTotalConnections"))); 
		paramss.setConnectionTimeout(Integer.parseInt(prop.getProperty("ConnectionTimeout"))); 
		paramss.setSoTimeout(Integer.parseInt(prop.getProperty("SoTimeout"))); 
		
		
		connectionManager.setParams(paramss);
		this.httpclient = new HttpClient(connectionManager);
	}
	

	
	

    
}
