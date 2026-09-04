package rahulshettyacademy.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class ConfirmationPage extends AbstractComponent{
	private WebDriver driver;

	@FindBy(css = ".hero-primary")
	private WebElement confirmationMessage;
	
	@FindBy(css = "label[routerlink=\"/dashboard/myorders\"]")
	private WebElement linkOrderHistory;
	
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getConfirmationMessage()	{
		//CheckoutPage cp = new CheckoutPage(driver);
		System.out.println("Confirmation message is as >> "+ confirmationMessage);
		return confirmationMessage.getText();
	}
	
	public OrderPage goToOrderHistoryPage() {
		linkOrderHistory.click();
		return new OrderPage(driver);
	}
}
