import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AssociateComponent } from './associate-master/associate-master.component';
import { CostSheetComponent } from './cost-sheet/cost-sheet.component';
import { AuditStatusComponent } from './audit-status/audit-status.component';
import { JobMasterComponent } from './job-master/job-master.component';
import { ResourceComponent } from './resource-master/resource-master.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';

const routes: Routes = [
  { path: '',                 redirectTo: '/auditAllocation',       pathMatch: 'full' },
  { path: 'manpowerMaster',   component: ResourceComponent },
  { path: 'associate',  component: AssociateComponent },
  { path: 'jobMaster',        component: JobMasterComponent },
  { path: 'auditAllocation',    
    component: AuditStatusComponent,
    canActivate: [RoleGuard], 
    data: { 
      expectedRole: 'subAssociate'
    }  },
  { path: 'costSheet',        component: CostSheetComponent },
  
 // { path: '**',               component: PageNotFoundComponent },
 // { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
 
 } 