package com.lambdatest;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class ProductFilters {
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
    public void productFilters() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        driver.findElement(By.cssSelector("#container input[name='mz_fp[min]']")).clear();
        driver.findElement(By.cssSelector("#container input[name='mz_fp[min]']")).sendKeys("0");
        driver.findElement(By.cssSelector("#container input[name='mz_fp[max]']")).clear();
        driver.findElement(By.cssSelector("#container input[name='mz_fp[max]']")).sendKeys("200");
        driver.findElement(By.cssSelector("#container input[name='mz_fp[max]']")).sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector("#container .manufacturer .mz-filter-group-content div:first-of-type div")).click();
        driver.findElement(By.cssSelector("#container .module-category a:nth-of-type(5)")).click();
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
