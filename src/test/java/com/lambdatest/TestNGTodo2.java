package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodo2 {

  private AppiumDriver driver;
  private String Status = "failed";
  public static final String AUTOMATE_USERNAME = "gauravkumar";
  public static final String AUTOMATE_KEY = "x3ACEbZEcIl6DHJp8ygtPrRNIgKmhlDwzWZl4QJJebcV6RWCbH";
  public static final String hub = "@hub.lambdatest.com/wd/hub";
  public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_KEY + hub;

  @BeforeMethod
  public void setup(Method m, ITestContext ctx) throws MalformedURLException {

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("platformName", "Android");
    caps.setCapability("deviceName", "One Plus 6");
    caps.setCapability("platformVersion", "9");

    driver = new AndroidDriver(new URL(URL), caps);

  }

  @Test
  public void basicTest() throws InterruptedException {
    driver.get("https://webcamtests.com/check");
    Thread.sleep(2000);
    driver.findElement(By.id("webcam-launcher")).click();
    Thread.sleep(2000);
    // To accept/block the popup, you need to switch the context to “NATIVE_APP“ and click on the Allow/Block button.
    driver.context("NATIVE_APP");
    driver.findElement(By.xpath(".//android.widget.Button[@text='Allow']")).click();
    Thread.sleep(5000);
    driver.findElement(By.xpath(".//android.widget.Button[@text='ALLOW']")).click();
    Thread.sleep(5000);
    driver.context("CHROMIUM");
    //Mic Test
    driver.get("https://www.vidyard.com/mic-test/");
    Thread.sleep(2000);
  }

  @AfterMethod
  public void tearDown() {

    driver.quit();
  }

}