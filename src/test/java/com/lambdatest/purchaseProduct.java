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

public class purchaseProduct {
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

    @Test
    public void purchaseProduct() {
        driverHelper.getURL("https://ecommerce-playground.lambdatest.io/");
        driverHelper.click(By.cssSelector("shop-by-category"));
        driverHelper.click(By.cssSelector(".mz-pure-drawer:first-of-type .navbar-nav>li:nth-of-type(3)"));
        driverHelper.click(By.cssSelector("#container .manufacturer .mz-filter-group-content div:first-of-type div"));
        driverHelper.mouseHoverOnElement(By.cssSelector(".carousel-item:first-of-type [title='iPod Touch']"));
        driverHelper.click(By.cssSelector("div[data-view_id='grid'] .product-layout:first-of-type button[title='Add to Cart']"));
        driverHelper.click(By.cssSelector("#notification-box-top .btn-primary"));
        driverHelper.click(By.cssSelector("#content a[href*='checkout/checkout']"));
        driverHelper.waitForPresence(By.id("input-payment-firstname"), 30);
        driverHelper.sendKeys(By.id("input-payment-firstname"), "Name");
        driverHelper.sendKeys(By.id("input-payment-lastname"), "LastName");
        driverHelper.sendKeys(By.id("input-payment-email"), "Email");
        driverHelper.sendKeys(By.id("input-payment-telephone"), "Number");
        driverHelper.sendKeys(By.id("input-payment-password"), "Password");
        driverHelper.sendKeys(By.id("input-payment-confirm"), "Confirm password");

        driverHelper.sendKeys(By.id("input-payment-company"), "Company name");
        driverHelper.sendKeys(By.id("input-payment-address-1"), "Address One");
        driverHelper.sendKeys(By.id("input-payment-address-2"), "Address Two");
        driverHelper.sendKeys(By.id("input-payment-city"), "City");
        driverHelper.sendKeys(By.id("input-payment-postcode"), "Postal code");

        driverHelper.selectDropDownByValue(By.id("input-payment-country"), "India");
        driverHelper.selectDropDownByValue(By.id("input-payment-zone"), "Delhi");

        driverHelper.click(By.id("input-account-agree"));
        driverHelper.click(By.id("input-agree"));
        driverHelper.click(By.id("button-save"));
        driverHelper.isDisplayed(By.className("page-title"));
        driverHelper.click(By.id("button-confirm"));
        String orderStatus = driverHelper.getText(By.id("page-title"));
        Assert.assertEquals(orderStatus, "Your order has been placed!");
        Status = "Passed";
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
