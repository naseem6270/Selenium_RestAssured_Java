package com.Company.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Company.ReusableComponents.CommonFunctions;

/**
 *******************************************************************************
 * 
 * Page Object class for the Payment Page
 * 
 * @implNote:- This class uses Page Factory model, need to initialize Page
 *             factory initElement and use @FindBy, @FindBys, @FindAll keyword
 *             to initialize the WebEelement
 * 
 * @author Naseem Ahmed, Date:- 04/19/2022
 * 
 *******************************************************************************
 **/

public class PaymentPage {

	WebDriver driver;
	CommonFunctions commFunc;

	@FindBy(xpath = "//a[@title='Pay by bank wire']")
	WebElement sectionPayByBankWire;

	@FindBy(xpath = "//span[text()='I confirm my order']")
	WebElement btnConfirmOrder;

	public PaymentPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions();
	}

	public void makePayment() {
		commFunc.waitForElementPresent(sectionPayByBankWire, 60, "Payment Page");
		commFunc.click(sectionPayByBankWire, "Pay By Bank Option");

		commFunc.waitForElementPresent(btnConfirmOrder, 60, "Confirm Order button");
		commFunc.click(btnConfirmOrder, "I Confirm my Order button");

	}

}
