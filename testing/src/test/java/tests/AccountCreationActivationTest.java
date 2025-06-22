package tests;

import org.testng.annotations.Test;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

/**
 * Tests account creation and activation workflow.
 * 
 * Verifies user registration, admin approval, and access control.
 */
public class AccountCreationActivationTest extends TestBase {
    // Page objects
    HomePage homePage;
    AdminPanelPage adminPanelPage;

    // Test data
    String name = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String password = faker.number().digits(6);
    String role = "EMPLOYEE";

    /**
     * Tests that non-activated users cannot login.
     */
    @Test(priority = 0, description = "Non-activated user cannot log in")
    public void testNonActivatedUserCannotLogin() {
        homePage = new HomePage(webDriver);
        homePage.signUp(name, email, password, password, role);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!");
        softAssert.assertAll();
        homePage.closeToastMsg();
        homePage.login(email, password);
        assertIsEqual(homePage.toastMsg, "Invalid credentials!");
        softAssert.assertAll();
        homePage.closeLoginModal();
        homePage.closeToastMsg();
    }

    /**
     * Tests account creation with pending activation status.
     */
    @Test(priority = 1, description = "User can create an account and the account is pending activation")
    public void testUserCanCreateAccountPendingActivation() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.number().digits(6);
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
        homePage.signUp(name, email, password, password, role);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!");
        softAssert.assertAll();
        homePage.closeToastMsg();
    }

    /**
     * Tests that unactivated users cannot access features.
     */
    @Test(priority = 2, description = "User cannot access features without admin activation")
    public void testUserCannotAccessFeaturesWithoutActivation() {
        homePage = new HomePage(webDriver);
        homePage.click(homePage.tryEncryptionBtn);
        softAssert.assertNotNull(homePage.emailLoginField);
        softAssert.assertAll();
        homePage.closeToastMsg();
        homePage.closeLoginModal();
    }

    /**
     * Tests admin activation of pending user accounts.
     */
    @Test(priority = 3, description = "Admin can activate a pending user account")
    public void testAdminCanActivatePendingUser() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openAdminPanel();
        assertIsEqual(adminPanelPage.adminPanelTitle, "ADMIN PANEL");
        assertIsEqual(adminPanelPage.email, email);
        assertIsEqual(adminPanelPage.roleNewAccount, role);
        assertIsEqual(adminPanelPage.name, name);
        adminPanelPage.approveSignUpRequest();
        softAssert.assertNotEquals(adminPanelPage.email, email);
        softAssert.assertNotEquals(adminPanelPage.name, name);
        softAssert.assertAll();
    }
}