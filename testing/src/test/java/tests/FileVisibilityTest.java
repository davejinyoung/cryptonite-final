package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

import java.time.Duration;

public class FileVisibilityTest extends TestBase {
    HomePage homePage;
    AdminPanelPage adminPanelPage;

    String name = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String password = faker.number().digits(6);
    String role = "USER";

    // This test currently fails due to an existing bug in the source code. The functional steps of the test is
    // logically correct
    @Test(priority = 0, description = "File uploaded by an admin is visible to admins and employees")
    public void testAdminFileVisibleToAdminAndEmployee() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);

        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
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

    // This test currently fails due to an existing bug in the source code. The functional steps of the test is
    // logically correct
    @Test(priority = 1, description = "File uploaded by an admin is not visible to regular users")
    public void testAdminFileNotVisibleToRegularUser() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);

        String email = faker.internet().emailAddress();
        String password = faker.number().digits(6);

        homePage.signUp(name, email, password, password, role);
        homePage.closeToastMsg();

        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.closeToastMsg();
        homePage.openAdminPanel();

        adminPanelPage.approveSignUpRequest();
        homePage.logout();

        homePage.login(email, password);
        homePage.closeToastMsg();
        softAssert.assertFalse(homePage.isElementPresent(homePage.resourcesTab));
        softAssert.assertAll();
    }
}