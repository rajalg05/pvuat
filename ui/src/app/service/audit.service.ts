import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { Audit } from '../model/audit';
import { AuditAllocation } from '../model/auditAllocation';
import { AuditDate } from '../model/auditDate';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuditService {
  public newAuditAddedEmit = new EventEmitter<Audit>();
  
  constructor(private http: HttpClient) { }
  BASE_URL: string = environment.apiUrl;

  
  saveAudit(audit: Audit): Observable<Audit> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.post<Audit>(this.BASE_URL + '/saveAudit', audit, {headers: headers});
  }

  deleteAudit(audit: Audit): Observable<string> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.post<string>(this.BASE_URL + '/deleteAudit', audit, {headers: headers});
  }

  findAllAudits(): Observable<Audit[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.get<Audit[]>(this.BASE_URL + '/findAllAudits', {headers: headers})
    
      .pipe(
        //catchError(this.handleError('findAllJobs'))
      );
  }

  findAllAuditDates(): Observable<AuditDate[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.get<AuditDate[]>(this.BASE_URL + '/findAllAuditDates', {headers: headers})
    
      .pipe(
        //catchError(this.handleError('findAllJobs'))
      );
  }

  findAllAllocatedAudits(): Observable<AuditAllocation[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.get<AuditAllocation[]>(this.BASE_URL + '/findAllAllocatedAudits', {headers: headers})
    
      .pipe(
        //catchError(this.handleError('findAllJobs'))
      );
  }

  allocateAudits(auditAllocation: AuditAllocation[]): Observable<AuditAllocation[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.post<AuditAllocation[]>(this.BASE_URL + '/allocateAudits', auditAllocation, {headers: headers})
    
      .pipe(
        //catchError(this.handleError('findAllJobs'))
      );
  }

  unallocateAudits(auditAllocation: AuditAllocation[]): Observable<AuditAllocation[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.post<AuditAllocation[]>(this.BASE_URL + '/unallocateAudits', auditAllocation, {headers: headers})
    
      .pipe(
        //catchError(this.handleError('findAllJobs'))
      );
  }
}
