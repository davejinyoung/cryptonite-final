package org.uranus.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class AdminPanelPage extends PageBase {
    public AdminPanelPage(WebDriver webDriver) {
        super(webDriver);
    }

    public By adminPanelTitle = By.cssSelector(".text-uppercase");
    public By email = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(2) span");
    public By name = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(3) span");
    public By roleNewAccount = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(4) p-celleditor");
    By approve = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(6) .approve");
    By reject = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(6) .reject");
    By staffAccountsTab = By.cssSelector("app-accounts ul #staff-tab");
    By btnEditRoleStaff = By.cssSelector("#staff tbody tr:nth-child(1) td:nth-child(6) button");
    By listStafRolesField = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(4) p-celleditor select");
    By saveEditIcon = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(6) button:nth-child(1)");
    By resourcesTab = By.cssSelector("button#resources-tab");
    By chooseFileBtn = By.cssSelector(".p-button.p-component.p-element.p-fileupload-choose.p-ripple > .p-button-icon.p-button-icon-left.pi.pi-plus");
    By roleButton = By.xpath("/html/body/app-root/app-layout/div/app-admin-layout/div/div/div[2]/div/div[1]/app-accounts/div/div[2]/app-staff-account/p-table/div/div/table/tbody/tr[1]/td[4]/p-celleditor/select");
    public By uploadBtn = By.cssSelector("p-button:nth-of-type(1) > .p-button.p-component.p-element.p-ripple > .ng-star-inserted.p-button-label");

    public void approveSignUpRequest() {
        click(approve);
    }

    public void clickChooseFileButton() { click(chooseFileBtn); }

    public void navigateResourceTab() {
        click(resourcesTab);
    }

    public void editRole(String newRole) {
        click(staffAccountsTab);
        click(btnEditRoleStaff);
        click(roleButton);
        select(roleButton, newRole);
        click(saveEditIcon);
    }

}
