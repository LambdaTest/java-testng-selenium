package com.lambdatest.Tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.lang.reflect.Method;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ParallelTest {

	public static String status = "failed";
	/*
	 * public String buildTag = System.getenv("LT_BUILD"); public String username =
	 * System.getenv("LT_USERNAME"); public String accesskey =
	 * System.getenv("LT_APIKEY"); public String gridURL =
	 * System.getenv("LT_GRID_URL");
	 */

	@Test(dataProvider = "browsersDetails")
	public void test(String browser, String version, String os, Method method) throws Exception {

		// Launch the app
		this.setUp(browser, version, os, method.getName());
		getWebDriver().get("https://lambdatest.github.io/sample-todo-app/");

		// Click on First Item
		getWebDriver().findElement(By.name("li1")).click();

		// Click on Second Item
		getWebDriver().findElement(By.name("li2")).click();

		// Add new item is list
		getWebDriver().findElement(By.id("sampletodotext")).clear();
		getWebDriver().findElement(By.id("sampletodotext")).sendKeys("Yey, Let's add it to list");
		getWebDriver().findElement(By.id("addbutton")).click();

		// Verify Added item
		String item = getWebDriver().findElement(By.xpath("/html/body/div/div/div/ul/li[6]/span")).getText();
		AssertJUnit.assertTrue(item.contains("Yey, Let's add it to list"));
		status = "passed";

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws Exception {
		((JavascriptExecutor) webDriver.get()).executeScript("lambda-status="
		+ (result.isSuccess() ? "passed" : "failed"));
		webDriver.get().quit();
	}
	
	
	
	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	@DataProvider(name = "browsersDetails", parallel = true)
	public static Object[][] ltBrowserDataProvider(Method testMethod) {
		return new Object[][] { new Object[] { "MicrosoftEdge", "16.0", "WIN10" },
				new Object[] { "firefox", "60.0", "WIN10" }, new Object[] { "internet explorer", "11.0", "WIN7" },
				new Object[] { "chrome", "60.0", "OS X 10.11" }, new Object[] { "chrome", "70.0", "macOS 10.13" },
				new Object[] { "firefox", "60.0", "WIN7" }, };
	}

	public WebDriver getWebDriver() {
		return webDriver.get();
	}
	
	
	protected void setUp(String browser, String version, String os, String methodName)
			throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// set desired capabilities to launch appropriate browser on Lambdatest
		capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
		capabilities.setCapability(CapabilityType.VERSION, version);
		capabilities.setCapability(CapabilityType.PLATFORM, os);
		capabilities.setCapability("name", methodName);
		capabilities.setCapability("build", "TestNG Parallel Test");
		capabilities.setCapability("name", "TestNG Parallel");
		capabilities.setCapability("screen_resolution", "1024x768");
		capabilities.setCapability("network", true);
		capabilities.setCapability("video", true);
		capabilities.setCapability("console", true);
		capabilities.setCapability("visual", true);
		
		String username = Configuration.readConfig("LambdaTest_UserName");
		String accesskey = Configuration.readConfig("LambdaTest_AppKey");

		
		// Launch remote browser and set it as the current thread
		String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";
		webDriver.set(new RemoteWebDriver(new URL(gridURL), capabilities));

	}

}