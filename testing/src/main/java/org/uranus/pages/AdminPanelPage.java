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

    // Account deletion selectors
    By allAccountsTab = By.cssSelector("app-accounts ul #All-tab");
    By userAccountsTab = By.cssSelector("app-accounts ul #reg-tab");
    By deleteButtonFirstRow = By.cssSelector("tr:nth-of-type(1) > td:nth-of-type(6) > button[title='Delete User Account'] > .p-button-icon.pi.pi-trash");
    By confirmDeleteButton = By.cssSelector(".p-confirm-dialog-accept");
    By cancelDeleteButton = By.cssSelector(".p-confirm-dialog-reject");
    By confirmationDialog = By.cssSelector(".p-confirm-dialog");
    By confirmationMessage = By.cssSelector(".p-confirm-dialog-message");
    
    // Alternative selectors for confirmation dialog
    By confirmDialogAlternative = By.cssSelector("p-confirmdialog");
    By confirmMessageAlternative = By.cssSelector("p-confirmdialog .p-confirm-dialog-message");
    By confirmAcceptAlternative = By.cssSelector("p-confirmdialog .p-confirm-dialog-accept");
    By confirmRejectAlternative = By.cssSelector("p-confirmdialog .p-confirm-dialog-reject");

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

    // Account deletion methods
    public void navigateToAllAccountsTab() {
        click(allAccountsTab);
    }

    public void navigateToUserAccountsTab() {
        click(userAccountsTab);
    }

    public void navigateToStaffAccountsTab() {
        click(staffAccountsTab);
    }

    public void clickDeleteButton(int row) {
        String selector = String.format("tr:nth-of-type(%d) > td:nth-of-type(6) > button[title='Delete User Account'] > .p-button-icon.pi.pi-trash", row);
        By deleteButtonLastRow = By.cssSelector(selector);
        click(deleteButtonLastRow);
    }

    public void confirmDelete() {
        try {
            click(confirmDeleteButton);
        } catch (Exception e) {
            click(confirmAcceptAlternative);
        }
    }

    public void cancelDelete() {
        try {
            click(cancelDeleteButton);
        } catch (Exception e) {
            click(confirmRejectAlternative);
        }
    }

    public String getConfirmationMessage() {
        try {
            return getByGetText(confirmationMessage);
        } catch (Exception e) {
            return getByGetText(confirmMessageAlternative);
        }
    }

    public boolean isConfirmationDialogVisible() {
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(confirmationDialog));
            return true;
        } catch (Exception e) {
            try {
                webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(confirmDialogAlternative));
                return true;
            } catch (Exception e2) {
                return false;
            }
        }
    }

    public String getFirstRowEmail() {
        return getByGetText(email);
    }

    public String getFirstRowName() {
        return getByGetText(name);
    }

    public boolean isDeleteButtonVisible() {
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(deleteButtonFirstRow));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAccountInTable(String email) {
        try {
            By accountRow = By.xpath("//td[contains(text(), '" + email + "')]");
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(accountRow));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getTableRowCount() {
        try {
            By tableRows = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr");
            return webDriver.findElements(tableRows).size();
        } catch (Exception e) {
            return 0;
        }
    }

}
