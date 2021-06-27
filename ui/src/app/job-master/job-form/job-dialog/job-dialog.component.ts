import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Audit } from 'src/app/model/audit';
import { Job } from 'src/app/model/job';

@Component({
  selector: 'app-job-dialog',
  templateUrl: './job-dialog.component.html',
  styleUrls: ['./job-dialog.component.css']
})
export class JobDialogComponent implements OnInit {

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig) { }
  job: Job;
  audit: Audit;
  ngOnInit(): void {
    this.job = this.config.data.job;
    this.audit = this.config.data.audit;
    console.log('dialog job = ', this.config)
  }

}
