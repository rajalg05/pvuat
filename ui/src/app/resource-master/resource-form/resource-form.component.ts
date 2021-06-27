import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MessageService, SelectItem } from 'primeng/api';
import { Address } from 'src/app/model/address';
import { BasicContactDetail } from 'src/app/model/BasicContactDetail';
import { KYC } from 'src/app/model/kyc';
import { Resource } from 'src/app/model/resource';
import { ResourceService } from 'src/app/service/resource.service';
import {DialogService} from 'primeng/dynamicdialog';
import { ResourceDialogComponent } from './resource-dialog/resource-dialog.component';
import { SearchCountryField, CountryISO, PhoneNumberFormat } from 'ngx-intl-tel-input';

@Component({
  selector: 'app-resource-form',
  templateUrl: './resource-form.component.html',
  styleUrls: ['./resource-form.component.css'],
  providers: [DialogService, MessageService]
})
export class ResourceFormComponent implements OnInit {
  resourceForm: FormGroup;  //declaring our form variable
  // phone 
  separateDialCode = false;
	SearchCountryField = SearchCountryField;
	CountryISO = CountryISO;
  PhoneNumberFormat = PhoneNumberFormat;
	preferredCountries: CountryISO[] = [CountryISO.UnitedStates, CountryISO.UnitedKingdom];
  cities: SelectItem[];
  city: SelectItem;
  
  payments: SelectItem[];
  payment: SelectItem;
  

  bikes: SelectItem[];
  bike: SelectItem;


  qualifications: SelectItem[];
  excelSkills: SelectItem[];

  stockAuditExps: SelectItem[];
  tlNonTls: SelectItem[];
  @Output() public tabNameChangeEmit = new EventEmitter();
  @Input() resource: Resource;
  constructor(private resourceService: ResourceService,
    public dialogService: DialogService,
    private messageService: MessageService) {
    this.cities = [
      { label: 'Pune', value: 'Pune' },
      { label: 'Mumbai', value: 'Mumbai' },
      { label: 'Nagpur', value: 'Nagpur' },
      { label: 'New Delhi', value: 'New Delhi' },
      { label: 'Kolkata', value: 'Kolkata' },
      { label: 'Chennai', value: 'Chennai' }
    ];

    this.payments = [
      { label: 'Monthly', value: 'Monthly' },
      { label: 'Daily', value: 'Daily' }
    ];

    this.bikes = [
      { label: 'Yes', value: 'Yes' },
      { label: 'No', value: 'No' },
      { label: 'Third Choice', value: 'Third Choice' }
    ];

    this.qualifications = [
      { label: 'Graduate', value: 'Graduate' },
      { label: 'Post Graduate', value: 'Post Graduate' },
      { label: 'Under Grad', value: 'Under Grad' }
    ];

    this.excelSkills = [
      { label: 'Yes', value: 'Yes' },
      { label: 'No', value: 'No' },
      { label: 'Third Choice', value: 'Third Choice' }
    ];

    this.stockAuditExps = [
      { label: '0', value: '0' },
      { label: '1-3', value: '1-3' },
      { label: '>3', value: '>3' }
    ];

    this.tlNonTls = [
      { label: 'TL', value: 'TL' },
      { label: 'Non TL', value: 'Non TL' }
    ];
  }

  ngOnInit(): void {
    if (this.resource == null) {
      this.resourceForm = new FormGroup({
        firstName: new FormControl(null),
        lastName: new FormControl(null),
        email: new FormControl(null),
        addressLine1: new FormControl(null),
        streetAddress2: new FormControl(null),
        city: new FormControl(null),
        state: new FormControl(null),
        postalCode: new FormControl(null),
        country: new FormControl('India'),
        dob: new FormControl(null),
        bike: new FormControl(null),
        phone: new FormControl(null),
        firstKycId: new FormControl(null),
        secondKycId: new FormControl(null),
        qualification: new FormControl(null),
        excelSkill: new FormControl(null),
        stockAuditExp: new FormControl(null),
        tlNonTl: new FormControl(null),
        gender: new FormControl('Male'),
        paymentType: new FormControl(null),
        paymentAmount: new FormControl(null)
      });
    } else {
      this.resourceForm = new FormGroup({
        firstName: new FormControl(this.resource.basicContactDetail.firstName),
        lastName: new FormControl(this.resource.basicContactDetail.lastName),
        email: new FormControl(this.resource.basicContactDetail.email),
        addressLine1: new FormControl(this.resource.address.addressLine1),
        streetAddress2: new FormControl(this.resource.address.streetAddress2),
        city: new FormControl(this.resource.address.city),
        state: new FormControl(this.resource.address.state),
        postalCode: new FormControl(this.resource.address.postalCode),
        country: new FormControl('India'),
        dob: new FormControl(new Date(this.resource.dateOfBirth)),
        bike: new FormControl(this.resource.bike),
        phone: new FormControl(null),
        firstKycId: new FormControl(this.resource.kyc.firstKycId),
        secondKycId: new FormControl(this.resource.kyc.secondKycId),
        qualification: new FormControl(this.resource.qualification),
        excelSkill: new FormControl(this.resource.excelSkills),
        stockAuditExp: new FormControl(this.resource.stockAuditExp),
        tlNonTl: new FormControl(this.resource.resourceType),
        gender: new FormControl('Male'),
        paymentType: new FormControl(this.resource.paymentType),
        paymentAmount: new FormControl(this.resource.paymentAmount)
      }); 
    }
  }
  onSubmit() {
    let resource: Resource = this.populateFormValues();
    //this.tabNameChangeEmit.emit(this.resourceForm.get('firstName').value);
    this.tabNameChangeEmit.emit(resource);

    this.resourceService.saveResource(resource).subscribe(data => {
      console.log('resource data = ', data);
      this.messageService.add({ severity: 'info', summary: 'Resource Saved', detail: '' });
      // update the Resources list
    });
  }
  populateFormValues() {
    let resource: Resource = new Resource();
    let basicContactDetail = new BasicContactDetail();
    let address = new Address();
    let kyc = new KYC();

    resource.dateOfBirth = this.resourceForm.get('dob').value;
    resource.excelSkills = this.resourceForm.get('excelSkill').value;
    resource.qualification = this.resourceForm.get('qualification').value;
    resource.resourceType = this.resourceForm.get('tlNonTl').value;
    resource.stockAuditExp = this.resourceForm.get('stockAuditExp').value;
    resource.bike = this.resourceForm.get('bike').value;
    resource.allocated = 'false';
    
    basicContactDetail.firstName = this.resourceForm.get('firstName').value;
    basicContactDetail.lastName = this.resourceForm.get('lastName').value;
    
    let phoneNumber: number = this.resourceForm.get('phone').value;
    basicContactDetail.whatsappMobileNumber = phoneNumber['number'];
    basicContactDetail.whatsappCountryCode = phoneNumber['dialCode'];

    basicContactDetail.email = this.resourceForm.get('email').value;

    resource.basicContactDetail = basicContactDetail;

    address.addressLine1 = this.resourceForm.get('addressLine1').value;
    //address.streetAddress1 = this.resourceForm.get('streetAddress1').value;
    address.streetAddress2 = this.resourceForm.get('streetAddress2').value;
    address.city = this.resourceForm.get('city').value;
    address.state = this.resourceForm.get('state').value;
    address.postalCode = this.resourceForm.get('postalCode').value;
    address.country = this.resourceForm.get('country').value;

    resource.address = address;

    kyc.firstKycId = this.resourceForm.get('firstKycId').value;
    kyc.firstKycType = 'Adhar';
    kyc.secondKycId = this.resourceForm.get('secondKycId').value;
    kyc.secondKycType = 'PAN';

    resource.kyc = kyc;

    return resource;
  }
  numericOnly(event) {
    let patt = /^([0-9])$/;
    let result = patt.test(event.key);
    return result;
  }
  adharFiles: any = [];

  uploadAdharFile(event) {
    for (let index = 0; index < event.length; index++) {
      const element = event[index];
      this.adharFiles.push(element.name)
    }
  }
  deleteAdharAttachment(index) {
    this.adharFiles.splice(index, 1)
  }
  panFiles: any = [];

  uploadPanFile(event) {
    for (let index = 0; index < event.length; index++) {
      const element = event[index];
      this.panFiles.push(element.name)
    }
  }
  deletePanAttachment(index) {
    this.panFiles.splice(index, 1)
  }

  reset(e: any) {
    this.resourceForm.reset();
    this.adharFiles = [];
    this.panFiles = [];
    this.resourceForm.get('country').setValue('India');
  }
  displayReviewDialog: boolean = false;
  reviewDialog() {
    const ref = this.dialogService.open(ResourceDialogComponent, {
      header: 'Form Summary',
      width: '50%',
      data: this.resource
  });
    //this.displayReviewDialog = true;
  }
}


