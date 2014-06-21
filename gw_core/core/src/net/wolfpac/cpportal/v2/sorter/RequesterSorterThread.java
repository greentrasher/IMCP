package net.wolfpac.cpportal.v2.sorter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


import net.wolfpac.cpportal.v2.clients.IncomingFileFormat;
import net.wolfpac.cpportal.v2.clients.Misc;
import net.wolfpac.cpportal.v2.clients.OutgoingFileFormat;
import net.wolfpac.cpportal.v2.clients.ClientType.TYPE;
import net.wolfpac.cpportal.v2.clients.OutgoingFileFormat.MsgType;
import net.wolfpac.cpportal.v2.db.DefaultACReply;
import net.wolfpac.cpportal.v2.db.RequesterReporterDir;
import net.wolfpac.logger.BasicLoggerPool;

public class RequesterSorterThread extends SorterThread{
	private static BasicLoggerPool loggerPool = BasicLoggerPool.getLogger(System.getProperty("confFile"));
	
	private String kw_not_found;
	private String kw_found;
	private String trying_to_write;
	private String success;
	private String def_msg;
	
	
	public RequesterSorterThread(Properties prop, File directory, File default_dir, int ac_id, String def_msg ) throws Exception {		
		super(prop, directory, default_dir, ac_id, TYPE.REQUESTER);
		this.def_msg = def_msg;
	}
	
	public void processLine(String line, File file) throws SQLException, IOException{
		IncomingFileFormat incoming = new IncomingFileFormat(line);
		OutgoingFileFormat outgoing = new OutgoingFileFormat(null);
		
		
		
		loggerPool.logMeHum( this.ac_id + " KW: " + incoming.getKeyword());
		
		RequesterReporterDir rrd = dao.getRequesterReporterDir(this.ac_id, incoming.getKeyword());
		
		long time = System.currentTimeMillis();
		
		File ff = null;
		
		if (rrd == null) {
			if (kw_not_found == null) {
				kw_not_found = this.ac_id +  " kw not found";
			}
									
			DefaultACReply dar = dao.getDefaultACReply(this.ac_id, "default");
			
			/**
			 * create outgoing file, for keyword not found
			 */			
			outgoing.setInFileFormat(incoming);			
			outgoing.setMsg_12(dar.getDefMsg());
			outgoing.setMsgType_04(MsgType.Text);
			outgoing.setRepo_09(Misc.genRepoData(incoming));
			outgoing.setTc_07(dar.getTc());
			outgoing.setSd_08(dar.getSd());
			outgoing.setRepoFileName_05(Misc.genFileName("status"));
			outgoing.genFile();
			
			ff = new File(this.default_directory.getAbsolutePath() + '/' + Misc.genFileName("outgoing") + '-' + time);
			
			line = outgoing.getOutLine();
			
			
			/**
			 * TODO's
			 * 	how to handle STOP/STOP ALL?
			 */
						
			
			loggerPool.logMeHum(kw_not_found.intern() + outgoing.getOutLine());
			
		}
		else {
			if (kw_found == null) {
				kw_found = this.ac_id + " kw found: ";
			}
			
			loggerPool.logMeHum( kw_found.intern() + rrd.getKwd_id());
			
			ff = new File(rrd.getRequester_dir() + '/' + file.getName() + '-' + time);
		}
		
		try {
			if (trying_to_write == null) {
				trying_to_write = this.ac_id +  " trying to write to file: " ;
			}
			
			loggerPool.logMeHum(trying_to_write.intern() + ff);
			
			Misc.createFile(ff, line);
			
			if (success == null) {
				success = this.ac_id +  " success";
			}
			loggerPool.logMeHum(success.intern());			
		}
		catch(IOException e) {			
			loggerPool.logMeHum(this.ac_id + " failed: " + e.getMessage());
		}
	}

}
