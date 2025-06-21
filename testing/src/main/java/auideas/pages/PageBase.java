package auideas.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PageBase {
    WebDriverWait webDriverWait;
    Actions action;
    JavascriptExecutor js;
    Select select;
    List<WebElement> webElements;
    WebElement webElement;
    WebDriver webDriver;

    public  PageBase(WebDriver webDriver){
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        action = new Actions(webDriver);
        js = (JavascriptExecutor) webDriver;
        this.webDriver = webDriver;
    }

    public void click(By by){
        webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(by)));
        webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(by)));
        webElement = webDriver.findElement(by);
        webElement.click();
    }
    public void type(By by, Object word) {
        webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(by)));
        webElement = webDriver.findElement(by);
        webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(webElement)));
        webElement.clear();
        webElement.sendKeys(word.toString());
    }

    public void scrollInto(By by) {
        webElement = webDriver.findElement(by);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        js.executeScript("arguments[0].scrollIntoView();", webElement);
    }

    public void select(By by, String value) {
        webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(by)));
        webElements = webDriver.findElements(by);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        select = new Select(webDriver.findElement(by));
        select.selectByValue(value);
    }
    
    public void uploadFile(By by, String filePath) {
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
        webElement = webDriver.findElement(by);
        webElement.sendKeys(filePath);
    }

    public String getAttributeValue(By by){
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
        webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(by)));
        webElement =webDriver.findElement(by);
        webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(webElement)));
        //logger.info("Helllllllllllllllllllllllllllllllllllllllllll output at Encryption: {}", webElement.getAttribute("value"));
        return webElement.getAttribute("value");
}
    public String getByGetText(By by){
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
        webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(by)));
        webElement =webDriver.findElement(by);
        webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(webElement)));
        //logger.info("Helllllllllllllllllllllllllllllllllllllllllll output at Encryption: {}", webElement.getAttribute("value"));
        return webElement.getText();
    }
}
