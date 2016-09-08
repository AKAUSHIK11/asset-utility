package assets.upload.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import assets.upload.beans.Asset;


public class ReportGenerator {
	
	
	private static  Logger LOGGER = Logger.getLogger(ReportGenerator.class);
	
	/**
	 * Generates output report file in excel with upload status
	 */
	public void generateReport(List<Asset> assets, String reportName, String reportLocation){
		
		String fileNameWithAppendedTimeStamp = reportLocation + File.separator + reportName
				+ "_"
				+ (new SimpleDateFormat("yyyyMMddhhmm").format(new Date()))
				+ ".csv" ;
		
		StringBuffer reportPath = new StringBuffer();
		reportPath.append(fileNameWithAppendedTimeStamp);	
		PrintWriter out;
		try {
			out = getPrintWriter(reportPath.toString());
			out.println("Id"+","+"File Name"+","+"Status");
			for (Asset asset : assets) {
				if(asset.isUploaded()){
					out.println(asset.getId()+","+asset.getFile().getName()+","+"SUCCESS");
				}else{
					out.println(asset.getId()+","+asset.getFile().getName()+","+"Failure");
				}				
			}
			out.close();
		} catch (Exception ex) {
			LOGGER.error("Problem while generating reports for BLOB inserts." , ex);
		}


	}
	
	protected PrintWriter getPrintWriter(String reportPath)throws Exception{
		
		return new PrintWriter(new FileOutputStream(new File(reportPath.toString())));
	}

}
