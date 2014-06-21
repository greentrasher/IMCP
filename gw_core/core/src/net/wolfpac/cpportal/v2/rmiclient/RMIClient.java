package net.wolfpac.cpportal.v2.rmiclient;

import java.rmi.Naming;

import net.wolfpac.cpportal.v2.clients.ClientInterface;
import net.wolfpac.cpportal.v2.clients.ClientType.TYPE;

public class RMIClient {
	
	public RMIClient() {
		try {
			ClientInterface ci = (ClientInterface)Naming.lookup("cpportal_client");
			ci.spawn(1, "/home/wammp/spool/cpportalv2/wlf01/i/", TYPE.REQUESTER);
			ci.stop(1, "/home/wammp/spool/cpportalv2/wlf01/i/", TYPE.REQUESTER);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		RMIClient boo = new RMIClient();
		
		
	}
	
}
