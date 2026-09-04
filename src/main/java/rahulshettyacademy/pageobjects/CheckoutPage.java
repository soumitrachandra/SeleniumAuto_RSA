package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {

	private WebDriver driver;

	public CheckoutPage(WebDriver driver) 	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);		}

	@FindBy(css = ".action__submit")
	private WebElement submit;

	@FindBy(css = "[placeholder='Select Country']")
	private WebElement country;

	//@FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
	@FindBy(xpath = "//span[normalize-space()='India']")
	private WebElement selectCountry;
	
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement ApplyCoupon;
	
	@FindBy(xpath = "//div[@aria-label=\"Please Enter Coupon\" and normalize-space()='Please Enter Coupon']")
	private WebElement tostMesgAppCoup;
	
	private By tostMesgApplCoupBy = By.xpath("//div[@aria-label=\\\"Please Enter Coupon\\\" and normalize-space()='Please Enter Coupon']");
	
//	private By results = By.cssSelector(".ta-results");
	public void clickApplyCoupon() throws InterruptedException 		{
//		System.out.println("I am in clickApplyCoupon()");
		JavascriptExecutor jsa = (JavascriptExecutor)driver;
		jsa.executeScript("window.scrollBy(0,200)");
		ApplyCoupon.click();
		 long startApp = System.currentTimeMillis();
//		waitForElementToAppear(tostMesgAppCoup);
		 long endApp = System.currentTimeMillis();
		 System.out.println("Toast wait 1st time: " + (endApp - startApp) + " ms");
		 
		 long startDis = System.currentTimeMillis();
		//waitForElementToDisAppearBy(tostMesgApplCoupBy);
		waitForElementToDisappear(tostMesgAppCoup);
		long endDis = System.currentTimeMillis();
		System.out.println("Toast wait 2nd time: " + (endDis - startDis) + " ms");
		}
	
	public void selectCountry(String countryName) 		{
		Actions a = new Actions(driver);
		a.sendKeys(country, countryName).build().perform();
		waitForElementToAppear(By.cssSelector(".ta-results"));
		selectCountry.click();			}
	
	public String verifyCheckOutPage() 			{
		System.out.println("Text on PLACE ORDER button >> " + submit.getText().trim());
		return submit.getText().trim();			}
	
	public ConfirmationPage submitOrder()			{
		JavascriptExecutor jsa = (JavascriptExecutor)driver; 
		jsa.executeScript("window.scrollBy(0,200)");
		ConfirmationPage conPage = new ConfirmationPage(driver);
		submit.click();
		return conPage;			}
}
