package com.lambdatest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.Supplier;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class InterceptNetwork {
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

        driver = new RemoteWebDriver(new URL(hubURL), capabilities);
        System.out.println(driver);
    }

    @Test
    public void interceptNetwork() {
        Augmenter augmenter = new Augmenter();
        driver = augmenter.augment(driver);

        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        Supplier<InputStream> message = () -> new ByteArrayInputStream(
                "Creamy, delicious cheese!".getBytes(StandardCharsets.UTF_8));

        NetworkInterceptor interceptor = new NetworkInterceptor(
                driver,
                Route.matching(req -> true)
                        .to(() -> req -> new HttpResponse()
                                .setStatus(200)
                                .addHeader("Content-Type", StandardCharsets.UTF_8.toString())
                                .setContent(message)));
        driver.get("https://example-sausages-site.com");
        String source = driver.getPageSource();
        System.out.println(source);

        if (source.contains("delicious cheese!")) {
            markStatus("passed", "Source contains the contents", driver);
        } else {
            markStatus("failed", "Content was not found in the source", driver);
        }
        interceptor.close();

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