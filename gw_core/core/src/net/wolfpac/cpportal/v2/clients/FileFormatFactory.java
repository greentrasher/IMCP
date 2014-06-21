package net.wolfpac.cpportal.v2.clients;

import net.wolfpac.cpportal.v2.clients.ClientType.TYPE;

public class FileFormatFactory {

	public static FileFormat getFileFormat(String line, TYPE type) {
		FileFormat ff = null;
		
		if (type == TYPE.REQUESTER) {
			ff = new IncomingFileFormat(line);
		}
		else {
			ff = new StatusFileFormat(line);
		}
		
		return ff;
		
	}
}
