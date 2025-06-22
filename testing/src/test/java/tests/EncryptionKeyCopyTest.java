package tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.DecryptionPage;
import org.uranus.pages.EncryptionPage;
import org.uranus.pages.HomePage;

public class EncryptionKeyCopyTest extends TestBase {

    HomePage homePage;
    EncryptionPage encryptionPage;
    DecryptionPage decryptionPage;
    public static String outputText;
    public static List<String> outputs = new ArrayList<>();

    @DataProvider(name = "encryptionTypes")
    public Object[][] getEncryptionTypes() {
        return new Object[][] {
                {"aes"},
                {"blowfish"}
        };
    }

    @Test(priority = 0, dataProvider = "encryptionTypes")
    public void encryptionKeyCopyButtonTest(String encryptType){
        homePage = new HomePage(webDriver);
        encryptionPage = new EncryptionPage(webDriver);
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openService("encryption");
        assertIsEqual(encryptionPage.encryptionPageId, "File & Text Encryption");
        outputs = encryptionPage.createAndCopyEncryptKey(encryptType);
        WebElement textField = webDriver.findElement(EncryptionPage.textToEncrypt);
        Keys cmdCtrl = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
        textField.click();
        new Actions(webDriver)
                .keyDown(cmdCtrl)
                .sendKeys("v")
                .keyUp(cmdCtrl)
                .perform();
        softAssert.assertEquals(outputs.get(0), textField.getAttribute("value"));
        softAssert.assertAll();
        homePage.logout();
        homePage.closeToastMsg();
    }
}