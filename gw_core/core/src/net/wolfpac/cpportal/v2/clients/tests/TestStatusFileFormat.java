package net.wolfpac.cpportal.v2.clients.tests;

import static org.junit.Assert.*;

import net.wolfpac.cpportal.v2.clients.FileFormat;
import net.wolfpac.cpportal.v2.clients.FileFormatFactory;
import net.wolfpac.cpportal.v2.clients.StatusFileFormat;
import net.wolfpac.cpportal.v2.clients.ClientType.TYPE;

import org.junit.Test;

public class TestStatusFileFormat {

	@Test
	public void testStatusFileFormat() {
		String s = "0|09196823643|PENTANA:6391892301281234567891:11";
		
		StatusFileFormat sf = (StatusFileFormat)FileFormatFactory.getFileFormat(s, TYPE.REPORTER);
		
		assertEquals(0, sf.getStatus());
		assertEquals("09196823643", sf.getDes_msisdn());
		assertEquals("PENTANA:6391892301281234567891:11", sf.getMsg());
		assertEquals("6391892301281234567891", sf.getUid());
		assertEquals(11, sf.getKwd_id());
		assertTrue(sf.isValidLine());
		
		s = "950|639285212695|LOTARIS:6391892301281234567892:12";		
		sf.reset(s);		
		assertEquals(950, sf.getStatus());
		assertEquals("639285212695", sf.getDes_msisdn());
		assertEquals("LOTARIS:6391892301281234567892:12", sf.getMsg());
		assertEquals("6391892301281234567892", sf.getUid());
		assertEquals(12, sf.getKwd_id());
		assertTrue(sf.isValidLine());
		
		
		s = "950|639285212695|LOTARIS:63918923012812345";		
		sf.reset(s);		
		assertEquals(950, sf.getStatus());
		assertEquals("639285212695", sf.getDes_msisdn());
		assertEquals("LOTARIS:63918923012812345", sf.getMsg());
		assertFalse(sf.isValidLine());
		assertEquals("unknown repo format: LOTARIS:63918923012812345", sf.getProblem());
		
		
		s = "950|639285212695|LOTARIS:6391892301281234567892:12|as";		
		sf.reset(s);
		assertTrue(sf.isValidLine());
		assertEquals(950, sf.getStatus());
		assertEquals("639285212695", sf.getDes_msisdn());
		assertEquals("LOTARIS:6391892301281234567892:12", sf.getMsg());
		assertEquals("6391892301281234567892", sf.getUid());
		assertEquals(12, sf.getKwd_id());
		assertEquals("as", sf.getVaeRRN());
		assertEquals(0, sf.getVaeSID());
		assertTrue(sf.isVae());
		
		s = "950|639285212695|LOTARIS:6391892301281234567892:12|as|21";		
		sf.reset(s);
		assertTrue(sf.isValidLine());
		assertEquals(950, sf.getStatus());
		assertEquals("639285212695", sf.getDes_msisdn());
		assertEquals("LOTARIS:6391892301281234567892:12", sf.getMsg());
		assertEquals("6391892301281234567892", sf.getUid());
		assertEquals(12, sf.getKwd_id());
		assertEquals("as", sf.getVaeRRN());
		assertEquals(21, sf.getVaeSID());
		assertTrue(sf.isVae());
		
		s = "950|639285212695|LOTARIS:63918923012812345";		
		sf.reset(s);		
		assertEquals(950, sf.getStatus());
		assertEquals("639285212695", sf.getDes_msisdn());
		assertEquals("LOTARIS:63918923012812345", sf.getMsg());
		assertFalse(sf.isValidLine());
		assertEquals("unknown repo format: LOTARIS:63918923012812345", sf.getProblem());
		
		sf.reset(null);
		assertEquals(0, sf.getStatus());
		assertNull(sf.getDes_msisdn());
		assertNull(sf.getMsg());
		assertNull(sf.getUid());
		assertNull(sf.getVaeRRN());
		assertEquals(0, sf.getKwd_id());
		assertEquals(0, sf.getVaeSID());
		assertFalse(sf.isVae());
		assertFalse(sf.isValidLine());
		
		
		
		s = "951|639285212696|LOTARIS:6391892301281234567811:22";		
		sf.reset(s);		
		assertEquals(951, sf.getStatus());
		assertEquals("639285212696", sf.getDes_msisdn());
		assertEquals("LOTARIS:6391892301281234567811:22", sf.getMsg());
		assertEquals("6391892301281234567811", sf.getUid());
		assertEquals(22, sf.getKwd_id());
		assertTrue(sf.isValidLine());
		
		
		s = "950|639285212695|LOTARIS:6391892301281234567892:12|as|21";		
		sf.reset(s);
		assertTrue(sf.isValidLine());
		assertEquals(950, sf.getStatus());
		assertEquals("639285212695", sf.getDes_msisdn());
		assertEquals("LOTARIS:6391892301281234567892:12", sf.getMsg());
		assertEquals("6391892301281234567892", sf.getUid());
		assertEquals(12, sf.getKwd_id());
		assertEquals("as", sf.getVaeRRN());
		assertEquals(21, sf.getVaeSID());
		assertTrue(sf.isVae());
		
		
	}

}
