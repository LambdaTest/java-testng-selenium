package com.lambdatest;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;

public class TestNGRelativeLocator {

    private WebDriver driver;

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {

        String hubURL = "https://hub.lambdatest.com/wd/hub";

        String[] Tags = new String[] { "Feature", "Falcon", "Severe" };

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
        ltOptions.put("tags", Tags);
        capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL(hubURL), capabilities);
        System.out.println(driver);
    }

    @Test
    public void testNGRelativeLocator() throws InterruptedException {

        driver.get("http://cookbook.seleniumacademy.com/mobilebmicalculator.html");

        // find the height and weight labels using css selector
        WebElement heightLabel = driver.findElement(By.cssSelector("label[for='heightCMS']"));
        WebElement weightLabel = driver.findElement(By.cssSelector("label[for='weightKg']"));

        // find the height input using toRightOf relative locator
        // input is right of height label
        WebElement heightInput = driver.findElement(with(By.tagName("input"))
                .toRightOf(heightLabel));

        heightInput.sendKeys("181");
        Thread.sleep(3000);

        // find the weight input using combination of below and toRightOf relative
        // locator
        // weight input is below height input and right of weight label
        WebElement weightInput = driver.findElement(with(By.tagName("input"))
                .below(heightInput).toRightOf(weightLabel));

        weightInput.sendKeys("75");
        Thread.sleep(3000);

        // find the calculate button which is below the weight label
        WebElement calculateButton = driver.findElement(with(By.tagName("input"))
                .below(weightLabel));

        calculateButton.click();
        Thread.sleep(3000);

        // find the read only input below calculate button to verify value
        if ("22.9".equalsIgnoreCase(driver.findElement(with(By.tagName("input"))
                .below(calculateButton)).getAttribute("value"))) {
            markStatus("passed", "Value Verified", driver);
            Thread.sleep(150);

            System.out.println("TestFinished");
        } else {
            markStatus("failed", "Wrong calculation", driver);
        }

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    public static void markStatus(String status, String reason, WebDriver driver) {
        JavascriptExecutor jsExecute = (JavascriptExecutor) driver;
        jsExecute.executeScript("lambda-status=" + status);
        System.out.println(reason);
    }

}