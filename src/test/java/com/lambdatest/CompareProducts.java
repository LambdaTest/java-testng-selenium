package com.lambdatest;

import Utills.UtilsMethods;
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

public class CompareProducts {
    private RemoteWebDriver driver;
    UtilsMethods methods = new UtilsMethods();

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
    }

    @Test
    public void compareProducts() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        driver.findElement(By.cssSelector("shop-by-category")).click();
        driver.findElement(By.cssSelector(".mz-pure-drawer:first-of-type .navbar-nav>li:nth-of-type(3)")).click();
        driver.findElement(By.cssSelector(".carousel-item:first-of-type [title='HTC Touch HD']"));
        methods.mouseHoverOnElement(driver, By.cssSelector(".carousel-item:first-of-type [title='HTC Touch HD']"));
        driver.findElement(By.cssSelector("div[data-view_id='grid'] .product-layout:first-of-type button[title='Compare this Product']")).click();
        driver.findElement(By.cssSelector("#container .manufacturer .mz-filter-group-content div:first-of-type div")).click();
        methods.mouseHoverOnElement(driver, By.cssSelector(".carousel-item:first-of-type [title='iPod Touch']"));
        driver.findElement(By.cssSelector("div[data-view_id='grid'] .product-layout:first-of-type button[title='Compare this Product']")).click();
        driver.findElement(By.cssSelector("#notification-box-top a.btn-secondary")).click();
        methods.wait(driver, By.cssSelector(".h4"), 30);
        Status = "Passed";
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
