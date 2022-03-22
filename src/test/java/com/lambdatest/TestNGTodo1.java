package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodo1 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "madhanshanmugam234" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "1tkRas4MYfc3VIj4N9E16QIh8ZEUixjK4IPgX4DXiob3CdkBuV" : System.getenv("LT_ACCESS_KEY");
        ;
        String hub = "https://madhanshanmugam234:1tkRas4MYfc3VIj4N9E16QIh8ZEUixjK4IPgX4DXiob3CdkBuV@hub.lambdatest.com/wd/hub";
	
        DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("build", "Lambda Test");
		capabilities.setCapability("name", "simple form demo test");
		capabilities.setCapability("platform", "Windows 10");
		capabilities.setCapability("browserName", "Firefox");
		capabilities.setCapability("version","97.0");
        caps.setCapability("name", m.getName() + " - " + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");

        String[] Tags = new String[] { "Feature", "Falcon", "Severe" };
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);

    }

    @Test
    public void basicTest() throws InterruptedException {
        String url = " https://www.lambdatest.com/selenium-playground/ ";
		String ExpectedText = "welcome to lambda test";

		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.get(url);
		driver.findElement(By.linkText("Simple Form Demo")).click();	
		String actualUrl = driver.getCurrentUrl();
		if (actualUrl.equals("https://www.lambdatest.com/selenium-playground/simple-form-demo"))  
		{  
		System.out.println("Verification Successful - The correct Url is opened.");  
		}  
		else  
		{  
		System.out.println("Verification Failed - An incorrect Url is opened.");  
		}  
	   WebElement Text = driver.findElement(By.id("user-message"));  
	   Text.sendKeys(ExpectedText);
	   String ActualText = Text.getText();
	   driver.findElement(By.id("showInput")).click();
		
		if (ActualText.equals(ExpectedText))
		{
			System.out.println("valid text");
		}
		else
		{
			System.out.println("invalid text");
		}		

        System.out.println("TestFinished");

    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}
