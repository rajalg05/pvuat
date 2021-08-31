import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AuthenticationService } from '../service/authentication.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) {}
    intercept(req: HttpRequest<any>, next: HttpHandler) {

        if (sessionStorage.getItem('userName') && sessionStorage.getItem('token')) {
          req = req.clone({
            setHeaders: {
              Authorization: sessionStorage.getItem('token')
            }
          })
        }
    
        return next.handle(req);
    
    }
}