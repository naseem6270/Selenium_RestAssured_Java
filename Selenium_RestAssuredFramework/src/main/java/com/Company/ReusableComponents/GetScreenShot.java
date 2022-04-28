package com.Company.ReusableComponents;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

/**
 *******************************************************************************
 * 
 * Utility to take screenshot while test execution
 * 
 * @author Naseem Ahmed, Date:- 04/18/2022
 *******************************************************************************
 **/

public class GetScreenShot {

	/**
	 *************************************************************
	 * Taking the screenshot of the entire page
	 *
	 * @param driver         WebDriver
	 * @param screenShotName String
	 * @return screenshotDestination String
	 *************************************************************
	 */

	public static String capture(WebDriver driver, String screenShotName) {
		String dest = null;
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			Files.createDirectories(Paths.get(System.getProperty("user.dir") + "\\test-output\\ErrorScreenshots"));
			dest = System.getProperty("user.dir") + "\\test-output\\ErrorScreenshots\\" + screenShotName + ".png";
			File destination = new File(dest);
			FileHandler.copy(source, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dest;
	}
}