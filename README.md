
# Java-TestNG-Selenium-Sample
![Geb](https://www.lambdatest.com/support/assets/images/og-images/TestNG-framework-Selenium.jpg)

### Prerequisites
1. Install and set environment variable for java.
    * Windows - https://www.oracle.com/java/technologies/downloads/
    * Linux - ```  sudo apt-get install openjdk-8-jre  ```
    * MacOS - Java should already be present on Mac OS X by default.
2. Install and set environment varibale for Maven.
    * Windows - https://maven.apache.org/install.html
    * Linux/ MacOS -  [Homebrew](http://brew.sh/) (Easier)
    ```
     install maven
    ```
    
### Run your First Test
1. Clone the Java-TestNG-Selenium repository. 
```
git clone https://github.com/LambdaTest/Java-TestNG-Selenium
```
2. Next get into Java-TestNG-Selenium folder, and import Lamabdatest Credentials. You can get these from lambdatest automation dashboard.
   <p align="center">
   <b>For Linux/macOS:</b>:
 
```
export LT_USERNAME="YOUR_USERNAME"
export LT_ACCESS_KEY="YOUR ACCESS KEY"
```
<p align="center">
   <b>For Windows:</b>

```
set LT_USERNAME="YOUR_USERNAME"
set LT_ACCESS_KEY="YOUR ACCESS KEY"
```
Step 3. Make sure to install the mandatory Selenium dependencies for Maven by running the below command.
```
mvn compile
mvn versions:display-dependency-updates
```
### Run Single Test
Use the command below from the root of the project to run single tests.
```
mvn test -D suite=single.xml
```
### Run Parallel Test
Use the command below from the root of the project to run parallel tests.
```
mvn test -D suite=parallel.xml
```
### Results
You can check the test results on LambdaTest [Automation Dashboard](https://automation.lambdatest.com/build)

## About LambdaTest
[LambdaTest](https://www.lambdatest.com/) is a cloud based selenium grid infrastructure that can help you run automated cross browser compatibility tests on 2000+ different browser and operating system environments. LambdaTest supports all programming languages and frameworks that are supported with Selenium, and have easy integrations with all popular CI/CD platforms. It's a perfect solution to bring your [selenium automation testing](https://www.lambdatest.com/selenium-automation) to cloud based infrastructure that not only helps you increase your test coverage over multiple desktop and mobile browsers, but also allows you to cut down your test execution time by running tests on parallel.
