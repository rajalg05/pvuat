import { Injectable } from '@angular/core';
import { Job } from '../model/job';
import { ManPower } from '../model/manPower';

@Injectable({
  providedIn: 'root'
})
export class CoreService {
  public manpowerMaster: boolean = false;
  public associate: boolean = false;
  public jobMaster: boolean = false;
  public auditStatus: boolean = false;
  public costSheet: boolean = false;
  dataSourceManPower: ManPower[] = [];
  dataSourceJobMaster: Job[] = [];
  constructor() { }
}
