package com.lambdatest;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.URL;
import java.util.HashMap;

/**
 * Base class for all test cases which will manage the driver.
 */
public class LamdaBaseTest {
    WebDriver driver;
    private static final String HUB_URL = "https://hub.lambdatest.com/wd/hub";

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        MutableCapabilities caps = new MutableCapabilities();
        HashMap<String, String> lambdaOptions = new HashMap<>();
        caps.setCapability("lt:options", lambdaOptions);
        try {
            driver = new RemoteWebDriver(new URL(HUB_URL), caps);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}
