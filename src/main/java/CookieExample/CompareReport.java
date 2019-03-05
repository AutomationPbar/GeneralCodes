package CookieExample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CompareReport {
	
	static String excelpath_update = "C:\\GoogleAnalyticsProject\\GA\\result_.xlsx";
	static String sheetname = "Report";
	
	static int excelrow = 1;
	int dexcelrow =1;
	XSSFSheet sheet;
	XSSFSheet modelsheet;
	XSSFRow row = null;
	XSSFWorkbook workbook;
	static String resultdata[] = new String[8];
	static boolean status = false;
	static String reportfilename = "";

 public static String[] reportsheet(String reportfile) {
	 
	 String returnvalue[] = new String[2];
        try {
        	
        	SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
			Date datedd = new Date();
			System.out.println(formatter.format(datedd));
			String localDate11 = formatter.format(datedd).toString();
			excelpath_update = "C:\\GoogleAnalyticsProject\\GA\\FinalResult_"+ reportfile+".xlsx";
			SetExcelFile(excelpath_update, sheetname);
			
			reportfilename = "FinalResult_"+ reportfile+".xlsx";
            // get input excel files
         System.out.println("Welcome");
            FileInputStream excellFile1 = new FileInputStream(new File("C:\\GoogleAnalyticsProject\\GA\\"+reportfile+".xlsx"));
            FileInputStream excellFile2 = new FileInputStream(new File("C:\\GoogleAnalyticsProject\\compare\\refrence.xlsx"));

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook1 = new XSSFWorkbook(excellFile1);
            XSSFWorkbook workbook2 = new XSSFWorkbook(excellFile2);
            
            System.out.println("the number of sheets" + workbook1.getNumberOfSheets());

            // Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook1.getSheet("details");
            XSSFSheet sheet2 = workbook2.getSheetAt(5);

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
        returnvalue[1] = reportfile;
        return returnvalue;
    }

 private static boolean compareTwoSheets(XSSFSheet sheet1, XSSFSheet sheet2) throws Exception {
  // TODO Auto-generated method stub
   int firstRow1 = sheet1.getFirstRowNum();
         int lastRow1 = sheet1.getLastRowNum();
         boolean equalSheets = true;
         for(int i=1; i <= lastRow1; i++) {
           
             System.out.println("\n\nComparing Row "+i);
           
             XSSFRow row1 = sheet1.getRow(i);
             XSSFRow row2 = sheet2.getRow(i);
             
            
             if(!compareTwoRows(row1, row2)) {
                 equalSheets = false;
                 System.err.println("Row "+i+" - Not Equal");
                 //break;
             } else {
                 System.out.println("Row "+i+" - Equal");
             }
         }
         return equalSheets;
 }

 private static boolean compareTwoRows(XSSFRow row1, XSSFRow row2) throws Exception {
  // TODO Auto-generated method stub
   if((row1 == null) && (row2 == null)) {
             return true;
         } else if((row1 == null) || (row2 == null)) {
             return false;
         }
       
         int firstCell1 = row1.getFirstCellNum();
         int lastCell1 = row1.getLastCellNum();
         boolean equalRows = true;
       
         // Compare all cells in a row
         for(int i=1; i <2; i++) {
             XSSFCell cell1 = row1.getCell(6);
             XSSFCell cell2 = row2.getCell(6);
             
             
             if(!compareTwoCells(cell1, cell2)) {
                 equalRows = false;
                 System.err.println("Cell "+6+" - NOt Equal");
                
                 try{
                resultdata[0]= "FAIL Event Category";
             	resultdata[1]= row1.getCell(6).getStringCellValue();
             	
             	resultdata[3]= row2.getCell(6).getStringCellValue();
             	
             	row1.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
               	row2.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
             	resultdata[5]= row1.getCell(11).getStringCellValue();
             	resultdata[6]= row2.getCell(11).getStringCellValue();
             	
                 }catch(Exception e){
                	 
                	 if (cell1 == null) {
                      
                	 resultdata[0]= "FAIL Event Category";
                  	resultdata[1]=  "";
                  	
                  	resultdata[3]= row2.getCell(6).getStringCellValue();
                  	
                  	row2.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                 	resultdata[6]= row2.getCell(11).getStringCellValue();
                 }else if(cell2 == null){
                	 
                	 resultdata[0]= "FAIL Event Category";
                   	resultdata[1]=  row1.getCell(6).getStringCellValue();
                     resultdata[3]= "";
                   	row1.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                  
                   	resultdata[5]= row1.getCell(11).getStringCellValue();
                 	
                 }
                 }
             	SetCellData1(excelpath_update, sheetname,resultdata, excelrow);
					excelrow++;
                
                 //break;
             } else {
            	 equalRows = true;
                 System.out.println("Cell "+6+" - Equal");
                 
                 
                 resultdata[0]= "PASS";
             	resultdata[1]= "";
             	resultdata[2]= "";
             	resultdata[3]= "";
             	resultdata[4]= "";
             	resultdata[5]= "";
             	resultdata[6]= "";
					
             	SetCellData1(excelpath_update, sheetname,resultdata, excelrow);
					excelrow++;
             }
         }   
             for(int j=1; j <2; j++) {
                
                 XSSFCell cell3 = row1.getCell(9);
                 XSSFCell cell4 = row2.getCell(9);
                 
                 
                 if(!compareTwoCells(cell3, cell4)) {
                     equalRows = false;
                     System.err.println("Cell "+9+" - NOt Equal");
                  
                     try{
                     resultdata[0]= "FAIL Event Action";
                 	
                 	resultdata[2]= row1.getCell(9).getStringCellValue();
                 	
                 	resultdata[4]= row2.getCell(9).getStringCellValue();
                 	row1.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                   	row2.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                 	resultdata[5]= row1.getCell(11).getStringCellValue();
                 	resultdata[6]= row2.getCell(11).getStringCellValue();
                 	
                     }catch(Exception e){
                    	 
                    	 if (cell3 == null) {
                             
                        	 resultdata[0]= "FAIL Event Action";
                          	resultdata[1]=  "";
                          	
                          	resultdata[3]= row2.getCell(9).getStringCellValue();
                          	
                           	row2.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                        
                         	resultdata[6]= row2.getCell(11).getStringCellValue();
                         }else if(cell4 == null){
                        	 
                        	 resultdata[0]= "FAIL Event Action";
                           	resultdata[1]=  row1.getCell(9).getStringCellValue();
                           	
                           	resultdata[3]= "";
                           	
                           	row1.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                           	
                           	resultdata[5]= row1.getCell(11).getStringCellValue();
                         	
                         }
                    	 
                     }
    					
                 	SetCellData1(excelpath_update, sheetname,resultdata, excelrow);
    					excelrow++;
                     //break;
                 } else {
                	 equalRows = true;
                     System.out.println("Cell "+9+" - Equal");
                     
                     resultdata[0]= "PASS";
                 	resultdata[1]= "";
                 	resultdata[2]= "";
                 	resultdata[3]= "";
                 	resultdata[4]= "";
                 	resultdata[5]= "";
                 	resultdata[6]= "";	
                 	SetCellData1(excelpath_update, sheetname,resultdata, excelrow);
    					excelrow++;
                 }

             
         }
         return equalRows;
 }

 private static boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2) {
   if((cell1 == null) && (cell2 == null)) {
             return true;
         } else if((cell1 == null) || (cell2 == null)) {
             return false;
         }
       
         boolean equalCells = false;
         int type1 = cell1.getCellType();
         int type2 = cell2.getCellType();
        
         
        if (type1 == type2) {
              if (cell1.getCellStyle().equals(cell2.getCellStyle())) {
                 // Compare cells based on its type
                 switch (cell1.getCellType()) {
                 case HSSFCell.CELL_TYPE_FORMULA:
                     if (cell1.getCellFormula().equals(cell2.getCellFormula())) {
                         equalCells = true;
                     }
                     break;
                 case HSSFCell.CELL_TYPE_NUMERIC:
                     if (cell1.getNumericCellValue() == cell2
                             .getNumericCellValue()) {
                         equalCells = true;
                     }
                     break;
                 case HSSFCell.CELL_TYPE_STRING:
                     if (cell1.getStringCellValue().equals(cell2
                             .getStringCellValue())) {
                         equalCells = true;
                     }
                     break;
                 case HSSFCell.CELL_TYPE_BLANK:
                     if (cell2.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                         equalCells = true;
                     }
                     break;
                 case HSSFCell.CELL_TYPE_BOOLEAN:
                     if (cell1.getBooleanCellValue() == cell2
                             .getBooleanCellValue()) {
                         equalCells = true;
                     }
                     break;
                 case HSSFCell.CELL_TYPE_ERROR:
                     if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
                         equalCells = true;
                     }
                     break;
                 default:
                     if (cell1.getStringCellValue().equals(
                             cell2.getStringCellValue())) {
                         equalCells = true;
                     }
                     break;
                 }
             } else {
                 return false;
             }
         } else {
        	 
        	 System.out.println("cell type mismatch ");
             return false;
         }
         return equalCells;
 }
 
 
 public static void SetCellData1(String filePath, String sheetName, String[] result, int row) throws Exception {

		FileInputStream ExcelFile = new FileInputStream(filePath);

		XSSFWorkbook wb = new XSSFWorkbook(ExcelFile);

		Sheet resultSheet = wb.getSheet(sheetName);


		if (row == 1) {
			Row row0 = resultSheet.createRow(0);


			row0.createCell(1).setCellValue("Status");
			row0.createCell(2).setCellValue("Event Category Refrence sheet");
			row0.createCell(3).setCellValue("Event Action Refrence sheet");
			row0.createCell(4).setCellValue("Event Category GA sheet");
			row0.createCell(5).setCellValue("Event Action GA sheet");	
			row0.createCell(6).setCellValue("Label Refrence sheet");
			row0.createCell(7).setCellValue("Label GA sheet");	
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



}