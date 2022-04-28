package com.Company.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Company.ReusableComponents.CommonFunctions;

/**
 *******************************************************************************
 * 
 * Page Object class for the Confirmation Page
 * 
 * @implNote:- This class uses Page Factory model, need to initialize Page
 *             factory initElement and use @FindBy, @FindBys, @FindAll keyword
 *             to initialize the WebEelement
 * 
 * @author Naseem Ahmed
 * 
 *******************************************************************************
 **/

public class ConfirmationPage {

	WebDriver driver;
	CommonFunctions commFunc;

	@FindBy(xpath = "//p[@class='alert alert-success']")
	WebElement txtOrderSuccess;

	@FindBy(xpath = "//a[@title='Log me out']")
	WebElement linkSignOut;

	public ConfirmationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions();
	}

	public void verifyOrder() {
		commFunc.waitForElementPresent(txtOrderSuccess, 60, "Confirmation Page");
		commFunc.verifyString(commFunc.getText(txtOrderSuccess, "Success text"), "Your order on My Store is complete.");
	}

	public void signOut() {
		commFunc.click(linkSignOut, "Sign Out link");
	}

}
