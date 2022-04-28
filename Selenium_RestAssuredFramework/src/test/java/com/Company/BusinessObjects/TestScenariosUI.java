package com.Company.BusinessObjects;

import org.testng.annotations.Test;

import com.Company.FrameworkController.BasePage;
import com.Company.PageObjects.AddressPage;
import com.Company.PageObjects.CartPage;
import com.Company.PageObjects.ConfirmationPage;
import com.Company.PageObjects.HomePage;
import com.Company.PageObjects.LoginPage;
import com.Company.PageObjects.MyAccountPage;
import com.Company.PageObjects.PaymentPage;
import com.Company.PageObjects.ProductDetailsPage;
import com.Company.PageObjects.ProductListPage;
import com.Company.PageObjects.ShippingPage;
import com.Company.ReusableComponents.CommonFunctions;
import com.relevantcodes.extentreports.LogStatus;

/**
 *******************************************************************************
 * 
 * Test class to validate the business requirement of the application UI
 * 
 * @implNote:- This class should extend the BasePage. This is required to
 *             initialize the driver, start reporting etc.
 * 
 * @author Naseem Ahmed, Date:- 04/19/2022
 *******************************************************************************
 **/

public class TestScenariosUI extends BasePage {
	CommonFunctions commFunc;
	String url, emailID, password, itemToBeSearched, size;

	public TestScenariosUI() {
		commFunc = new CommonFunctions();
		url = commFunc.readPropertyFile("TestData", "url");
		emailID = commFunc.readPropertyFile("TestData", "emailID");
		password = commFunc.readPropertyFile("TestData", "password");
		itemToBeSearched = commFunc.readPropertyFile("TestData", "itemToBeSearched");
		size = commFunc.readPropertyFile("TestData", "size");

	}

	/**
	 *****************************************************************************
	 * This function will verify the Login functionality with valid credentials in
	 * the site
	 *
	 * @param UserID   String
	 * @param Password String
	 *****************************************************************************
	 **/

	@Test(priority = 1)
	public void testLogin() {

		launchURL(url);
		HomePage homePage = new HomePage(driver);
		LoginPage loginPage = new LoginPage(driver);
		MyAccountPage myAccPage = new MyAccountPage(driver);

		homePage.navigatingToLoginPage();
		loginPage.Login(emailID, password);
		myAccPage.verifyMyAccountPageIsShown();
		myAccPage.navigateBackToHomePage();

	}

	/**
	 *****************************************************************************
	 * This function will verify the Search functionality with below options
	 * Option1:- With the Search functionality at the home page
	 * 
	 * Option2:- Navigating to the Product List Page using the Main Menu
	 * 
	 * Option3:- Navigating to the Product List Page using the Sub Menu
	 * 
	 * Option4:- Navigating to the Product List Page using the Filter
	 * 
	 * @param itemName String
	 *****************************************************************************
	 **/

	@Test(priority = 2)
	public void testSearchProducts() {
		HomePage homePage = new HomePage(driver);
		ProductListPage prodList = new ProductListPage(driver);

		homePage.searchItemThroughSearchFunctioanlityAndVerify(itemToBeSearched);
		prodList.validateHeaderAfterSearch(itemToBeSearched);
		prodList.navigateBackToHomePage();

		homePage.navigatingToTheProductListThroughTShirtsMenu();
		prodList.validateHeader(itemToBeSearched);
		prodList.navigateBackToHomePage();

		homePage.navigatingToTheProductListThroughTShirtsSubMenu();
		prodList.validateHeader(itemToBeSearched);
		prodList.navigateBackToHomePage();

		homePage.navigatingToTheProductListThroughWomenMenu();
		prodList.navigatingToTShirtsListPage();
	}

	/**
	 *****************************************************************************
	 * This function will verify that the application is properly adding the item to
	 * the Cart
	 * 
	 *****************************************************************************
	 **/
	@Test(priority = 3)
	public void testAddToCart() {
		ProductListPage prodList = new ProductListPage(driver);
		ProductDetailsPage prodDetails = new ProductDetailsPage(driver);
		prodList.navigatingToProductDetailsPage();
		prodDetails.addItemToTheCart(size);
	}

	/**
	 *****************************************************************************
	 * This function will verify the Checkout process till the booking is completed
	 * 
	 *****************************************************************************
	 **/
	@Test(priority = 4)
	public void testCheckOut() {
		CartPage cartPage = new CartPage(driver);
		AddressPage addressPage = new AddressPage(driver);
		ShippingPage shippingPage = new ShippingPage(driver);
		PaymentPage paymentPage = new PaymentPage(driver);
		ConfirmationPage confirmationPage = new ConfirmationPage(driver);

		cartPage.waitForCartPage();
		cartPage.navigateToTheAddressPage();
		addressPage.checkForAddressPage();
		addressPage.navigateToTheShippingPage();
		shippingPage.checkForShippingPage();
		shippingPage.navigateToThePaymentPage();
		paymentPage.makePayment();
		confirmationPage.verifyOrder();
	}

	/**
	 *****************************************************************************
	 * This function will verify the application Sign out functionality
	 * 
	 *****************************************************************************
	 **/

	@Test(priority = 5)
	public void testSignOut() {
		ConfirmationPage confirmationPage = new ConfirmationPage(driver);
		LoginPage loginPage = new LoginPage(driver);

		confirmationPage.signOut();

		if (loginPage.verifyLoginPage()) {
			extentTest.log(LogStatus.PASS, "Application is properly showing the login page after the sign out");
		} else {
			extentTest.log(LogStatus.FAIL, "Application is not showing the login page after the sign out");

		}
	}

}
