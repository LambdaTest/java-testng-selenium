package com.lambdatest;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.reflect.Method;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AutoHealingDemo
{
    RemoteWebDriver driver = null;
    public static String status = "passed";
    public static String username = System.getenv("LT_USERNAME");
    public static String access_key = System.getenv("LT_ACCESS_KEY");


    @BeforeMethod
    @Parameters(value={"browser","version","platform", "resolution"})
    public void testSetUp(String browser, String version, String platform, String resolution,Method m, ITestContext ctx) throws Exception
    {
        
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("build", "Demonstration of the AutoHeal");
        // capabilities.setCapability("platform", "Windows 10"));
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("name",m.getName() );
        // capabilities.setCapability("version", version);

        // capabilities.setCapability("tunnel",false);
        // capabilities.setCapability("network",true);
        // capabilities.setCapability("console",true);
        capabilities.setCapability("visual",true);
        capabilities.setCapability("autoHeal", System.getProperty("autoheal", "true"));

        try
        {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"), capabilities);
        }
        catch (MalformedURLException e)
        {
            System.out.println("Invalid grid URL");
        }
        System.out.println("Started session");
    }

    @Test()
    public void autoHealBaseTestWithoutChangedDOM() throws InterruptedException
    {

        try {

            driver.get("https://www.lambdatest.com/selenium-playground/auto-healing");
            Thread.sleep(5000);

            driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"AutoHealWithoutDomChanged-Login Case\", \"level\": \"info\"}}");
    
            WebElement changedom = driver.findElementByXPath("//*[contains(text(), 'Change DOM ID')]");
            // changedom.click();      //Uncomment this line in the 2nd test run for the autoheal to work. 
    
            WebElement username = driver.findElementById("username");
            username.sendKeys("test@gmail.com");
        
            WebElement password = driver.findElementById("password");
            password.sendKeys("password");
    
            WebElement login = driver.findElementByXPath("//*[contains(text(), 'Submit')]");
            login.click();
            System.setProperty("autoheal", "false");

        } catch (Exception e) {
            status = "failed";
            System.setProperty("autoheal", "false");
        }
    }

    @Test()
    public void autoHealFalseWithChangedDOM() throws InterruptedException
    {

        try {
            

            driver.get("https://www.lambdatest.com/selenium-playground/auto-healing");
            Thread.sleep(5000);

            driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"AutoHealWithoutDomChanged-Login Case\", \"level\": \"info\"}}");
    
            WebElement changedom = driver.findElementByXPath("//*[contains(text(), 'Change DOM ID')]");
            changedom.click();      //Uncomment this line in the 2nd test run for the autoheal to work. 
    
            WebElement username = driver.findElementById("username");
            username.sendKeys("test@gmail.com");
        
            WebElement password = driver.findElementById("password");
            password.sendKeys("password");
    
            WebElement login = driver.findElementByXPath("//*[contains(text(), 'Submit')]");
            login.click();
            System.setProperty("autoheal", "true");

        } catch (Exception e) {
            status = "failed";
            System.setProperty("autoheal", "true");
        }
    }

    @Test()
    public void autoHealedWithChangedDOM() throws InterruptedException
    {

        try {

            driver.get("https://www.lambdatest.com/selenium-playground/auto-healing");
            Thread.sleep(5000);
            
            driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"AutoHealDomChanged-Login Case\", \"level\": \"info\"}}");

            WebElement changedom = driver.findElementByXPath("//*[contains(text(), 'Change DOM ID')]");
            changedom.click();     
    
            WebElement username = driver.findElementById("username");
            username.sendKeys("test@gmail.com");
        
            WebElement password = driver.findElementById("password");
            password.sendKeys("password");
    
            WebElement login = driver.findElementByXPath("//*[contains(text(), 'Submit')]");
            login.click();
            status="passed";

        } catch (Exception e) {
            status = "failed";
        }



    }

    @AfterMethod
    public void tearDown()
    {
        if (driver != null)
        {    driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Completed-Login Case - Closing Browser\", \"level\": \"info\"}}");
             driver.executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}