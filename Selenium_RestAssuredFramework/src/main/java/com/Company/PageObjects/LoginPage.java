package com.Company.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Company.ReusableComponents.CommonFunctions;

/**
 *******************************************************************************
 * 
 * Page Object class for the Login Page
 * 
 * @implNote:- This class uses Page Factory model, need to initialize Page
 *             factory initElement and use @FindBy, @FindBys, @FindAll keyword
 *             to initialize the WebEelement
 * 
 * @author Naseem Ahmed, Date:- 04/19/2022
 * 
 *******************************************************************************
 **/

public class LoginPage {

	WebDriver driver;
	CommonFunctions commFunc;

	@FindBy(id = "email")
	WebElement txtFieldEmailID;

	@FindBy(id = "passwd")
	WebElement txtFieldPassword;

	@FindBy(id = "SubmitLogin")
	WebElement btnSubmit;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions();
	}

	public void Login(String emailID, String password) {
		commFunc.waitForElementPresent(txtFieldEmailID, 60, "Login Page");

		commFunc.click(txtFieldEmailID, "Email ID text field");
		commFunc.sendKeys(txtFieldEmailID, emailID, "Email ID text field");

		commFunc.click(txtFieldPassword, "Password ID text field");
		commFunc.sendKeys(txtFieldPassword, password, "Password ID text field");
		commFunc.click(btnSubmit, "Submit button");

	}

	public Boolean verifyLoginPage() {

		if (commFunc.waitForElementPresent(txtFieldEmailID, 60, "Login Page")) {
			return true;
		} else {
			return false;
		}

	}

}
