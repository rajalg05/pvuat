import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { MessageService, SelectItem } from 'primeng/api'; 
import { Job } from 'src/app/model/job'; 
import { Resource } from 'src/app/model/resource';
import { JobService } from 'src/app/service/job.service';
import { ResourceService } from 'src/app/service/resource.service';
import { NgxSpinnerService } from "ngx-spinner";
import { DialogService } from 'primeng/dynamicdialog';
import { AuditService } from 'src/app/service/audit.service'; 
import { Audit } from 'src/app/model/audit';
import { Address } from 'src/app/model/address';
import * as moment from 'moment';
import { AuditDate } from 'src/app/model/auditDate';

@Component({
  selector: 'app-audit-form',
  templateUrl: './audit-form.component.html',
  styleUrls: ['./audit-form.component.css'],
  providers: [DialogService, MessageService]
})
export class AuditFormComponent implements OnInit {
  auditForm: FormGroup;  //declaring our form variable
  // phone 
  separateDialCode = false;
  cities: SelectItem[];
  city: SelectItem;
  list1: Resource[] = [];

  list2: Resource[] = [];
  auditDates: Date[] = [];
  auditNameDuplicate: boolean = false;
  
  jobNameDuplicate: boolean = false;

  private subsriptionResource: any = null;
  @Output() public tabNameChangeAuditEmit = new EventEmitter();
  @Input() audit: Audit;
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
  dateOfAudit: Date[] = [];

  ngOnInit(): void {

    if (this.audit.auditName == undefined) {
      this.auditForm = new FormGroup({
        dateOfAudit: new FormControl(),
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
      let dates: Date[] = [];
      this.audit.auditDates.forEach(ad => {
        dates.push(new Date(ad.auditDate));
      }); 
      this.auditForm = new FormGroup({
        dateOfAudit: new FormControl(dates),
        paymentReceived: new FormControl(this.audit.paymentReceived),
        auditName: new FormControl(this.audit.auditName),
        addressLine1: new FormControl(this.audit.address.addressLine1),
        streetAddress2: new FormControl(this.audit.address.streetAddress2),
        city: new FormControl(this.audit.address.city),
        state: new FormControl(this.audit.address.state),
        postalCode: new FormControl(this.audit.address.postalCode),
        country: new FormControl('India')
      });
    }

  }
 
  onSubmit() {
    this.SpinnerService.show();
    this.populateAudit();

    this.auditService.saveAudit(this.audit).subscribe(data => {
      console.log('saveAudit data = ', data);
      this.tabNameChangeAuditEmit.emit(data);
      this.SpinnerService.hide();
      this.messageService.add({ severity: 'info', summary: 'Audit Saved', detail: 'Audit Saved' });
    });
  }

  populateAudit() {
    let address = new Address(); 
    address.addressLine1 = this.auditForm.get('addressLine1').value;
    //address.streetAddress1 = this.associateForm.get('streetAddress1').value;
    address.streetAddress2 = this.auditForm.get('streetAddress2').value;
    address.city = this.auditForm.get('city').value;
    address.state = this.auditForm.get('state').value;
    address.postalCode = this.auditForm.get('postalCode').value;
    address.country = 'India';

    this.audit.address = address;

    //audit.auditLocationAddressId = null;
    this.audit.auditStatus = 'Audit created';
   
    this.audit.auditDates;
    this.audit.paymentReceived = this.auditForm.get('paymentReceived').value;
    this.audit.auditName = this.auditForm.get('auditName').value;

    this.audit.statusUpdatedBy = 'LG';

    this.auditDates = this.auditForm.get('dateOfAudit').value;
    this.audit.auditDates = [];
    this.auditDates.forEach(dt => {
      let auditDate = new AuditDate();
      auditDate.auditDate = dt;
      auditDate.name = moment(dt).format("YYYY-MM-DD");
      auditDate.code = moment(dt).format("YYYY-MM-DD");
      // auditDate.audit = this.audit // dont put parent ref in audit - it gies circular dependency error 
      auditDate.auditId = this.audit.id;
      this.audit.auditDates.push(auditDate);
    });
  }
  reset(e: any) {
    this.auditForm.reset();
  }

  get auditDate() {
    return this.auditForm.controls['auditDate'] as FormArray;
  }
}
