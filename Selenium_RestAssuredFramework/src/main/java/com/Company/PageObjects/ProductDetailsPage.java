package com.Company.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Company.ReusableComponents.CommonFunctions;

/**
 *******************************************************************************
 * 
 * Page Object class for the Product Details Page
 * 
 * @implNote:- This class uses Page Factory model, need to initialize Page
 *             factory initElement and use @FindBy, @FindBys, @FindAll keyword
 *             to initialize the WebEelement
 * 
 * @author Naseem Ahmed, Date:- 04/19/2022
 * 
 *******************************************************************************
 **/

public class ProductDetailsPage {

	WebDriver driver;
	CommonFunctions commFunc;

	@FindBy(id = "product_condition")
	WebElement productDesc;

	@FindBy(xpath = "//select[@id='group_1']")
	WebElement dropdownSize;

	@FindBy(xpath = "//p[@id='add_to_cart']//span[text()='Add to cart']")
	WebElement btnAddToCart;

	@FindBy(xpath = "(//div[@id='layer_cart']//span[@class='title'])[1]")
	WebElement txtSuccessMsg;

	@FindBy(xpath = "//a[@title='Proceed to checkout']/span")
	WebElement btnProceedToCheckout;

	public ProductDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions();
	}

	public void addItemToTheCart(String size) {
		commFunc.waitForElementPresent(productDesc, 60, "Product Details Page");
		commFunc.scrollPage();
		commFunc.dropDownSelection(dropdownSize, "M", "Size Drop Down");
		commFunc.waitForElementPresent(btnAddToCart, 60, "Add to Cart button");
		commFunc.click(btnAddToCart, "Add to Cart button");
		commFunc.waitForElementPresent(txtSuccessMsg, 60, "Success Message text");
		commFunc.verifyString(commFunc.getText(txtSuccessMsg, "Success Message text"),
				"Product successfully added to your shopping cart");
		commFunc.click(btnProceedToCheckout, "Proceed to checkout button");

	}

}
