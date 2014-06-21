package net.wolfpac.cpportal.v2.reporter;

import java.io.FileInputStream;
import java.util.Properties;

import net.wolfpac.cpportal.v2.clients.ClientType;

public class Reporter extends ClientType{
	public Reporter(Properties prop) throws Exception {
		super(prop);
	}

	public static void main(String[] args) throws Exception{
		
    	Properties prop = new Properties();    	
    	prop.load(new FileInputStream(args[0]));
    	Reporter a = new Reporter(prop);
	}
}
