import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService, ConfirmationService } from 'primeng/api';
import { AccountService } from 'src/app/services/account.service';
import { AuthService } from 'src/app/services/auth.service';
import { RoleService } from 'src/app/services/role.service';

@Component({
  selector: 'app-staff-account',
  templateUrl: './staff-account.component.html',
  styleUrls: ['./staff-account.component.scss']
})
export class StaffAccountComponent implements OnInit{

  @Input('data')data: any[];
  @Input('cols')cols: any[];
  @Output() accountDeleted = new EventEmitter<boolean>();

  dataBackups:any[];
  roles: any[];

  constructor(
    private accountService: AccountService,
    protected authService: AuthService,
    private roleService: RoleService,
    private spinnerService: NgxSpinnerService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ){

  }
  ngOnInit(): void {
    this.getRoles();
    this.dataBackups=this.data;
  }

  getRoles() {
    this.spinnerService.show();
    this.roleService.getCurrentUserRoles().subscribe({
      next: (roles) => {
        this.spinnerService.hide();

        this.roles = roles;
      },
      error: (error) => {
        this.spinnerService.hide();

        this.roles = [];
        if (error?.error?.message) {
          this.messageService.add({severity:'error', summary:'Error Retriving data', detail: error.error.message})
        } else {
          this.messageService.add({severity:'error', detail: 'some thing went wrong!'});

        }
      }
    });
  }

  clonedAccount:any;
  onRowEditInit(account: any) {
    if(!this.dataBackups || this.data.length != this.dataBackups.length){
      this.dataBackups=this.data;
    }
    this.clonedAccount = {...account};
    let role= this.roles.find(x=>(x.name as string).toLowerCase() === (account.role as string).toLowerCase())
    account.role = role.displayName;
}

onRowEditSave(account: any, index: number) {
    if ((this.clonedAccount .role as string).toLowerCase() !== (account.role as string).toLowerCase()) {
      let obj={
          newRole: account.role
      }

      this.spinnerService.show();
    this.accountService.updateUsersRole(account.id,obj).subscribe({
      next: (roles) => {
        this.spinnerService.hide();

        // delete this.clonedAccount[account.id];
        this.dataBackups[index] = this.clonedAccount;

        this.messageService.add({severity:'success', detail: 'user role updated successfully!'});
      },
      error: (error) => {
        this.spinnerService.hide();
        this.onRowEditCancel(account,index);

        if (error?.error?.message) {
          this.messageService.add({severity:'error', summary:'Error Retriving data', detail: error?.error?.message || 'some thing went wrong'})
        } else {
          this.messageService.add({severity:'error', detail: 'some thing went wrong!'});

        }
      }
    });

    }
    else {
      this.messageService.add({severity:'error', summary: 'Error', detail:'Same role'});
    }
}

onRowEditCancel(account: any, index: number) {
    this.dataBackups[index] = this.clonedAccount;
    delete this.clonedAccount;
}

deleteUser(account: any) {
    this.confirmationService.confirm({
        message: `Are you sure you want to permanently delete the staff account for ${account.name} (${account.email})? This action cannot be undone.`,
        header: 'Confirm Staff Account Deletion',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
            this.spinnerService.show();
            this.accountService.deleteUser(account.id).subscribe({
                next: () => {
                    this.spinnerService.hide();
                    this.messageService.add({severity:'success', detail: 'Staff account deleted successfully!'});
                    // Remove the account from the data array
                    const index = this.data.findIndex(item => item.id === account.id);
                    if (index > -1) {
                        this.data.splice(index, 1);
                    }
                    this.accountDeleted.emit(true);
                },
                error: (error) => {
                    this.spinnerService.hide();
                    if (error?.error?.message) {
                        this.messageService.add({severity:'error', summary:'Error deleting staff account', detail: error.error.message});
                    } else {
                        this.messageService.add({severity:'error', detail: 'Something went wrong while deleting the staff account!'});
                    }
                }
            });
        }
    });
}

}
