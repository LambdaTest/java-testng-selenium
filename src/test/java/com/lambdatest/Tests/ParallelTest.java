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
	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	@Test(dataProvider = "browsersDetails")
	public void test(String browser, String version, String os, Method method) throws Exception {

		try {
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
		
		catch(Exception e) {
			e.printStackTrace();
	}
	}	

	@AfterTest(alwaysRun = true)
	public void tearDown(ITestResult result) throws Exception {
		((JavascriptExecutor) webDriver.get()).executeScript("lambda-status="
		+ (result.isSuccess() ? "passed" : "failed"));
		webDriver.get().quit();
		Thread.sleep(10000);
	}
	

	@DataProvider(name = "browsersDetails", parallel = true)
	public static Object[][] ltBrowserDataProvider(Method testMethod) {
		return new Object[][] {
				 new Object[] { "internet explorer", "11.0", "Windows 8.1" },
				new Object[] { "chrome", "60.0", "Windows 8.1" },
				new Object[] { "firefox", "60.0", "Windows 8.1" },
				
				new Object[] { "Safari", "11.0", "macOS High Sierra" },
				
				new Object[] { "chrome", "69.0", "OS X El Capitan" },
				new Object[] { "chrome", "67.0", "OS X Mavericks" }};
	}

	public WebDriver getWebDriver() {
		return webDriver.get();
	}
	
	@BeforeTest(alwaysRun=true)
	protected void setUp(String browser, String version, String os, String methodName)
			throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// set desired capabilities to launch appropriate browser on Lambdatest
		capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
		capabilities.setCapability(CapabilityType.VERSION, version);
		capabilities.setCapability(CapabilityType.PLATFORM, os);
		capabilities.setCapability("name", methodName);
		capabilities.setCapability("build", "TestNG Parallel");
		capabilities.setCapability("name", "TestNG Parallel Tests");
		capabilities.setCapability("screen_resolution", "1024x768");
		capabilities.setCapability("network", false);
		capabilities.setCapability("video", true);
		capabilities.setCapability("console", false);
		capabilities.setCapability("visual", true);
		capabilities.setCapability("tunnel", true);
		
		System.out.println("capabilities"+capabilities);
		
		String username = System.getenv("LT_USERNAME") != null ?System.getenv("LT_USERNAME"): Configuration.readConfig("LambdaTest_UserName");
		String accesskey = System.getenv("LT_ACCESS_KEY") != null ?System.getenv("LT_ACCESS_KEY"):Configuration.readConfig("LambdaTest_AppKey");

		
		// Launch remote browser and set it as the current thread
		String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";
		webDriver.set(new RemoteWebDriver(new URL(gridURL), capabilities));

	}

}