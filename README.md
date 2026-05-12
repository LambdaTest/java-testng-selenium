# Run Selenium Tests With TestNG — TestMu AI (Formerly LambdaTest)

![image](https://user-images.githubusercontent.com/70570645/171934563-4806efd2-1154-494c-a01d-1def95657383.png)


<p align="center">
  <a href="https://www.testmuai.com/blog/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium" target="_bank">Blog</a>
  &nbsp; &#8901; &nbsp;
  <a href="https://www.testmuai.com/support/docs/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium" target="_bank">Docs</a>
  &nbsp; &#8901; &nbsp;
  <a href="https://www.testmuai.com/learning-hub/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium" target="_bank">Learning Hub</a>
  &nbsp; &#8901; &nbsp;
  <a href="https://www.testmuai.com/newsletter/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium" target="_bank">Newsletter</a>
  &nbsp; &#8901; &nbsp;
  <a href="https://www.testmuai.com/certification/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium" target="_bank">Certifications</a>
  &nbsp; &#8901; &nbsp;
  <a href="https://www.youtube.com/@TestMuAI" target="_bank">YouTube</a>
</p>
&emsp;
&emsp;
&emsp;

*Learn how to use TestNG framework to configure and run your Java automation testing scripts on the TestMu AI platform*

[<img height="58" width="200" src="https://user-images.githubusercontent.com/70570645/171866795-52c11b49-0728-4229-b073-4b704209ddde.png">](https://accounts.lambdatest.com/register?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)

## Table Of Contents

* [Pre-requisites](#pre-requisites)
* [Run Your First Test](#run-your-first-test)
* [Parallel Testing With TestNG](#executing-parallel-tests-using-testng)
* [Local Testing With TestNG](#testing-locally-hosted-or-privately-hosted-projects)

## Pre-requisites

Before you can start performing Java automation testing with Selenium, you would need to:

- Install the latest **Java development environment** i.e. **JDK 1.6** or higher. We recommend using the latest version.

- Download the latest **Selenium Client** and its **WebDriver bindings** from the [official website](https://www.selenium.dev/downloads/). Latest versions of Selenium Client and WebDriver are ideal for running your automation script on TestMu AI Selenium cloud grid.

- Install **Maven** which supports **JUnit** framework out of the box. **Maven** can be downloaded and installed following the steps from [the official website](https://maven.apache.org/). Maven can also be installed easily on **Linux/MacOS** using [Homebrew](https://brew.sh/) package manager.

### Cloning Repo And Installing Dependencies

**Step 1:** Clone the TestMu AI’s Java-TestNG-Selenium repository and navigate to the code directory as shown below:

```bash
git clone https://github.com/LambdaTest/Java-TestNG-Selenium
cd Java-TestNG-Selenium
```

You can also run the command below to check for outdated dependencies.

```bash
mvn versions:display-dependency-updates
```

### Setting Up Your Authentication

Make sure you have your TestMu AI credentials with you to run test automation scripts. You can get these credentials from the [TestMu AI Automation Dashboard](https://automation.lambdatest.com/build?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium) or by your [TestMu AI Profile](https://accounts.lambdatest.com/login?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium).

**Step 2:** Set TestMu AI **Username** and **Access Key** in environment variables.

* For **Linux/macOS**:
  
  ```bash
  export LT_USERNAME="YOUR_USERNAME" 
  export LT_ACCESS_KEY="YOUR ACCESS KEY"
  ```
  * For **Windows**:
  ```bash
  set LT_USERNAME="YOUR_USERNAME" 
  set LT_ACCESS_KEY="YOUR ACCESS KEY"
  ```

## Run Your First Test

>**Test Scenario**: The sample [TestNGTodo1.java](https://github.com/LambdaTest/Java-TestNG-Selenium/blob/master/src/test/java/com/lambdatest/TestNGTodo1.java) tests a sample to-do list app by marking couple items as done, adding a new item to the list and finally displaying the count of pending items as output.


### Configuring Your Test Capabilities

**Step 3:** In the test script, you need to update your test capabilities. In this code, we are passing browser, browser version, and operating system information, along with TestMu AI Selenium grid capabilities via capabilities object. The capabilities object in the above code are defined as:

```java
DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("version", "70.0");
        capabilities.setCapability("platform", "win10"); // If this cap isn't specified, it will just get the any available one
        capabilities.setCapability("build", "LambdaTestSampleApp");
        capabilities.setCapability("name", "LambdaTestJavaSample");
```

You can generate capabilities for your test requirements with the help of our inbuilt [Desired Capability Generator](https://www.testmuai.com/capabilities-generator/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium).

### Executing The Test

**Step 4:** The tests can be executed in the terminal using the following command.

```bash
mvn test -D suite=single.xml
```

Your test results would be displayed on the test console (or command-line interface if you are using terminal/cmd) and on TestMu AI automation dashboard. 

## Run Parallel Tests Using TestNG


Here is an example `xml` file which would help you to run a single test on various browsers at the same time, you would also need to generate a testcase which makes use of **TestNG** framework parameters (`org.testng.annotations.Parameters`).

```xml title="testng.xml"
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

To run parallel tests using **TestNG**, we would have to execute the below commands in the terminal:

- For the above example code
  ```bash
  mvn test
  ```
- For the cloned Java-TestNG-Selenium repo used to run our first sample test
  ```bash
  mvn test -D suite=parallel.xml
  ```

## Testing Locally Hosted Or Privately Hosted Projects

You can test your locally hosted or privately hosted projects with TestMu AI Selenium grid using TestMu AI Tunnel. All you would have to do is set up an SSH tunnel using tunnel and pass toggle `tunnel = True` via desired capabilities. TestMu AI Tunnel establishes a secure SSH protocol based tunnel that allows you in testing your locally hosted or privately hosted pages, even before they are live.

Refer our [TestMu AI Tunnel documentation](https://www.testmuai.com/support/docs/testing-locally-hosted-pages/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium) for more information.

Here’s how you can establish TestMu AI Tunnel.

Download the binary file of:
* [TestMu AI Tunnel for Windows](https://downloads.lambdatest.com/tunnel/v3/windows/64bit/LT_Windows.zip)
* [TestMu AI Tunnel for macOS](https://downloads.lambdatest.com/tunnel/v3/mac/64bit/LT_Mac.zip)
* [TestMu AI Tunnel for Linux](https://downloads.lambdatest.com/tunnel/v3/linux/64bit/LT_Linux.zip)

Open command prompt and navigate to the binary folder.

Run the following command:

```bash
LT -user {user’s login email} -key {user’s access key}
```
So if your user name is lambdatest@example.com and key is 123456, the command would be:

```bash
LT -user lambdatest@example.com -key 123456
```
Once you are able to connect **TestMu AI Tunnel** successfully, you would just have to pass on tunnel capabilities in the code shown below :

**Tunnel Capability**

```java
DesiredCapabilities capabilities = new DesiredCapabilities();        
        capabilities.setCapability("tunnel", true);
```

## Tutorials 📙

Check out our latest tutorials on TestNG automation testing 👇

* [JUnit 5 vs TestNG: Choosing the Right Framework for Automation Testing](https://www.testmuai.com/blog/junit-5-vs-testng/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [How To Install TestNG?](https://www.testmuai.com/blog/how-to-install-testng-in-eclipse-step-by-step-guide/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [Create TestNG Project in Eclipse & Run Selenium Test Script](https://www.testmuai.com/blog/create-testng-project-in-eclipse-run-selenium-test-script/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [A Complete Guide for Your First TestNG Automation Script](https://www.testmuai.com/blog/a-complete-guide-for-your-first-testng-automation-script/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [How to Automate using TestNG in Selenium?](https://www.testmuai.com/blog/testng-in-selenium/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [How to Perform Parallel Test Execution in TestNG with Selenium](https://www.testmuai.com/blog/parallel-test-execution-in-testng/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [Creating TestNG XML File & Execute Parallel Testing](https://www.testmuai.com/blog/create-testng-xml-file-execute-parallel-testing/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [Speed Up Automated Parallel Testing in Selenium with TestNG](https://www.testmuai.com/blog/speed-up-automated-parallel-testing-in-selenium-with-testng/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [Automation Testing With Selenium, Cucumber & TestNG](https://www.testmuai.com/blog/automation-testing-with-selenium-cucumber-testng/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [How to Run JUnit Selenium Tests using TestNG](https://www.testmuai.com/blog/test-example-junit-and-testng-in-selenium/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [How to Group Test Cases in TestNG [With Examples]](https://www.testmuai.com/blog/grouping-test-cases-in-testng/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [How to Set Test Case Priority in TestNG with Selenium](https://www.testmuai.com/blog/prioritizing-tests-in-testng-with-selenium/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [How to Use Assertions in TestNG with Selenium](https://www.testmuai.com/blog/asserts-in-testng/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [How to Use DataProviders in TestNG [With Examples]](https://www.testmuai.com/blog/how-to-use-dataproviders-in-testng-with-examples/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [Parameterization in TestNG - DataProvider and TestNG XML [With Examples]](https://www.testmuai.com/blog/parameterization-in-testng-dataprovider-and-testng-xml-examples/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [TestNG Listeners in Selenium WebDriver [With Examples]](https://www.testmuai.com/blog/testng-listeners-in-selenium-webdriver-with-examples/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [TestNG Annotations Tutorial with Examples for Selenium Automation](https://www.testmuai.com/blog/complete-guide-on-testng-annotations-for-selenium-webdriver/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [How to Use TestNG Reporter Log in Selenium](https://www.testmuai.com/blog/how-to-use-testng-reporter-log-in-selenium/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [How to Generate TestNG Reports in Jenkins](https://www.testmuai.com/blog/how-to-generate-testng-reports-in-jenkins/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)


## Documentation & Resources :books:

      
Visit the following links to learn more about TestMu AI's features, setup and tutorials around test automation, mobile app testing, responsive testing, and manual testing.

* [TestMu AI Documentation](https://www.testmuai.com/support/docs/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [TestMu AI Blog](https://www.testmuai.com/blog/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)
* [TestMu AI Learning Hub](https://www.testmuai.com/learning-hub/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium)    

## TestMu AI Community :busts_in_silhouette:

The [TestMu AI Community](https://community.testmuai.com/?utm_source=github&utm_medium=repo&utm_campaign=Java-TestNG-Selenium) allows people to interact with tech enthusiasts. Connect, ask questions, and learn from tech-savvy people. Discuss best practises in web development, testing, and DevOps with professionals from across the globe 🌎

## What's New At TestMu AI ❓

To stay updated with the latest features and product add-ons, visit [Changelog](https://changelog.lambdatest.com)

## 🚀 [LambdaTest is Now TestMu AI](https://www.testmuai.com/lambdatest-is-now-testmuai/)

👋 Welcome to TestMu AI, the next evolution of LambdaTest. As of January 2026, LambdaTest has officially rebranded to TestMu AI. We have evolved from a cross-browser testing cloud into a unified, AI-native quality engineering platform designed for the modern DevOps era.

Whether you have been part of the LambdaTest community for years or are just discovering TestMu AI, our mission remains the same: to help you ship faster with high-scale test execution, autonomous testing, and deep quality analytics.

**🔄 Our Rebrand Journey**

We chose the name TestMu AI to reflect our shift towards intelligent, autonomous testing. While our identity has changed, our core technology and commitment to the testing community stay the same.

**✨ Specialties**

- 🤖 AI-Native Test Execution (Formerly LambdaTest)
- ⚡ Autonomous Test Automation
- 🌐 Cross-Browser & Mobile Testing
- 📊 Unified Quality Intelligence

👉 Find [LambdaTest's New Home](https://www.testmuai.com/).