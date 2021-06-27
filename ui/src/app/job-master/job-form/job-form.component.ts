import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MessageService, SelectItem } from 'primeng/api';
import { Address } from 'src/app/model/address';
import { Associate } from 'src/app/model/associateMaster';
import { Audit } from 'src/app/model/audit';
import { BasicContactDetail } from 'src/app/model/BasicContactDetail';
import { Job } from 'src/app/model/job';
import { KYC } from 'src/app/model/kyc';
import { Resource } from 'src/app/model/resource';
import { JobService } from 'src/app/service/job.service';
import { ResourceService } from 'src/app/service/resource.service';
import { NgxSpinnerService } from "ngx-spinner";
import { DialogService } from 'primeng/dynamicdialog';
import { JobDialogComponent } from './job-dialog/job-dialog.component';
import { AuditService } from 'src/app/service/audit.service';

@Component({
  selector: 'app-job-form',
  templateUrl: './job-form.component.html',
  styleUrls: ['./job-form.component.css'],
  providers: [DialogService, MessageService]
})
export class JobFormComponent implements OnInit, OnDestroy {
  jobForm: FormGroup;  //declaring our form variable
  // phone 
  separateDialCode = false;
  cities: SelectItem[];
  city: SelectItem;
  list1: Resource[] = [];

  list2: Resource[] = [];
  
  auditNameDuplicate: boolean = false;
  
  jobNameDuplicate: boolean = false;

  private subsriptionResource: any = null;
  @Output() public tabNameChangeEmit = new EventEmitter();
  @Input() job: Job;
  constructor(private jobService: JobService,
    private auditService: AuditService,
    private resourceService: ResourceService,
    public dialogService: DialogService,
    private SpinnerService: NgxSpinnerService,
    private messageService: MessageService) {
    this.cities = [
      { label: 'Pune', value: 'pun' },
      { label: 'Mumbai', value: 'mum' },
      { label: 'Nagpur', value: 'nag' },
      { label: 'New Delhi', value: 'delhi' },
      { label: 'Kolkata', value: 'klk' },
      { label: 'Chennai', value: 'chn' }
    ];
  }

  ngOnInit(): void {
    this.subsriptionResource = this.resourceService.getResources().subscribe(resources => {
      this.list1 = resources;
    },
      error => {
        console.log('error getResources : ', error)
      });

    if (this.job == null) {
      this.jobForm = new FormGroup({
        jobName: new FormControl(null),
        associate: new FormControl(null),
        clientName: new FormControl(null),
        frequencyOfAudit: new FormControl(null),
        paymentType: new FormControl(null),
        totalPayment: new FormControl(null),
        resourcesNeeded: new FormControl(null),
        dateOfAudit: new FormControl(new Date()),
        paymentReceived: new FormControl(null),
        auditName: new FormControl(null),
        addressLine1: new FormControl(null),
        streetAddress2: new FormControl(null),
        city: new FormControl(null),
        state: new FormControl(null),
        postalCode: new FormControl(null),
        country: new FormControl('India')
      });
    } else {
      this.jobForm = new FormGroup({
        jobName: new FormControl(this.job.jobName),
        associate: new FormControl(this.job.associate),
        clientName: new FormControl(this.job.clientName),
        frequencyOfAudit: new FormControl(this.job.frequencyOfAudit),
        paymentType: new FormControl(this.job.paymentType),
        totalPayment: new FormControl(this.job.totalPayment),
        resourcesNeeded: new FormControl(this.job.resourcesNeeded),
        dateOfAudit: new FormControl(new Date()),
        paymentReceived: new FormControl(null),
        auditName: new FormControl(null, { validators: [Validators.required], updateOn: 'blur' }),
        addressLine1: new FormControl(null),
        streetAddress2: new FormControl(null),
        city: new FormControl(null),
        state: new FormControl(null),
        postalCode: new FormControl(null),
        country: new FormControl('India'),
      });
    }
  }

  handleInput(e: any) {
    if (this.job.audits != null) {
      let auditNameIndex: number = this.job.audits.findIndex(audit => audit.auditName == this.jobForm.get('auditName').value);
      if (auditNameIndex != -1) {
        this.jobForm.controls['auditName'].setErrors({ 'incorrect': true });
        this.auditNameDuplicate = true;
      } else {
        this.auditNameDuplicate = false;
      }
    }
  }
  handleDuplicateJob(e: any) {
    if (this.jobService.jobs != null) {
      let jobNameIndex: number = this.jobService.jobs.findIndex(job => job.jobName == this.jobForm.get('jobName').value);
      if (jobNameIndex != -1) {
        this.jobForm.controls['jobName'].setErrors({ 'incorrect': true });
        this.jobNameDuplicate = true;
      } else {
        this.jobNameDuplicate = false;
      }
    }
  }
  onSubmit() {
    this.SpinnerService.show();
    let job: Job = this.populateFormValues();
    
    if (this.job == null) { // only for Add job, save the job, not during save Audit, you should be able to edit job  
      this.jobService.saveJob(job).subscribe(data => {
        console.log('saveJob data = ', data);
        this.tabNameChangeEmit.emit(data);
        this.SpinnerService.hide();
        this.messageService.add({ severity: 'info', summary: 'Job Saved', detail: '' });
        this.jobService.jobs.push(data); // this will be used in Audit page. Save new Audit, update the job details in that new Audit 
      });
    }

  }
  populateFormValues() {
    let job: Job;
    if (this.job != null)
      job = Object.assign({}, this.job);
    else
      job = new Job();

    job.jobName = this.jobForm.get('jobName').value;
    job.clientName = this.jobForm.get('clientName').value;
    job.frequencyOfAudit = this.jobForm.get('frequencyOfAudit').value;
    job.paymentType = this.jobForm.get('paymentType').value;
    job.totalPayment = this.jobForm.get('totalPayment').value;
    job.resourcesNeeded = this.jobForm.get('resourcesNeeded').value;

    job.associate = this.populateAssociate();
    let audit: Audit = null;
    if (this.job !== null) {
      audit = this.populateAudit();

      //job.audits = []; // Check if the original this.job has audits not null, if yes then take this.job.audits as base. Then the new audit is pushed in that job.audits
      if (this.job.audits != null) {
        job.audits = this.job.audits;
      } else {
        job.audits = [];
      }
      job.audits.push(audit);
      job.auditOrJob = 'Audit'; // to display Audit fields in job form. Pls remember there is not separate Audit form, rather its embedded inside job form
    }
    if (audit != null) {
      this.auditService.saveAudit(audit).subscribe(
        data => console.log('success', data)
      );
    }

    return job;
  }
  populateAssociate() {
    // TO DO - get the associate data from login module. 
    let associate: Associate = new Associate();
    let basicContactDetail = new BasicContactDetail();
    let address = new Address();
    let kyc = new KYC();

    basicContactDetail.firstName = 'Munish';
    basicContactDetail.lastName = 'Gupta';
    //basicContactDetail.whatsappCountryCode = this.associateForm.get('whatsappCountryCode').value;
    basicContactDetail.whatsappMobileNumber = 9999999;
    basicContactDetail.email = 'sgupta@gmail.com';

    associate.basicContactDetail = basicContactDetail;

    address.addressLine1 = 'G302, Mystique moods';
    //address.streetAddress1 = this.associateForm.get('streetAddress1').value;
    address.streetAddress2 = 'Viman Nagar';
    address.city = 'Pune';
    address.state = 'Maharashtra';
    address.postalCode = '411014';
    address.country = 'India';

    associate.address = address;

    //kyc.firstKycId = this.associateForm.get('firstKycId').value;
    kyc.firstKycType = 'Adhar';
    //kyc.secondKycId = this.associateForm.get('secondKycId').value;
    kyc.secondKycType = 'PAN';

    associate.kyc = kyc;

    return associate;
  }

  populateAudit() {
    let audit: Audit = new Audit();
    let address = new Address();

    audit.jobId = this.job.id;
    //audit.job = this.job;

    address.addressLine1 = this.jobForm.get('addressLine1').value;
    //address.streetAddress1 = this.associateForm.get('streetAddress1').value;
    address.streetAddress2 = this.jobForm.get('streetAddress2').value;
    address.city = this.jobForm.get('city').value;
    address.state = this.jobForm.get('state').value;
    address.postalCode = this.jobForm.get('postalCode').value;
    address.country = 'India';

    audit.address = address;

    //audit.auditLocationAddressId = null;
    audit.auditStatus = 'Audit created';
    //audit.dateOfAudit = this.jobForm.get('dateOfAudit').value;
    audit.paymentReceived = this.jobForm.get('paymentReceived').value;
    audit.auditName = this.jobForm.get('auditName').value;

    audit.statusUpdatedBy = 'LG';
    this.SpinnerService.hide();
    return audit;
  }
  numericOnly(event) {
    let patt = /^([0-9])$/;
    let result = patt.test(event.key);
    return result;
  }
  adharFiles: any = [];


  reset(e: any) {
    this.jobForm.reset();
  }
  reviewDialog() {
    let audit: Audit = this.populateAudit();
    const ref = this.dialogService.open(JobDialogComponent, {
      header: 'Form Summary',
      width: '50%',
      // data: this.job
      data: {
        "job": this.job,
        "audit": audit
      }
    });
  }
  ngOnDestroy() {
    if(this.subsriptionResource != undefined)
      this.subsriptionResource.unsubscribe();
  }
}
