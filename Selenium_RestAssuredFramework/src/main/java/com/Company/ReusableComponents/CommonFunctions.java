package com.Company.ReusableComponents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 *******************************************************************************
 * CommonFunctions implements different actions that can be implemented on the
 * Application Under Test. For Example click(), sendKeys(), getText(), waitFor()
 * etc
 * 
 * @author Naseem Ahmed, Date:- 04/18/2022
 *******************************************************************************
 * 
 **/

public class CommonFunctions {

	private static WebDriver driver;
	private static ExtentTest extentTest;
	private Properties properties;

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void setReporter(ExtentTest extentTest) {
		this.extentTest = extentTest;
	}

	/**
	 *************************************************************
	 * 
	 * Launch the given baseURL web page in browser
	 *
	 * @param url String
	 * @return status Boolean
	 *************************************************************
	 * 
	 */

	public boolean launchURL(String url) {
		Boolean status = false;
		try {
			driver.get(url);
			extentTest.log(LogStatus.PASS, "Successfuly launched the URL " + url);

			status = true;
		} catch (Exception e) {
			status = false;
			extentTest.log(LogStatus.FATAL, "Failed to Successfuly launched the URL. Exception is " + e.toString());

		}
		return status;
	}

	/**
	 *************************************************************
	 * 
	 * Click on the Web Element.
	 *
	 * @param elem        WebElement
	 * @param elementName String
	 * @return status Boolean
	 *************************************************************
	 */

	public boolean click(WebElement elem, String elementName) {
		Boolean status = false;
		try {
			elem.click();
			extentTest.log(LogStatus.PASS, "Clicked on element " + elementName + " Successfuly");

			status = true;
		} catch (Exception e) {
			status = false;
			extentTest.log(LogStatus.FAIL,
					"Failed to clicked on element " + elementName + " Successfuly. Exception is " + e.toString());

		}
		return status;
	}

	/**
	 *************************************************************
	 * Send value to the Web Element.
	 *
	 * @param elem        WebElement
	 * @param value       String
	 * @param elementName String
	 * @return status Boolean
	 *************************************************************
	 */

	public boolean sendKeys(WebElement elem, String value, String elementName) {
		Boolean status = false;
		try {
			elem.sendKeys(value);
			extentTest.log(LogStatus.PASS, "Successfuly entered value as " + value + " in the element " + elementName);

			status = true;
		} catch (Exception e) {
			status = false;
			extentTest.log(LogStatus.FAIL, "Failed to Successfuly enter value as " + value + " in the element "
					+ elementName + " .Exception is " + e.toString());

		}
		return status;
	}

	/**
	 *************************************************************
	 * Select the option from drop down by visible text.
	 *
	 * @param elem        WebElement
	 * @param value       String
	 * @param elementName String
	 * @return status Boolean
	 *************************************************************
	 */

	public boolean dropDownSelection(WebElement elem, String value, String elementName) {
		Boolean status = false;
		try {
			Select select = new Select(elem);
			select.selectByVisibleText(value);
			extentTest.log(LogStatus.PASS,
					"Successfuly selected value as " + value + " in the drop down " + elementName);

			status = true;
		} catch (Exception e) {
			status = false;
			extentTest.log(LogStatus.FAIL, "Failed to Successfuly select value as " + value + " in the drop down "
					+ elementName + " .Exception is " + e.toString());

		}
		return status;
	}

	/**
	 *************************************************************
	 * Performing the Mouse Hover the given WebElement.
	 *
	 * @param elem        WebElement
	 * @param elementName String
	 * @return status Boolean
	 *************************************************************
	 */

	public boolean mouseHover(WebElement elem, String elementName) {
		Boolean status = false;
		try {
			Actions action = new Actions(driver);
			action.moveToElement(elem).build().perform();
			extentTest.log(LogStatus.PASS, "Performed the mouse hoever over the " + elementName + " Successfuly");

			status = true;
		} catch (Exception e) {
			status = false;
			extentTest.log(LogStatus.FAIL,
					"Failed to perform the mouse hover over the " + elementName + " .Exception is " + e.toString());

		}
		return status;
	}

	/**
	 *************************************************************
	 * Get Object text.
	 *
	 * @param we          WebElement
	 * @param elementName String
	 * @return the text
	 *************************************************************
	 */

	public String getText(WebElement elem, String elementName) {

		String text = "";
		try {
			text = elem.getText();
		} catch (Exception e) {
			extentTest.log(LogStatus.FAIL, "There was an error while trying to get the text from the element "
					+ elementName + " .Exception is " + e.toString());

		}
		return text;
	}

	/**
	 *************************************************************
	 * Scrolling the page
	 *
	 *************************************************************
	 */

	public void scrollPage() {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
	}

	/**
	 ******************************************************************************
	 * Wait for a element to be visible. Waiting has a standard timeout with 500ms
	 * polling frequency.
	 *
	 * @param elem        WebElement
	 * @param timeOut     Integer
	 * @param elementName String
	 * @return status Boolean
	 * @throws TimeoutException the timeout exception
	 ******************************************************************************
	 */

	public boolean waitForElementPresent(WebElement elem, int timeOut, String elementName) {
		Boolean status = false;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.visibilityOf(elem));
			extentTest.log(LogStatus.PASS, elementName + " loaded Successfuly");
			status = true;
		} catch (TimeoutException e) {
			status = false;
			extentTest.log(LogStatus.FAIL,
					"Unable to find an element " + elementName + ". Exception is " + e.toString());

		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return status;

	}

	/**
	 *************************************************************
	 * Compare two String
	 *
	 * @param actualResult   String
	 * @param expectedResult String
	 * @return status Boolean
	 *************************************************************
	 */
	public boolean verifyString(String actualResult, String expectedResult) {
		Boolean status = false;
		try {
			if (actualResult.trim().equalsIgnoreCase(expectedResult.trim())) {
				extentTest.log(LogStatus.PASS,
						"PASSED:- Expected was " + expectedResult + " and actual is " + actualResult);
				status = true;
			} else {

				String screenShotPath = GetScreenShot.capture(driver, "screenShotName");

				extentTest.log(LogStatus.FAIL,
						"FAILED:- Expected was " + expectedResult + " BUT actual is " + actualResult);
				extentTest.log(LogStatus.FAIL, "Snapshot below: " + extentTest.addScreenCapture(screenShotPath));

				status = false;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 *************************************************************
	 * Read the properties file
	 *
	 * @param fileName     String
	 * @param propertyName String
	 * @return propertyValue String
	 *************************************************************
	 */

	public String readPropertyFile(String fileName, String propertyName) {

		String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\config";

		String propertyValue = "";

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + "\\" + fileName + ".properties"));
			properties = new Properties();
			properties.load(reader);
			propertyValue = properties.getProperty(propertyName);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();

		}

		return propertyValue;
	}

}
