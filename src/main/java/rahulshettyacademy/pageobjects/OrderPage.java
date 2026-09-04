package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;


public class OrderPage extends AbstractComponent {
	private WebDriver driver;

	@FindBy(css = ".totalRow button")
	private WebElement checkoutEle;

	@FindBy(css = "tr td:nth-child(3)")
	private List<WebElement> productNames;
	
	@FindBy(xpath = "//h1[normalize-space()='Your Orders']")
	WebElement yourOrder;

	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public Boolean verifyOrderHistory() {
		System.out.println("Inside verifyOrderHistory() "+yourOrder.getText());
		if((yourOrder.getText()).equals("Your Orders") ){		return true;		}
		else {return false;}
		}
	
	public Boolean VerifyOrderDisplay(String productName) {
		Boolean match = productNames.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
		return match;
	}
}
