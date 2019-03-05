package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


public class ExcelReport {
	
	static String excelpath_update = "C:\\Testcases\\compare\\result_.xlsx";
	static String sheetname = "Report";

	int rowCount;
	static int excelrow = 1;
	int dexcelrow =1;
	XSSFSheet sheet;
	XSSFSheet modelsheet;
	XSSFRow row = null;
	XSSFWorkbook workbook;
	static String resultdata[] = new String[6];
	
	String nodata ="No Data Found";
	
	List<String> arrName = new ArrayList<String>();


	//@BeforeMethod

	public void setup() {

		try {	
			System.setProperty("webdriver.chrome.driver", "C:\\eclipse\\chromedriver.exe");

			SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
			Date datedd = new Date();
			System.out.println(formatter.format(datedd));
			String localDate11 = formatter.format(datedd).toString();
			excelpath_update = "C:\\Testcases\\compare\\result_" + localDate11 + ".xlsx";
			SetExcelFile(excelpath_update, sheetname);

			
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@Test(priority = 1)
	public static String getDiesel() throws Exception {
		
		
		try {	
			System.setProperty("webdriver.chrome.driver", "C:\\eclipse\\chromedriver.exe");

			SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
			Date datedd = new Date();
			System.out.println(formatter.format(datedd));
			String localDate11 = formatter.format(datedd).toString();
			excelpath_update = "C:\\Testcases\\compare\\result_" + localDate11 + ".xlsx";
			SetExcelFile(excelpath_update, sheetname);

			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		try {
            // get input excel files
         System.out.println("Welcome");
            FileInputStream excellFile1 = new FileInputStream(new File("C:\\Testcases\\compare\\refrence.xlsx"));
            FileInputStream excellFile2 = new FileInputStream(new File("C:\\Testcases\\compare\\refrence1.xlsx"));
          

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook1 = new XSSFWorkbook(excellFile1);
            XSSFWorkbook workbook2 = new XSSFWorkbook(excellFile2);
            

            // Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook1.getSheetAt(5);
            XSSFSheet sheet2 = workbook2.getSheetAt(5);
                    
            int firstRow1 = sheet1.getFirstRowNum();
            int lastRow1 = sheet1.getLastRowNum();
            boolean equalSheets = true;
            for(int i=1; i <= lastRow1; i++) {
              
                System.out.println("\n\nComparing Row "+i);
              
                XSSFRow row1 = sheet1.getRow(i);
                XSSFRow row2 = sheet2.getRow(i);
               
                Cell cell = null;
                boolean a = false;
                boolean b = false;
                
                if(row1.getCell(6).getStringCellValue().equalsIgnoreCase(row2.getCell(6).getStringCellValue())){
                	a = true;
                	
                	resultdata[0]= "PASS";
                	resultdata[1]= "";
                	resultdata[2]= "";
                	resultdata[3]= "";
                	resultdata[4]= "";
					
                	SetCellData1(excelpath_update, sheetname,resultdata, excelrow);
					excelrow++;
                	
                }else{
                	
                	resultdata[0]= "FAIL";
                	resultdata[1]= row1.getCell(6).getStringCellValue();
                	resultdata[2]= row1.getCell(9).getStringCellValue();
                	resultdata[3]= row2.getCell(6).getStringCellValue();
                	resultdata[4]= row2.getCell(9).getStringCellValue();
					
                	SetCellData1(excelpath_update, sheetname,resultdata, excelrow);
					excelrow++;
					
                	 System.err.println("Row "+i+" - Not Equal");
                }
                
                if(row1.getCell(9).equals(row2.getCell(9))){
                	
                	b = true;
                	
                	resultdata[0]= "PASS";
                	resultdata[1]= "";
                	resultdata[2]= "";
                	resultdata[3]= "";
                	resultdata[4]= "";
					
                	SetCellData1(excelpath_update, sheetname,resultdata, excelrow);
					excelrow++;
                	
                }else{
                	
                	resultdata[0]= "FAIL";
                	resultdata[1]= row1.getCell(6).getStringCellValue();
                	resultdata[2]= row1.getCell(9).getStringCellValue();
                	resultdata[3]= row2.getCell(6).getStringCellValue();
                	resultdata[4]= row2.getCell(9).getStringCellValue();
					
                	SetCellData1(excelpath_update, sheetname,resultdata, excelrow);
					excelrow++;
					
                	 System.err.println("Row "+i+" - Not Equal");
                	
                }
                
            }
            
            excellFile1.close();
            excellFile2.close();
            
	}catch(Exception e){
		e.printStackTrace();
	}
		
		return excelpath_update;
	}

	
	@AfterTest
	public void teardown() {

		try {
			

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void SetInputData(String filePath, String sheetName, int row, List<String> data) throws Exception {

		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet inputSheet = workbook.getSheetAt(0);

		// Retrieve the row and check for null
		XSSFRow row0 = (XSSFRow) inputSheet.getRow(row);
		Cell cell = null;
		if (row0 == null) {
			row0 = (XSSFRow) inputSheet.createRow(row);
		}
		
		Row row1 = inputSheet.createRow(0);


		row1.createCell(0).setCellValue("Status");
		row1.createCell(1).setCellValue("Event category Input");
		row1.createCell(2).setCellValue("Event action Input");
		row1.createCell(3).setCellValue("Event Category GA");
		row1.createCell(4).setCellValue("Event action GA");	
		// Update the value of cell
		for(int i=0;i<data.size();i++){
		cell = row0.getCell(i);
		if (cell == null) {
			cell = row0.createCell(i);
		}
		cell.setCellValue(data.get(i));
		}
		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		//workbook.close();
	}
	
	
	public static void SetCellData1(String filePath, String sheetName, String[] result, int row) throws Exception {

		FileInputStream ExcelFile = new FileInputStream(filePath);

		XSSFWorkbook wb = new XSSFWorkbook(ExcelFile);

		Sheet resultSheet = wb.getSheet(sheetName);


		if (row == 1) {
			Row row0 = resultSheet.createRow(0);


			row0.createCell(1).setCellValue("Status");
			row0.createCell(2).setCellValue("Event category Input");
			row0.createCell(3).setCellValue("Event action Input");
			row0.createCell(4).setCellValue("Event Category GA");
			row0.createCell(5).setCellValue("Event action GA");	
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
