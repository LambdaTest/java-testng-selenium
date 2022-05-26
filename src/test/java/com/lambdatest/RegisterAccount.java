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

    //Elements
    protected static final By MY_ACCOUNT_DROP_DOWN = By.cssSelector("#main-navigation a[href*='account/account']");
    protected static final By REGISTER_LINK_TEXT = By.cssSelector("#column-right a[href*='account/register']");
    protected static final By FIRST_NAME_INPUT_FIELD = By.id("input-firstname");
    protected static final By LAST_NAME_INPUT_FIELD = By.id("input-lastname");
    protected static final By EMAIL_INPUT_FIELD = By.id("input-email");
    protected static final By TELEPHONE_INPUT_FIELD = By.id("input-telephone");
    protected static final By PASSWORD_INPUT_FIELD = By.id("input-password");
    protected static final By CONFIRM_PASSWORD_INPUT_FIELD = By.id("input-confirm");
    protected static final By PRIVACY_POLICY_CHECKBOX = By.id("input-agree");
    protected static final By CONTINUE_BUTTON = By.id("input[type='submit']");
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

    @Test(priority = 1) public void register() {
        driverHelper.getURL("https://ecommerce-playground.lambdatest.io/");
        driverHelper.click(MY_ACCOUNT_DROP_DOWN);
        driverHelper.click(REGISTER_LINK_TEXT);
        driverHelper.waitForPresence(FIRST_NAME_INPUT_FIELD, 30);
        driverHelper.sendKeys(FIRST_NAME_INPUT_FIELD, "name");
        driverHelper.sendKeys(LAST_NAME_INPUT_FIELD, "LastName");
        driverHelper.sendKeys(EMAIL_INPUT_FIELD, "Email");
        driverHelper.sendKeys(TELEPHONE_INPUT_FIELD, "Number");
        driverHelper.sendKeys(PASSWORD_INPUT_FIELD, "Password");
        driverHelper.sendKeys(CONFIRM_PASSWORD_INPUT_FIELD, "Confirm password");
        //        driverHelper.click(PRIVACY_POLICY_CHECKBOX);
        driverHelper.click(CONTINUE_BUTTON);
        Status = "Passed";
    }

    @AfterMethod public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
