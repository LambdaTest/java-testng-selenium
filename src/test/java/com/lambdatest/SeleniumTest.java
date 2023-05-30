package com.lambdatest;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.*;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.Assert;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SeleniumTest {

    private RemoteWebDriver driver;
    private String Status = "failed";
    private WebDriverWait wait;

    @BeforeClass
    public void setup(ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        ;

        /*
         * Steps to run Smart UI project (https://beta-smartui.lambdatest.com/)
         * Step - 1 : Change the hub URL to @beta-smartui-hub.lambdatest.com/wd/hub
         * Step - 2 : Add "smartUI.project": "<Project Name>" as a capability above
         * Step - 3 : Add
         * "((JavascriptExecutor) driver).executeScript("smartui.takeScreenshot");" code
         * wherever you need to take a screenshot
         * Note: for additional capabilities navigate to
         * https://www.lambdatest.com/support/docs/test-settings-options/
         */

        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("browserName", "chrome");
        caps.setCapability("version", "latest");
        caps.setCapability("build", "TestNG With Java");
        caps.setCapability("name", ctx.getName());
        caps.setCapability("plugin", "git-testng");

        /*
         * Enable Smart UI Project
         * caps.setCapability("smartUI.project", "<Project Name>");
         */

        String[] Tags = new String[] { "Feature", "Magicleap", "Severe" };
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void visitTest() {
        driver.get("https://example.cypress.io/");
        Assert.assertEquals(driver.getCurrentUrl(), "https://example.cypress.io/");
    }

    @Test
    public void getAndContainsTest() {
        driver.get("https://example.cypress.io/");
        WebElement kitchenSinkLink = driver.findElement(By.linkText("Kitchen Sink"));
        Assert.assertTrue(kitchenSinkLink.getText().contains("Kitchen Sink"));
    }

    @Test
    public void typeAndClickTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement emailInput = driver.findElement(By.cssSelector(".action-email"));
        emailInput.sendKeys("test@example.com");
        Assert.assertEquals(emailInput.getAttribute("value"), "test@example.com");

        WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit']"));
        submitButton.click();
    }

    @Test
    public void titleTest() {
        driver.get("https://example.cypress.io/");
        Assert.assertEquals(driver.getTitle(), "Cypress Example Kitchen Sink");
    }

    @Test
    public void urlAndHashTest() {
        driver.get("https://example.cypress.io/commands/querying#Element");
        Assert.assertEquals(driver.getCurrentUrl(), "https://example.cypress.io/commands/querying#Element");
    }

    @Test
    public void viewportTest() {
        driver.get("https://example.cypress.io/");
        driver.manage().window().setSize(new Dimension(1024, 768));
        Assert.assertEquals(driver.manage().window().getSize(), new Dimension(1024, 768));
    }

    @Test
    public void traversalTest() {
        driver.get("https://example.cypress.io/");
        WebElement parentElement = driver.findElement(By.id("commands"));
        WebElement childElement = parentElement.findElement(By.linkText("Querying"));
        Assert.assertEquals(childElement.getText(), "Querying");
    }

    @Test
    public void assertionTest() {
        driver.get("https://example.cypress.io/");
        WebElement kitchenSinkLink = driver.findElement(By.linkText("Kitchen Sink"));
        Assert.assertTrue(kitchenSinkLink.isDisplayed());
    }

    @Test
    public void cookiesTest() {
        driver.get("https://example.cypress.io/");
        Cookie cookie = new Cookie("testCookie", "testValue");
        driver.manage().addCookie(cookie);

        Cookie retrievedCookie = driver.manage().getCookieNamed("testCookie");
        Assert.assertEquals(retrievedCookie.getValue(), "testValue");
    }

    @Test
    public void storageTest() {
        driver.get("https://example.cypress.io/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.setItem('testKey', 'testValue');");

        String localStorageValue = (String) js.executeScript("return window.localStorage.getItem('testKey');");
        Assert.assertEquals(localStorageValue, "testValue");
    }

    @Test
    public void visibilityTest() {
        driver.get("https://example.cypress.io/");
        WebElement kitchenSinkLink = driver.findElement(By.linkText("Kitchen Sink"));
        Assert.assertTrue(kitchenSinkLink.isDisplayed());
    }

    @Test
    public void waitingTest() {
        driver.get("https://example.cypress.io/");
        WebElement kitchenSinkLink = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Kitchen Sink")));
        Assert.assertTrue(kitchenSinkLink.isDisplayed());
    }

    @Test
    public void networkRequestsTest() {
        driver.get("https://example.cypress.io/commands/network-requests");
        String expectedUrl = "https://jsonplaceholder.cypress.io/comments/1";
        WebElement urlLink = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".network-PUT-comment")));
        urlLink.click();
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);
    }

    @Test
    public void hoveringTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement hoverElement = driver.findElement(By.id("hoverOverMe"));
        Actions action = new Actions(driver);
        action.moveToElement(hoverElement).perform();
        WebElement tooltip = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tooltip")));
        Assert.assertTrue(tooltip.isDisplayed());
    }


    @Test
    public void formSubmissionTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement emailInput = driver.findElement(By.cssSelector(".action-email"));
        emailInput.sendKeys("test@example.com");
        emailInput.submit();
        WebElement result = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".action-email-submitted")));
        Assert.assertEquals(result.getText(), "Your form has been submitted!");
    }

    @Test
    public void ajaxRequestTest() {
        driver.get("https://example.cypress.io/commands/network-requests");
        WebElement getCommentButton = driver.findElement(By.cssSelector(".network-btn"));
        getCommentButton.click();
        WebElement result = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".network-comment")));
        Assert.assertTrue(result.getText().contains("dolor"));
    }

    @Test
    public void modalDialogTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement openDialogButton = driver.findElement(By.cssSelector(".action-alert"));
        openDialogButton.click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "Hello, this is a JavaScript alert");
        alert.accept();
    }

    @Test
    public void frameHandlingTest() {
        driver.get("https://example.cypress.io/commands/iframe");
        driver.switchTo().frame(driver.findElement(By.cssSelector(".iframes-frame")));
        WebElement buttonInsideFrame = driver.findElement(By.id("button-inside-iframe"));
        Assert.assertTrue(buttonInsideFrame.isDisplayed());
        driver.switchTo().defaultContent();
    }

    @Test
    public void dragAndDropTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droppable = driver.findElement(By.id("droppable"));
        Actions action = new Actions(driver);
        action.dragAndDrop(draggable, droppable).perform();
        Assert.assertEquals(droppable.getText(), "Dropped!");
    }

    @Test
    public void windowHandlingTest() {
        driver.get("https://example.cypress.io/commands/window");
        WebElement newWindowButton = driver.findElement(By.id("new-window-button"));
        newWindowButton.click();

        String mainWindowHandle = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                Assert.assertEquals(driver.getCurrentUrl(), "https://example.cypress.io/commands/window");
                driver.close();
            }
        }
        driver.switchTo().window(mainWindowHandle);
    }

    @Test
    public void cookiesHandlingTest() {
        driver.get("https://example.cypress.io/");
        Cookie cookie = new Cookie("myCookie", "cookieValue");
        driver.manage().addCookie(cookie);

        Cookie retrievedCookie = driver.manage().getCookieNamed("myCookie");
        Assert.assertEquals(retrievedCookie.getValue(), "cookieValue");
    }

    @Test
    public void javascriptExecutionTest() {
        driver.get("https://example.cypress.io/");
        WebElement element = driver.findElement(By.linkText("Kitchen Sink"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);

        Assert.assertEquals(driver.getCurrentUrl(), "https://example.cypress.io/examples");
    }

    @Test
    public void screenshotCaptureTest() throws IOException {
        driver.get("https://example.cypress.io/");
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(screenshot.toPath(), Paths.get("/path/to/screenshot.png"));
    }

    @Test
    public void fileUploadTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement fileInput = driver.findElement(By.cssSelector("input[type='file']"));
        fileInput.sendKeys(new File("path/to/file").getAbsolutePath());
    }

    @Test
    public void hoverOverTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement element = driver.findElement(By.cssSelector(".action-btn"));
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    @Test
    public void checkboxTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement checkbox = driver.findElement(By.cssSelector(".action-checkboxes input[type='checkbox']"));
        checkbox.click();
    }

    @Test
    public void radioButtonTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement radioButton = driver.findElement(By.cssSelector(".action-radiobuttons input[type='radio']"));
        radioButton.click();
    }

    @Test
    public void dropdownTest() {
        driver.get("https://example.cypress.io/commands/actions");
        Select select = new Select(driver.findElement(By.cssSelector(".action-select")));
        select.selectByVisibleText("Option 1");
    }

    @Test
    public void scrollingTest() {
        driver.get("https://example.cypress.io/commands/actions");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
    }

    @Test
    public void alertBoxTest() {
        driver.get("https://example.cypress.io/commands/actions");
        driver.findElement(By.cssSelector(".example-prompt")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Test
    public void backAndForwardNavigationTest() {
        driver.get("https://example.cypress.io/");
        driver.navigate().to("https://example.cypress.io/commands/actions");
        driver.navigate().back();
        driver.navigate().forward();
    }

    @Test
    public void refreshPageTest() {
        driver.get("https://example.cypress.io/");
        driver.navigate().refresh();
    }

    @Test
    public void navigationToUrlTest() {
        driver.get("https://example.cypress.io/");
    }

    @Test
    public void keyboardInteractionsTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement input = driver.findElement(By.cssSelector(".action-email"));
        Actions actions = new Actions(driver);
        actions.sendKeys(input, "text").perform();
    }

    @Test
    public void typeIntoElementTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement element = driver.findElement(By.cssSelector(".action-email"));
        element.sendKeys("[email protected]");
        Assert.assertEquals(element.getAttribute("value"), "[email protected]");
    }

    @Test
    public void focusOnElementTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement element = driver.findElement(By.cssSelector(".action-focus"));
        Actions action = new Actions(driver);
        action.moveToElement(element).click().perform();
        // verify class and style attributes
    }

    @Test
    public void blurOnElementTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement element = driver.findElement(By.cssSelector(".action-blur"));
        element.sendKeys("About to blur");
        element.sendKeys(Keys.TAB);
        // verify class and style attributes
    }

    @Test
    public void clearElementTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement element = driver.findElement(By.cssSelector(".action-clear"));
        element.sendKeys("Clear this text");
        element.clear();
        Assert.assertEquals(element.getAttribute("value"), "");
    }

    @Test
    public void submitFormTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement form = driver.findElement(By.cssSelector(".action-form"));
        form.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("HALFOFF");
        form.submit();
        // verify form submission
    }

    @Test
    public void clickElementTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement element = driver.findElement(By.cssSelector(".action-btn"));
        element.click();
        // verify click action
    }

    @Test
    public void doubleClickElementTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement element = driver.findElement(By.cssSelector(".action-div"));
        Actions action = new Actions(driver);
        action.doubleClick(element).perform();
        // verify double click action
    }

    @Test
    public void rightClickElementTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement element = driver.findElement(By.cssSelector(".rightclick-action-div"));
        Actions action = new Actions(driver);
        action.contextClick(element).perform();
        // verify right click action
    }

    @Test
    public void checkCheckboxTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement element = driver.findElement(By.cssSelector(".action-checkboxes [type=\"checkbox\"]"));
        if (!element.isSelected()) {
            element.click();
        }
        Assert.assertTrue(element.isSelected());
    }

    @Test
    public void uncheckCheckboxTest() {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement element = driver.findElement(By.cssSelector(".action-check [type=\"checkbox\"]"));
        if (element.isSelected()) {
            element.click();
        }
        Assert.assertFalse(element.isSelected());
    }

    @Test
    public void selectOptionTest() {
        driver.get("https://example.cypress.io/commands/actions");
        Select select = new Select(driver.findElement(By.cssSelector(".action-select")));
        select.selectByVisibleText("apples");
        Assert.assertEquals(select.getFirstSelectedOption().getText(), "apples");
    }

    @Test
    public void mouseInteractionsTest() throws InterruptedException {
        driver.get("https://example.cypress.io/commands/actions");
        WebElement button = driver.findElement(By.cssSelector(".action-btn"));
        Actions actions = new Actions(driver);
        actions.doubleClick(button).perform();

        Status = "passed";
        Thread.sleep(15000);

        System.out.println("TestFinished");
    }

    @AfterClass
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}