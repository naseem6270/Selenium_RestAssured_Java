package com.Company.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Company.ReusableComponents.CommonFunctions;

/**
 *******************************************************************************
 * 
 * Page Object class for the Address Page
 * 
 * @implNote:- This class uses Page Factory model, need to initialize Page
 *             factory initElement and use @FindBy, @FindBys, @FindAll keyword
 *             to initialize the WebEelement
 * 
 * @author Naseem Ahmed, Date:- 04/19/2022
 * 
 *******************************************************************************
 **/
public class AddressPage {

	WebDriver driver;
	CommonFunctions commFunc;

	@FindBy(xpath = "//h1[@class='page-heading' and text()='Addresses']")
	WebElement headerAddressPage;

	@FindBy(xpath = "//button[@name='processAddress']/span")
	WebElement btnProceedToCheckout;

	public AddressPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions();
	}

	public void checkForAddressPage() {
		commFunc.waitForElementPresent(headerAddressPage, 60, "Address Page");
		commFunc.verifyString(commFunc.getText(headerAddressPage, "Address Page Header"), "Addresses");

	}

	public void navigateToTheShippingPage() {
		commFunc.click(btnProceedToCheckout, "Proceed to checkout button in the Address Page");

	}

}
