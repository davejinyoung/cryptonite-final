package tests;

import org.testng.annotations.Test;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

public class AccountDeletionTest extends TestBase {
    HomePage homePage;
    AdminPanelPage adminPanelPage;

    String testUserName = faker.name().fullName();
    String testUserEmail = faker.internet().emailAddress();
    String testUserPassword = faker.number().digits(6);
    String testUserRole = "USER";

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
        
        // Try to navigate to different tabs
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
        
        // Check if delete buttons are visible (they might not be if no data)
        boolean deleteButtonVisible = adminPanelPage.isDeleteButtonVisible();
        System.out.println("Delete button visible: " + deleteButtonVisible);
        
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Admin can successfully delete a user account with confirmation")
    public void testAdminCanDeleteUserAccountSuccessfully() {
        // Setup: Create a test user account first
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
        
        // Navigate to home page first
        webDriver.navigate().to(LoadProperties.env.getProperty("URL"));
        
        // Create a new user account
        homePage.signUp(testUserName, testUserEmail, testUserPassword, testUserPassword, testUserRole);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!");
        softAssert.assertAll();
        homePage.closeToastMsg();
        
        // Login as admin and activate the user
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openAdminPanel();
        assertIsEqual(adminPanelPage.adminPanelTitle, "ADMIN PANEL");
        
        // Activate the user account
        adminPanelPage.approveSignUpRequest();
        
        // Navigate to user accounts tab and delete the account
        adminPanelPage.navigateToUserAccountsTab();
        
        // Wait a moment for the table to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify delete button is visible
        softAssert.assertTrue(adminPanelPage.isDeleteButtonVisible(), "Delete button should be visible");
        
        // Store the row count before deletion
        int rowCountBefore = adminPanelPage.getTableRowCount();
        
        // Click delete button
        adminPanelPage.clickDeleteButton();
        
        // Wait for confirmation dialog
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify confirmation dialog appears
        softAssert.assertTrue(adminPanelPage.isConfirmationDialogVisible(), "Confirmation dialog should be visible");
        
        // Verify confirmation message contains the user's name and email
        String confirmationMsg = adminPanelPage.getConfirmationMessage();
        softAssert.assertTrue(confirmationMsg.contains(testUserName), "Confirmation message should contain user name");
        softAssert.assertTrue(confirmationMsg.contains(testUserEmail), "Confirmation message should contain user email");
        
        // Confirm deletion
        adminPanelPage.confirmDelete();
        
        // Wait for deletion to complete
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify success message
        assertIsEqual(homePage.toastMsg, "User account deleted successfully!");
        softAssert.assertAll();
        
        // Verify the account is no longer in the list
        softAssert.assertFalse(adminPanelPage.isAccountInTable(testUserEmail), "Account should be removed from the table");
        
        // Verify the row count decreased by 1
        int rowCountAfter = adminPanelPage.getTableRowCount();
        softAssert.assertEquals(rowCountAfter, rowCountBefore - 1, "Table should have one less row after deletion");
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Admin can cancel account deletion and account remains")
    public void testAdminCanCancelAccountDeletion() {
        // Setup: Create another test user account
        String cancelTestUserName = faker.name().fullName();
        String cancelTestUserEmail = faker.internet().emailAddress();
        String cancelTestUserPassword = faker.number().digits(6);
        
        // Create a new user account
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
        
        // Navigate to home page first
        webDriver.navigate().to(LoadProperties.env.getProperty("URL"));
        
        homePage.signUp(cancelTestUserName, cancelTestUserEmail, cancelTestUserPassword, cancelTestUserPassword, testUserRole);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!");
        softAssert.assertAll();
        homePage.closeToastMsg();
        
        // Login as admin and activate the user
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openAdminPanel();
        assertIsEqual(adminPanelPage.adminPanelTitle, "ADMIN PANEL");
        
        // Activate the user account
        adminPanelPage.approveSignUpRequest();
        
        // Navigate to user accounts tab
        adminPanelPage.navigateToUserAccountsTab();
        
        // Wait a moment for the table to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Store the row count before attempting deletion
        int rowCountBefore = adminPanelPage.getTableRowCount();
        
        // Click delete button
        adminPanelPage.clickDeleteButton();
        
        // Wait for confirmation dialog
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify confirmation dialog appears
        softAssert.assertTrue(adminPanelPage.isConfirmationDialogVisible(), "Confirmation dialog should be visible");
        
        // Cancel the deletion
        adminPanelPage.cancelDelete();
        
        // Wait a moment for any potential toast messages to appear
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify the account is still in the list
        softAssert.assertTrue(adminPanelPage.isAccountInTable(cancelTestUserEmail), "Account should still be in the table after cancellation");
    }
} 