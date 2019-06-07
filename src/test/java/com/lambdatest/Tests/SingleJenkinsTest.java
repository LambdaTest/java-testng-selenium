package com.lambdatest.Tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.testng.ITestContext;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SingleJenkinsTest {

	public WebDriver driver;
	public static String status = "failed";

	public static String username = System.getenv("LT_USERNAME");
	public static String accesskey = System.getenv("LT_ACCESS_KEY");
	public static String gridURL = System.getenv("LT_GRID_URL");
	public static String os = System.getenv("LT_OPERATING_SYSTEM");
	public static String browser = System.getenv("LT_BROWSER_NAME");
	public static String version = System.getenv("LT_BROWSER_VERSION");
	public static String res = System.getenv("LT_RESOLUTION");

	@BeforeTest
	public void setUp() throws Exception {
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability(CapabilityType.BROWSER_NAME, browser);
		capability.setCapability(CapabilityType.VERSION, version);
		capability.setCapability(CapabilityType.PLATFORM, os);
		capability.setCapability("build", System.getenv("LT_BUILD"));
		capability.setCapability("name", "Single Junit Jenkins Test");
		capability.setCapability("screen_resolution", res);
		capability.setCapability("network", true);
		capability.setCapability("video", true);
		capability.setCapability("console", true);
		capability.setCapability("visual", true);
		capability.setCapability("tunnel", true);

		driver = new RemoteWebDriver(new URL(gridURL), capability);

	}

	@Test
	public void test() {
		// Launch the app
		driver.get("https://lambdatest.github.io/sample-todo-app/");

		// Click on First Item
		driver.findElement(By.name("li1")).click();

		// Click on Second Item
		driver.findElement(By.name("li2")).click();

		// Add new item is list
		driver.findElement(By.id("sampletodotext")).clear();
		driver.findElement(By.id("sampletodotext")).sendKeys("Yey, Let's add it to list");
		driver.findElement(By.id("addbutton")).click();

		// Verify Added item
		String item = driver.findElement(By.xpath("/html/body/div/div/div/ul/li[6]/span")).getText();
		AssertJUnit.assertTrue(item.contains("Yey, Let's add it to list"));
		status = "passed";
		((JavascriptExecutor) driver).executeScript("lambda-status=" + status + "");
		//driver.quit();

	}

	@AfterTest
	public void afterTest() {
		((JavascriptExecutor) driver).executeScript("lambda-status=" + status + "");
		driver.quit();
	}

}