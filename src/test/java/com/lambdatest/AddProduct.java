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

public class AddProduct {
    private RemoteWebDriver driver;

    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        //        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String username = System.getProperty("LT_USERNAME");
        //        String authKey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        String authKey = System.getProperty("LT_ACCESS_KEY");
        String hub = "@hub.lambdatest.com/wd/hub";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "win10");
        caps.setCapability("browserName", "chrome");
        caps.setCapability("version", "latest");
        caps.setCapability("build", System.getProperty("Build_Name") == null ? m.getName() + " - " + this.getClass().getName() : System.getProperty("Build_Name"));
        caps.setCapability("name", m.getName() + " - " + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");
        String[] Tags = new String[] { "Feature", "Falcon", "Severe" };
        caps.setCapability("tags", Tags);
        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authKey + hub), caps);
    }

    @Test
    public void addProducts() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        driver.findElement(By.cssSelector("shop-by-category")).click();
        driver.findElement(By.cssSelector(".mz-pure-drawer:first-of-type .navbar-nav>li:nth-of-type(3)")).click();
        driver.findElement(By.cssSelector("#container .manufacturer .mz-filter-group-content div:first-of-type div")).click();
        driver.findElement(By.cssSelector(".carousel-item:first-of-type [title='HTC Touch HD']"));
        driver.findElement(By.cssSelector("div[data-view_id='grid'] .product-layout:first-of-type button[title='Add to Cart']")).click();
        driver.findElement(By.cssSelector("#notification-box-top .btn-primary")).click();
        driver.findElement(By.cssSelector("#content .btn-secondary")).click();
        driver.findElement(By.xpath("//strong[contains(text(),'This is a dummy website for Web Automation Testing')]")).isSelected();
        Status = "Passed";
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
