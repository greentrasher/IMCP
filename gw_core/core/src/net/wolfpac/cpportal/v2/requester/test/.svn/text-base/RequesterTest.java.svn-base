package net.wolfpac.cpportal.v2.requester.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.Properties;

import net.wolfpac.cpportal.v2.clients.ClientType.TYPE;
import net.wolfpac.cpportal.v2.requester.Requester;

import org.junit.Before;
import org.junit.Test;

public class RequesterTest {

	Properties prop;
	@Before
	public void setUp() throws Exception {
		this.prop = new Properties();
		prop.load(new FileInputStream("/home/gouki/workspace/CPv2/conf/client.conf"));
		System.setProperty("confFile", "/home/gouki/workspace/CPv2/conf/client.conf");
	}

	@Test
	public void testRequester() {
		Requester req = null;
		try { 
			req = new Requester(this.prop);
			
		}
		catch(Exception e) {
			fail("exception caught: " + e.getMessage());
		}
		
	}

}
