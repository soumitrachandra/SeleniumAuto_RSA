package seleniumauto.TestComponents;

import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException	{		// properties class
	Properties prop = new Properties();		 //<---- I can use static as prop is being used for reading purpose only.
	FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
			 + "//src//main//java//rahulshettyacademy//resources//GlobalData.properties");
	prop.load(fis);
	String browserName = System.getProperty("browser")!= null ? System.getProperty("browser") : prop.getProperty("browser");
	 //prop.getProperty("browser");
	 
	if (browserName.contains("chrome")) {
	ChromeOptions options = new ChromeOptions();
	//	 WebDriverManager.chromedriver().setup();	//<---- Comments by Saumi and added the below line
//< ---------- Added by Saumi not using WebDriverManager ---------- ----------> 
//	System.setProperty("webdriver.chrome.driver","/home/saumi/SAUMI/SAUMI/MyWork/Wind11Laptop/EclipseWorkSpace/chromedriver-linux64/chromedriver");
	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "/chromedriver-linux64/chromedriver");
	//< ---------- Added by Saumi not using WebDriverManager ---------- ---------->
//<---------- Added the below lines to suppress chrome pop ---------- ---------- >		
	//	options.addArguments("--force-device-scale-factor=1.33"); // Browser opens in 75 zoom.
		options.addArguments("--force-device-scale-factor=1"); // Browser opens in 75 zoom.
		options.addArguments("--disable-notifications");
		options.addArguments("--disable-save-password-bubble");
		options.addArguments("--guest");

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.password_manager_leak_detection", false);
		options.setExperimentalOption("prefs", prefs);		
//<---------- Added the below lines to suppress chrome pop ---------- ---------- >
		if(browserName.contains("headless"))	{	
			options.addArguments("--headless=new");	
			options.addArguments("--window-size=1920,1080");}
		driver = new ChromeDriver(options);
//		driver.manage().window().setSize(new Dimension(1440,900));//full screen 
		driver.manage().window().setSize(new Dimension(1920,1080));
	 	}
	 
	 else if (browserName.equalsIgnoreCase("firefox")) {							//<---- Firefox
		System.setProperty("webdriver.gecko.driver", "C:\\SAUMI\\MyWork\\geckodriver-v0.37.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();				}
	 
	 else if (browserName.equalsIgnoreCase("edge")) {
		System.setProperty("webdriver.edge.driver", "edge.exe");				//<---- Edge
		driver = new EdgeDriver();				}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException	{
		//read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
	//String to HashMap- Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
		});
		return data;	//		{map, map} 
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException		{
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goToURL();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()	{		driver.close();			}
}
