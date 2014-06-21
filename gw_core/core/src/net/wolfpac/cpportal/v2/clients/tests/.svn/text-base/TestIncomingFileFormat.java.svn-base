package net.wolfpac.cpportal.v2.clients.tests;
import static org.junit.Assert.*;

import net.wolfpac.cpportal.v2.clients.IncomingFileFormat;

import org.junit.Test;


public class TestIncomingFileFormat {

	@Test
	public void testParseFile() {
		String s = "inpho|639293605561|463254004|0|785||0|| pentana boo";
		IncomingFileFormat iff = new IncomingFileFormat(s);
		
		assertTrue(iff.isValidLine());
		assertTrue(!iff.isVae());
		assertNull(iff.getVaeRRN());
		assertEquals(0, iff.getVaeSID());
		assertEquals("639293605561", iff.getSrc_msisdn());
		assertEquals("463254004", iff.getDes_msisdn());
		assertEquals(" pentana boo", iff.getMsg());
		assertNull(iff.getProblem());
		assertEquals("pentana", iff.getKeyword());
		
		s = "inpho|639293605561|463254004|0|785||0|pentana ";
		iff.reset(s);
		assertTrue(!iff.isValidLine());
		assertTrue(!iff.isVae());
		assertNull(iff.getVaeRRN());
		assertEquals(0, iff.getVaeSID());
		assertNull(iff.getDes_msisdn());
		assertNull(iff.getSrc_msisdn());
		assertNull(iff.getMsg());
		assertEquals("Pipe separation is not equal to 9", iff.getProblem());
		
		s = "inpho|639293605561|463254004|0|785||0|| test me";
		iff.reset(s);
		assertTrue(iff.isValidLine());
		assertTrue(!iff.isVae());
		assertNull(iff.getVaeRRN());
		assertEquals(0, iff.getVaeSID());
		assertEquals("639293605561", iff.getSrc_msisdn());
		assertEquals("463254004", iff.getDes_msisdn());
		assertEquals(" test me", iff.getMsg());
		assertEquals("test", iff.getKeyword());
		assertNull(iff.getProblem());
		
		s = "inpho|639293605561|463254004|0|785||0||  ";
		iff.reset(s);
		assertTrue(iff.isValidLine());
		assertTrue(!iff.isVae());
		assertNull(iff.getVaeRRN());
		assertEquals(0, iff.getVaeSID());
		assertEquals("639293605561", iff.getSrc_msisdn());
		assertEquals("463254004", iff.getDes_msisdn());
		assertEquals("  ", iff.getMsg());
		assertEquals("", iff.getKeyword());
		assertNull(iff.getProblem());
		
		s = "inpho|639293605561|463254004|0|785||0||;BOO Super 6/49;110603751073;33";
		iff.reset(s);
		assertTrue(iff.isValidLine());
		assertTrue(iff.isVae());
		assertEquals("110603751073", iff.getVaeRRN());		
		assertEquals(33, iff.getVaeSID());
		assertEquals("639293605561", iff.getSrc_msisdn());
		assertEquals("463254004", iff.getDes_msisdn());
		assertEquals("BOO Super 6/49", iff.getMsg());
		assertEquals("BOO", iff.getKeyword());
		assertNull(iff.getProblem());
		
		s = "inpho|639293605561|463254004|0|785||0||";
		iff.reset(s);
		assertTrue(iff.isValidLine());
		assertTrue(!iff.isVae());
		assertNull(iff.getVaeRRN());
		assertEquals(0, iff.getVaeSID());
		assertEquals("639293605561", iff.getSrc_msisdn());
		assertEquals("463254004", iff.getDes_msisdn());
		assertEquals("", iff.getMsg());
		assertEquals("", iff.getKeyword());
		assertNull(iff.getProblem());
		
		s = "inpho|639293605561|463254004|0|785||0||;   LOTTO Super 6/49;110603751072;35";
		iff.reset(s);
		assertTrue(iff.isValidLine());
		assertTrue(iff.isVae());
		assertEquals("110603751072", iff.getVaeRRN());		
		assertEquals(35, iff.getVaeSID());
		assertEquals("639293605561", iff.getSrc_msisdn());
		assertEquals("463254004", iff.getDes_msisdn());
		assertEquals("   LOTTO Super 6/49", iff.getMsg());
		assertEquals("LOTTO", iff.getKeyword());
		assertNull(iff.getProblem());
		
		
	}

}
