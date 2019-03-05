package jsonProcessing;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 
  
public class JSONReadExample  {

	static String filepath = "C:\\GoogleAnalyticsProject\\GA\\GA_Report.xlsx";
	static String gasheet = "reportsheet";
	static String resultdata[] = new String[4];
	static String action = "";
	static String category = "";
	static String label = "";
	static int excelrow = 1;

    public static void main(String[] args) throws Exception  
    { 
    	try{
        // parsing file "JSONExample.json" 
    	
    	SetExcelFile(filepath, gasheet);
        Object obj = new JSONParser().parse(new FileReader("C:\\GoogleAnalyticsProject\\GA\\user-report-export.json")); 
          
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
        
        JSONArray arr1 = (JSONArray) jo.get("dates");
        
        int arraysize = arr1.size();
        
        System.out.println("the array size is " + arraysize);
        
        for (int i = 0;i<arraysize;i++){
        	
        	 arr1.get(i);
        	 
        	// System.out.println("the object  is " + arr1.get(i).toString());
        	 
        	 JSONObject jobj1 = (JSONObject)arr1.get(i);
        	 
        	 JSONArray arr2 = (JSONArray) jobj1.get("sessions");
        	        	 
        	 int arraysize2 = arr2.size();
             
             System.out.println("the second array size is " + arraysize2);
             
             for (int j = 0;j<arraysize2;j++){
            	 
            	// System.out.println("the object of second array is " + arr2.get(j).toString());
            	 
            	 JSONObject jobj2 = (JSONObject)arr2.get(j);
            	 
            	 JSONArray arr3 = (JSONArray) jobj2.get("activities");
            	 int arraysize3 = arr3.size();
                 
                 System.out.println("the third array size is " + arraysize3);
                 
                 for (int k = 0;k<arraysize3;k++){
                	 
                	 //System.out.println("the object of third array is " + arr3.get(k).toString());
                	  
                	  JSONObject jobj3 = (JSONObject)arr3.get(k);
                 	 
                 	 JSONArray arr4 = (JSONArray) jobj3.get("details");
                 	 int arraysize4 = arr4.size();
                 	  System.out.println("the fourth array size is " + arraysize4);
                      
                      for (int m = 0;m<arraysize4;m++){
                    	  
                    	 // System.out.println("the object of 4the array is " + arr4.get(m).toString());
                    	  
                    	  JSONObject jobj4 = (JSONObject)arr4.get(m);
                    	  System.out.println("the size of json object in array is " +jobj4.size());
                    	  
                    	  JSONArray arr5 = (JSONArray) jobj4.get("Event category");
                    	  JSONArray arr6 = (JSONArray) jobj4.get("Event action");
                    	  JSONArray arr7 = (JSONArray) jobj4.get("Event label");
                    	  try{
                    	  System.out.println("the array 5 data is " + arr5.get(0).toString());
                    	  category = arr5.get(0).toString();
                    	  
                    	  } catch(Exception e){
                    		  
                    		  System.out.println("the category string is null");
                    		  category = "NoData";
                    	  }
                    	  
                    	  try{
                        	  System.out.println("the array 6 data is " + arr6.get(0).toString());
                        	  action = arr6.get(0).toString();
                        	  
                        	  } catch(Exception e){
                        		  
                        		  System.out.println("the action string is null");
                        		  action = "NoData";
                        	  }
                    	  
                    	  try{
                        	  System.out.println("the array 7 data is " + arr7.get(0).toString());
                        	  label = arr7.get(0).toString();
                        	  
                        	  } catch(Exception e){
                        		  
                        		  System.out.println("the label string is null");
                        		  label = "NoData";
                        	  }
                    	  	
                    		resultdata[0] = category;
                    		resultdata[1] = action;
                    		resultdata[2] = label;
                      	 
                      	   SetCellData1(filepath, gasheet, resultdata, excelrow, "");
                      	   excelrow++;
                      }
                	 
                 }
                 
               
                 
             }
        	 
        }
    }catch(Exception e){
    	e.printStackTrace();
    }
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
    
    public static void SetCellData1(String filePath, String sheetName, String[] result, int row,String status) throws Exception {

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


		if (row == 1) {
			
			Row row0 = resultSheet.createRow(0);
			row0.createCell(0).setCellValue("S No");
			row0.getCell(0).setCellStyle(style);
			row0.createCell(1).setCellValue("Event Category");
			row0.getCell(1).setCellStyle(style);
			row0.createCell(2).setCellValue("Event Action");
			row0.getCell(2).setCellStyle(style);
			row0.createCell(3).setCellValue("Event Label");
			row0.getCell(3).setCellStyle(style);
			
		}
		Row row2 = resultSheet.createRow(row);
		row2.createCell(0).setCellValue(row);
		
		// TODO give max i length as result.length
		for (int i = 0; i < result.length; i++) {

			row2.createCell(i + 1).setCellValue(result[i]);
			
		}

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		//wb.close();

	}

	
} 