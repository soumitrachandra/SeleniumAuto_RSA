package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent	{
	private WebDriver driver;

	public LandingPage(WebDriver driver)		{
	super(driver);
	this.driver=driver;		//<--------- initialization
	PageFactory.initElements(driver, this);		}	
	//WebElement userEmails = driver.findElement(By.id("userEmail"));
	//PageFactory
	
	@FindBy(id="userEmail")
	private WebElement userEmail;
	
	@FindBy(id="userPassword")
	private WebElement passwordEle;
	
	@FindBy(id="login")
	private WebElement submit;
	
	@FindBy(css="[class*='flyInOut']")
	private WebElement errorMessage;

	public ProductCatalogue loginApplication(String email, String password)	{
	userEmail.sendKeys(email);
	passwordEle.sendKeys(password);
	//<----- added by Saumi
	/*
	 * waitForElementToBeClickable(submit); JavascriptExecutor jse =
	 * (JavascriptExecutor) driver;
	 * jse.executeScript("arguments[0].scrollIntoView({block:'center'});",submit);
	 */
	//<----- added by Saumi
	waitForElementToBeClickable(submit);
	//submit.click();
	((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
	return new ProductCatalogue(driver);			}
	
	public String getErrorMessage()		{
	waitForWebElementToAppear(errorMessage);
	return errorMessage.getText();		}
	
	public void goToURL()	{	driver.get("https://rahulshettyacademy.com/client");		}
	
}
