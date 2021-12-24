package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v95.emulation.Emulation;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EmulateGeolocation {

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

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("googlegeolocationaccess.enabled", true);
        prefs.put("profile.default_content_setting_values.geolocation", 1); // 1:allow 2:block
        prefs.put("profile.default_content_setting_values.notifications", 1);
        prefs.put("profile.managed_default_content_settings", 1);
        options.setExperimentalOption("prefs", prefs);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new RemoteWebDriver(new URL(hubURL), capabilities);

        System.out.println(driver);
    }

    @Test
    public void emulateGeolocation() {
        Augmenter augmenter = new Augmenter();
        driver = augmenter.augment(driver);

        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        // setGeolocationOverride() takes input lattitude, longitude and accuracy as
        // parameters.
        devTools.send(Emulation.setGeolocationOverride(Optional.of(28.622409),
                Optional.of(77.364925),
                Optional.of(1)));
        driver.get("https://my-location.org");

        String address = driver.findElement(By.id("address")).getText();
        System.out.println(address);
        if (address.contains("Noida")) {
            markStatus("passed", "I am in Noida", driver);
        } else {
            markStatus("failed", "I am not in Noida", driver);
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