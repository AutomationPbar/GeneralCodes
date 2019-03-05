package CookieExample;

import java.io.BufferedReader;		
import java.io.File;		
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;		
import org.openqa.selenium.Cookie;		
import org.openqa.selenium.WebDriver;		
import org.openqa.selenium.chrome.ChromeDriver;

public class CookieWrite		
{		
  
	public static void main(String[] args){ 
    	WebDriver driver;     
       	System.setProperty("webdriver.chrome.driver","C:\\eclipse\\chromedriver.exe");					
        driver=new ChromeDriver();	
        driver.get("https://health.policybazaar.com");	
    try{			
     
        File file = new File("Cookies.data");							
        FileReader fileReader = new FileReader(file);							
        BufferedReader Buffreader = new BufferedReader(fileReader);							
        String strline;			
        while((strline=Buffreader.readLine())!=null){									
        StringTokenizer token = new StringTokenizer(strline,";");									
        while(token.hasMoreTokens()){					
        String name = token.nextToken();					
        String value = token.nextToken();					
        String domain = token.nextToken();					
        String path = token.nextToken();					
        Date expiry = null;					
        	
        String val;			
        if(!(val=token.nextToken()).equals("null"))
		{	
        	SimpleDateFormat sdf=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        	expiry = sdf.parse(val);			
        	//expiry = java.text.DateFormat.getDateInstance().parse(val);
        }		
        Boolean isSecure = new Boolean(token.nextToken()).								
        booleanValue();		
        Cookie ck = new Cookie(name,value,domain,path,expiry,isSecure);			
        System.out.println(ck);
        
        //driver.manage().addCookie(ck); // This will add the stored cookie to your current session					
        }		
        }		
        }catch(Exception ex){					
        ex.printStackTrace();			
        }		
       driver.get("https://health.policybazaar.com/?utm_content=home_v4");					
}	
	}	