package main;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
	public WebDriver driver;
	
	public Login(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(id="user-name")
	public WebElement userNameTxt;
	
	@FindBy(id="password")
	public WebElement passwordTxt;
	
	@FindBy(id="login-button")
	public WebElement loginButton;	
	
	@FindBy(xpath="//div[@class='error-message-container error']/h3")
	public WebElement errorText;
	
	@FindBy(id="//*[local-name() = 'svg']")
	public WebElement errorTextCloseIcon;
	
	@FindBy(xpath="//div[@id='header_container']/div/span[text()='Products']")
	public WebElement homePage;
	
	@FindBy(xpath = "//div[@id='inventory_container']//img[@src='/static/media/sl-404.168b1cce.jpg']")
	public WebElement productIssueImage;
	
	/**
	 * Method to Login the application
	 * @param userName
	 * @param password
	 * @return
	 */
	public String loginThePage(String userName,String password){
		String errorMessage = null;		
		userNameTxt.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		userNameTxt.sendKeys(userName);
		passwordTxt.clear();
		passwordTxt.sendKeys(password);
		//loginButton.click();
		loginButton.sendKeys(Keys.ENTER);
		if(userName.contains("performance")){
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='header_container']/div/span[text()='Products']")));
		}
		try{
			if(errorText.isDisplayed()){
				errorMessage = errorText.getText();
			}	
		}catch(Exception e){
			System.out.println("Exception caught"+e);				
			if(homePage.isDisplayed()){
				System.out.println("Successfully Logged in");
			}
		}
		return errorMessage;	
	}
	
	/**
	 * Method to click Error Icon and verify
	 * @return
	 */
	public boolean closeErrorMsg(){
		boolean flag = false;
		if(driver.findElement(By.xpath("//*[local-name() = 'svg']")).isDisplayed()){
			List<WebElement> elements = driver.findElements(By.xpath("//*[local-name() = 'svg']//*[local-name()='path']"));
			Actions action = new Actions(driver);
			action.click(elements.get(2));
			action.perform();
			action.release();
			try{
				if((!elements.get(0).isDisplayed()) & (!elements.get(1).isDisplayed())){
					flag = true;
				}
			}catch(Exception e){
				if(e.getMessage().contains("element is not attached to the page document")){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * Verify image problem in home page
	 */
	public boolean homePage_verification(){
		boolean flag = false;
		if(productIssueImage.isDisplayed()){
			System.out.println("There is problem with the loaded product image");			
		}else{
			System.out.println("The Product image loaded successfully");
			flag = true;
		}
		return flag;
	}
}
