package tests;

import org.testng.annotations.Test;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

public class AccountCreationActivationTest extends TestBase {
    HomePage homePage;
    AdminPanelPage adminPanelPage;

    String name = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String password = faker.number().digits(6);
    String role = "EMPLOYEE";

    @Test(priority = 0, description = "User can create an account and the account is pending activation")
    public void testUserCanCreateAccountPendingActivation() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
        homePage.signUp(name, email, password, password, role);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!");
        softAssert.assertAll();
        homePage.click(homePage.closeToastMsg);
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.openAdminPanel();
        assertIsEqual(adminPanelPage.name, name);
    }

    @Test(priority = 1, description = "Admin can activate a pending user account")
    public void testAdminCanActivatePendingUser() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.openAdminPanel();
        assertIsEqual(adminPanelPage.adminPanelTitle, "ADMIN PANEL");
        assertIsEqual(adminPanelPage.email, email);
        assertIsEqual(adminPanelPage.roleNewAccount, role);
        assertIsEqual(adminPanelPage.name, name);
        adminPanelPage.approveSignUpRequest();
        softAssert.assertNotEquals(adminPanelPage.email, email);
        softAssert.assertNotEquals(adminPanelPage.name, name);
    }

    @Test(priority = 2, description = "Non-activated user cannot log in")
    public void testNonActivatedUserCannotLogin() {
        homePage = new HomePage(webDriver);
        homePage.signUp(name, email, password, password, role);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!");
        softAssert.assertAll();
        homePage.click(homePage.closeToastMsg);
        homePage.login(email, password);
        assertIsEqual(homePage.toastMsg, "Invalid credentials!");
    }

    @Test(priority = 3, description = "User cannot access features without admin activation")
    public void testUserCannotAccessFeaturesWithoutActivation() {
        homePage = new HomePage(webDriver);
        homePage.signUp(name, email, password, password, role);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!");
        homePage.click(homePage.tryEncryptionBtn);
        softAssert.assertNotNull(homePage.emailLoginField);
    }
}