package com.Company.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Company.ReusableComponents.CommonFunctions;

/**
 *******************************************************************************
 * 
 * Page Object class for the Home Page
 * 
 * @implNote:- This class uses Page Factory model, need to initialize Page
 *             factory initElement and use @FindBy, @FindBys, @FindAll keyword
 *             to initialize the WebEelement
 * 
 * @author Naseem Ahmed, Date:- 04/19/2022
 * 
 *******************************************************************************
 **/

public class HomePage {

	WebDriver driver;
	CommonFunctions commFunc;

	@FindBy(xpath = "//a[@title='My Store']/img")
	WebElement logoBrandHomePage;

	@FindBy(xpath = "//div[@class='header_user_info']/a[@class='login']")
	WebElement linkSignIn;

	@FindBy(name = "search_query")
	WebElement txtFieldSearch;

	@FindBy(name = "submit_search")
	WebElement btnSearch;

	@FindBy(xpath = "//*[@class='page-heading product-listing']/span[@class='cat-name']")
	WebElement headerSearchResult;

	@FindBy(xpath = "//*[contains(@class,'menu-content')]/li/a[@title='T-shirts']")
	WebElement menuTShirts;

	@FindBy(xpath = "//*[contains(@class,'menu-content')]/li/a[@title='Women']")
	WebElement menuWomen;

	@FindBy(xpath = "//ul[contains(@class,'submenu-container') and (not (@style='display: none;'))]//a[@title='T-shirts']")
	WebElement subMenuTShirts;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions();
	}

	public void navigatingToLoginPage() {
		commFunc.click(linkSignIn, "Sign In link");
	}

	public void searchItemThroughSearchFunctioanlityAndVerify(String searchItem) {
		commFunc.click(txtFieldSearch, "Search text field");
		commFunc.sendKeys(txtFieldSearch, searchItem, "Search text field");
		commFunc.click(btnSearch, "Search button");
		commFunc.click(btnSearch, searchItem);
	}

	public void navigatingToTheProductListThroughTShirtsMenu() {
		commFunc.waitForElementPresent(menuTShirts, 60, "T-Shirts Menu");
		commFunc.click(menuTShirts, "T-Shirts Menu");
	}

	public void navigatingToTheProductListThroughTShirtsSubMenu() {
		commFunc.waitForElementPresent(menuWomen, 60, "Women Menu");
		commFunc.mouseHover(menuWomen, "Women Menu");
		commFunc.waitForElementPresent(subMenuTShirts, 60, "Sub menu T-Shirts");
		commFunc.click(subMenuTShirts, "Sub menu T-Shirts");
	}

	public void navigatingToTheProductListThroughWomenMenu() {
		commFunc.waitForElementPresent(menuWomen, 60, "Women Menu");
		commFunc.click(menuWomen, "Women Menu");

	}
}
