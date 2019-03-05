package CookieExample;

import java.io.BufferedWriter;		
import java.io.File;		
import java.io.FileWriter;
import java.util.Set;
import org.openqa.selenium.By;		
import org.openqa.selenium.WebDriver;		
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Cookie;		

public class cookieRead{	
    

    public static void main(String[] args)		
    {
    	WebDriver driver;	
        System.setProperty("webdriver.chrome.driver","C:\\eclipse\\chromedriver.exe");					
		driver=new ChromeDriver();        
		driver.get("https://health.policybazaar.com/?utm_content=home_v4");

       				
        // Input Email id and Password If you are already Register		
        driver.findElement(By.name("name")).sendKeys("abc123");							
        driver.findElement(By.name("mobile")).sendKeys("9777777777");							
        driver.findElement(By.xpath("//*[@id='grid']/div/div/div/div/div[1]/div/div[2]/div/div/div/div[2]/div[2]/div[1]/div/form/div[4]/button/div")).click();					
        		
        // create file named Cookies to store Login Information		
        File file = new File("Cookies.data");							
        try		
        {	  
            // Delete old file if exists
			file.delete();		
            file.createNewFile();			
            FileWriter fileWrite = new FileWriter(file);							
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);							
            // loop for getting the cookie information 		
            	
            // loop for getting the cookie information 		
            for(Cookie ck : driver.manage().getCookies())							
            {			
                Bwrite.write((ck.getName()+";"+ck.getValue()+";"+ck.getDomain()+";"+ck.getPath()+";"+ck.getExpiry()+";"+ck.isSecure()));																									
                Bwrite.newLine();             
            }			
            Bwrite.close();			
            fileWrite.close();	
            
        }
        catch(Exception ex)					
        {		
            ex.printStackTrace();			
        }		
    }		
}