package org.uranus.pages;

import org.uranus.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DecryptionPage extends PageBase {
    public DecryptionPage (WebDriver webDriver){
        super(webDriver);
    }

    //private static final Logger logger = LoggerFactory.getLogger(DecryptionPage.class);
    // all web elements from decryption page that we might need
    public By decryptionPageId = By.xpath("/html/body/app-root/app-layout/div/app-decryption/div/div/h2");
    By inputTextArea = By.xpath("/html/body/app-root/app-layout/div/app-decryption/div/div/div/div[2]/form/div/div[1]/textarea");
    By encryptionTechnique = By.xpath("/html/body/app-root/app-layout/div/app-decryption/div/div/div/div[2]/form/div/div[2]/div[1]/select");
    
    By textDecryptBtn = By.xpath("/html/body/app-root/app-layout/div/app-decryption/div/div/div/div[2]/form/div/div[4]/div[2]/button");
    public By outputTextArea = By.xpath("/html/body/app-root/app-layout/div/app-decryption/div/div/div/div[2]/form/div/div[5]/textarea");
    By selectKey = By.xpath("/html/body/app-root/app-layout/div/app-decryption/div/div/div/div[2]/form/div/div[2]/div[2]/select");

    public String decryption(String cipherText, String encrypType, String key){
        type(inputTextArea, cipherText);
        //click(encryptionTechnique);
        
        switch (encrypType) {
            case "aes" -> select(encryptionTechnique, "aes");
            case "tripledes" -> select(encryptionTechnique, "triple_des");
            default -> select(encryptionTechnique, "blowfish");
        }
        //click(blowFishChoice);
        select(selectKey, key);
        click(textDecryptBtn);

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(outputTextArea));
        webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(outputTextArea)));
        webElement =webDriver.findElement(outputTextArea);
        webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(webElement)));
        return webElement.getAttribute("value");
    }


}
