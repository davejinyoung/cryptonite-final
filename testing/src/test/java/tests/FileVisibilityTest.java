package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

import java.time.Duration;

/**
 * Tests file visibility and access control.
 * 
 * Verifies file upload permissions and visibility across user roles.
 */
public class FileVisibilityTest extends TestBase {
    // Page objects
    HomePage homePage;
    AdminPanelPage adminPanelPage;

    // Test data
    String name = faker.name().fullName();
    String role = "USER";

    /**
     * Tests admin file visibility to admin and employee users.
     * Note: This test currently fails due to an existing bug in the source code.
     */
    @Test(priority = 0, description = "File uploaded by an admin is visible to admins and employees")
    public void testAdminFileVisibleToAdminAndEmployee() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);

        // Admin uploads file
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openAdminPanel();
        adminPanelPage.navigateResourceTab();
        adminPanelPage.clickChooseFileButton();
        String filePath = System.getProperty("user.dir") + "/src/test/resources/sample-file.txt";
        WebElement fileInput = webDriver.findElement(By.xpath("//input[@type='file']"));
        fileInput.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(1));
        WebElement uploadButton = wait.until(ExpectedConditions.elementToBeClickable(adminPanelPage.uploadBtn));
        uploadButton.click();

        assertIsEqual(homePage.toastMsg, "file is uploaded successfully");

        homePage.closeToastMsg();
        homePage.logout();

        // Employee verifies file visibility
        homePage.login(LoadProperties.env.getProperty("employee_email"), LoadProperties.env.getProperty("employee_password"));
        homePage.closeToastMsg();
        homePage.openResourcesTab();

        softAssert.assertTrue(homePage.isElementPresent(homePage.fileParagraphLocator));
        homePage.click(By.xpath("//div[@id='collapsibleNavId']/div/ul//a[@id='dropdownId']/img"));
        homePage.click(homePage.logoutBtn);
        softAssert.assertAll();

        WebElement fileElement = wait.until(ExpectedConditions.visibilityOfElementLocated(homePage.fileParagraphLocator));

        String actualFileName = fileElement.getText();
        String expectedFileName = "sample-file.txt";

        softAssert.assertEquals(actualFileName, expectedFileName);
        softAssert.assertAll();
    }

    /**
     * Tests that admin files are not visible to regular users.
     * Note: This test currently fails due to an existing bug in the source code.
     */
    @Test(priority = 1, description = "File uploaded by an admin is not visible to regular users")
    public void testAdminFileNotVisibleToRegularUser() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);

        // Create regular user account
        String email = faker.internet().emailAddress();
        String password = faker.number().digits(6);

        homePage.signUp(name, email, password, password, role);
        homePage.closeToastMsg();

        // Admin approves user account
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openAdminPanel();

        adminPanelPage.approveSignUpRequest();
        homePage.logout();

        // Verify regular user cannot access resources
        homePage.login(email, password);
        homePage.closeToastMsg();
        softAssert.assertFalse(homePage.isElementPresent(homePage.resourcesTab));
        softAssert.assertAll();
    }
}