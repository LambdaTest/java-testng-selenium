package com.lambdatest;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class SampleAutomation {

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
        caps.setCapability("build", "TestNG With Java");
        caps.setCapability("name", m.getName() + " - " + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");

        String[] Tags = new String[] { "Feature", "Falcon", "Severe" };
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authKey + hub), caps);

    }
    public void wait(By locator, int waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @Test(priority = 1)
    public void register() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        driver.findElement(By.cssSelector("#main-navigation a[href*='account/account']")).click();
        driver.findElement(By.cssSelector("#column-right a[href*='account/register']")).click();
        wait(By.id("input-firstname"), 30);
        driver.findElement(By.id("input-firstname")).sendKeys("name");
        driver.findElement(By.id("input-lastname")).sendKeys("LastName");
        driver.findElement(By.id("input-email")).sendKeys("Email");
        driver.findElement(By.id("input-telephone")).sendKeys("Number");
        driver.findElement(By.id("input-password")).sendKeys("Password");
        driver.findElement(By.id("input-confirm")).sendKeys("Confirm password");
//        driver.findElement(By.id("input-agree")).click();
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        System.out.println("TestFinished");
    }

//    @Test(priority = 2)
    public void searchProduct() {
        driver.findElement(By.cssSelector("#main-header [placeholder='Search For Products']")).sendKeys("i pod Nano");
        driver.findElement(By.cssSelector("#search .dropdown ul>li:first-of-type")).click();
        driver.findElement(By.cssSelector(".order-1 .btn-cart")).click();
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
