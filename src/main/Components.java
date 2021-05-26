package main;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;

public class Components {

	public static WebDriver webDriverInstance;
	
	public Components(WebDriver driver) {
		Components.webDriverInstance = driver;			
	}
	
	static ResourceBundle res = ResourceBundle.getBundle("test.setup");
	
	/**
	 * Method to get values from property file
	 * @param key
	 * @return value
	 */
	public static String getString(String key) {
		String value = res.getString(key);
		return value;		
	}
	
	/**
	 * Method to wait for element displayed using pool and sleep
	 * @param locator
	 * @return
	 * @throws InterruptedException
	 */
	public boolean waitForElementDisplayed(By locator) throws InterruptedException {
		boolean flag=false;
		for(int i=0; i<5; i++) {
			if(flag) {
				break;
			}
			if(doesElementExists(locator)) {
				flag=true;
			}else {
				TimeUnit.SECONDS.sleep(3);
			}
		}
		return flag;
	}
	
	/**
	 * Verifying is element displayed
	 * @param locator
	 * @return
	 */
	public static boolean doesElementExists(By locator) {
		boolean flag=false;
		try{
			if(webDriverInstance.findElement(locator).isEnabled()){
				flag = true;			
			}
		}catch(Exception e){
			System.out.println("Exception caught:"+e);
		}
		return flag;
	}
	
	/**
	 * Take Screenshot
	 */
	public static File takeScreenshot(String methodName,Object driver){
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/target/surefire-reports";
            destFile = new File((String) reportDirectory+"/failure_screenshots/"+methodName+"_"+formater.format(calendar.getTime())+".png");
            FileUtils.copyFile(scrFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
		return destFile;
	}
	
	
}
