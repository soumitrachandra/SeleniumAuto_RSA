package seleniumauto.TestComponents;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rahulshettyacademy.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //<---- Thread safe

	@Override
	public void onTestStart(ITestResult result) 	{
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);						}				//<---- unique thread id(ErrorValidationTest)->test

	@Override
	public void onTestSuccess(ITestResult result) 	{
		System.out.println("Passed Test Case Name >> "+  result.getName());
		extentTest.get().log(Status.PASS, "Test Passed");		}

	@Override
	public void onTestFailure(ITestResult result) 	{
		String path = System.getProperty("user.dir")+"//reports//ScreenShots";
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		try {		FileUtils.copyFile(src, new File(path+"result.getName()"+".png"));		}
		catch (IOException e) 		{		e.printStackTrace();		}
		
		System.out.println("Failed Test Case Name >> "+ result.getName());
		extentTest.get().fail(result.getThrowable());
		
		try {	driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());		}
		catch (Exception e1) {		e1.printStackTrace();	}

		String filePath = null;
		try {	filePath = getScreenshot(result.getMethod().getMethodName(), driver);			}
		catch (IOException e) {		e.printStackTrace();			}

		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());		
		//Screenshot, Attach to report	
	}//<----- end of onTestFailure()

	@Override 		// TODO Auto-generated method stub
	public void onTestSkipped(ITestResult result) {
		System.out.println("Skipped Test Name >>" + result.getName());		
	}

	@Override		// TODO Auto-generated method stub
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}

	@Override			// TODO Auto-generated method stub
	public void onStart(ITestContext context) {
		System.out.println("I am in onStart() method of Listner{} class");		
	}

	@Override			// TODO Auto-generated method stub
	public void onFinish(ITestContext context) {
		System.out.println("I am in onFinish() method of Listner{} class");			
		extent.flush();
	}
}
