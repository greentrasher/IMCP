package net.wolfpac.cpportal.v2.clients;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OutgoingFileFormat extends FileFormat {

	private IncomingFileFormat in;
	private MsgType msgType_04;
	private String  repoFileName_05;
	
	private String  tc_07;
	private String  sd_08;
	private String  repo_09;
	
	private String sid_11; // smart service id for NGIN
	private String msg_12;
	
	private String outLine;
	
	
	public OutgoingFileFormat(String line) {
		super(line);
	}
	
	
	
	@Override
	public void parseFile() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void reset(String line) {
		// TODO Auto-generated method stub
		
	}	
	public static enum MsgType {        
	    Text(0), OpLogo(1), CLILogo(2), DloadProfile(3), PicMsg(4), RingTone(5), 
	    SILink(7), ServiceLoading(8), vCard(9), EMS(10), vCal(11), Unknown(-1);

	    private int value;
	    
	    private MsgType(int value) {
	    	this.value = value;
	    }
	    
	    public int getIntValue() {
	    	return this.value;
	    }
	    
	    public static MsgType getType( int i ) {
	        for (MsgType t : MsgType.values()) {
	            if (t.getIntValue() == i) {
	            	return t;
	            }
	        }
	        return Unknown;
	    }

	}
	
	public void setInFileFormat ( IncomingFileFormat _in ) {
		in = _in;
		if ( in.vae ){
			this.vaeRRN = in.vaeRRN;
			this.vaeSID = in.vaeSID;
		}
		
		this.src_ac = in.src_ac;
		this.des_msisdn = in.des_msisdn;
		
	}
	
	/**
	 * legacy - CPV2|2211|639189230128|0|CPV2_1267078071069_PENTANA.txt|0|0|0|PENTANA:PUSH_63918923012812670780711813:11|test_--and _Oayahoo.com
     * vae ngin - info|src|Dest|MsgType|MsgId|Misc|Tariff|ServD|RepMsg|RRN|Service ID|TextOrPath|CSPDetails     
     * */	
	public void genFile(){
		StringBuilder sb = new StringBuilder("CPV2|");
		sb.append(src_ac).append('|').
		append(des_msisdn).append('|').
		append(MsgType.Text).append('|').
		append(getRepoFileName_05()).append('|').
		append('0').append('|').
		append(getTc_07()).append('|').
		append(getSd_08()).append('|').
		append('0').append('|');
		
		if ( vae ){
			sb.append(vaeRRN).append('|').append(vaeSID).append('|');
		}
		
		sb.append(getMsg_12());
		setOutLine(sb.toString());
		
	}



	public MsgType getMsgType_04() {
		return msgType_04;
	}



	public void setMsgType_04(MsgType msgType_04) {
		this.msgType_04 = msgType_04;
	}



	public String getRepoFileName_05() {
		return repoFileName_05;
	}



	public void setRepoFileName_05(String repoFileName_05) {
		this.repoFileName_05 = repoFileName_05;
	}



	public String getTc_07() {
		return tc_07;
	}



	public void setTc_07(String tc_07) {
		this.tc_07 = tc_07;
	}



	public String getSd_08() {
		return sd_08;
	}



	public void setSd_08(String sd_08) {
		this.sd_08 = sd_08;
	}



	public String getRepo_09() {
		return repo_09;
	}



	public void setRepo_09(String repo_09) {
		this.repo_09 = repo_09;
	}



	public String getSid_11() {
		return sid_11;
	}



	public void setSid_11(String sid_11) {
		this.sid_11 = sid_11;
	}



	public String getMsg_12() {
		return msg_12;
	}



	public void setMsg_12(String msg_12) {
		this.msg_12 = msg_12;
	}



	public String getOutLine() {
		return outLine;
	}



	public void setOutLine(String outLine) {
		this.outLine = outLine;
	}
	
	
	

}
