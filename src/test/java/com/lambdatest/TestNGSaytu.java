package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGSaytu {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "oumar.kante" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "NlRyACNZli05jmvwAIdKcxjNWXT5mB490LAqlCpyjzPDFURy8J" : System.getenv("LT_ACCESS_KEY");
        ;
        
        /*
        Steps to run Smart UI project (https://beta-smartui.lambdatest.com/)
        Step - 1 : Change the hub URL to @beta-smartui-hub.lambdatest.com/wd/hub
        Step - 2 : Add "smartUI.project": "<Project Name>" as a capability above
        Step - 3 : Add "((JavascriptExecutor) driver).executeScript("smartui.takeScreenshot");" code wherever you need to take a screenshot
        Note: for additional capabilities navigate to https://www.lambdatest.com/support/docs/test-settings-options/
        */

        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("browserName", "firefox");
        caps.setCapability("version", "latest");
        caps.setCapability("build", "TestNG With Java");
        caps.setCapability("name", m.getName() + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");

        /*
        Enable Smart UI Project
        caps.setCapability("smartUI.project", "<Project Name>");
        */

        String[] Tags = new String[] { "Feature", "Magicleap", "Severe" };
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
    }

    @Test
    Run|Debug
    public void basicTest() throws InterruptedException {
        System.out.println("Loading Url");

// Ouvrez la page de connexion
/*driver.get("https://frontend.saytutension.baamtuservices.com/login");

// Entrez les informations d'identification
driver.findElement(By.id("email")).sendKeys("adminlocalhost@gmail.com");
driver.findElement(By.id("password")).sendKeys("fyc2ELY&07z1Q7");

// Cliquez sur le bouton de connexion
//driver.findElement(By.id("button")).click();
driver.findElement(By.xpath("//button[@class='w-full p-2 text-white bg-primary-saytu rounded-lg my-6\']")).click();
*/

// Test name: New Test
    // Step # | name | target | value
    // 1 | open | https://frontend.saytutension.baamtuservices.com/home/community-health-worker | 
    driver.get("https://frontend.saytutension.baamtuservices.com/home/community-health-worker");
    // 2 | click | id=email | 
    driver.findElement(By.id("email")).click();
    // 3 | type | id=email | adminlocalhost@gmail.com
    driver.findElement(By.id("email")).sendKeys("adminlocalhost@gmail.com");
    // 4 | click | id=password | 
    driver.findElement(By.id("password")).click();
    // 5 | type | id=password | fyc2ELY&07z1Q7
    driver.findElement(By.id("password")).sendKeys("fyc2ELY&07z1Q7");
    // 6 | click | css=.bg-primary-saytu | 
    driver.findElement(By.cssSelector(".bg-primary-saytu")).click();
    // 7 | mouseOver | linkText=Administrateurs | 
    {
      WebElement element = driver.findElement(By.linkText("Administrateurs"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    // 8 | click | linkText=Agents Communautaires | 
    driver.findElement(By.linkText("Agents Communautaires")).click();
    // 9 | click | css=.btn-primary | 
    driver.findElement(By.cssSelector(".btn-primary")).click();
    // 10 | click | id=grid-last-name | 
    driver.findElement(By.id("grid-last-name")).click();
    // 11 | type | id=grid-last-name | oumar
    driver.findElement(By.id("grid-last-name")).sendKeys("oumar");
    // 12 | click | id=grid-first-name | 
    driver.findElement(By.id("grid-first-name")).click();
    // 13 | type | id=grid-first-name | test
    driver.findElement(By.id("grid-first-name")).sendKeys("test");
    // 14 | click | id=grid-email | 
    driver.findElement(By.id("grid-email")).click();
    // 15 | type | id=grid-email | teststaging@gmail.com
    driver.findElement(By.id("grid-email")).sendKeys("teststaging@gmail.com");
    // 16 | click | id=grid-phone | 
    driver.findElement(By.id("grid-phone")).click();
    // 17 | type | id=grid-phone | 774180088
    driver.findElement(By.id("grid-phone")).sendKeys("774180088");
    // 18 | click | id=grid-birthday | 
    driver.findElement(By.id("grid-birthday")).click();
    // 19 | type | id=grid-birthday | 2024-05-01
    driver.findElement(By.id("grid-birthday")).sendKeys("2024-05-01");
    // 20 | click | id=grid-region | 
    driver.findElement(By.id("grid-region")).click();
    // 21 | select | xpath=(//section[@id='home-outlet']/app-community-health-worker-update/main/form/div[2]/div[3]/select)[1] | label=Dakar
    {
      WebElement dropdown = driver.findElement(By.id("grid-region"));
      dropdown.findElement(By.xpath("//option[. = 'Dakar']")).click();
    }
    // 16 | click | id=grid-district | 
    driver.findElement(By.id("grid-district")).click();
    // 17 | select | id=grid-district | label=Dakar Sud
    {
      WebElement dropdown = driver.findElement(By.id("grid-district"));
      dropdown.findElement(By.xpath("//option[. = 'Dakar Sud']")).click();
    }
    // 24 | click | css=.btn-primary | 
    driver.findElement(By.cssSelector(".btn-primary")).click();
// Vérifiez si vous êtes connecté en vérifiant un élément sur la page suivante
//boolean isLoggedIn = driver.findElement(By.id("welcomeMessage")).isDisplayed();
//if(isLoggedIn) {
 //   System.out.println("Connexion réussie !");
//} else {
  //  System.out.println("Échec de la connexion !");
//}

// Fermez le navigateur
// driver.quit();

}



    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}






