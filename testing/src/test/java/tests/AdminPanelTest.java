package tests;

import org.testng.annotations.Test;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

/**
 * Tests admin panel role management functionality.
 * 
 * Verifies admin ability to change user roles.
 */
public class AdminPanelTest extends TestBase {
    // Page objects
    HomePage homePage;
    AdminPanelPage adminPanelPage;

    /**
     * Tests admin role change functionality.
     */
    @Test(description = "Admin can successfully change a staff's role to user.")
    public void checkThatEditRoleOfUseriIsWorkingSuccessfuly() {
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
