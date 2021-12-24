package com.lambdatest;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v94.emulation.Emulation;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;


public class OverrideDeviceMode {
    public static String hubURL = "https://hub.lambdatest.com/wd/hub";

    private WebDriver driver;
    

    
  @BeforeMethod
  public void setup(Method m, ITestContext ctx) throws MalformedURLException  {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "latest");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("user", System.getenv("LT_USERNAME"));
        ltOptions.put("accessKey", System.getenv("LT_ACCESS_KEY"));
        ltOptions.put("build", "Selenium 4");
        ltOptions.put("name",this.getClass().getName());
        ltOptions.put("platformName", "Windows 10");
        ltOptions.put("seCdp", true);
        ltOptions.put("selenium_version", "4.0.0");
        capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL(hubURL), capabilities);
        System.out.println(driver);
    }

    @Test
    public void overrideDevicemode() {
        Augmenter augmenter = new Augmenter();
        driver = augmenter.augment(driver);

        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        devTools.send(Emulation.setDeviceMetricsOverride(414, 736, 50, true, Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty()));
        driver.get("http://whatismyscreenresolution.net/");
        String resolution = driver.findElement(By.id("resolution")).getText();

        System.out.println(resolution);
        if (resolution.equals("414x736")) {
            markStatus("passed", "Found screen resolution: 414x736", driver);
        } else {
            markStatus("failed", "Not found screen resolution: 414x736", driver);
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            driver.quit();

        } catch (

        Exception e) {
            markStatus("failed", "Got exception!", driver);
            e.printStackTrace();
            driver.quit();
        }
    }

    public static void markStatus(String status, String reason, WebDriver driver) {
        JavascriptExecutor jsExecute = (JavascriptExecutor) driver;
        jsExecute.executeScript("lambda-status=" + status);
        System.out.println(reason);
    }

}