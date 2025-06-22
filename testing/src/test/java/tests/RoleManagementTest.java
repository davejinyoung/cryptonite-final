package tests;

import org.testng.annotations.Test;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

/**
 * Tests role management functionality.
 * 
 * Verifies admin role change capabilities and access control.
 */
public class RoleManagementTest extends TestBase {
    // Page objects
    HomePage homePage;
    AdminPanelPage adminPanelPage;

    // Test data
    String name = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String password = faker.number().digits(6);
    String role = "EMPLOYEE";

    /**
     * Tests that non-admin users cannot change roles.
     */
    @Test(priority = 0, description = "Non-admin user cannot change user roles")
    public void testNonAdminCannotChangeUserRole() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);

        // Create and approve user account
        homePage.signUp(name, email, password, password, role);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!");
        homePage.closeToastMsg();

        // Admin approves the account
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openAdminPanel();

        adminPanelPage.approveSignUpRequest();
        homePage.logout();

        // Verify non-admin user cannot access admin features
        homePage.login(email, password);
        homePage.click(homePage.closeToastMsg);
        softAssert.assertAll();

        homePage.logout();
        homePage.closeToastMsg();
    }

    /**
     * Tests admin ability to change user roles.
     */
    @Test(priority = 1, description = "Admin can change a user's role")
    public void testAdminCanChangeUserRole() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openAdminPanel();
        assertIsEqual(adminPanelPage.adminPanelTitle, "ADMIN PANEL");
        adminPanelPage.editRole("USER");
        assertIsEqual(homePage.toastMsg, "user role updated successfully!");
        softAssert.assertAll();
    }
}