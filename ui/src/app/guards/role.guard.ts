import { Injectable } from '@angular/core';
import { 
  Router,
  CanActivate,
  ActivatedRouteSnapshot
} from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';
//import decode from 'jwt-decode';
@Injectable()
export class RoleGuard implements CanActivate {
  constructor(public auth: AuthenticationService, public router: Router) {}
  canActivate(route: ActivatedRouteSnapshot): boolean {
    // this will be passed from the route config
    // on the data property
    const expectedRole = route.data.expectedRole;
    const token = localStorage.getItem('token');
    // decode the token to get its payload
    //const tokenPayload = decode(token);
    if (this.auth.currentUserValue  && this.auth.currentUserValue.role == 'subAssociate') {
      return true;
    } else if(!this.auth.currentUserValue) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}