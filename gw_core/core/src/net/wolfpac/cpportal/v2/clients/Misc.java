package net.wolfpac.cpportal.v2.clients;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Misc {

	
	private Misc() { }
	
    
    public static void writeFile( String dir, String fileName, String data ) throws Exception {
    	/**
        * not existing directory is being handled.
        * therefore newly generated dir on the database
        * would be automatically created. so make sure,
        * you define the right directory on the UI.
        */
    	if ( !new File(dir).exists() ){
    		new File(dir).mkdirs();
    	}
        
    	StringBuffer absfileNameFinal = new StringBuffer(dir).append('/').append(fileName);
    	StringBuffer absfileNameTmp = new StringBuffer(dir).append('/').append('.').append(fileName);

    	File fileTmp = new File(absfileNameTmp.toString());       
    	FileWriter writer = new FileWriter(fileTmp,  true);
    	writer.write(data);
    	writer.close();        
    	fileTmp.renameTo(new File(absfileNameFinal.toString()));

    }


    public static String getStackStrace(Exception e) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	e.printStackTrace(pw);

    	return sw.toString();
    }

	
	/**
	 * Move file to a new directory or file
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void moveFile(File source, File dest) throws IOException{
		
		if(!dest.exists()){			  
			dest.createNewFile();			 
		}
		   
		InputStream in = null;		 
		OutputStream out = null;
		
		File outFile = null;
		
		if (dest.isDirectory()) {
			outFile = new File(dest.getAbsolutePath() + source.getName());
		}
		else {
			outFile = dest;
		}
		
		
		try{
			in = new FileInputStream(source);
		    out = new FileOutputStream(outFile);
		    
		    byte[] buf = new byte[1024];
		    int len;			 
		    while((len = in.read(buf)) > 0){			 
		    	out.write(buf, 0, len);
		    }
		    
		}finally{			 
			in.close();			 
		    out.close();
		    try {			    
		    	source.delete();
		    }catch(Exception e) {
		    	dest.delete();
		    } 
		}
	}			 
	
	
	public static void createFile(File name, String line) throws IOException{
		
		BufferedWriter bw = null;
		try		{
			bw = new BufferedWriter(new FileWriter(name));
			bw.write(line);
			bw.write('\n');
		}
		catch(IOException e) {
			throw e;
		}
		finally {
			if (bw != null ) {
				bw.close();
			}
		} 
	}
	
	public static String genFileName ( String type ){
         StringBuilder fileName = new StringBuilder();         
         // CPV2_1267078071069_PENTANA
         
         if ( type.equalsIgnoreCase("outgoing") ){
        	fileName.append("CPV2_").append(System.currentTimeMillis()).append('_').append("DEFAULT");
         }
         else {
        	fileName.append("CPV2_").append(System.currentTimeMillis()).append('_').append("DEFAULT").append("-").append("status");
         }
         
         fileName.append('.').append("txt");
         return fileName.toString();
     }
	
     public static String genRepoData ( IncomingFileFormat in ){
        StringBuilder repo = new StringBuilder();
        repo.append("CPV2-DEFAULT").append(':').append("PULL_").append(genUniqueID(in)).append(':').append("DEFAULT");
        return repo.toString();
     }
     
     public static String genUniqueID ( IncomingFileFormat in ) {
         StringBuilder uid = new StringBuilder();
         long stime = System.currentTimeMillis();
         
         uid.append(in.getDes_msisdn().substring(3)).append(Long.toString(stime).substring(3));
         if ( in.vae ){ 
        	 uid.append(in.vaeRRN);
         }
           
         return uid.toString();
     }
}
