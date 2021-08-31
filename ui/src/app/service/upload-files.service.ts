import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UploadFilesService {
 
  BASE_URL: string = environment.apiUrl;

  constructor(private http: HttpClient) { }

  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    
    formData.append('file', file);

    /* const req = new HttpRequest(
      'POST', 
    `${this.BASE_URL}/upload`, 
    formData, 
    {headers: headers},
    {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req); */
    return this.http.post<HttpEvent<any>>(this.BASE_URL + '/upload', formData, {headers: headers})
    .pipe(
      //catchError(this.handleError('findAllJobs'))
    );
  }

  getFiles(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/files`);
  }
}