package Utills;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class UtilsMethods {
    public void wait(RemoteWebDriver driver, By locator, int waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void mouseHoverOnElement(RemoteWebDriver driver, By locator) {
        Actions actions = new Actions(driver);
        WebElement ele = driver.findElement(locator);
        actions.moveToElement(ele).build().perform();
    }

    public String getRandomString(int size) {
        byte[] bytArray = new byte[256];
        new Random().nextBytes(bytArray);

        String randomStr = new String(bytArray, StandardCharsets.UTF_8);
        StringBuilder strBuilder = new StringBuilder();
        // remove all special char
        String alphaStr = randomStr.replaceAll("[^A-Za-z]", "");

        for (int i = 0; i < alphaStr.length(); i++) {
            if (size > 0 && (Character.isLetter(alphaStr.charAt(i)) || Character.isDigit(alphaStr.charAt(i)))) {
                strBuilder.append(alphaStr.charAt(i));
            }
            size--;
        }
        return strBuilder.toString();
    }
}
