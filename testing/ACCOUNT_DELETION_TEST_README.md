# Account Deletion Test Suite

## Overview
This test suite validates the account deletion functionality for administrators in the UCrypt application. The tests ensure that admins can successfully delete user accounts with proper confirmation and that the cancellation flow works correctly.

## Test Cases

### 1. Happy Path: Successful Account Deletion
**Test Method**: `testAdminCanDeleteUserAccountSuccessfully()`

**Description**: Tests the complete flow of account deletion with confirmation.

**Steps**:
1. Create a new user account via signup
2. Login as admin and activate the user account
3. Navigate to user accounts tab in admin panel
4. Verify delete button is visible
5. Verify the account exists in the table
6. Click delete button
7. Verify confirmation dialog appears with correct user details
8. Confirm deletion
9. Verify success message appears
10. Verify account is removed from table
11. Verify table row count decreased by 1

**Expected Results**:
- Confirmation dialog shows user name and email
- Success message: "User account deleted successfully!"
- Account is permanently removed from the system
- Table row count decreases by 1

### 2. Negative Case: Cancelled Account Deletion
**Test Method**: `testAdminCanCancelAccountDeletion()`

**Description**: Tests that account deletion can be cancelled and the account remains intact.

**Steps**:
1. Create a new user account via signup
2. Login as admin and activate the user account
3. Navigate to user accounts tab in admin panel
4. Verify the account exists in the table
5. Click delete button
6. Verify confirmation dialog appears
7. Cancel the deletion
8. Verify account is still in the table
9. Verify table row count remains the same

**Expected Results**:
- Confirmation dialog appears
- No deletion success message appears
- Account remains in the system
- Table row count remains unchanged

## Test Configuration

### Prerequisites
- Admin credentials must be configured in `.env` file:
  - `ADMIN_EMAIL`
  - `ADMIN_PASSWORD`
- Application must be running and accessible
- Chrome WebDriver must be available

### Test Data
- Tests use Faker library to generate unique test data:
  - Random user names
  - Random email addresses
  - Random 6-digit passwords
  - Default role: "USER"

### Page Objects
- **AdminPanelPage**: Contains selectors and methods for admin panel interactions
- **HomePage**: Contains selectors and methods for general page interactions
- **PageBase**: Base class with common web element interaction methods

## Helper Methods

### AdminPanelPage Methods
- `navigateToUserAccountsTab()`: Navigate to user accounts tab
- `isDeleteButtonVisible()`: Check if delete button is visible
- `clickDeleteButton()`: Click the delete button
- `isConfirmationDialogVisible()`: Check if confirmation dialog appears
- `getConfirmationMessage()`: Get confirmation dialog message text
- `confirmDelete()`: Click confirm button in dialog
- `cancelDelete()`: Click cancel button in dialog
- `isAccountInTable(String email)`: Check if account exists in table
- `getTableRowCount()`: Get current number of rows in table

## Running the Tests

### Prerequisites
1. Ensure the application is running
2. Configure admin credentials in `.env` file
3. Install test dependencies

### Execution
```bash
# Run all account deletion tests
mvn test -Dtest=AccountDeletionTest

# Run specific test
mvn test -Dtest=AccountDeletionTest#testAdminCanDeleteUserAccountSuccessfully
```

## Test Dependencies
- TestNG for test framework
- Selenium WebDriver for browser automation
- JavaFaker for test data generation
- Chrome WebDriver for browser control

## Notes
- Tests are designed to be independent and can run in any order
- Each test creates its own test data to avoid conflicts
- Tests include proper cleanup and verification steps
- Soft assertions are used to collect all failures before reporting 