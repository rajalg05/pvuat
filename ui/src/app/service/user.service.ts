import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { config } from '../environment';
import { User } from '../model/user';

@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }
    BASE_URL: string = 'http://ec2-18-224-109-184.us-east-2.compute.amazonaws.com:8085/audit';
    getAll() {
       // return this.http.get<User[]>(`${config.apiUrl}/users`);
       const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');
       headers.set('Access-Control-Allow-Origin', '*');
       return this.http.get<User[]>(this.BASE_URL + '/getUsers', {headers: headers})
         .pipe(
           //catchError(this.handleError('getUsers'))
         );
    }

    getById(id: number) {
        return this.http.get(`${config.apiUrl}/users/${id}`);
    }

    register(user: User) {
        // return this.http.post(`${config.apiUrl}/users/register`, user);
        // const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');
        // headers.set('Access-Control-Allow-Origin', '*');
        const headers = new HttpHeaders();
        return this.http.post<string>(this.BASE_URL + '/saveUser', user, { headers: headers })

            .pipe(
                //catchError(this.handleError('saveUser'))
            );
    }

    update(user: User) {
        return this.http.put(`${config.apiUrl}/users/${user.id}`, user);
    }

    delete(id: number) {
        const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');
        headers.set('Access-Control-Allow-Origin', '*');
        return this.http.post<string>(this.BASE_URL + '/deleteuser', id, {headers: headers})
        
          .pipe(
            //catchError(this.handleError('deleteuser'))
          );
    }
}