package main;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

public class StartUP {

	public WebDriver driver; 
		
	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Executing Before Suite");
	}
	
	@BeforeTest
	public void beforeTest() {
			String chromeexe = System.getProperty("user.dir")+ "\\chromedriver.exe";
			
			System.setProperty("webdriver.chrome.driver", chromeexe);
			
			driver = new ChromeDriver();						  
			driver.manage().window().maximize();			
			driver.get(Components.getString("url"));
	}
	
	@BeforeMethod
	public Object[] beforeMethod(Method m) throws Exception {
		System.out.println("Executing Before Method");
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
		System.out.println("Executing After Method");
	}
	
	@AfterTest
	public void afterTest() {
		System.out.println("Executing After Test");
		driver.close();
		driver.quit();
	}
	
	@AfterSuite
	public void afterSuite() {
		System.out.println("Executing After Suite");
	}
	
}
