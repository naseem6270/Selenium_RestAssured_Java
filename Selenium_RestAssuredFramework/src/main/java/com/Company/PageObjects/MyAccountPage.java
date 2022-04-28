package com.Company.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Company.ReusableComponents.CommonFunctions;

/**
 *******************************************************************************
 * 
 * Page Object class for the My Account Page
 * 
 * @implNote:- This class uses Page Factory model, need to initialize Page
 *             factory initElement and use @FindBy, @FindBys, @FindAll keyword
 *             to initialize the WebEelement
 * 
 * @author Naseem Ahmed, Date:- 04/19/2022
 * 
 *******************************************************************************
 **/

public class MyAccountPage {

	WebDriver driver;
	CommonFunctions commFunc;

	@FindBy(xpath = "//*[@class='page-heading' and text()='My account']")
	WebElement headerMyAccount;

	@FindBy(xpath = "//div[contains(@class, 'breadcrumb')]//a[@title='Return to Home']")
	WebElement breadCrumbHomePage;

	public MyAccountPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions();
	}

	public void verifyMyAccountPageIsShown() {
		commFunc.waitForElementPresent(headerMyAccount, 60, "Header in Home Page");
		commFunc.verifyString(commFunc.getText(headerMyAccount, "My Account header"), "MY ACCOUNT");

	}

	public void navigateBackToHomePage() {
		commFunc.waitForElementPresent(headerMyAccount, 60, "Header in Home Page");
		commFunc.click(breadCrumbHomePage, "Home page bread crumb");

	}

}
