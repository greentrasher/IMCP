package net.wolfpac.cpportal.v2.clients;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.wolfpac.cpportal.v2.clients.ClientType.TYPE;

public interface ClientInterface extends Remote {

	 public void spawn(int cp_id, String directory, TYPE type) throws Exception;
	 public void stop(int cp_id, String directory, TYPE type) throws Exception;
	 
	 
	 
}
