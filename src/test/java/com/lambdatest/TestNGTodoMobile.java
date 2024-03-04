package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

import io.appium.java_client.ios.IOSDriver;
import org.json.simple.JSONObject;
//import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodoMobile {

    private IOSDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        ;
        String hub = "@mobile-hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("w3c", true);
        ltOptions.put("platformName", "ios");
        ltOptions.put("deviceName", "iphone 13");
        ltOptions.put("platformVersion", "16");
        ltOptions.put("isRealMobile", true);
        ltOptions.put("udid", "abcd123");
        ltOptions.put("build", "Apple Pay");
        ltOptions.put("privateCloud", true);
        ltOptions.put("applePay", true);
        ltOptions.put("passcode", "123456");
        ltOptions.put("applePayCardType", new String[] { "discover" });
        caps.setCapability("lt:options", ltOptions);
        // add username and access key in below URL
        driver = new IOSDriver(new URL("https://username:accessKey@mobile-hub.lambdatest.com/wd/hub"), caps);
    }

    @Test
    public void basicTest() throws InterruptedException {
        driver.get("https://applepaydemo.apple.com/");
        Thread.sleep(5000);

        String pageSource = driver.getPageSource();
        WebElement ele = driver.findElement(By.id("transcriptButton"));
        ele.getRect();

        int centerX = ele.getRect().x + (ele.getSize().width / 2);
        int centerY = ele.getRect().y + (ele.getSize().height / 2);
        System.out.println("centerX" + centerX);
        System.out.println("centerY" + centerY);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), centerX, centerY));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
// Lambda Hook for assistive touch
        JSONObject json = new JSONObject();
        json.put("confirm", true);
        ((JavascriptExecutor) driver).executeScript("lambda-applepay", json);
        new Actions(driver).sendKeys("123456").perform();

        Thread.sleep(3000);
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}