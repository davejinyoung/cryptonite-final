package tests;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.DecryptionPage;
import org.uranus.pages.EncryptionPage;
import org.uranus.pages.HomePage;

public class FileEncryptionDecryptionTest extends TestBase {

    HomePage homePage;
    EncryptionPage encryptionPage;
    DecryptionPage decryptionPage;
    public static String outputText;
    public static List<String> outputs = new ArrayList<>();

    @Test(priority = 0)
    public void checkThatEncryptionWorks(){
        homePage = new HomePage(webDriver);
        encryptionPage = new EncryptionPage(webDriver);
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.openService("encryption");
        assertIsEqual(encryptionPage.encryptionPageId, "File & Text Encryption");
        outputs = encryptionPage.encryption(LoadProperties.env.getProperty("plainText"), LoadProperties.env.getProperty("encryptType"));
        assertIsEqualByStringOnly(outputs.get(1), "Key is Saved Successfully");
        assertIsEqualByStringOnly(outputs.get(2), "data encrypted successfully");
        softAssert.assertAll();
    }

    @Test(priority = 1)
    public void checkThatDecryptionWorks(){
        homePage = new HomePage(webDriver);
        decryptionPage = new DecryptionPage(webDriver);
        homePage.openService("decryption");
        assertIsEqual(decryptionPage.decryptionPageId, "File & Text Decryption");
        outputText = decryptionPage.decryption(outputs.get(3), LoadProperties.env.getProperty("encryptType"), outputs.get(0));
        assertIsEqualByStringOnly(outputText, LoadProperties.env.getProperty("plainText"));
        softAssert.assertAll();
    }
}