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

public class AddProduct {
    private RemoteWebDriver driver;
    WebDriverHelper driverHelper;


    //Elements
    protected static final By SHOP_BY_CATEGORY_NAVIGATION = By.className("shop-by-category");
    protected static final By PHONE_TABLETS_IPOD_NAVIGATION = By.cssSelector(".mz-pure-drawer:first-of-type .navbar-nav>li:nth-of-type(3)");
    protected static final By APPLE_MANUFACTURER_FILTER = By.cssSelector("#container .manufacturer .mz-filter-group-content div:first-of-type div");
    protected static final By FIRST_IPOD_PRODUCT = By.cssSelector(".carousel-item:first-of-type [title='iPod Touch']");
    protected static final By ADD_TO_CART_FIRST_PRODUCT = By.cssSelector("div[data-view_id='grid'] .product-layout:first-of-type button[title='Add to Cart']");
    protected static final By VIEW_CART_BUTTON_IN_BOX = By.cssSelector("#notification-box-top .btn-primary");
    protected static final By CONTINUE_SHOPPING_BUTTON = By.cssSelector("#content .btn-secondary");
    protected static final By WEBSITE_DISCLAIMER_HEADING_ON_HOME_PAGE = By.xpath("//strong[contains(text(),'This is a dummy website for Web Automation Testing')]");

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

    @Test public void addProducts() {
        driverHelper.getURL("https://ecommerce-playground.lambdatest.io/");
        driverHelper.waitForPresence(SHOP_BY_CATEGORY_NAVIGATION, 30);
        driverHelper.click(SHOP_BY_CATEGORY_NAVIGATION);
        driverHelper.click(PHONE_TABLETS_IPOD_NAVIGATION);
        driverHelper.click(APPLE_MANUFACTURER_FILTER);
        driverHelper.mouseHoverOnElement(FIRST_IPOD_PRODUCT);
        driverHelper.staleElementRefresh(FIRST_IPOD_PRODUCT);
        driverHelper.waitForTime(5);
        driverHelper.mouseHoverOnElement(FIRST_IPOD_PRODUCT);
        driverHelper.waitForClickable(ADD_TO_CART_FIRST_PRODUCT, 30);
        driverHelper.click(ADD_TO_CART_FIRST_PRODUCT);
        driverHelper.click(VIEW_CART_BUTTON_IN_BOX);
        driverHelper.waitForVisibility(CONTINUE_SHOPPING_BUTTON, 30);
        driverHelper.click(CONTINUE_SHOPPING_BUTTON);
        driverHelper.waitForVisibility(WEBSITE_DISCLAIMER_HEADING_ON_HOME_PAGE, 30);
        boolean value = driver.findElement(WEBSITE_DISCLAIMER_HEADING_ON_HOME_PAGE).isDisplayed();
        Assert.assertTrue(value, "Element is not displayed.");
        Status = "passed";
    }

    @AfterMethod public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
