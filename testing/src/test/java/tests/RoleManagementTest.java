package tests;

import org.testng.annotations.Test;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

public class RoleManagementTest extends TestBase {
    HomePage homePage;
    AdminPanelPage adminPanelPage;

    String name = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String password = faker.number().digits(6);
    String role = "EMPLOYEE";

    @Test(priority = 0, description = "Non-admin user cannot change user roles")
    public void testNonAdminCannotChangeUserRole() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);

        homePage.signUp(name, email, password, password, role);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!"); // assertion command about the showing success message of sign up
        homePage.closeToastMsg();

        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openAdminPanel();

        assertIsEqual(adminPanelPage.adminPanelTitle, "ADMIN PANEL");
        assertIsEqual(adminPanelPage.email, email);
        assertIsEqual(adminPanelPage.roleNewAccount, role);
        assertIsEqual(adminPanelPage.name, name);

        adminPanelPage.approveSignUpRequest();
        homePage.logout();

        homePage.login(email, password);
        homePage.click(homePage.closeToastMsg);
        softAssert.assertAll();

        homePage.logout();
        homePage.closeToastMsg();
    }

    @Test(priority = 1, description = "Admin can change a user's role")
    public void testAdminCanChangeUserRole() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openAdminPanel();
        assertIsEqual(adminPanelPage.adminPanelTitle, "ADMIN PANEL");
        adminPanelPage.editRole("user");
        assertIsEqual(homePage.toastMsg, "Same role");
        softAssert.assertAll();
    }
}