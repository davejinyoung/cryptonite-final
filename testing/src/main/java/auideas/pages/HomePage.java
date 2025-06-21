package auideas.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class HomePage extends PageBase{
    public HomePage(WebDriver webDriver){
        super(webDriver);
    }
    // All the web elements that we need on home page
    By loginBtn = By.cssSelector(".btn.btn-outline-secondary.fs-14.me-2.my-2.my-sm-0.ng-star-inserted");
    By loginEmailField = By.xpath("/html/body/app-root/app-layout/app-header/div[2]/div/app-login-page/div/div[2]/form/div[1]/input");
    By loginPasswordField = By.xpath("/html/body/app-root/app-layout/app-header/div[2]/div/app-login-page/div/div[2]/form/div[2]/input");
    By loginSubmitBtn = By.xpath("/html/body/app-root/app-layout/app-header/div[2]/div/app-login-page/div/div[2]/form/div[3]/button");
    By homePageToastMsg = By.cssSelector(".p-toast-detail");
    By closeToastMsg = By.cssSelector(".p-toast-icon-close-icon");
    By tryUcryptBtn = By.xpath("/html/body/app-root/app-layout/app-header/header/nav/div/div/ul/li[6]/a");
    By encryptionChoice = By.xpath("/html/body/app-root/app-layout/app-header/header/nav/div/div/ul/li[6]/div/a[1]");
    By decryptionChoice = By.xpath("/html/body/app-root/app-layout/app-header/header/nav/div/div/ul/li[6]/div/a[2]");
    
            //Method to log in a user with the provided information.
    public void login(String email, String password){
        click(loginBtn);
        type(loginEmailField,email);
        type(loginPasswordField,password);
        click(loginSubmitBtn);
    }
    public void openService(String service){
        click(tryUcryptBtn);
        if(service.equals("encryption")){
            click(encryptionChoice);
        }
        else{
            click(decryptionChoice);
        }

    }

}
