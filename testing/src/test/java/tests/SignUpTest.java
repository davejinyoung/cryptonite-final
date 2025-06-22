package tests;

import org.testng.annotations.Test;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

/**
 * Tests user registration and approval workflow.
 * 
 * Verifies signup process and admin approval functionality.
 */
public class SignUpTest extends TestBase {

    // Page objects
    HomePage homePage;
    AdminPanelPage adminPanelPage;

    // Test data using faker
    String name = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String password = faker.number().digits(6);
    String role = "EMPLOYEE";

    /**
     * Tests successful user registration process.
     */
    @Test(priority = 0)
    public void checkThatSignUpScenarioWorkingSuccessfully()  {
        homePage = new HomePage(webDriver);
        homePage.signUp(name, email, password, password, role);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!"); // assertion command about the showing success message of sign up
        softAssert.assertAll();
        homePage.closeToastMsg();
    }

    /**
     * Tests admin approval of registration request.
     */
    @Test(priority = 1)
    public void approveRegistrationRequest() {
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
        softAssert.assertAll();
    }

}
