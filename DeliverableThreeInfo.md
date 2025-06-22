# UCrypt Automated Functional Testing Suite

This repository contains the automated functional testing suite for the UCrypt application. The tests are built using Java, Selenium WebDriver, and TestNG to ensure the application's core functionalities are working as expected and to prevent regressions.

## 1. Project Overview

This testing suite is designed to cover the following key areas of the UCrypt application:
* **Account Management:** Creation, activation, and deletion of user accounts.
* **Role-Based Access Control:** Verification of admin-only privileges.
* **Core Functionality:** File and text encryption/decryption.
* **Bug Fix Verification:** Specific tests to confirm that previously identified bugs have been resolved.

## 2. Prerequisites

Before you can run the tests, please ensure you have the following installed on your system:
* **Java Development Kit (JDK):** Version 11 or higher.
* **Apache Maven:** A build automation tool used to manage project dependencies and run the tests.
* **Google Chrome:** The tests are configured to run on the Chrome browser.
* **ChromeDriver:** This must be installed and configured in your system's PATH.

## 3. Setup and Installation

1.  **Clone the Repository:**
    ```bash
    git clone [your-repository-link]
    cd cryptonite-final/testing
    ```
2.  **Install Dependencies:**
    Maven will automatically download and install all the necessary project dependencies when you run the tests for the first time.

## 4. Running the Tests

To execute the entire test suite, navigate to the `testing` directory in your project's root and run the following Maven command:

```bash
mvn clean test
```

This command will:
1.  Clean the project.
2.  Compile the source code.
3.  Execute all the test cases defined in the `testng.xml` file.

After the tests have been executed, you can find the detailed test reports in the `target/surefire-reports` directory.

## 5. Test Case Descriptions

This suite includes a variety of tests to cover the core functionalities of the UCrypt application. Below is a summary of the test classes and what they cover.

### `AccountCreationActivationTest.java`
* **Purpose:** To verify the end-to-end process of user account creation and admin activation.
* **Test Cases:**
    * `testUserCanSignUpSuccessfully()`: Ensures a new user can successfully register for an account.
    * `testAdminCanApproveSignUpRequest()`: Confirms that an admin can approve a pending user account, allowing the user to log in.

### `AccountDeletionTest.java`
* **Purpose:** To validate the account deletion feature implemented by the development team.
* **Test Cases:**
    * `testAdminCanDeleteUserAccountSuccessfully()`: Verifies that an admin can successfully delete a user account and that the account is removed from the system.
    * `testAdminCanCancelAccountDeletion()`: Ensures that if an admin cancels the deletion process, the user account remains in the system.

### `AdminPanelTest.java`
* **Purpose:** To test the basic navigation and functionality of the admin panel.
* **Test Cases:**
    * `testAdminPanelBasicFunctionality()`: Checks that the admin can log in, access the admin panel, and navigate between the different account tabs.

### `BugFixEncryptionKeyCopyTest.java`
* **Purpose:** To confirm that the bug related to the "Copy Key" button has been fixed.
* **Test Cases:**
    * `encryptionKeyCopyButtonTest()`: Verifies that the "Copy Key" button correctly copies the generated key to the clipboard for both AES and Blowfish encryption algorithms.

### `FileEncryptionDecryptionTest.java`
* **Purpose:** To ensure the core file encryption and decryption functionalities are working correctly.
* **Test Cases:**
    * `testFileEncryptionAndDecryption()`: Tests the end-to-end process of a user encrypting a file, downloading it, and then successfully decrypting it with the correct key.

### `FileVisibilityTest.java`
* **Purpose:** To enforce the business rule that only admins and employees can see files uploaded by an admin.
* **Test Cases:**
    * `testAdminUploadedFileIsVisibleToEmployee()`: Confirms that a file uploaded by an admin is visible to an employee.
    * `testAdminUploadedFileIsNotVisibleToUser()`: Ensures that a file uploaded by an admin is not visible to a regular user.

### `RoleManagementTest.java`
* **Purpose:** To test the role management functionality within the admin panel.
* **Test Cases:**
    * `testAdminCanChangeUserRole()`: Verifies that an admin can successfully change a user's role.

### `SignUpTest.java`
* **Purpose:** To test the user sign-up process with various data inputs.
* **Test Cases:**
    * `testUserCanSignUpSuccessfully()`: A positive test case to ensure a user can sign up with valid data.
    * `testUserCannotSignUpWithExistingEmail()`: A negative test case to ensure the system prevents registration with an already existing email address.
