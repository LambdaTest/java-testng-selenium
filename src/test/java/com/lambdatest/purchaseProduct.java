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
    //Elements
    protected static final By SHOP_BY_CATEGORY_NAVIGATION = By.className("shop-by-category");
    protected static final By PHONE_TABLETS_IPOD_NAVIGATION = By.cssSelector(
        ".mz-pure-drawer:first-of-type .navbar-nav>li:nth-of-type(3)");
    protected static final By APPLE_MANUFACTURER_FILTER = By.cssSelector(
        "#container .manufacturer .mz-filter-group-content div:first-of-type div");
    protected static final By FIRST_IPOD_PRODUCT = By.cssSelector(".carousel-item:first-of-type [title='iPod Touch']");
    protected static final By ADD_TO_CART_FIRST_PRODUCT = By.cssSelector(
        "div[data-view_id='grid'] .product-layout:first-of-type button[title='Add to Cart']");
    protected static final By VIEW_CART_BUTTON_IN_BOX = By.cssSelector("#notification-box-top .btn-primary");
    protected static final By CHECKOUT_BUTTON = By.cssSelector("#content a[href*='checkout/checkout']");
    protected static final By FIRST_NAME_INPUT_FIELD = By.id("input-payment-firstname");
    protected static final By LAST_NAME_INPUT_FIELD = By.id("input-payment-lastname");
    protected static final By EMAIL_NAME_INPUT_FIELD = By.id("input-payment-email");
    protected static final By TELEPHONE_NAME_INPUT_FIELD = By.id("input-payment-telephone");
    protected static final By PASSWORD_NAME_INPUT_FIELD = By.id("input-payment-password");
    protected static final By CONFIRM_PASSWORD_NAME_INPUT_FIELD = By.id("input-payment-confirm");
    protected static final By COMPANY_NAME_NAME_INPUT_FIELD = By.id("input-payment-company");
    protected static final By ADDRESS_ONE_NAME_INPUT_FIELD = By.id("input-payment-address-1");
    protected static final By ADDRESS_TWO_NAME_INPUT_FIELD = By.id("input-payment-address-2");
    protected static final By CITY_NAME_INPUT_FIELD = By.id("input-payment-city");
    protected static final By POST_CODE_NAME_INPUT_FIELD = By.id("input-payment-postcode");
    protected static final By COUNTRY_DROP_DOWN = By.id("input-payment-country");
    protected static final By REGION_DROP_DOWN = By.id("input-payment-zone");
    protected static final By I_AGREE_PRIVACY_POLICY_CHECKBOX = By.id("input-account-agree");
    protected static final By I_AGREE_TERMS_AND_CONDITION_CHECKBOX = By.id("input-agree");
    protected static final By CONTINUE_BUTTON = By.id("button-save");
    protected static final By PAGE_TITLE = By.className("page-title");
    protected static final By CONFIRM_ORDER_BUTTON = By.id("button-confirm");

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

    @Test public void purchaseProduct() {
        driverHelper.getURL("https://ecommerce-playground.lambdatest.io/");
        driverHelper.click(SHOP_BY_CATEGORY_NAVIGATION);
        driverHelper.click(PHONE_TABLETS_IPOD_NAVIGATION);
        driverHelper.click(APPLE_MANUFACTURER_FILTER);
        driverHelper.mouseHoverOnElement(FIRST_IPOD_PRODUCT);
        driverHelper.click(ADD_TO_CART_FIRST_PRODUCT);
        driverHelper.click(VIEW_CART_BUTTON_IN_BOX);
        driverHelper.click(CHECKOUT_BUTTON);
        driverHelper.waitForPresence(FIRST_NAME_INPUT_FIELD, 30);
        driverHelper.sendKeys(FIRST_NAME_INPUT_FIELD, "Name");
        driverHelper.sendKeys(LAST_NAME_INPUT_FIELD, "LastName");
        driverHelper.sendKeys(EMAIL_NAME_INPUT_FIELD, "Email");
        driverHelper.sendKeys(TELEPHONE_NAME_INPUT_FIELD, "Number");
        driverHelper.sendKeys(PASSWORD_NAME_INPUT_FIELD, "Password");
        driverHelper.sendKeys(CONFIRM_PASSWORD_NAME_INPUT_FIELD, "Confirm password");
        driverHelper.sendKeys(COMPANY_NAME_NAME_INPUT_FIELD, "Company name");
        driverHelper.sendKeys(ADDRESS_ONE_NAME_INPUT_FIELD, "Address One");
        driverHelper.sendKeys(ADDRESS_TWO_NAME_INPUT_FIELD, "Address Two");
        driverHelper.sendKeys(CITY_NAME_INPUT_FIELD, "City");
        driverHelper.sendKeys(POST_CODE_NAME_INPUT_FIELD, "Postal code");
        driverHelper.selectDropDownByValue(COUNTRY_DROP_DOWN, "India");
        driverHelper.selectDropDownByValue(REGION_DROP_DOWN, "Delhi");
        driverHelper.click(I_AGREE_PRIVACY_POLICY_CHECKBOX);
        driverHelper.click(I_AGREE_TERMS_AND_CONDITION_CHECKBOX);
        driverHelper.click(CONTINUE_BUTTON);
        driverHelper.isDisplayed(PAGE_TITLE);
        driverHelper.click(CONFIRM_ORDER_BUTTON);
        String orderStatus = driverHelper.getText(PAGE_TITLE);
        Assert.assertEquals(orderStatus, "Your order has been placed!");
        Status = "Passed";
    }

    @AfterMethod public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
