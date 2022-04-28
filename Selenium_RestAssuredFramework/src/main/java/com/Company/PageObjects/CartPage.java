package com.Company.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Company.ReusableComponents.CommonFunctions;

/**
 *******************************************************************************
 * 
 * Page Object class for the Cart Page
 * 
 * @implNote:- This class uses Page Factory model, need to initialize Page
 *             factory initElement and use @FindBy, @FindBys, @FindAll keyword
 *             to initialize the WebEelement
 * 
 * @author Naseem Ahmed, Date:- 04/19/2022
 * 
 *******************************************************************************
 **/

public class CartPage {

	WebDriver driver;
	CommonFunctions commFunc;

	@FindBy(id = "cart_title")
	WebElement headerCartPage;

	@FindBy(xpath = "//*[contains(@class,'cart_navigation')]//a[@title='Proceed to checkout']/span")
	WebElement btnProceedToCheckout;

	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions();
	}

	public void waitForCartPage() {
		commFunc.waitForElementPresent(headerCartPage, 60, "Cart Page");

	}

	public void navigateToTheAddressPage() {
		commFunc.click(btnProceedToCheckout, "Proceed to checkout button in the Cart");

	}

}
