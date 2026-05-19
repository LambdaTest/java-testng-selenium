# Run Selenium AutoHeal Tests With TestNG On TestMu AI

<p align="center">
  <a href="https://www.testmuai.com/"><img src="https://img.shields.io/badge/MADE%20BY%20TestMu%20AI-000000.svg?style=for-the-badge&labelColor=000" alt="Made by TestMu AI"></a>
  <a href="https://community.testmuai.com/"><img src="https://img.shields.io/badge/Join%20the%20community-blueviolet.svg?style=for-the-badge&labelColor=000000" alt="Community"></a>
</p>

## Getting Started

[TestMu AI](https://www.testmuai.com/) (Formerly LambdaTest) is the world's first full-stack AI Agentic Quality Engineering platform that empowers teams to test intelligently, smarter, and ship faster. Built for scale, it offers a full-stack testing cloud with 10K+ real devices and 3,000+ browsers. With AI-native test management, MCP servers, and agent-based automation, TestMu AI supports Selenium, Appium, Playwright, and all major frameworks. 

With TestMu AI (Formerly LambdaTest), you can run Karma Test Runner with Selenium across real browsers and operating systems. This sample shows how to configure Angular + Karma to run on the TestMu AI cloud.

- [Sign up on TestMu AI](https://www.testmuai.com/register/) (Formerly LambdaTest).
- Follow the [TestMu AI documentation](https://www.testmuai.com/support/docs/) for the full setup walkthrough.
  
## Pre-requisites

Before you can start performing Java automation testing with Selenium, you would need to:

- Install the latest **Java development environment** i.e. **JDK 1.6** or higher. We recommend using the latest version.

- Download the latest **Selenium Client** and its **WebDriver bindings**.

- Install **Maven**.

### Cloning Repo And Installing Dependencies

**Step 1:** Clone the TestMu AI Java-TestNG-Selenium repository and navigate to the code directory as shown below:

```bash
git clone https://github.com/LambdaTest/Java-TestNG-Selenium
cd Java-TestNG-Selenium
```

You can also run the command below to check for outdated dependencies.

```bash
mvn versions:display-dependency-updates
```

### Setting Up Your Authentication

Make sure you have your TestMu AI credentials with you to run test automation scripts. You can get these credentials from the TestMu AI Automation Dashboard.

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
        capabilities.setCapability("version", "latest");
        capabilities.setCapability("platform", "Windows 10"); // If this cap isn't specified, it will just get the any available one
        capabilities.setCapability("build", "LambdaTestSampleApp");
        capabilities.setCapability("name", "LambdaTestJavaSample");
```

You can generate capabilities for your test requirements with the help of our in-built [Desired Capability Generator](https://www.testmuai.com/capabilities-generator/).

### Executing The Test

**Step 4:** The tests can be executed in the terminal using the following command.

```bash
mvn test -D suite=single.xml
```

Your test results would be displayed on the test console (or command-line interface if you are using terminal/cmd) and on the TestMu AI automation dashboard. 

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

To run parallel tests using **TestNG**, we would have to execute the following commands in the terminal:

- For the above example code
  ```bash
  mvn test
  ```
- For the cloned Java-TestNG-Selenium repo used to run our first sample test
  ```bash
  mvn test -D suite=parallel.xml
  ```

Open the command prompt and navigate to the binary folder.

Run the following command:

```bash
LT -user {user’s login email} -key {user’s access key}
```
So if your user name is testmuai@example.com and key is 123456, the command would be:

```bash
LT -user testmuai@example.com -key 123456
```
Once you are able to connect **TestMu AI Tunnel** successfully, you would just have to pass on tunnel capabilities in the code shown below :

**Tunnel Capability**

```java
DesiredCapabilities capabilities = new DesiredCapabilities();        
        capabilities.setCapability("tunnel", true);
```

## TestMu AI (Formerly LambdaTest) Community

Connect with testers and developers in the [TestMu AI Community](https://community.testmuai.com/). Ask questions, share what you are building, and discuss best practices in test automation and DevOps.

## TestMu AI (Formerly LambdaTest) Certifications

Earn free [TestMu AI Certifications](https://www.testmuai.com/certifications/) for testers, developers, and QA engineers. Validate your skills in Selenium, Cypress, Playwright, Appium, Espresso and more. Industry-recognized, shareable on LinkedIn, and built by practitioners, not marketers.

## Learning Resources by TestMu AI (Formerly LambdaTest)

Learn modern testing through tutorials, guides, videos, and weekly updates:

* [TestMu AI Blog](https://www.testmuai.com/blog/) - Tutorials, deep dives, and framework guides for testers and developers.
* [TestMu AI Learning Hub](https://www.testmuai.com/learning-hub/) - Long-form learning paths on Selenium, Cypress, Playwright, Appium, and AI-native testing.
* [TestMu AI Newsletter](https://www.testmuai.com/newsletter/) - Weekly roundup of what's new in testing, dev tools, and AI.
* [TestMu AI on YouTube](https://www.youtube.com/@TestMuAI) - Walkthroughs, product demos, and talks from TestMu Conference.

## Contributions

Contributions are welcome. Open an issue to discuss your idea before submitting a pull request. When reporting bugs, include your Node.js version, OS, and Angular CLI version.

## 🚀 LambdaTest is Now TestMu AI

👋 On **January 12, 2026**, [LambdaTest evolved to TestMu AI](https://www.testmuai.com/lambdatest-is-now-testmuai/), the world's first fully autonomous **Agentic AI Quality Engineering Platform**.

Same team. Same infrastructure. Same customer accounts. All existing LambdaTest logins, scripts, capabilities, and integrations continue to work without change.

👉 Find the new home for [LambdaTest](https://www.testmuai.com).

### 🔄 How LambdaTest Evolved into TestMu AI

In 2017, we launched LambdaTest with a simple mission: make testing fast, reliable, and accessible. As LambdaTest grew, we expanded into Test Intelligence, Visual Regression Testing, Accessibility Testing, API Testing, and Performance Testing, covering the full depth of the testing lifecycle.

As software development entered the AI era, testing had to evolve, too. We rebuilt the architecture to be AI-native from the ground up, with autonomous agents that **plan, author, execute, analyze, and optimize tests** while keeping humans in the loop. The platform integrates with your repos, CI, IDEs, and terminals, continuously learning from every code change and development signal.

That evolution earned a new name: **TestMu AI**, built for an AI-first future of quality engineering. TestMu is not a new name for us. It is the name of our annual community conference, which has brought together 100,000+ quality engineers to discuss how AI would reshape testing, long before that became an industry norm. TestMu AI reflects our commitment to community-driven innovation and AI-native architecture.

## Support

Got a question? Email [support@testmuai.com](mailto:support@testmuai.com) or chat with us 24x7 from our chat portal.
