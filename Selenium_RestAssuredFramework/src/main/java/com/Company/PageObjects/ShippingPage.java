package com.Company.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Company.ReusableComponents.CommonFunctions;

/**
 *******************************************************************************
 * 
 * Page Object class for the Shipping Page
 * 
 * @implNote:- This class uses Page Factory model, need to initialize Page
 *             factory initElement and use @FindBy, @FindBys, @FindAll keyword
 *             to initialize the WebEelement
 * 
 * @author Naseem Ahmed, Date:- 04/19/2022
 *  
 *******************************************************************************
 **/

public class ShippingPage {

	WebDriver driver;
	CommonFunctions commFunc;

	@FindBy(xpath = "//h1[@class='page-heading' and text()='Shipping:']")
	WebElement headerShippingPage;

	@FindBy(id = "cgv")
	WebElement checkBoxIAgress;

	@FindBy(xpath = "//button[@name='processCarrier']/span")
	WebElement btnProceedToCheckout;

	public ShippingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions();
	}

	public void checkForShippingPage() {
		commFunc.waitForElementPresent(headerShippingPage, 60, "Shipping Page");
		commFunc.verifyString(commFunc.getText(headerShippingPage, "Shipping Page Header"), "Shipping:");

	}

	public void navigateToThePaymentPage() {
		commFunc.waitForElementPresent(btnProceedToCheckout, 60, "Cart Page");
		commFunc.click(checkBoxIAgress, "I Agree CheckBox");

		commFunc.click(btnProceedToCheckout, "Proceed to checkout button in the Shipping Page");

	}

}
