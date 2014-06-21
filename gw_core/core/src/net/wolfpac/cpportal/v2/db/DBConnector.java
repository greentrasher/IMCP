package net.wolfpac.cpportal.v2.db;
/**
 * @author gouki 20070701
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import net.wolfpac.cpportal.v2.clients.ClientType;
import net.wolfpac.cpportal.v2.clients.KeywordAndURL;
import net.wolfpac.cpportal.v2.clients.ClientType.TYPE;

import org.apache.log4j.Logger;






public class DBConnector {
	protected Connection con = null;
	protected static Logger logger  = Logger.getLogger("db");
	
	
	private PreparedStatement ps_getKwURL;
	private PreparedStatement ps_getKwURL1;
	private PreparedStatement ps_insertRRN;
	
	
	/**
	 * Method to connect to the database
	 * @param url database URL
	 * @param username database username
	 * @param pass database password
	 * @throws Exception general exception thrown
	 */
	public DBConnector(String url, String username, String pass) throws Exception{
		connect2DB(url, username, pass);
	}
	
    public static enum DBTYPE{
    	MYSQL("com.mysql.jdbc.Driver"), TIMESTEN("com.timesten.jdbc.TimesTenDriver");
    	
    	private String driverStr;
    	
    	private DBTYPE(String driverSt) {
    		this.driverStr = driverSt;
		
		}
    	
    }
    
	protected void connect2DB(String url, String username, String pass) throws Exception {
			connect2DB(url, username, pass, DBTYPE.MYSQL);
	}
	
	/**
	 * Method to connect to the database
	 * @param url database URL
	 * @param username database username
	 * @param pass database password
	 * @oaram driver driver String
	 * @throws Exception general exception thrown
	 */
	protected void connect2DB(String url, String username, String pass, DBTYPE type) throws Exception{
			Class.forName (type.driverStr).newInstance ();
			this.con = DriverManager.getConnection (url, username,pass);
			
			this.con.setAutoCommit(true);
	}
	
	protected void connect2DB(String driver, String url, String username, String pass) throws Exception{
		Class.forName (driver).newInstance ();
		this.con = DriverManager.getConnection (url, username,pass);		
		this.con.setAutoCommit(false);
	}

	/**
	 * Method to disconnect to the database
	 * @throws SQLException general sql exception thrown
	 */
	public void disconnect2DB() throws SQLException{
	
       con.close();
	}
	
	
	
	public CPInfo getCPInfo(TYPE type, int cp_id) throws SQLException {
		CPInfo r = null;
		
		PreparedStatement ps = null;
		
		if (type == TYPE.REQUESTER) {
			ps = con.prepareStatement(" select distinct f.cp_alias, f.cp_requester_dir, f.cp_reporter_dir,  f.cp_id  from  cpprofilesinfo_tb f  join  cpprofiles_tb d  using (cp_id)   join url_tb e where   f.cp_requester_dir is not NULL and f.cp_id = ?");
		}
		else {
			ps = con.prepareStatement(" select distinct f.cp_alias, f.cp_requester_dir, f.cp_reporter_dir,  f.cp_id  from  cpprofilesinfo_tb f  join  cpprofiles_tb d  using (cp_id)   join url_tb e where   f.cp_reporter_dir is not NULL and f.cp_id = ?");
		}
		
		
		ps.setInt(1, cp_id);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {			
			r = new CPInfo(rs.getString("cp_alias"), rs.getString("cp_requester_dir"),rs.getInt("cp_id"), rs.getString("cp_reporter_dir"));
		}
		
		rs.close();
		ps.close();
		
		return r;
		
	}
	
	public List<CPInfo> getCPInfos(TYPE type) throws SQLException {
		List<CPInfo> list = new ArrayList<CPInfo>();
		
		PreparedStatement ps = null;
		
		if (type == TYPE.REQUESTER) {
			ps = con.prepareStatement(" select distinct f.cp_alias, f.cp_requester_dir, f.cp_reporter_dir,  f.cp_id  from  cpprofilesinfo_tb f  join  cpprofiles_tb d  using (cp_id)   join url_tb e where   f.cp_requester_dir is not NULL ");
		}
		else {
			ps = con.prepareStatement(" select distinct f.cp_alias, f.cp_requester_dir, f.cp_reporter_dir,  f.cp_id  from  cpprofilesinfo_tb f  join  cpprofiles_tb d  using (cp_id)   join url_tb e where   f.cp_reporter_dir is not NULL  ");
		}
		
		ResultSet rs = ps.executeQuery();		
		while(rs.next()) {			
			CPInfo r = new CPInfo(rs.getString("cp_alias"), rs.getString("cp_requester_dir"), rs.getInt("cp_id"), rs.getString("cp_reporter_dir"));
			list.add(r);
		}
		
		rs.close();
		ps.close();
		
		return list;
		
	}
	
	
	public KeywordAndURL getRequesterKeywordUrl(String keyword, int cp_id) throws SQLException {
		KeywordAndURL keywordUrl = null;
		
		
		if (ps_getKwURL == null ) {
				ps_getKwURL  = con.prepareStatement("select svc_status, url, a.keyword_id from keywords_tb a join url_tb b join servicesinfo_tb c using (svc_id) join services_tb using (svc_id) where b.url_id = a.url_requester_id and c.cp_id = b.cp_id and a.ac_id = c.ac_id and keyword = ? and c.cp_id = ?");
		}
		
		ps_getKwURL.setString(1, keyword);
		ps_getKwURL.setInt(2, cp_id);
		
		ResultSet rs = ps_getKwURL.executeQuery();

		if (rs.next()) {
			keywordUrl = new KeywordAndURL(keyword, rs.getString("url"), rs.getInt("svc_status"), rs.getInt("keyword_id"));			
		}
		
		rs.close();
		
		return keywordUrl;
	}
	
	public KeywordAndURL getRequesterKeywordUrl(int kwd_id, int cp_id) throws SQLException {
		KeywordAndURL keywordUrl = null;
		
		
		if (ps_getKwURL1 == null ) {
				ps_getKwURL1  = con.prepareStatement("select svc_status, url from keywords_tb a join url_tb b join servicesinfo_tb c using (svc_id) join services_tb using (svc_id) where b.url_id = a.url_reporter_id and c.cp_id = b.cp_id and a.ac_id = c.ac_id and keyword_id = ? and c.cp_id = ?");
		}
		
		ps_getKwURL1.setInt(1, kwd_id);
		ps_getKwURL1.setInt(2, cp_id);
		
		ResultSet rs = ps_getKwURL1.executeQuery();

		if (rs.next()) {
			keywordUrl = new KeywordAndURL(null, rs.getString("url"), rs.getInt("svc_status"), kwd_id);			
		}
		
		rs.close();
		
		return keywordUrl;
	}
	
	
	public int insertRRN(String rrn, String msisdn, int kwd_id) throws SQLException {
		if (ps_insertRRN == null ) {
			ps_insertRRN = con.prepareStatement("insert into rrn_tb (rrn, msisdn, kwd_id, date) values(? , ? , ?, now() ) ");
		}
		
		ps_insertRRN.setString(1, rrn);
		ps_insertRRN.setString(2, msisdn);
		ps_insertRRN.setInt(3, kwd_id);
		
		int count = ps_insertRRN.executeUpdate();
		
		
		return count;
			
	}
	
	public int resetRRNTable ( ) throws SQLException {
		PreparedStatement ps = con.prepareStatement("delete from rrn_tb where rrn_count >= 1");
		int count = ps.executeUpdate();
		
		ps.close();
		return count;
	}
	  
	/**
     * Frequency Reset from keywords_tb, depending on the frequency type.
     *
     * @throws SQLException
     */
    public int resetFrequencyType ( FreqType ft ) throws SQLException {
        PreparedStatement ps = con.prepareStatement("update keywords_tb set actual_count = 0 where freq_type = ?");
        ps.setInt(1, ft.getIntValue());
        int count = ps.executeUpdate();
		ps.close();
		return count;
    }

    public int resetSubsTBcount() throws SQLException {
        PreparedStatement ps  = con.prepareStatement("update subs_tb set subs_push_cnt = 0");
        int count = ps.executeUpdate();
        ps.close();
		return count;        
    }

    
    public int resetDispatchedTB() throws SQLException{
    	PreparedStatement ps = con.prepareStatement("truncate table dispatched_tb");
        int count = ps.executeUpdate();
        ps.close();
		return count;        
    }

    public int resetbcastTB() throws SQLException{
    	PreparedStatement ps = con.prepareStatement("truncate table broadcast_tb");
        int count = ps.executeUpdate();
        ps.close();
		return count;
    }

    public int resetMsgTB() throws SQLException{
    	PreparedStatement ps = con.prepareStatement("truncate table msg_tb");
        int count = ps.executeUpdate();
        ps.close();
		return count;
    }
    
    

	
	public RequesterReporterDir getRequesterReporterDir(int ac_id, String kw) throws SQLException {
		RequesterReporterDir retValue = null;
		PreparedStatement ps = con.prepareStatement(" select c.cp_requester_dir, cp_reporter_dir, a.keyword_id  from keywords_tb a join services_tb f using(svc_id) join servicesinfo_tb b using (svc_id) join cpprofilesinfo_tb c using(cp_id) where a.keyword=? and a.ac_id = ? and f.svc_status != 2");
		ps.setString(1, kw);
		ps.setInt(2, ac_id);
		ps.execute();
		
		
		ResultSet rs = ps.getResultSet();
		if (rs.next() ) {
			retValue = new RequesterReporterDir(ac_id, rs.getString("cp_requester_dir"), rs.getString("cp_reporter_dir"), rs.getInt("keyword_id"), kw);
		}
		
		
		ps.close();
		
		return retValue;
	}
	
	public RequesterReporterDir getRequesterReporterDir(int ac_id, int kw_id) throws SQLException {
		RequesterReporterDir retValue = null;
		PreparedStatement ps = con.prepareStatement("  select c.cp_requester_dir, cp_reporter_dir,  a.keyword  from keywords_tb a join services_tb f using(svc_id) join servicesinfo_tb b using (svc_id) join cpprofilesinfo_tb c using(cp_id) where a.keyword_id = ? and a.ac_id = ? and f.svc_status != 2");
		ps.setInt(1, kw_id);
		ps.setInt(2, ac_id);
		ps.execute();
		
		
		ResultSet rs = ps.getResultSet();
		if (rs.next() ) {
			retValue = new RequesterReporterDir(ac_id, rs.getString("cp_requester_dir"), rs.getString("cp_reporter_dir"), kw_id, rs.getString("keyword"));
		}
		
		
		ps.close();
		
		return retValue;
	}
	
	
	public DefaultACReply getDefaultACReply ( int ac_id, String tcsd_alias ) throws SQLException {
		DefaultACReply dar = null;
		
		PreparedStatement ps = con.prepareStatement("select sms_out, default_msg, tc, sd from " +
				"directory_tb a join ac_tb b using (ac_id) join tcsd_tb using (ac_id) where ac_id = ? and tcsd_alias = ? ");		
		ps.setInt(1, ac_id);
		ps.setString(2, tcsd_alias);
	
		ps.execute();
		
		ResultSet rs = ps.getResultSet();
		
		if (rs.next() ){
			dar = new DefaultACReply(rs.getString("sms_out"), rs.getString("tc"), rs.getString("sd"), rs.getString("default_msg"));
		}
		
		ps.close();
		
		return dar;		
	}
	
	
	

}
