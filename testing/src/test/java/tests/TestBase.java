package tests;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.uranus.configuration.LoadProperties;

import java.time.Duration;

/**
 * Base class for all test classes.
 * 
 * Provides common setup, teardown, and assertion methods
 * for consistent test execution across the application.
 */
public class TestBase {

    // Shared test resources
    public static String token;
    SoftAssert softAssert = new SoftAssert();
    public static WebDriver webDriver;
    WebDriverWait webDriverWait;
    WebElement webElement;
    Faker faker = new Faker();

    /**
     * Initializes Chrome WebDriver and navigates to test URL.
     */
    @BeforeClass
    public void startDriver() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.navigate().to(LoadProperties.env.getProperty("URL"));
        webDriver.manage().window().maximize();
    }

    /**
     * Cleans up WebDriver resources after test execution.
     */
    @AfterClass
    public void endDriver() {
        webDriver.close();
        webDriver.quit();
    }

    /**
     * Asserts element text matches expected value with wait conditions.
     */
    public void assertIsEqual(By by, String expected) {
        if (expected != null) {
            webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(by)));
            webElement = webDriver.findElement(by);
            webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(webElement)));
            softAssert.assertEquals(webElement.getText(), expected);
        } else {
            return;
        }
    }

    /**
     * Asserts string values match without element location.
     */
    public void assertIsEqualByStringOnly(String actual, String expected) {
        if (expected != null) {
            softAssert.assertEquals(actual, expected);
        } else {
            return;
        }
    }
}
