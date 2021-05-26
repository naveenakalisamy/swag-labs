package test;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import main.ExcelFunctions;
import main.Login;
import main.StartUP;

public class TestCase extends StartUP{
	
	Login loginPage;
	Object[] array;
	
	@Override
	@BeforeMethod
	public Object[] beforeMethod(Method m) throws Exception {
		loginPage = new Login(driver);
		String testDataPath = System.getProperty("user.dir")+ "\\TestData.xlsx";		
		array = ExcelFunctions.getTableArray(testDataPath,m.getName());
		return array;
	}
	
	/**
	 * TESTCASE 1 -  User must provide credentials and Login must be successfull
	 * @throws Exception 
	 */
	@Test(priority=1)
	@Parameters({"userName","passWord"})
	public void validateLoginWithCredentials(String userName, String passWord) throws Exception {		
		String errorMessage = loginPage.loginThePage(userName,passWord);		
		if(errorMessage==null) {
			System.out.println("Successfully logged in");
		}else {
			Assert.fail("Error Found while loggin in");
		}						
	}
	
	/**
	 * TESTCASE 2 -  User must provide credentials and Login must be successfull
	 * @throws InterruptedException
	 */
	@Test(priority=2)
	public void validateLoginWithEmptyFields() throws InterruptedException {
		driver.navigate().back();
		String errorMessage = loginPage.loginThePage(array[2].toString(), "");
		if(errorMessage.equalsIgnoreCase("Epic sadface: Password is required")) {
			System.out.println("Error Message displayed correctly for empty user");
		}else {
			Assert.fail("Error Found while loggin in");
		}
		boolean flag = loginPage.closeErrorMsg();
		if(!flag){
			Assert.fail("Error message appeared even after clicking close icon");
		}
		errorMessage = loginPage.loginThePage("", array[3].toString());
		if(errorMessage.equalsIgnoreCase("Epic sadface: Username is required")) {
			System.out.println("Successfully logged in");
		}else {
			Assert.fail("Error Found while loggin in");
		}
		flag = loginPage.closeErrorMsg();
		if(!flag){
			Assert.fail("Error message appeared even after clicking close icon");
		}
	}

	/**
	 * TESTCASE 3 - Validate "locked_out_user" user
	 * @throws InterruptedException
	 */
	@Test(priority=3)
	public void validateLockedOutUser() throws InterruptedException {	
		String errorMessage = loginPage.loginThePage(array[2].toString(), array[3].toString());
		if(errorMessage.equals("Epic sadface: Sorry, this user has been locked out.")) {
			System.out.println("Successfully verified locked out user login");
		}else {
			Assert.fail("Error Found while verifying locked out user");
		}						
	}

	/**
	 * TESTCASE 4 - Validate "problem_User" user
	 * @throws InterruptedException
	 */
	@Test(priority=4)
	public void validateProblemUser() throws InterruptedException {	
		String errorMessage = loginPage.loginThePage(array[2].toString(), array[3].toString());
		if(errorMessage==null) {
			System.out.println("Successfully logged in as Problem_user");
			boolean flag = loginPage.homePage_verification();
			if(!flag){
				System.out.println("There is some issue with the loaded product image");
			}else{
				Assert.fail("Products loaded successfully in \"Problem_User\" Login");
			}
		}else {
			Assert.fail("Error Found while verifying locked out user");
		}						
	}

	/**
	 * TESTCASE 5 - Validate "performance_glitch_user" user
	 * @throws InterruptedException
	 */
	@Test(priority=5)
	public void validatePerformanceUser() throws InterruptedException {			
		driver.navigate().back();
		String errorMessage = loginPage.loginThePage(array[2].toString(), array[3].toString());
		if(errorMessage==null) {
			System.out.println("Successfully logged in as PerformanceUser");			
		}else {
			Assert.fail("Error Found while verifying locked out user");
		}						
	}

	
	/*@DataProvider(name="swagInputs")
	public Object[][] getData(Method m) throws Exception{
		String testDataPath = System.getProperty("user.dir")+ "\\TestData.xlsx";
		Object[][] testObjArray = ExcelFunctions.getTableArray(testDataPath,m.getName());		 
	    return (testObjArray);
	}*/
	
}
