package com.lambdatest;

import Utills.UtilsMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
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
    public void purchaseProduct() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        driver.findElement(By.cssSelector("shop-by-category")).click();
        driver.findElement(By.cssSelector(".mz-pure-drawer:first-of-type .navbar-nav>li:nth-of-type(3)")).click();
        driver.findElement(By.cssSelector("#container .manufacturer .mz-filter-group-content div:first-of-type div")).click();
        methods.mouseHoverOnElement(driver, By.cssSelector(".carousel-item:first-of-type [title='iPod Touch']"));
        driver.findElement(By.cssSelector("div[data-view_id='grid'] .product-layout:first-of-type button[title='Add to Cart']")).click();
        driver.findElement(By.cssSelector("#notification-box-top .btn-primary")).click();
        driver.findElement(By.cssSelector("#content a[href*='checkout/checkout']")).click();
        methods.wait(driver, By.id("input-payment-firstname"), 30);
        driver.findElement(By.id("input-payment-firstname")).sendKeys("name");
        driver.findElement(By.id("input-payment-lastname")).sendKeys("LastName");
        driver.findElement(By.id("input-payment-email")).sendKeys("Email");
        driver.findElement(By.id("input-payment-telephone")).sendKeys("Number");
        driver.findElement(By.id("input-payment-password")).sendKeys("Password");
        driver.findElement(By.id("input-payment-confirm")).sendKeys("Confirm password");

        driver.findElement(By.id("input-payment-company")).sendKeys("Company name");
        driver.findElement(By.id("input-payment-address-1")).sendKeys("Address One");
        driver.findElement(By.id("input-payment-address-2")).sendKeys("Address Two");
        driver.findElement(By.id("input-payment-city")).sendKeys("City");
        driver.findElement(By.id("input-payment-postcode")).sendKeys("Postal code");
        Select select = new Select(driver.findElement(By.id("input-payment-country")));
        select.selectByValue("India");
        Select select1 = new Select(driver.findElement(By.id("input-payment-zone")));
        select1.selectByValue("Delhi");
        driver.findElement(By.id("input-account-agree")).click();
        driver.findElement(By.id("input-agree")).click();
        driver.findElement(By.id("button-save")).click();
        driver.findElement(By.className("page-title")).isDisplayed();
        driver.findElement(By.id("button-confirm")).click();
        String orderStatus = driver.findElement(By.id("page-title")).getText();
        Assert.assertEquals(orderStatus, "Your order has been placed!");
        Status = "Passed";
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
