package tests;

import org.testng.annotations.Test;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

/**
 * Tests account deletion functionality.
 * 
 * Verifies admin ability to delete user accounts with confirmation.
 */
public class AccountDeletionTest extends TestBase {
    // Page objects
    HomePage homePage;
    AdminPanelPage adminPanelPage;

    // Test data
    String testUserName = faker.name().fullName();
    String testUserEmail = faker.internet().emailAddress();
    String testUserPassword = faker.number().digits(6);
    String testUserRole = "USER";

    /**
     * Tests admin panel basic functionality and navigation.
     */
    @Test(priority = 0, description = "Verify admin panel basic functionality")
    public void testAdminPanelBasicFunctionality() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
        
        // Login as admin
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        
        // Open admin panel
        homePage.openAdminPanel();
        assertIsEqual(adminPanelPage.adminPanelTitle, "ADMIN PANEL");
        softAssert.assertAll();
        
        // Navigate through different tabs
        adminPanelPage.navigateToUserAccountsTab();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        adminPanelPage.navigateToAllAccountsTab();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        adminPanelPage.navigateToStaffAccountsTab();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Check delete button visibility
        boolean deleteButtonVisible = adminPanelPage.isDeleteButtonVisible();
        System.out.println("Delete button visible: " + deleteButtonVisible);
        
        softAssert.assertAll();
    }

    /**
     * Tests successful account deletion with confirmation dialog.
     */
    @Test(priority = 1, description = "Admin can successfully delete a user account with confirmation")
    public void testAdminCanDeleteUserAccountSuccessfully() {
        // Setup: Create test user account
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
        
        webDriver.navigate().to(LoadProperties.env.getProperty("URL"));
        
        // Create and activate user account
        homePage.signUp(testUserName, testUserEmail, testUserPassword, testUserPassword, testUserRole);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!");
        softAssert.assertAll();
        homePage.closeToastMsg();
        
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openAdminPanel();
        assertIsEqual(adminPanelPage.adminPanelTitle, "ADMIN PANEL");
        
        adminPanelPage.approveSignUpRequest();
        adminPanelPage.navigateToUserAccountsTab();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify delete functionality
        softAssert.assertTrue(adminPanelPage.isDeleteButtonVisible(), "Delete button should be visible");
        
        int rowCountBefore = adminPanelPage.getTableRowCount();
        adminPanelPage.clickDeleteButton();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify confirmation dialog
        softAssert.assertTrue(adminPanelPage.isConfirmationDialogVisible(), "Confirmation dialog should be visible");
        
        String confirmationMsg = adminPanelPage.getConfirmationMessage();
        softAssert.assertTrue(confirmationMsg.contains(testUserName), "Confirmation message should contain user name");
        softAssert.assertTrue(confirmationMsg.contains(testUserEmail), "Confirmation message should contain user email");
        
        // Confirm deletion
        adminPanelPage.confirmDelete();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify deletion success
        assertIsEqual(homePage.toastMsg, "User account deleted successfully!");
        softAssert.assertAll();
        
        softAssert.assertFalse(adminPanelPage.isAccountInTable(testUserEmail), "Account should be removed from the table");
        
        int rowCountAfter = adminPanelPage.getTableRowCount();
        softAssert.assertEquals(rowCountAfter, rowCountBefore - 1, "Table should have one less row after deletion");
        softAssert.assertAll();
    }

    /**
     * Tests cancellation of account deletion process.
     */
    @Test(priority = 2, description = "Admin can cancel account deletion and account remains")
    public void testAdminCanCancelAccountDeletion() {
        // Setup: Create test user account
        String cancelTestUserName = faker.name().fullName();
        String cancelTestUserEmail = faker.internet().emailAddress();
        String cancelTestUserPassword = faker.number().digits(6);
        
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
        
        webDriver.navigate().to(LoadProperties.env.getProperty("URL"));
        
        // Create and activate user account
        homePage.signUp(cancelTestUserName, cancelTestUserEmail, cancelTestUserPassword, cancelTestUserPassword, testUserRole);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!");
        softAssert.assertAll();
        homePage.closeToastMsg();
        
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openAdminPanel();
        assertIsEqual(adminPanelPage.adminPanelTitle, "ADMIN PANEL");
        
        adminPanelPage.approveSignUpRequest();
        adminPanelPage.navigateToUserAccountsTab();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Test cancellation
        int rowCountBefore = adminPanelPage.getTableRowCount();
        adminPanelPage.clickDeleteButton();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        softAssert.assertTrue(adminPanelPage.isConfirmationDialogVisible(), "Confirmation dialog should be visible");
        
        // Cancel deletion
        adminPanelPage.cancelDelete();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify account remains
        softAssert.assertTrue(adminPanelPage.isAccountInTable(cancelTestUserEmail), "Account should still be in the table after cancellation");
    }
} 