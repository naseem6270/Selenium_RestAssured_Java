package com.Company.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Company.ReusableComponents.CommonFunctions;

/**
 *******************************************************************************
 * 
 * Page Object class for the Product List Page
 * 
 * @implNote:- This class uses Page Factory model, need to initialize Page
 *             factory initElement and use @FindBy, @FindBys, @FindAll keyword
 *             to initialize the WebEelement
 * 
 * @author Naseem Ahmed, Date:- 04/19/2022
 * 
 *******************************************************************************
 **/

public class ProductListPage {

	WebDriver driver;
	CommonFunctions commFunc;

	@FindBy(xpath = "//*[@class='page-heading  product-listing']/span[@class='lighter']")
	WebElement headerSearchResult;

	@FindBy(xpath = "//*[@class='page-heading product-listing']/span[@class='cat-name']")
	WebElement headerProductList;

	@FindBy(xpath = "//div[contains(@class, 'breadcrumb')]//a[@title='Return to Home']")
	WebElement breadCrumbHomePage;

	@FindBy(xpath = "//a[contains(@class,'subcategory-name') and text()='Tops']")
	WebElement imageTops;

	@FindBy(xpath = "//a[text()='T-shirts']/parent::label/preceding-sibling::div")
	WebElement checkboxTShirts;

	@FindBy(xpath = "//ul[@id='product_list']//a[@class='product-name']")
	WebElement imgFirstResult;

	public ProductListPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions();
	}

	public void validateHeaderAfterSearch(String header) {
		commFunc.waitForElementPresent(headerSearchResult, 60, "Product List Page");
		String headerValue = commFunc.getText(headerSearchResult, "Header");
		headerValue = headerValue.replaceAll("\"", "");
		commFunc.verifyString(headerValue, header);
	}

	public void validateHeader(String header) {
		commFunc.waitForElementPresent(headerProductList, 60, "Product List Page");
		String headerValue = commFunc.getText(headerProductList, "Header");
		headerValue = headerValue.replaceAll("\"", "");
		commFunc.verifyString(headerValue, header);
	}

	public void navigatingToTShirtsListPage() {
		commFunc.waitForElementPresent(imageTops, 60, "Top Image");
		commFunc.click(imageTops, "Top Image");
		commFunc.waitForElementPresent(checkboxTShirts, 60, "T-Shirt Checkbox");
		commFunc.click(checkboxTShirts, "T-Shirt Checkbox");

	}

	public void navigatingToProductDetailsPage() {

		commFunc.click(imgFirstResult, "First Result Image");

	}

	public void navigateBackToHomePage() {
		commFunc.click(breadCrumbHomePage, "Home page bread crumb");
	}
}
