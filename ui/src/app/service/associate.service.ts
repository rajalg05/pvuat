import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs'; 
import { Associate } from '../model/associateMaster'; 

@Injectable({
  providedIn: 'root'
})
export class AssociateService {

 
  constructor(private http: HttpClient) { }
  BASE_URL: string = 'http://ec2-18-224-109-184.us-east-2.compute.amazonaws.com:8085/audit';
  
  saveAssociate(associate: Associate): Observable<string> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.post<string>(this.BASE_URL + '/saveAssociate', associate, {headers: headers})
    
      .pipe(
        //catchError(this.handleError('saveResource'))
      );
  }

  deleteAssociate(associate: Associate): Observable<string> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.post<string>(this.BASE_URL + '/deleteAssociate', associate, {headers: headers})
    
      .pipe(
        //catchError(this.handleError('saveResource'))
      );
  }

  findAllAssociates(): Observable<Associate[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.get<Associate[]>(this.BASE_URL + '/findAllAssociates', {headers: headers})
    
      .pipe(
        //catchError(this.handleError('saveResource'))
      );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }
}
