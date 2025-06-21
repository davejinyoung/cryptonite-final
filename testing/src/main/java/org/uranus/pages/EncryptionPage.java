package org.uranus.pages;

import auideas.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EncryptionPage extends PageBase {
    public EncryptionPage (WebDriver webDriver){
        super(webDriver);
    }

    private static final Logger logger = LoggerFactory.getLogger(EncryptionPage.class);
    // all the elments in encryption page that we might need
    public By encryptionPageId = By.xpath("/html/body/app-root/app-layout/div/app-encryption/div/div/h2");
    By textToEncrypt = By.xpath("/html/body/app-root/app-layout/div/app-encryption/div/div/div/div[2]/form/div/div[1]/textarea");
    By encryptionTechnique = By.xpath("/html/body/app-root/app-layout/div/app-encryption/div/div/div/div[2]/form/div/div[2]/div[1]/select");
    By autoGenKey = By.xpath("/html/body/app-root/app-layout/div/app-encryption/div/div/div/div[2]/form/div/div[2]/div[2]/label/button");
    By keyTextArea = By.xpath("/html/body/app-root/app-layout/div/app-encryption/div/div/div/div[2]/form/div/div[2]/div[2]/div/textarea");
    By keySaveToastMsg = By.xpath("//app-root/app-layout[@class='ng-star-inserted']//p-toast//p-toastitem//div[@role='alert']/div/div[.='Key is Saved Successfully']");
    By encryptionToastMessage = By.xpath("//app-root/app-layout[@class='ng-star-inserted']//p-toast//p-toastitem//div[@role='alert']/div/div[.='data encrypted successfully ']");
    By enKeySave = By.cssSelector(".p-element:nth-child(2) > svg");
    By encryptBTn = By.xpath("/html/body/app-root/app-layout/div/app-encryption/div/div/div/div[2]/form/div/div[3]/div[2]/button");
    By outputTextArea = By.xpath("/html/body/app-root/app-layout/div/app-encryption/div/div/div/div[2]/form/div/div[4]/textarea");
    
    List<String> outputs = new ArrayList<>();

    public List<String> encryption(String plainText, String encrypType){
        type(textToEncrypt, plainText);

        switch (encrypType) {
            case "aes" -> select(encryptionTechnique, "aes");
            case "tripledes" -> select(encryptionTechnique, "triple_des");
            default -> select(encryptionTechnique, "blowfish");
        }

        click(autoGenKey);
        click(enKeySave);
        outputs.add(getAttributeValue(keyTextArea));
        click(encryptBTn);
        outputs.add(getByGetText(keySaveToastMsg));
        outputs.add(getByGetText(encryptionToastMessage));
        outputs.add(getAttributeValue(outputTextArea));
        return outputs;
    }


}
