package core;

import org.testng.annotations.Test;

public class ApiTest {
	
	
	
	@Test
	public void returnapi() throws Exception{
		
		String reportfile = CompareReport.reportsheet();
		
		String ReportURL = S3bucketurl.GetUrl("GA_Report.xlsx", "","1","xlsx","Excel","PortalAutomation", "C:\\Testcases\\compare\\result_06_02_2019_15_50.xlsx");
		
		System.out.println("The encoded url is " + ReportURL);
		
		
	}

}
