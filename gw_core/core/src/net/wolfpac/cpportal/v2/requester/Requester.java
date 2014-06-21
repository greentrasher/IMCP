package net.wolfpac.cpportal.v2.requester;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import net.wolfpac.cpportal.v2.clients.ClientInterface;
import net.wolfpac.cpportal.v2.clients.ClientThread;
import net.wolfpac.cpportal.v2.clients.ClientType;
import net.wolfpac.cpportal.v2.clients.ClientType.TYPE;
import net.wolfpac.cpportal.v2.db.CPInfo;
import net.wolfpac.cpportal.v2.reporter.ReporterThread;
import net.wolfpac.logger.BasicLoggerPool;


/**
 * This example demonstrates the use of the {@link ResponseHandler} to simplify 
 * the process of processing the HTTP response and releasing associated resources.
 */
public class Requester extends ClientType implements ClientInterface{
	
	private static BasicLoggerPool loggerPool = BasicLoggerPool.getLogger(System.getProperty("confFile"));
	Hashtable<String, Future<?>> pool = new Hashtable<String, Future<?>>();
	ExecutorService es = Executors.newFixedThreadPool(50);
	
	public Requester(Properties prop) throws Exception {
		super(prop);
	}
	
	
	@Override
	public synchronized void spawn(int ac_id, String directory, TYPE type)
			throws Exception {
		
			CPInfo req = null; 
			synchronized (dbh) {
				req = dbh.getCPInfo(type, ac_id);	
			}
			
			
			ClientThread rt = null;
			
			
			String key = directory + "-" + ac_id + "-" + type;
			loggerPool.logMeHum("Requester: " + key);
			
			if (pool.get(key) == null) {
				return;
			}
			
			
			Future<?> future;
			if (type == TYPE.REQUESTER) {
				rt = new RequesterThread(req, this.prop, this.httpclient);
			
			}
			else if (type == TYPE.REPORTER){
				rt = new ReporterThread(req, this.prop, this.httpclient);
				
			}
			
			try {
				future = es.submit(rt);
				pool.put(key, future);
			}
			catch(Exception e) {
				
			}
	}
	
	@Override
	public synchronized void stop(int ac_id, String directory, TYPE type) throws Exception {
		
		CPInfo req = null; 
		synchronized (dbh) {
			req = dbh.getCPInfo(type, ac_id);	
		}
		
		
		ClientThread rt = null;
		
		
		String key = directory + "-" + ac_id + "-" + type;
		
		Future<?> future = pool.get(key); 
		if (future  == null) {
			return;
		}
		
		boolean cancelled = future.cancel(true);
		if (cancelled ) {
			pool.remove(key);
		}
		
		
	}
	
	
    public static void main(String[] args) throws Exception  {
    	
    	Properties prop = new Properties();    	
    	prop.load(new FileInputStream(args[0]));
    	Requester client = new Requester(prop);
    	 
    }
    
}
