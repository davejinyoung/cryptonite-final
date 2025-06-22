package tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.DecryptionPage;
import org.uranus.pages.EncryptionPage;
import org.uranus.pages.HomePage;

/**
 * Tests encryption key copy functionality bugfix.
 * 
 * Fixes clipboard copy issues on macOS vs Windows/Linux systems
 * by using platform-specific keyboard shortcuts (CMD vs CTRL).
 */
public class BugFixEncryptionKeyCopyTest extends TestBase {

    // Page objects for test automation
    HomePage homePage;
    EncryptionPage encryptionPage;
    DecryptionPage decryptionPage;
    
    // Test output storage
    public static String outputText;
    public static List<String> outputs = new ArrayList<>();

    /**
     * Provides test data for different encryption algorithms.
     */
    @DataProvider(name = "encryptionTypes")
    public Object[][] getEncryptionTypes() {
        return new Object[][] {
                {"aes"},      // Advanced Encryption Standard
                {"blowfish"}  // Blowfish algorithm
        };
    }

    /**
     * Tests encryption key copy functionality across platforms.
     * 
     * @param encryptType Encryption algorithm to test
     */
    @Test(priority = 0, dataProvider = "encryptionTypes")
    public void encryptionKeyCopyButtonTest(String encryptType){
        // Initialize and login
        homePage = new HomePage(webDriver);
        encryptionPage = new EncryptionPage(webDriver);
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        
        // Navigate to encryption service
        homePage.openService("encryption");
        assertIsEqual(encryptionPage.encryptionPageId, "File & Text Encryption");
        
        // Create and copy encryption key
        outputs = encryptionPage.createAndCopyEncryptKey(encryptType);
        
        // Get text field for pasting
        WebElement textField = webDriver.findElement(EncryptionPage.textToEncrypt);
        
        // Platform-specific keyboard shortcut (CMD on Mac, CTRL on others)
        Keys cmdCtrl = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
        
        // Perform paste operation
        textField.click();
        new Actions(webDriver)
                .keyDown(cmdCtrl)    // Press modifier key
                .sendKeys("v")       // Paste
                .keyUp(cmdCtrl)      // Release modifier key
                .perform();
        
        // Verify clipboard content matches copied key
        softAssert.assertEquals(outputs.get(0), textField.getAttribute("value"));
        softAssert.assertAll();
        
        // Cleanup
        homePage.logout();
        homePage.closeToastMsg();
    }
}