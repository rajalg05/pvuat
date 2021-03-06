import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../model/user';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AESEncryptDecryptService } from './aesencrypt-decrypt-service.service';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;
    BASE_URL: string = environment.apiUrl;

    constructor(private http: HttpClient, public jwtHelper: JwtHelperService,
        private _AESEncryptDecryptService: AESEncryptDecryptService) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    public login(userName: string, password: string) {
        let user: User = new User();
        user.userName = userName;
        user.password = this._AESEncryptDecryptService.encrypt(password);
        const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
        headers.set('Access-Control-Allow-Origin', '*');
        return this.http.post<User>(this.BASE_URL + '/login', user, { headers: headers })
            .pipe(map(user => { // TO DO - the username & password need to be fixed    
                localStorage.setItem('JSESSIONID', JSON.stringify(user.token));
                sessionStorage.setItem('userName',user.userName);
                let tokenStr= user.token;
                sessionStorage.setItem('token', tokenStr);
                this.currentUserSubject.next(user);
            })
                //catchError(this.handleError('saveUser'))
            );
        /* return this.http.post<any>(`${config.apiUrl}/users/authenticate`, { username, password })
            .pipe(map(user => {
                // login successful if there's a jwt token in the response
                if (user && user.token) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    this.currentUserSubject.next(user);
                }
                return user;
            })); */
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem('userName')
        //console.log(!(user === null))
        return !(user === null)
      }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        sessionStorage.removeItem('userName')
        this.currentUserSubject.next(null);
    }

    public isAuthenticated(): boolean {
        const token = localStorage.getItem('token');
        // Check whether the token is expired and return
        // true or false
        return !this.jwtHelper.isTokenExpired(token);
      }    
}