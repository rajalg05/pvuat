import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { Audit } from '../model/audit';
import { Job } from '../model/job';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  jobs: Job[] = []; // Used to check the duplicate job name enter check 
  constructor(private http: HttpClient) { }
  BASE_URL: string = 'http://ec2-18-224-109-184.us-east-2.compute.amazonaws.com:8085/audit';
  
  saveJob(job: Job): Observable<Job> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.post<Job>(this.BASE_URL + '/saveJob', job, {headers: headers});
  }

  findAllJobs(): Observable<Job[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.get<Job[]>(this.BASE_URL + '/findAllJobs', {headers: headers})
    
      .pipe(
        //catchError(this.handleError('findAllJobs'))
      );
  }

  deleteJob(job: Job): Observable<string> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.post<string>(this.BASE_URL + '/deleteJob', job, {headers: headers})
    
      .pipe(
        //catchError(this.handleError('saveJob'))
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
