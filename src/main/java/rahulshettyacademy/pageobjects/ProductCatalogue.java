package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {
	private WebDriver driver;
	public ProductCatalogue(WebDriver driver) 	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);		}

	@FindBy(css = ".mb-3")
	private List<WebElement> products;
	
	@FindBy(css = ".ng-animating")
	private WebElement spinner;

	@FindBy(id = "burgundy")
	private List<WebElement> mainPageFilter;
	
	@FindBy(xpath = "//label[normalize-space()='1']")
	private WebElement incrementCartCount; 
	
	@FindBy (css = "#toast-container")
	private WebElement tostMsgEle;
	
	private By productsBy = By.cssSelector(".mb-3");
	private By addToCart = By.cssSelector(".card-body button:last-of-type");
	private By toastMessageBy = By.cssSelector("#toast-container");

	public void addProductToCart(String productName) throws InterruptedException		{
//		System.out.println("inside addProductToCart()");
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToDisappear(tostMsgEle);
//		JavascriptExecutor jsa = (JavascriptExecutor)driver;
//		jsa.executeScript("window.scrollBy(0,200)");
//		waitForElementToAppear(toastMessageBy);
//		System.out.println("end of addProductToCart()");
		}
	
	public List<WebElement> getProductList() 	{
		waitForElementToAppear(productsBy);
		return products;			}

	public WebElement getProductByName(String productName)		{
		WebElement prod = getProductList().stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		System.out.println("Producet name filtered >> "+prod.getText());
		return prod;		}

	public String verifyMainPage() {
		String filtText = "";
		for(WebElement ele: mainPageFilter) {
			if(ele.isDisplayed()) {				filtText= ele.getText();			}
			else{		continue;		}
		}//<---- end of for loop
		return filtText;
	}

	public Boolean verifyCountAddToCart() {
		incrementCartCount.getText();
		if(Integer.parseInt(incrementCartCount.getText())>0) {			return true;		}
		else {			return false;		}
	}
}
