import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { PrimeNGConfig, SelectItem } from 'primeng/api';
import { Associate } from 'src/app/model/associateMaster';
import { Audit } from 'src/app/model/audit';
import { Job } from 'src/app/model/job';
import { AssociateService } from 'src/app/service/associate.service';
import { JobService } from 'src/app/service/job.service';

@Component({
  selector: 'app-job-view',
  templateUrl: './job-view.component.html',
  styleUrls: ['./job-view.component.css']
})
export class JobViewComponent implements OnInit, OnChanges {

  jobs: Job[] = [];

  @Input() job: Job; // sent from resource-form on submit to resource-master which in turn sent via Input so update resource[] 

  @Input() audit: Audit; // sent from resource-form on submit to resource-master which in turn sent via Input so update resource[] 

  sortOptions: SelectItem[];

  sortOrder: number;

  sortField: string;

  @Output() sendJobEmitter = new EventEmitter();

  @Output() sendAuditEmitter = new EventEmitter();

  constructor(private primengConfig: PrimeNGConfig,
    private jobService: JobService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.job != undefined && this.job.jobName && this.jobs.length == 0) { // if jobs array is empty and job added first time
      this.jobs = [...this.jobs, this.job]; // update the Resource list tab when a new Resource is added in Resource form
    } else if (this.job != undefined && this.job.jobName && this.jobs.length > 0) {
      let index: number = this.jobs.findIndex(job => job.jobName == this.job.jobName);
      if (index == -1)
        this.jobs = [...this.jobs, this.job]; // update the Resource list tab when a new Resource is added in Resource form
      else {
        this.jobs[index] = this.job;
      }
    }
    
  }
  ngOnInit() {
    this.jobService.findAllJobs().subscribe(data => {
      console.log('associate = ', data);
      if(data != null) {
        this.jobs = data;
        this.jobService.jobs = data;
      }
    })

    this.sortOptions = [
      { label: 'Price High to Low', value: '!price' },
      { label: 'Price Low to High', value: 'price' }
    ];

    this.primengConfig.ripple = true;
  }

  onSortChange(event) {
    let value = event.value;

    if (value.indexOf('!') === 0) {
      this.sortOrder = -1;
      this.sortField = value.substring(1, value.length);
    }
    else {
      this.sortOrder = 1;
      this.sortField = value;
    }
  }
  openJobTab(job: Job) {
    this.sendJobEmitter.emit(job);
    job.auditOrJob = 'Job';
  }
  addNewAuditTab(job: Job) {
    let index: number = this.jobs.findIndex(t => t.jobName == job.jobName);
    console.log('job = ', job);
    job = this.jobs[index];
    job.auditOrJob = 'Audit';
    let audit: Audit = new Audit();
    audit.jobId = job.id;
    audit.jobName = job.jobName;
    this.sendAuditEmitter.emit(audit);
  }
  deleteJob(job: Job) {
    this.jobs = this.jobs.filter(o => o !== job);
    this.jobService.deleteJob(job).subscribe(job => {
      console.log('jobs deleted = ', job);
      let index: number = this.jobService.jobs.findIndex(t => t.jobName == job['jobName']);
      this.jobService.jobs.splice(index, 1);
    });
  }
  addJobAuditTab(job: Job, audit: Audit) {
    console.log('audit = ', audit.auditName);
    //job.audits.push(audit);
    audit.jobName = job.jobName;
    this.sendAuditEmitter.emit(audit);
  }
}
