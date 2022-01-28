# Java TestNG Selenium

TestNG is a testing framework for the Java programming language created by Cédric Beust and inspired by JUnit and NUnit. The design goal of TestNG is to cover a wider range of test categories: unit, functional, end-to-end, integration, etc., with more powerful and easy-to-use functionalities.

## Prerequisites

1. Install Homebrew, that will help install maven.

```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

2. Install Maven using brew.

```
brew install maven
```

## Run your Test

Step 1: Clone the Java-TestNG-Selenium Repository.

```
git clone https://github.com/LambdaTest/Java-TestNG-Selenium.git
```

Step 2: Inside the Java-TestNG-Selenium folder, Check if you have all the necessary packages,and update them.

```
cd Java-TestNG-Selenium
mvn versions:display-dependency-updates
```

Step 3: Export the Lambda-test Credentials. You can get these from your automation dashboard.

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

### Running Tests

1. To run single test.

```
mvn test -D suite=single.xml
```

2. To run parallel tests.

```
 mvn test -D suite=parallel.xml
```

## About LambdaTest

[LambdaTest](https://www.lambdatest.com/) is a cloud based selenium grid infrastructure that can help you run automated cross browser compatibility tests on 2000+ different browser and operating system environments. LambdaTest supports all programming languages and frameworks that are supported with Selenium, and have easy integrations with all popular CI/CD platforms. It's a perfect solution to bring your [selenium automation testing](https://www.lambdatest.com/selenium-automation) to cloud based infrastructure that not only helps you increase your test coverage over multiple desktop and mobile browsers, but also allows you to cut down your test execution time by running tests on parallel.
