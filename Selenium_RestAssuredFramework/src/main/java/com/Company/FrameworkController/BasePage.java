package com.Company.FrameworkController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.Company.ReusableComponents.CommonFunctions;
import com.Company.ReusableComponents.GetScreenShot;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 *******************************************************************************
 * 
 * This is the base class that takes care of initializing drivers, reporting
 * engine and loggers and destroying the same at the end of the test execution
 * and closing any open browsers.
 * 
 * @implNote:- Every test should extend this class For.Ex public class LoginTest
 *             extends BasePage
 * 
 * @author Naseem Ahmed, Date:- 04/18/2022
 *******************************************************************************
 * 
 **/

public class BasePage {

	protected static WebDriver driver;
	private Properties properties;
	private final String propertyFilePath = "config//Configuration.properties";
	CommonFunctions commFunc;
	protected static ExtentTest extentTest;
	static ExtentReports extentReport;
	private String browserName;
	public final String USERNAME = readPropertyFile("browserStackUserName");
	public final String AUTOMATE_KEY = readPropertyFile("browserStackAccessKey");
	public final String browserStackURL = "https://" + USERNAME + ":" + AUTOMATE_KEY
			+ "@hub-cloud.browserstack.com/wd/hub";

	public BasePage() {
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions();
	}

	@BeforeSuite
	public void beforeSuite(ITestContext context) {

		String suiteName = context.getCurrentXmlTest().getSuite().getName();
		startReporting(suiteName);
	}

	@Parameters({ "applicationType", "browserName", "browserVersion" })
	@BeforeTest
	public void beforeTest(String applicationType, String browserName, String browserVersion) throws Exception {

		if (applicationType.equalsIgnoreCase("UI")) {
			try {
				String platform = readPropertyFile("platFormName");
				if (platform.equalsIgnoreCase("local")) {
					createDriver(browserName);
				} else if (platform.equalsIgnoreCase("BrowserStack")) {
					createBrowserStackDriver(browserName, browserVersion);

				}
				commFunc.setDriver(driver);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;

			}
		}
	}

	@BeforeMethod
	public void setup(Method method) {
		String testMethodName = method.getName();
		extentTest = extentReport.startTest(testMethodName);
		extentTest.log(LogStatus.INFO, "Starting the test " + testMethodName + " on " + browserName + " browser");
		commFunc.setReporter(extentTest);

	}

	@AfterTest
	public void endTest() {
		endReporting();

		if (!(driver == null)) {
			driver.quit();
		}
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				extentTest.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
				extentTest.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
				if (!(driver == null)) {

					String screenShotPath = GetScreenShot.capture(driver, "screenShotName");
					extentTest.log(LogStatus.FAIL, "Snapshot below: " + extentTest.addScreenCapture(screenShotPath));
				}

			} else if (result.getStatus() == ITestResult.SKIP) {
				extentTest.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				extentTest.log(LogStatus.PASS, "Test Case Passed is " + result.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		extentReport.endTest(extentTest);
	}

	private String readPropertyFile(String propertyName) {

		String filePath = System.getProperty("user.dir") + "\\src\\main\\resources";

		String propertyValue = "";

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + "\\" + propertyFilePath));
			properties = new Properties();
			properties.load(reader);
			propertyValue = properties.getProperty(propertyName);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();

		}

		return propertyValue;
	}

	private WebDriver createDriver(String browserName) {
		try {
			if (browserName.equalsIgnoreCase("chrome")) {

				String chromeDriverPath = System.getProperty("user.dir") + "//" + readPropertyFile("chromeDriverPath");

				System.setProperty("webdriver.chrome.driver", chromeDriverPath);
				driver = new ChromeDriver();
			} else if (browserName.equalsIgnoreCase("edge")) {
				String edgeDriverPath = System.getProperty("user.dir") + "//" + readPropertyFile("edgeDriverPath");

				System.setProperty("webdriver.edge.driver", edgeDriverPath);
				driver = new EdgeDriver();
			}
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(readPropertyFile("implicitWait")),
					TimeUnit.SECONDS);
			driver.manage().window().maximize();

		} catch (Exception e) {
			System.out.println(e.toString());

		}
		return driver;
	}

	private WebDriver createBrowserStackDriver(String browserName, String browserVersion) {
		try {

			DesiredCapabilities caps = new DesiredCapabilities();

			caps.setCapability("os", "Windows");
			caps.setCapability("os_version", "10");
			caps.setCapability("browser", browserName.toLowerCase());
			caps.setCapability("browser_version", browserVersion);
			caps.setCapability("build", readPropertyFile("buildNo"));
			caps.setCapability("name", "Scenario on BrowserStack" + Instant.now());
			driver = new RemoteWebDriver(new URL(browserStackURL), caps);

			driver.manage().timeouts().implicitlyWait(Integer.parseInt(readPropertyFile("implicitWait")),
					TimeUnit.SECONDS);
			driver.manage().window().maximize();

		} catch (Exception e) {
			System.out.println(e.toString());

		}

		return driver;

	}

	public void startReporting(String suiteName) {
		try {
			Files.createDirectories(Paths.get(System.getProperty("user.dir") + "//test-output"));

			extentReport = new ExtentReports(
					System.getProperty("user.dir") + "//test-output//ExtentReportResults.html");
			extentReport.addSystemInfo("Suite Name", suiteName)
					.addSystemInfo("Environment", readPropertyFile("environmentName"))
					.addSystemInfo("User Name", System.getProperty("user.name"))
					.addSystemInfo("Test Name", readPropertyFile("TestName"))
					.addSystemInfo("Build No", readPropertyFile("buildNo"));
			extentReport.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void launchURL(String url) {
		commFunc.launchURL(url);
	}

	public void endReporting() {
		extentReport.endTest(extentTest);
		extentReport.flush();
	}

}
