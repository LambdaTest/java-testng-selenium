package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v95.network.Network;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EmulateNetworkConditions {
    public static String hubURL = "https://hub.lambdatest.com/wd/hub";

    private WebDriver driver;

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "latest");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("user", System.getenv("LT_USERNAME"));
        ltOptions.put("accessKey", System.getenv("LT_ACCESS_KEY"));
        ltOptions.put("build", "Selenium 4");
        ltOptions.put("name", this.getClass().getName());
        ltOptions.put("platformName", "Windows 10");
        ltOptions.put("seCdp", true);
        ltOptions.put("selenium_version", "4.0.0");
        capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL(hubURL), capabilities);

        System.out.println(driver);
    }

    @Test
    public void emulateNetworkConditions() throws InterruptedException {
        Augmenter augmenter = new Augmenter();
        driver = augmenter.augment(driver);
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        devTools.send(Network.emulateNetworkConditions(false, 0, 2 * 1024 * 1024, 2 * 1024 * 1024, Optional.empty()));
        driver.get("https://fast.com");
        Thread.sleep(15000);
        String speed = driver.findElement(By.id("speed-value")).getText();
        String units = driver.findElement(By.id("speed-units")).getText();
        System.out.println(speed);
        System.out.println(units);
        if (Float.parseFloat(speed) < 2 && units.equals("Mbps")) {
            markStatus("passed", "Speed is in the limit of 2 Mbps", driver);
        } else {
            markStatus("failed", "Speed is not in the limit of 2 Mbp", driver);
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