package seleniumauto.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class StandAloneTest {
	public static void main(String[] args) {
	String productName = "ZARA COAT 3";
	//WebDriverManager.chromedriver().setup();
	System.setProperty("webdriver.chrome.driver","/home/saumi/SAUMI/SAUMI/MyWork/Wind11Laptop/EclipseWorkSpace/chromedriver-linux64/chromedriver");
	ChromeOptions options = new ChromeOptions();
    options.addArguments("--force-device-scale-factor=1.33"); // Browser opens in 75 zoom.
    options.addArguments("--window-size=1920,1080");
    
	WebDriver driver = new ChromeDriver(options);
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.manage().window().maximize();
	driver.get("https://rahulshettyacademy.com/client");
		//LandingPage landingPage = new LandingPage(driver);
	driver.findElement(By.id("userEmail")).sendKeys("soumitrachandra.27@gmail.com");
	driver.findElement(By.id("userPassword")).sendKeys("Samanvi20");
	driver.findElement(By.id("login")).click();
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
	List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

	WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText()
			.equals(productName)).findFirst().orElse(null);

	JavascriptExecutor jsa = (JavascriptExecutor)driver; 
	jsa.executeScript("window.scrollBy(0,200)");
	//Click on add to Cart button for Zara Coat 3
	prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		//Wait for toast Container is visible.
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='toast-container']/div/div[normalize-space()='Product Added To Cart']")));
		//Click on cart icon
	driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

	List <WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
	Boolean match = 	cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText()
			.equalsIgnoreCase(productName));
	Assert.assertTrue(match);
	
	jsa.executeScript("window.scrollBy(0,200)");
	WebElement CheckOut = driver.findElement(By.xpath("(//button[normalize-space()='Checkout'])"));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Checkout']")));
	CheckOut.click();
//	driver.findElement(By.xpath("(//button[normalize-space()='Checkout'])")).click();
	
	Actions act = new Actions(driver);
	act.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();		// Select India from drop down.
	//JavascriptExecutor jsa = (JavascriptExecutor)driver;
	
	jsa.executeScript("window.scrollBy(0,100)");
	driver.findElement(By.cssSelector(".action__submit")).click();

	String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
	Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	System.out.println("Confirmation message is >> " +confirmMessage);
	driver.close();
	System.out.println("---- Closed Browser ----");
	}
}
