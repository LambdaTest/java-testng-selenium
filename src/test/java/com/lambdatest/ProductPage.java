package com.lambdatest;

import Utills.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class ProductPage {
    private RemoteWebDriver driver;
    WebDriverHelper driverHelper;

    //Elements
    protected static final By SHOP_BY_CATEGORY_NAVIGATION = By.className("shop-by-category");
    protected static final By PHONE_TABLETS_IPOD_NAVIGATION = By.cssSelector(
        ".mz-pure-drawer:first-of-type .navbar-nav>li:nth-of-type(3)");
    protected static final By APPLE_MANUFACTURER_FILTER = By.cssSelector(
        "#container .manufacturer .mz-filter-group-content div:first-of-type div");
    protected static final By PRODUCT_PAGE_IMAGE = By.cssSelector("[title='Tablets']");

    private String Status = "failed";

    @BeforeMethod public void setup(Method m, ITestContext ctx) throws MalformedURLException {
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

    @Test public void productPage() {
        driverHelper.getURL("https://ecommerce-playground.lambdatest.io/");
        driverHelper.click(SHOP_BY_CATEGORY_NAVIGATION);
        driverHelper.click(PHONE_TABLETS_IPOD_NAVIGATION);
        driverHelper.click(APPLE_MANUFACTURER_FILTER);
        Assert.assertTrue(driverHelper.isDisplayed(PRODUCT_PAGE_IMAGE), "Product page is not displayed.");
        ;
        Status = "Passed";
    }

    @AfterMethod public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
