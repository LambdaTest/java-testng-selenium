package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodo3 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");

        String hub = "@hub.lambdatest.com/wd/hub";

        // ✅ W3C-compliant capabilities using ChromeOptions + LT:Options
        MutableCapabilities ltOptions = new MutableCapabilities();
        ltOptions.setCapability("build", "TestNG With Java");
        ltOptions.setCapability("name", m.getName() + " - " + this.getClass().getName());
        ltOptions.setCapability("platformName", "macOS Sonoma"); // updated OS name
        ltOptions.setCapability("plugin", "git-testng");
        ltOptions.setCapability("tags", new String[] { "Feature", "Tag", "Moderate" });

        // ✅ Chrome-specific options
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setBrowserVersion("latest");
        browserOptions.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), browserOptions);
    }

    @Test
    public void basicTest() throws InterruptedException {
        System.out.println("Loading URL...");
        driver.get("https://lambdatest.github.io/sample-todo-app/");
        Thread.sleep(200);

        System.out.println("Checking Boxes...");
        driver.findElement(By.name("li1")).click();
        driver.findElement(By.name("li2")).click();
        driver.findElement(By.name("li3")).click();
        driver.findElement(By.name("li4")).click();

        System.out.println("Adding New Items...");
        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 6");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 7");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 8");
        driver.findElement(By.id("addbutton")).click();

        System.out.println("Checking More Boxes...");
        driver.findElement(By.name("li1")).click();
        driver.findElement(By.name("li3")).click();
        driver.findElement(By.name("li7")).click();
        driver.findElement(By.name("li8")).click();

        System.out.println("Adding Final Item...");
        driver.findElement(By.id("sampletodotext")).sendKeys("Get Taste of Lambda and Stick to It");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.name("li9")).click();

        // ✅ Safer and cleaner XPath
        String spanText = driver.findElement(By.xpath("//li[9]/span")).getText();
        Assert.assertEquals(spanText, "Get Taste of Lambda and Stick to It");

        Status = "passed";
        Thread.sleep(300);
        System.out.println("✅ Test Finished Successfully");
    }

    @AfterMethod
    public void tearDown() {
        try {
            driver.executeScript("lambda-status=" + Status);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
