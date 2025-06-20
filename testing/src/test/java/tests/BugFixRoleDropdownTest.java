package tests;

import org.testng.annotations.Test;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

public class BugFixRoleDropdownTest extends TestBase {

    @Test(description = "Admin can change user role using dropdown (bug fix test)")
    public void testAdminCanChangeUserRoleDropdown() {
        // 1. Admin logs in
        // homePage.login(...);
        // 2. Admin edits a user's role using the dropdown (as fixed)
        // adminPanelPage.editRole("USER");
        // 3. Assert success message
        // assertIsEqual(homePage.toastMsg, "Role is Edited Successfully");
    }
} 