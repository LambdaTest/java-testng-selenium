package Utills;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverHelper {
    private RemoteWebDriver driver;

    public WebDriverHelper(RemoteWebDriver driver) {
        super();
        this.driver = driver;
    }

    public void getURL(String url) {
        driver.get(url);
    }

    public void click(By locator) {
        driver.findElement(locator).click();
    }

    public void submit(By locator) {
        driver.findElement(locator).submit();
    }

    public void sendKeys(By locator, String str) {
        driver.findElement(locator).sendKeys(str);
    }

    public void clearInputField(By locator) {
        driver.findElement(locator).clear();
    }

    public void sendKeysByKeyBoard(By locator, Keys keys) {
        driver.findElement(locator).sendKeys(keys);
    }


    public String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    public void selectDropDownByValue(By locator, String value) {
        Select select = new Select(driver.findElement(locator));
        select.selectByValue(value);
    }

    public boolean isDisplayed(By locator) {
        return driver.findElement(locator).isDisplayed();
    }

    public void waitForPresence(By locator, int waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForVisibility(By locator, int waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForClickable(By locator, int waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void mouseHoverOnElement(By locator) {
        Actions actions = new Actions(driver);
        WebElement ele = driver.findElement(locator);
        actions.moveToElement(ele).build().perform();
    }

    public void staleElementRefresh(By locator) {
       try {
           WebDriverWait wait = new WebDriverWait(driver, 30);
           wait.until(ExpectedConditions.stalenessOf(driver.findElement(locator)));
       } catch (StaleElementReferenceException exception) {
           exception.printStackTrace();
       }
    }

    public void waitForTime(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
