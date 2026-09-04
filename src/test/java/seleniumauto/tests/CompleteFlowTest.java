package seleniumauto.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;

import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;
import seleniumauto.TestComponents.BaseTest;
import seleniumauto.TestComponents.Retry;

public class CompleteFlowTest extends BaseTest {
	String productName = "ZARA COAT 3";				String countryPartialName = "Ind";
	ProductCatalogue prodCatalogue;					CartPage cartPage;
	CheckoutPage checkoutPage;						ConfirmationPage confPage;
	OrderPage ordPage;

	@Test(dataProvider="getData", groups= {"Purchase"})
	public void verifyCompleteFlow(HashMap<String,String> input) throws InterruptedException {
		prodCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		Assert.assertEquals(prodCatalogue.verifyMainPage(), "Filters", "<-------- Logged in Successfully -------->");
//		List<WebElement> products = prodCatalogue.getProductList();
		prodCatalogue.addProductToCart(input.get("product"));
		//Assert.assertEquals(prodCatalogue.verifyCountAddToCart(), "1","<---------- Product added to Cart ---------->"); 
		Assert.assertTrue(prodCatalogue.verifyCountAddToCart());
		cartPage = prodCatalogue.goToCartPage();		  
		Assert.assertEquals(cartPage.verifyCartPage(), "My Cart","<---------- Verified Cart Page ---------->");
		Assert.assertTrue(cartPage.VerifyProductDisplay(input.get("product")),"<---------- Verified Product Added To Cart ---------->");
		checkoutPage = cartPage.goToCheckout();
		
		checkoutPage.clickApplyCoupon();
		
		checkoutPage.selectCountry(countryPartialName);
		Assert.assertEquals(checkoutPage.verifyCheckOutPage(), "PLACE ORDER","<------ Verify CheckOut Page ");
		confPage = checkoutPage.submitOrder();
		
		Assert.assertEquals(confPage.getConfirmationMessage(), "THANKYOU FOR THE ORDER.");
		ordPage = confPage.goToOrderHistoryPage();
		System.out.println("I am before verifyOrderHistory");
		Assert.assertTrue(ordPage.verifyOrderHistory());
		System.out.println("I am before VerifyOrderDisplay");
		Assert.assertTrue(ordPage.VerifyOrderDisplay(productName));
	
	}//	<----- end of verifyLogin() method

	@DataProvider
	public Object[][] getData() throws IOException	{
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//seleniumauto//data//PurchaseOrder.json");
		return new Object[][]  {{data.get(0)}, {data.get(1) }, {data.get(2) } };
//		return new Object[][]  {{data.get(0)}, {data.get(1) } };
	}// <---- ---- ---- ---- End of method() getData()
}
