package net.wolfpac.cpportal.v2.sorter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


import net.wolfpac.cpportal.v2.clients.Misc;
import net.wolfpac.cpportal.v2.clients.StatusFileFormat;
import net.wolfpac.cpportal.v2.clients.ClientType.TYPE;
import net.wolfpac.cpportal.v2.db.RequesterReporterDir;
import net.wolfpac.logger.BasicLoggerPool;

public class ReporterSorterThread extends SorterThread{
	 
	private static BasicLoggerPool loggerPool = BasicLoggerPool.getLogger(System.getProperty("confFile"));
	private String kw_not_found;
	private String kw_found;
	private String trying_to_write;
	private String success;
	

	public ReporterSorterThread(Properties prop, File directory, File default_dir, int ac_id) throws Exception {		
		super(prop, directory, default_dir, ac_id, TYPE.REPORTER);
	}
	
	@Override
	public void processLine(String line, File file) throws SQLException, IOException{
		StatusFileFormat status = new StatusFileFormat(line);
		loggerPool.logMeHum( this.ac_id + " kw_id: " + status.getKwd_id());
		
		RequesterReporterDir rrd = dao.getRequesterReporterDir(this.ac_id, status.getKwd_id());
		long time = System.currentTimeMillis();
		File ff = null;
		if (rrd == null) {
			if (kw_not_found == null) {
				kw_not_found = this.ac_id +  " kw_id not found";
			}
			loggerPool.logMeHum(kw_not_found.intern());
			ff = new File(this.default_directory.getAbsolutePath() + '/' + file.getName() + '-' + time);
		}
		else {
			if (kw_found == null) {
				kw_found = this.ac_id + " kw_id found: ";
			}
			loggerPool.logMeHum( kw_found.intern() + rrd.getKwd_id());
			ff = new File(rrd.getReporter_dir() + '/' + file.getName() + '-' + time);
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
			throw e;
		}
		
	}
	
	

}
