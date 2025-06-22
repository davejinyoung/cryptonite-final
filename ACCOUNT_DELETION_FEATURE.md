# Account Deletion Feature

## Overview
This feature allows administrators to permanently delete staff and user accounts from the system. This is a critical administrative function that should be used with caution as deleted accounts cannot be recovered.

## Backend Implementation

### API Endpoint
- **DELETE** `/users/{id}` - Permanently deletes a user account
- **Response**: 204 No Content on success
- **Authorization**: Admin role required

### Backend Changes
1. **UserService.java**: Added `deleteUser(Long userId)` method
2. **UserController.java**: Added `deleteUser(Long id)` endpoint
3. **api.yaml**: Added DELETE endpoint specification

### Security
- Only users with ADMIN role can delete accounts
- Uses `@PreAuthorize("hasRole('ADMIN')")` annotation
- Validates user existence before deletion

## Frontend Implementation

### Components Updated
1. **AllAccountComponent**: Added delete functionality for all accounts view
2. **StaffAccountComponent**: Added delete functionality for staff accounts
3. **UserAccountComponent**: Added delete functionality for user accounts
4. **AccountsComponent**: Added event handling for account deletion

### UI Features
- **Delete Button**: Red trash icon button in each account row
- **Confirmation Dialog**: PrimeNG confirmation dialog with warning message
- **Success/Error Messages**: Toast notifications for operation feedback
- **Real-time Updates**: Account list refreshes automatically after deletion

### Service Updates
- **AccountService**: Added `deleteUser(id)` method to call the API

## Usage

### For Administrators
1. Navigate to Admin Panel > Accounts
2. Select the appropriate tab (All, Staff, or User accounts)
3. Click the red trash icon next to the account you want to delete
4. Confirm the deletion in the dialog
5. The account will be permanently removed from the system

### Safety Features
- **Confirmation Dialog**: Requires explicit confirmation before deletion
- **Clear Warning**: Dialog shows account name and email for verification
- **Irreversible Action**: Clear messaging that deletion cannot be undone
- **Role-based Access**: Only administrators can access this feature

## Technical Notes

### Database Impact
- User records are permanently deleted from the database
- Associated encryption keys and files may be affected
- Consider implementing soft delete if data retention is required

### Error Handling
- 404 errors if user doesn't exist
- 403 errors if user lacks admin permissions
- Network errors are handled with user-friendly messages

### Performance
- Immediate UI updates for better user experience
- Automatic data refresh after successful deletion
- Optimistic UI updates with rollback on errors

## Future Enhancements
- Soft delete option for data retention
- Bulk delete functionality
- Deletion audit logs
- Account recovery options (if soft delete is implemented) 