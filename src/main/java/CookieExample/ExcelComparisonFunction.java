package CookieExample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelComparisonFunction {
	
	static String excelpath_update = "C:\\GoogleAnalyticsProject\\GA\\result_.xlsx";
	static String sheetname = "Report";
	
	static int excelrow = 1;
	static int dexcelrow =1;
	XSSFSheet sheet;
	XSSFSheet modelsheet;
	XSSFRow row = null;
	XSSFWorkbook workbook;
	static String resultdata[] = new String[8];
	static boolean status = false;
	static String reportfilename = "";
	static StringBuilder sb= new StringBuilder();
	static StringBuilder sb2= new StringBuilder();
	static String missactual = "";
	static String missexpected = "";

 public static String[] reportsheet(String reportfile) {
	 
	 String returnvalue[] = new String[4];
        try {
        	
        	SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
			Date datedd = new Date();
			System.out.println(formatter.format(datedd));
			String localDate11 = formatter.format(datedd).toString();
			excelpath_update = "C:\\GoogleAnalyticsProject\\compare\\FinalResult_"+localDate11+".xlsx";
			SetExcelFile(excelpath_update, sheetname);
			
			reportfilename = "FinalResult_"+localDate11+".xlsx";
            // get input excel files
         System.out.println("Welcome");
            FileInputStream excellFile1 = new FileInputStream(new File("C:\\GoogleAnalyticsProject\\compare\\HealthGA_Expected.xlsx"));
            FileInputStream excellFile2 = new FileInputStream(new File("C:\\GoogleAnalyticsProject\\GA\\HealthGA_Actual_Report_20190214.xlsx"));

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook1 = new XSSFWorkbook(excellFile1);
            XSSFWorkbook workbook2 = new XSSFWorkbook(excellFile2);
            
            // Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook1.getSheetAt(0);
            XSSFSheet sheet2 = workbook2.getSheetAt(0);

            // Compare sheets
            if(compareTwoSheets(sheet1, sheet2)) {
                System.out.println("\n\nThe two excel sheets are Equal");
                status = true;
                returnvalue[0]="true";
            } else {
                System.err.println("\n\nThe two excel sheets are Not Equal");
                returnvalue[0] = "false";
            }
           
            //close files
            excellFile1.close();
            excellFile2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        returnvalue[1] = reportfilename;
        returnvalue[2] = missactual;
        returnvalue[3] = missexpected;
        
        System.out.println("values os array are actual " + returnvalue[2] );
        System.out.println("values os array are expected " + returnvalue[3] );
        return returnvalue;
    }



 
 private static boolean compareTwoSheets(XSSFSheet sheet1, XSSFSheet sheet2){
	 boolean equalSheets = true;
	 boolean equalSheets2 = true;
	 boolean finalresult = true;
	 String status = "";
	 
	 try{
	 System.out.println("comparing the sheets ");
	   		
	         int lastRow1 = sheet1.getLastRowNum();
	        
	         int lastRow2 = sheet2.getLastRowNum();
	         
	         System.out.println("lastRow1 -> " + lastRow1  + "lastRow2 -> " + lastRow2);
	         String sheet1cell1data = null;
	         String sheet1cell2data = null;
	        
	         for(int i=1; i <= lastRow1; i++) {
	           
	             System.out.println("\n\nComparing Row "+i);
	           
	             XSSFRow row1 = sheet1.getRow(i);
	             XSSFCell cell1 = row1.getCell(0);
	             XSSFCell cell2 = row1.getCell(1);
	             
	             if(cell1 != null){
	            	 sheet1cell1data = cell1.getStringCellValue();
	            	 if(cell2 != null){
	            		 sheet1cell2data = cell2.getStringCellValue();
	            		 
	            		 System.out.println("data of sheet1 is " + sheet1cell1data + sheet1cell2data);
	            	 }else{
	            		 continue;
	            	 }
	             }else {
	            	 continue;
	             }
	             
	             
	             for(int j=1;j<=lastRow2;j++){
	            	 
	            	 String sheet2cell3data = null;
	    	         String sheet2cell4data = null;
		             XSSFRow row2 = sheet2.getRow(j);
		             XSSFCell cell3 = row2.getCell(1);
		             XSSFCell cell4 = row2.getCell(2) ;  
		             
		             if(cell3 != null){
		            	 sheet2cell3data = cell3.getStringCellValue();
		            	 if(cell4 != null){
		            		 sheet2cell4data = cell4.getStringCellValue();
		            		 System.out.println("the value of j " +j);
		            		 System.out.println("data of sheet2 is  ->" + sheet2cell3data +" " + sheet2cell4data);
		            	 }
		             }
	              
		             System.out.println("jvl:" + j + "lastRow" + lastRow2);
	             if(sheet1cell1data.equalsIgnoreCase(sheet2cell3data) && sheet1cell2data.equalsIgnoreCase(sheet2cell4data)) {
	            	 
	            	
	                System.out.println("Row "+i+" - Equal");
	                 
	                 resultdata[0]= sheet1cell1data;
		             	
		             resultdata[1]= sheet1cell2data;
		             resultdata[2]= sheet2cell3data;
		             	
		             resultdata[3]= sheet2cell4data;
		             
		            resultdata[4]= "";
		           
		             resultdata[6]= "PASS";
		             
		             status = "PASS";
		             
		             SetCellData1(excelpath_update, sheetname,resultdata, excelrow,status);
						excelrow++;
	                 
	                 break;
	             } 
	             if (j==lastRow2) {
	            	 System.out.println("entereing fail loop");
	            	 System.out.println("Exp1:" + sheet1cell1data + "Exp2:" + sheet1cell2data );
        	       	 
		            	 resultdata[0]= sheet1cell1data;
			             	
			             resultdata[1]= sheet1cell2data;
			             resultdata[2]= "";
			             	
			             resultdata[3]= "";
			             resultdata[4]= sheet1cell1data +":"+sheet1cell2data;
			             resultdata[5]="";
			             resultdata[6]= "FAIL";
			             
			             status = "FAIL";
			             
			             SetCellData1(excelpath_update, sheetname,resultdata, excelrow,status);
							excelrow++;
							equalSheets = false; 
							sb.append(sheet1cell1data +":"+sheet1cell2data).append(",");
		                 System.out.println("Row "+i+" - Not Equal");
		                 System.out.println("Missing tags is actual " +sb);
		                 
		                 missactual = sb.toString();
	            	 }
	                 
	             } 
	             System.out.println("the value of equalsheets is " + equalSheets);
	             
	         }
	         
	         dexcelrow = excelrow;
	         System.out.println("reverse comparison ");
	         System.out.println("the value of dexcelrow is " + dexcelrow);
	         for(int k=1; k<= lastRow2; k++) {
	        	 
	        	 String sheet2cell3newdata = null;
    	         String sheet2cell4newdata = null;
	             XSSFRow row22 = sheet2.getRow(k);
	             XSSFCell cell32 = row22.getCell(1);
	             XSSFCell cell42 = row22.getCell(2) ; 
	             
		           
	             System.out.println("\n\nComparing Row sheet 2"+k);
	           
	             if(cell32 != null){
	            	 sheet2cell3newdata = cell32.getStringCellValue();
	            	 if(cell42 != null){
	            		 sheet2cell4newdata = cell42.getStringCellValue();
	            		 
	            		 System.out.println("data of sheet 2 is " + sheet2cell3newdata + sheet2cell4newdata);
	            	 }else{
	            		 continue;
	            	 }
	             }else {
	            	 continue;
	             }
	             
	             
	             for(int m=1;m<=lastRow1;m++){
	            	 

		             XSSFRow row11 = sheet1.getRow(m);
		             XSSFCell cell1m = row11.getCell(0);
		             XSSFCell cell2m = row11.getCell(1);
		             
		             String sheet1cell1mnewdata = null;
			         String sheet1cell2mnewdata = null;
			         
		              
		             
		             if(cell1m != null){
		            	 sheet1cell1mnewdata = cell1m.getStringCellValue();
		            	 if(cell2m != null){
		            		 sheet1cell2mnewdata = cell2m.getStringCellValue();
		            		 System.out.println("data of sheet 1 is  ->" + sheet1cell1mnewdata +" " + sheet1cell2mnewdata);
		            	 }else{
		            		 continue;
		            	 }
		             }else{
		            	 continue;
		             }
	              
	            
	             if(sheet2cell3newdata.equalsIgnoreCase(sheet1cell1mnewdata) && sheet2cell4newdata.equalsIgnoreCase(sheet1cell2mnewdata)) {
	            	 
	            	
	                 System.out.println("Row "+k+" - Equal");
	                 
	                /* resultdata[0]= "";
		             resultdata[1]= "";
		             resultdata[2]= sheet2cell3newdata;
		             resultdata[3]= sheet2cell4newdata;
		             resultdata[4]= "";
		             resultdata[5]= "";
		             resultdata[6]= "PASS";
		             
		             SetCellData1(excelpath_update, sheetname,resultdata, dexcelrow);
						dexcelrow++;*/
	                 
	                 break;
	             } if (m==lastRow1) {
	            		 	equalSheets2 = false;
	            		 
	            		 	resultdata[0]= "";
				             resultdata[1]= "";	
			             resultdata[2]= sheet2cell3newdata;
			             resultdata[3]= sheet2cell4newdata;
			             resultdata[4]= "";
			             resultdata[5]= sheet2cell3newdata +":"+sheet2cell4newdata;
			             resultdata[6]= "FAIL";
			             status = "FAIL";
			             SetCellData1(excelpath_update, sheetname,resultdata, dexcelrow,status);
							dexcelrow++;
		                 System.out.println("Row "+k+" - Not Equal");
		                 
		                 sb2.append(sheet2cell3newdata +":"+sheet2cell4newdata).append(",");
		                 System.out.println("Missing tags is expected " +sb2);
		                 missexpected = sb2.toString();
	            	 }
	                 
	             } 
	             System.out.println("the value of equalsheets 2 is " + equalSheets2);
	         }
	    	         
	         
	 }catch(Exception e){
		 e.printStackTrace();
	 }
	  
	  if(equalSheets == false || equalSheets2 == false){
		  finalresult = false;
	  }
	 return finalresult;
	 }

	
 
 
 public static void SetCellData1(String filePath, String sheetName, String[] result, int row, String status) throws Exception {

		FileInputStream ExcelFile = new FileInputStream(filePath);

		XSSFWorkbook wb = new XSSFWorkbook(ExcelFile);

		Sheet resultSheet = wb.getSheet(sheetName);
		XSSFCellStyle style = wb.createCellStyle();

		XSSFFont font= wb.createFont();
	    font.setFontHeightInPoints((short)10);
	    font.setFontName("Arial");
	    font.setColor(IndexedColors.BLACK.getIndex());
	    font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	   
	    style.setFont(font);
		
		XSSFCellStyle style_1 = wb.createCellStyle();
		
		style_1.setFillForegroundColor(IndexedColors.RED.getIndex());
		style_1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		
		XSSFCellStyle style_2 = wb.createCellStyle();
		
		style_2.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
		style_2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		if (row == 1) {
			Row row0 = resultSheet.createRow(0);
			row0.createCell(0).setCellValue("S.No");
			row0.getCell(0).setCellStyle(style);
			row0.createCell(1).setCellValue("Event Category Refrence sheet");
			row0.getCell(1).setCellStyle(style);
			row0.createCell(2).setCellValue("Event Action Refrence sheet");
			row0.getCell(2).setCellStyle(style);
			row0.createCell(3).setCellValue("Event Category GA sheet");
			row0.getCell(3).setCellStyle(style);
			row0.createCell(4).setCellValue("Event Action GA sheet");
			row0.getCell(4).setCellStyle(style);
			row0.createCell(5).setCellValue("Missing in Actual");
			row0.getCell(5).setCellStyle(style);
			row0.createCell(6).setCellValue("Missing in Expected");
			row0.getCell(6).setCellStyle(style);
			row0.createCell(7).setCellValue("Status");
			row0.getCell(7).setCellStyle(style);
		}
		Row row2 = resultSheet.createRow(row);
		row2.createCell(0).setCellValue(row);
		
		// TODO give max i length as result.length
		for (int i = 0; i < result.length; i++) {

			row2.createCell(i + 1).setCellValue(result[i]);	
			
			if(status=="PASS" && i == 6){
			row2.getCell(i+1).setCellStyle(style_2);
			}else if (status=="FAIL" && i == 6){
				row2.getCell(i+1).setCellStyle(style_1);
			}
		}
		
		
		
		

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		//wb.close();

	}

	
	
	public static void SetExcelFile(String path, String sheetName) throws Exception {

		try {
			// Opening Excel File
			XSSFWorkbook wb = new XSSFWorkbook();
			
			XSSFSheet sh = wb.createSheet(sheetName);
			
			 sh = wb.getSheet(sheetName);

			
			FileOutputStream fileOut = new FileOutputStream(path);
			wb.write(fileOut);
      fileOut.close();
     // wb.close();
      System.out.println("Your excel file has been generated!");

		} catch (Exception e) {
			throw (e);
		}

	}
	
	public static void main(String[] args) throws FileNotFoundException{
		String Datareport[]= null;
		Datareport= reportsheet("newtest");
		 
		 System.out.println(Datareport[0]);
		 System.out.println("the file " +Datareport[1]);
		
    }

}
