# TestNG with Selenium
***

![testng-tutorial](https://user-images.githubusercontent.com/70570645/170252665-d1592e48-0cc3-4adc-9f7b-169f84992d70.png)


*Learn how to configure and run your Java automation testing scripts on [LambdaTest platform](https://www.lambdatest.com/selenium-automation) using **TestNG**.*

## Table of Contents:
***

* [Pre-requisites](#pre-requisites)
* [Run Your First Test](#run-your-first-test)
* [Parallel Testing With TestNG](#running-parallel-tests-using-testng-framework)
* [Local Testing With TestNG](#testing-locally-hosted-or-privately-hosted-projects)
* [Additional Links](#additional-links)
## Prerequisites
***

Before you can start performing Java automation testing with Selenium, you would need to:

* Install the latest **Java development environment** i.e. **JDK 1.6** or higher. We recommend using the latest version.

* Download the latest **Selenium Client** and its **WebDriver bindings** from the [official website](https://www.selenium.dev/downloads/). Latest versions of Selenium Client and WebDriver are ideal for running your automation script on LambdaTest Selenium cloud grid.

* Install **Maven** which supports **TestNG** framework out of the box. **Maven** can be downloaded and installed following the steps from [the official website](https://maven.apache.org/). Maven can also be installed easily on **Linux/MacOS** using [**Homebrew**](https://brew.sh/) package manager.

### Installing Selenium Dependencies and tutorial repo ⬇️
***

**Step 1:** Clone the LambdaTest’s [Java-TestNG-Selenium](https://github.com/LambdaTest/Java-TestNG-Selenium) repository and navigate to the code directory as shown below:
```bash
git clone https://github.com/LambdaTest/Java-TestNG-Selenium
cd Java-TestNG-Selenium
```
You may also want to run the command below to check for outdated dependencies.
```bash
mvn versions:display-dependency-updates
```

### Setting up Your Authentication ⚙️
***

Make sure you have your LambdaTest credentials with you to run test automation scripts on LambdaTest Selenium Grid. You can obtain these credentials from the [LambdaTest Automation Dashboard](https://automation.lambdatest.com/build) or through [LambdaTest Profile](https://accounts.lambdatest.com/login).

**Step 2:** Set LambdaTest `Username` and `Access Key` in environment variables.
  * For **Linux/macOS**:
  ```bash
  export LT_USERNAME="YOUR_USERNAME" export LT_ACCESS_KEY="YOUR ACCESS KEY"
  ```
  * For **Windows**:
  ```bash
  set LT_USERNAME="YOUR_USERNAME" set LT_ACCESS_KEY="YOUR ACCESS KEY"
  ```


## Run Your First Test
***

### Sample Test with TestNG
***

```java
//TestNG Todo : Sample App

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
public class TestNGTodo{
    public String username = "YOUR_LAMBDATEST_USERNAME";
    public String accesskey = "YOUR_LAMBDATEST_ACCESS_KEY";
    public static RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";
    boolean status = false;
    @BeforeClass
    public void setUp() throws Exception {
       DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("version", "70.0");
        capabilities.setCapability("platform", "win10"); // If this cap isn't specified, it will just get the any available one
        capabilities.setCapability("build", "LambdaTestSampleApp");
        capabilities.setCapability("name", "LambdaTestJavaSample");
        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void testSimple() throws Exception {
       try {//Change it to production page
            driver.get("https://lambdatest.github.io/sample-todo-app/");
              //Let's mark done first two items in the list.
              driver.findElement(By.name("li1")).click();
            driver.findElement(By.name("li2")).click();
             // Let's add an item in the list.
              driver.findElement(By.id("sampletodotext")).sendKeys("Yey, Let's add it to list");
            driver.findElement(By.id("addbutton")).click();
              // Let's check that the item we added is added in the list.
            String enteredText = driver.findElementByXPath("/html/body/div/div/div/ul/li[6]/span").getText();
            if (enteredText.equals("Yey, Let's add it to list")) {
                status = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @AfterClass
    public void tearDown() throws Exception {
       if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}
```
### Configuration of Your Test Capabilities
***

**Step 3:** In the test script, you need to update your test capabilities. In this code, we are passing browser, browser version, and operating system information, along with LambdaTest Selenium grid capabilities via capabilities object. The capabilities object in the above code are defined as:
```java
DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("version", "70.0");
        capabilities.setCapability("platform", "win10"); // If this cap isn't specified, it will just get the any available one
        capabilities.setCapability("build", "LambdaTestSampleApp");
        capabilities.setCapability("name", "LambdaTestJavaSample");
```
> **Note:** You can generate capabilities for your test requirements with the help of our inbuilt **[Capabilities Generator tool](https://www.lambdatest.com/capabilities-generator/)**.

### Executing the Test
***

**Step 4:** The tests can be executed in the terminal using the following command.
```bash
mvn test -D suite=single.xml
```
Your test results would be displayed on the test console (or command-line interface if you are using terminal/cmd) and on [LambdaTest automation dashboard](https://accounts.lambdatest.com/login). LambdaTest Automation Dashboard will help you view all your text logs, screenshots and video recording for your entire automation tests.

## Running Parallel Tests Using TestNG Framework
***

### Setting up the Parallel Environment
***

Here is an example `testng.xml` file which would help you to run a single test on various browsers at the same time, you would also need to generate a testcase which makes use of **TestNG** framework parameters (`org.testng.annotations.Parameters`).

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite thread-count="3" name="LambaTestSuite" parallel="tests">
 
  <test name="WIN8TEST">
  <parameter name="browser" value="firefox"/>
  <parameter name="version" value="62.0"/>
  <parameter name="platform" value="WIN8"/>
    <classes>
      <class name="LambdaTest.TestNGToDo"/>
    </classes>
  </test> <!-- Test -->
 
  <test name="WIN10TEST">
  <parameter name="browser" value="chrome"/>
  <parameter name="version" value="79.0"/>
  <parameter name="platform" value="WIN10"/>
    <classes>
      <class name="LambdaTest.TestNGToDo"/>
    </classes>
  </test> <!-- Test -->
  <test name="MACTEST">
  <parameter name="browser" value="safari"/>
  <parameter name="version" value="11.0"/>
  <parameter name="platform" value="macos 10.13"/>
    <classes>
      <class name="LambdaTest.TestNGToDo"/>
    </classes>
  </test> <!-- Test -->
 
</suite>
```
### Executing Parallel Tests Using TestNG
***

To run parallel tests using **TestNG**, we would have to execute the below commands in the terminal: 

* For the above example code
  ```bash
  mvn test
  ```
* For the cloned Java-TestNG-Selenium repo used to run our first sample test
  ```bash
  mvn test -D suite=parallel.xml
  ```

Your test results would be displayed on the test console (or command-line interface if you are using terminal/cmd) and on [LambdaTest automation dashboard](https://accounts.lambdatest.com/login).

## Testing Locally Hosted or Privately Hosted Projects
***

You can test your locally hosted or privately hosted projects with [LambdaTest Selenium grid cloud](https://www.lambdatest.com/selenium-automation) using LambdaTest Tunnel app. All you would have to do is set up an SSH tunnel using LambdaTest Tunnel app and pass toggle `tunnel = True` via desired capabilities. LambdaTest Tunnel establishes a secure SSH protocol based tunnel that allows you in testing your locally hosted or privately hosted pages, even before they are made live.

>Refer our [LambdaTest Tunnel documentation](https://www.lambdatest.com/support/docs/testing-locally-hosted-pages/) for more information.

Here’s how you can establish LambdaTest Tunnel.

>Download the binary file of:
* [LambdaTest Tunnel for Windows](https://downloads.lambdatest.com/tunnel/windows/64bit/LT_Windows.zip)
* [LambdaTest Tunnel for Mac](https://downloads.lambdatest.com/tunnel/mac/64bit/LT_Mac.zip)
* [LambdaTest Tunnel for Linux](https://downloads.lambdatest.com/tunnel/linux/64bit/LT_Linux.zip)

Open command prompt and navigate to the binary folder.

Run the following command:
```bash
LT -user {user’s login email} -key {user’s access key}
```
So if your user name is lambdatest@example.com and key is 123456, the command would be:
```bash
LT -user lambdatest@example.com -key 123456
```
Once you are able to connect **LambdaTest Tunnel** successfully, you would just have to pass on tunnel capabilities in the code shown below :

**Tunnel Capability**
```java
DesiredCapabilities capabilities = new DesiredCapabilities();        
        capabilities.setCapability("tunnel", true);
```


## Additional Links 🔗
***
* [Advanced Configuration for Capabilities](https://www.lambdatest.com/support/docs/selenium-automation-capabilities/)
* [How to test locally hosted apps](https://www.lambdatest.com/support/docs/testing-locally-hosted-pages/)
* [How to integrate LambdaTest with CI/CD](https://www.lambdatest.com/support/docs/integrations-with-ci-cd-tools/)

## LambdaTest Community :busts_in_silhouette:
***
The [LambdaTest Community](https://community.lambdatest.com/) allows people to interact with tech enthusiasts. Connect, ask questions, and learn from tech-savvy people. Discuss best practises in web development, testing, and DevOps with professionals from across the globe.

## Documentation & Resources :books:
***
      
If you want to learn more about the LambdaTest's features, setup, and usage, visit the [LambdaTest documentation](https://www.lambdatest.com/support/docs/). You can also find in-depth tutorials around test automation, mobile app testing, responsive testing, manual testing on [LambdaTest Blog](https://www.lambdatest.com/blog/) and [LambdaTest Learning Hub](https://www.lambdatest.com/learning-hub/).     
      
 ## About LambdaTest
***
[LambdaTest](https://www.lambdatest.com) is a leading test execution and orchestration platform that is fast, reliable, scalable, and secure. It allows users to run both manual and automated testing of web and mobile apps across 3000+ different browsers, operating systems, and real device combinations. Using LambdaTest, businesses can ensure quicker developer feedback and hence achieve faster go to market. Over 500 enterprises and 1 Million + users across 130+ countries rely on LambdaTest for their testing needs.

[<img height="70" src="https://user-images.githubusercontent.com/70570645/169649126-ed61f6de-49b5-4593-80cf-3391ca40d665.PNG">](https://accounts.lambdatest.com/register)
      
## We are here to help you :headphones:
***
* Got a query? we are available 24x7 to help. [Contact Us](mailto:support@lambdatest.com)
* For more info, visit - https://www.lambdatest.com
