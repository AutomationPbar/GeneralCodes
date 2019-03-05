package CookieExample;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

public class CookieReadWrite {
    WebDriver driver;

    @Test
    public void login_state_should_be_restored() throws Exception {
    	System.setProperty("webdriver.chrome.driver","C:\\eclipse\\chromedriver.exe");					
        driver=new ChromeDriver();		
        
        driver.get("https://health.policybazaar.com/?utm_content=home_v4");

			
        // Input Email id and Password If you are already Register		
        driver.findElement(By.name("name")).sendKeys("abc123");							
        driver.findElement(By.name("mobile")).sendKeys("9777777777");							
        driver.findElement(By.xpath("//*[@id='grid']/div/div/div/div/div[1]/div/div[2]/div/div/div/div[2]/div[2]/div[1]/div/form/div[4]/button/div")).click();					
       
        Thread.sleep(2500);
        Assert.assertTrue(
                driver.findElement(By.name("frmSelectMember")).isDisplayed());

        //Before closing the browser, read the cookies
        Set<Cookie> allCookies = driver.manage().getCookies();

        driver.close();

        //open a new browser window
        driver = new ChromeDriver();
        
        driver.get("https://health.policybazaar.com/?utm_content=home_v4");

        //restore all cookies from previous session
        for(Cookie cookie : allCookies) {
        	
        	System.out.println("the cookie data " + cookie);
            driver.manage().addCookie(cookie);
        }

                
        driver.navigate().refresh();

//Login page should not be disaplyed
        Assert.assertTrue(
                driver.findElement(By.name("frmSelectMember")).isDisplayed());

        //driver.close();
    }
}