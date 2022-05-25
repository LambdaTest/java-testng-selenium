package com.lambdatest;

import Utills.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterAccount {
    private RemoteWebDriver driver;
    WebDriverHelper driverHelper;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authKey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        String hub = "@hub.lambdatest.com/wd/hub";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "win10");
        caps.setCapability("browserName", "chrome");
        caps.setCapability("version", "latest");
        caps.setCapability("build", "TestNG With Java");
        caps.setCapability("name", m.getName() + " - " + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");
        String[] Tags = new String[] { "Feature", "Falcon", "Severe" };
        caps.setCapability("tags", Tags);
        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authKey + hub), caps);
        driverHelper = new WebDriverHelper(driver);
    }

    @Test(priority = 1)
    public void register() {
        driverHelper.getURL("https://ecommerce-playground.lambdatest.io/");
        driverHelper.click(By.cssSelector("#main-navigation a[href*='account/account']"));
        driverHelper.click(By.cssSelector("#column-right a[href*='account/register']"));
        driverHelper.waitForPresence(By.id("input-firstname"), 30);
        driverHelper.sendKeys(By.id("input-firstname"), "name");
        driverHelper.sendKeys(By.id("input-lastname"), "LastName");
        driverHelper.sendKeys(By.id("input-email"), "Email");
        driverHelper.sendKeys(By.id("input-telephone"), "Number");
        driverHelper.sendKeys(By.id("input-password"), "Password");
        driverHelper.sendKeys(By.id("input-confirm"), "Confirm password");
//        driverHelper.click(By.id("input-agree"));
        driverHelper.click(By.cssSelector("input[type='submit']"));
        Status = "Passed";
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
