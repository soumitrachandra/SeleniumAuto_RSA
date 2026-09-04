package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {
	private WebDriver driver;

	@FindBy(css = ".totalRow button")
	private WebElement checkoutEle;

	@FindBy(css = ".cartSection h3")
	private List<WebElement> cartProducts;
	
	@FindBy(xpath ="//h1[normalize-space()='My Cart']")
	WebElement cartPage;

	public CartPage(WebDriver driver) 	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);		}

	public Boolean VerifyProductDisplay(String productName) {
		Boolean match = cartProducts.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
		return match;		}

	public CheckoutPage goToCheckout() 	{
		JavascriptExecutor jsa = (JavascriptExecutor)driver; 
		jsa.executeScript("window.scrollBy(0,200)");
		waitForWebElementToAppear(checkoutEle);//<----- Added by Saumi
		System.out.println("In goToCheckout() CartPage{} class before moving to CheckoutPage");
		checkoutEle.click();
		CheckoutPage chObj = new CheckoutPage(driver);
		System.out.println("chObj is created" );
		return chObj;			}
	
	public String verifyCartPage() {
		String cartTitle = cartPage.getText();
		System.out.println("Cart Page title is >> " + cartTitle);
		return cartTitle;
	}
}
