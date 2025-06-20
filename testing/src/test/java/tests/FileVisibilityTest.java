package tests;

import org.testng.annotations.Test;

public class FileVisibilityTest extends TestBase {

    @Test(description = "File uploaded by an admin is visible to admins and employees")
    public void testAdminFileVisibleToAdminAndEmployee() {
        // 1. Admin uploads a file
        // adminPage.uploadFile(...);
        // 2. Admin checks file is visible
        // adminPage.checkFileVisible(...);
        // 3. Employee logs in and checks file is visible
        // employeePage.checkFileVisible(...);
    }

    @Test(description = "File uploaded by an admin is not visible to regular users")
    public void testAdminFileNotVisibleToRegularUser() {
        // 1. Regular user logs in
        // homePage.login(...);
        // 2. Assert admin-uploaded file is not visible
        // userPage.checkFileNotVisible(...);
    }
}