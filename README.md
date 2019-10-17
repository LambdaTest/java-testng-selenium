### Environment Setup

1. Global Dependencies
    * Install [Maven](https://maven.apache.org/install.html)
    * Or Install Maven with [Homebrew](http://brew.sh/) (Easier)
    ```
    $ install maven
    ```
    ```
2. Project Dependencies
    * checkout the repository
    * Check that packages are available
    ```
    $ cd Java-TestNG-Selenium



    ```
    * You may also want to run the command below to check for outdated dependencies. Please be sure to verify and review updates before editing your pom.xml file as they may not be compatible with your code.
    ```
    $ mvn versions:display-dependency-updates
    ```
    
### Running Tests

```
To run single test
    $ mvn test -P single

To run local test
    $ mvn test -P local

To run parallel test
    $ mvn test -P parallel

To run single test fron Jenkins
    $ mvn test -P singleJenkins

To run parallel test from Jenkins
    $ mvn test -P parallelJenkins
```
## About LambdaTest

[LambdaTest](https://www.lambdatest.com/) is a cloud based selenium grid infrastructure that can help you run automated cross browser compatibility tests on 2000+ different browser and operating system environments. All test data generated during testing including Selenium command logs, screenshots generated in testing, video logs, selenium logs, network logs, console logs, and metadata logs can be extracted using [LambdaTest automation APIs](https://www.lambdatest.com/support/docs/api-doc/). This data can then be used for creating custom reports.

