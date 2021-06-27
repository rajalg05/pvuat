import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { SelectItem } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { Address } from 'src/app/model/address';
import { Associate } from 'src/app/model/associateMaster';
import { BasicContactDetail } from 'src/app/model/BasicContactDetail';
import { KYC } from 'src/app/model/kyc';
import { Resource } from 'src/app/model/resource';
import { AssociateService } from 'src/app/service/associate.service'; 
import { AssociateDialogComponent } from './associate-dialog/associate-dialog.component';

@Component({
  selector: 'app-associate-form',
  templateUrl: './associate-form.component.html',
  styleUrls: ['./associate-form.component.css'],
  providers: [DialogService]
})
export class AssociateFormComponent implements OnInit {
  
  associateForm: FormGroup;  //declaring our form variable
  separateDialCode = false;
  cities: SelectItem[];
  city: SelectItem;

  bikes: SelectItem[];
  bike: SelectItem;

  qualifications: SelectItem[];
  excelSkills: SelectItem[];

  stockAuditExps: SelectItem[];
  tlNonTls: SelectItem[];
  @Output() public tabNameChangeEmit = new EventEmitter();
  @Input() associate: Associate;
  constructor(private associateService: AssociateService,
    public dialogService: DialogService) {
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
    if (this.associate == null) {
      this.associateForm = new FormGroup({
        firstName: new FormControl(null),
        lastName: new FormControl(null),
        email: new FormControl(null),
        addressLine1: new FormControl(null),
        streetAddress2: new FormControl(null),
        city: new FormControl(null),
        state: new FormControl(null),
        postalCode: new FormControl(null),
        country: new FormControl('India'),
        phone: new FormControl(null),
        firstKycId: new FormControl(null),
        secondKycId: new FormControl(null),
        gender: new FormControl('Male')
      });
    } else {
      this.associateForm = new FormGroup({
        firstName: new FormControl(this.associate.basicContactDetail.firstName),
        lastName: new FormControl(this.associate.basicContactDetail.lastName),
        email: new FormControl(this.associate.basicContactDetail.email),
        addressLine1: new FormControl(this.associate.address.addressLine1),
        streetAddress2: new FormControl(this.associate.address.streetAddress2),
        city: new FormControl(this.associate.address.city),
        state: new FormControl(this.associate.address.state),
        postalCode: new FormControl(this.associate.address.postalCode),
        country: new FormControl('India'),
        phone: new FormControl(null),
        firstKycId: new FormControl(this.associate.kyc.firstKycId),
        secondKycId: new FormControl(this.associate.kyc.secondKycId),
        gender: new FormControl('Male')
      }); 
    }
  }
  onSubmit() {
    //this.tabNameChangeEmit.emit(this.associateForm.get('firstName').value);
    let associate: Associate = this.populateFormValues();
    this.tabNameChangeEmit.emit(associate);

    this.associateService.saveAssociate(this.populateFormValues()).subscribe(data => {
      console.log('resource data = ', data);
    });
  }
  populateFormValues() {
    let associate: Associate = new Associate();
    let basicContactDetail = new BasicContactDetail();
    let address = new Address();
    let kyc = new KYC();

    basicContactDetail.firstName = this.associateForm.get('firstName').value;
    basicContactDetail.lastName = this.associateForm.get('lastName').value;
    //basicContactDetail.whatsappCountryCode = this.associateForm.get('whatsappCountryCode').value;
    basicContactDetail.whatsappMobileNumber = this.associateForm.get('phone').value;
    basicContactDetail.email = this.associateForm.get('email').value;

    associate.basicContactDetail = basicContactDetail;

    address.addressLine1 = this.associateForm.get('addressLine1').value;
    //address.streetAddress1 = this.associateForm.get('streetAddress1').value;
    address.streetAddress2 = this.associateForm.get('streetAddress2').value;
    address.city = this.associateForm.get('city').value['value'];
    address.state = this.associateForm.get('state').value;
    address.postalCode = this.associateForm.get('postalCode').value;
    address.country = this.associateForm.get('country').value;

    associate.address = address;

    kyc.firstKycId = this.associateForm.get('firstKycId').value;
    kyc.firstKycType = 'Adhar';
    kyc.secondKycId = this.associateForm.get('secondKycId').value;
    kyc.secondKycType = 'PAN';

    associate.kyc = kyc;

    return associate;
  }
  numericOnly(event) {
    let patt = /^([0-9])$/;
    let result = patt.test(event.key);
    return result;
  }
   reset(e: any) {
    this.associateForm.reset();
    this.associateForm.get('country').setValue('India');
  }
  displayReviewDialog: boolean = false;
  reviewDialog() {
    const ref = this.dialogService.open(AssociateDialogComponent, {
      header: 'Form Summary',
      width: '50%',
      data: this.associate
  });
    //this.displayReviewDialog = true;
  }

}
