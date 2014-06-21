package net.wolfpac.cpportal.v2.clients;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class IncomingFileFormat extends FileFormat{

	private static final Pattern pattern = Pattern.compile("^\\s*(\\S*)");
	private static final Pattern vaePattern = Pattern.compile("^;(.+);(\\d+);(\\d+)");
	private Matcher matcher ;
	private Matcher vaeMatcher ;
	
	
	private String src_msisdn ;
	
	
	private String keyword;
	
	public String getKeyword() {
		return keyword;
	}
	
	public IncomingFileFormat(String line) {
		super(line);	
	}
	
	
	
	@Override
	public void parseFile() {
		// inpho|639207932810|9301|0|5295||0||;B8S1 #812#00154#515031#102132810#1094#102#0#;220044018536;0

		keyword = null;
		String[] arrLine = this.line.split("\\|", 9);
		
		if (arrLine.length != 9) {
			validLine = false;
			problem = "Pipe separation is not equal to 9";
			return;
		}
		src_msisdn = 	arrLine[1];
		des_msisdn = arrLine[2];
		
		if (vaeMatcher == null) {
			vaeMatcher = vaePattern.matcher(arrLine[8]);
		}
		else {
			vaeMatcher.reset(arrLine[8]);
		}
		
		if (vaeMatcher.matches()) {
			vae = true;
			msg = vaeMatcher.group(1);
			vaeRRN = vaeMatcher.group(2);
			vaeSID = Integer.parseInt(vaeMatcher.group(3));			
		}
		else {
			msg = arrLine[8];
		}
		
		
		keyword = getPrimaryKeyword();
				
	}
	
	
	
	
	private String getPrimaryKeyword() {
		String kw = null;
		if (this.msg == null) {
			return kw;
		}
	
		
		
		if (matcher == null) {
		  matcher = pattern.matcher(this.msg);
		}
		else {
			matcher.reset(this.msg);
		}
		
		if (matcher.find()) {
			kw = matcher.group(1);
		}
		
		
		return kw;
	}
	
	public void reset(String file) {
		this.line = file;
		validLine  = true;
		problem = null;
		src_msisdn = null;
		des_msisdn = null;
		msg = null;
		this.vae = false;
		vaeRRN = null;
		vaeSID = 0;
		parseFile();
	}

	
	
	public String getSrc_msisdn() {
		return src_msisdn;
	}

}
