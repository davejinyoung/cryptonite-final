package org.uranus.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends PageBase {
    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }
    private WebDriverWait wait;

   // This defines a locators for a Elements in a webpage.
    By signUpBtn = By.cssSelector("#collapsibleNavId div a:first-child");
    By loginBtn = By.cssSelector(".btn-outline-secondary");
    By nameField = By.id("signUpName");
    By emailField = By.id("loginEmail");
    By passField = By.id("loginPassword");
    By confirmPassField = By.id("signUpConfirmPassword");
    By roleField = By.id("signUpRole");
    By signUpSubmitBtn = By.cssSelector("app-sign-up-page form .btn-submit");
   public By toastMsg=By.cssSelector(".p-toast-detail");
   public By closeToastMsg=By.cssSelector(".p-toast-icon-close");
   public By emailLoginField=By.cssSelector("app-header #signUp app-login-page .auth-form #loginEmail");
   By passwordLoginField=By.cssSelector("app-header #signUp app-login-page .auth-form #loginPassword");
   By loginSubmitBtn=By.cssSelector("app-header #signUp app-login-page .auth-form button");
   public By adminPanelModule=By.cssSelector("div #collapsibleNavId ul li:nth-child(8) a");
   public By tryEncryptionBtn = By.xpath("/html/body/app-root/app-layout/div/app-home/div[1]/div[1]/div/div/div[1]/div/div/a[1]");
    By tryUcryptBtn = By.xpath("/html/body/app-root/app-layout/app-header/header/nav/div/div/ul/li[6]/a");
    By encryptionChoice = By.xpath("/html/body/app-root/app-layout/app-header/header/nav/div/div/ul/li[6]/div/a[1]");
    By decryptionChoice = By.xpath("/html/body/app-root/app-layout/app-header/header/nav/div/div/ul/li[6]/div/a[2]");
    By loginEmailField = By.xpath("/html/body/app-root/app-layout/app-header/div[2]/div/app-login-page/div/div[2]/form/div[1]/input");
    By loginPasswordField = By.xpath("/html/body/app-root/app-layout/app-header/div[2]/div/app-login-page/div/div[2]/form/div[2]/input");
    By homePageToastMsg = By.cssSelector(".p-toast-detail");
    By profileBtn = By.cssSelector(".mt-2.mt-lg-0.ng-star-inserted.profile-ul  a#dropdownId");
    public By logoutBtn = By.cssSelector(".mt-2.mt-lg-0.ng-star-inserted.profile-ul  div > a:nth-of-type(2)");
    By myProfileBtn = By.cssSelector(".mt-2.mt-lg-0.ng-star-inserted.profile-ul  div > a:nth-of-type(1)");
    public By closeLoginModal = By.cssSelector(".show .btn-close");
    public By resourcesTab = By.cssSelector(".f-b.nav-link.ng-star-inserted");
    public By fileParagraphLocator = By.cssSelector("p.f-name.h-3");

    //Method to sign up a user with the provided information.
    public void signUp(String name , String email, String password, String confPassword, String role) {
    click(signUpBtn);
    type(nameField,name);
    type(emailField,email);
    type(passField,password);
    type(confirmPassField,confPassword);
    select(roleField,role);
    click(signUpSubmitBtn);
    }

    //Method to log in a user with the provided information.
    public void login(String email, String password){
        click(loginBtn);
        type(loginEmailField,email);
        type(loginPasswordField,password);
        click(loginSubmitBtn);
    }

    public void logout(){
        click(profileBtn);
        click(logoutBtn);
    }

    public void navigateProfile(){
        click(profileBtn);
        click(myProfileBtn);
    }

    public void openResourcesTab() { click(resourcesTab); }

    public void openAdminPanel(){
        click(adminPanelModule);
    }

    public void closeLoginModal() {
        click(closeLoginModal);
    }

    public void closeToastMsg() {
        click(closeToastMsg);
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

    public boolean isElementPresent(By locatorKey) {
        try {
            webDriver.findElement(locatorKey);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
