import { typeWithParameters } from '@angular/compiler/src/render3/util';
import { Component, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { PrimeNGConfig } from 'primeng/api';
import { Table } from 'primeng/table';
import { Audit } from '../model/audit';
import { AuditAllocation } from '../model/auditAllocation';
import { AuditDate } from '../model/auditDate';
import { Resource } from '../model/resource';
import { AuditService } from '../service/audit.service';
import { ResourceService } from '../service/resource.service';

@Component({
  selector: 'app-audit-allocation',
  templateUrl: './audit-allocation.component.html',
  styleUrls: ['./audit-allocation.component.css']
})
export class AuditAllocationComponent implements OnInit, OnChanges {

  resources: Resource[] = [];

  events: any[];

  options: any;

  audits: Audit[] = [];

  selectedAudit: Audit;

  selectedAuditDate: string;

  private subscriptionResource: any = null;

  private subscriptionAudit: any = null;

  private subscriptionAuditAllocation: any = null;

  statuses: any[];

  loading: boolean = false;

  @ViewChild('dt') table: Table;

  activityValues: number[] = [0, 100];

  sourceList: Resource[] = [];
  
  targetList: Resource[] = [];

  allocatedResources: number = 0;

  allocatedTLs: number = 0;

  unAllocatedResources: number = 0;

  unAllocatedTLs: number = 0;

  showPickList: boolean = false;

  allocatedAudits: AuditAllocation[] = [];

  unAllocatedAudits: AuditAllocation[] = [];

  knobValue: number = 15;

  constructor(resourceService: ResourceService,
    private auditService: AuditService) {

    this.subscriptionResource = resourceService.unAllocatedResources().subscribe(resources => {
      this.resources = resources;
      this.sourceList = [...resources];
      //this.updateResourceCount(resources);
    },
      error => {
        console.log('error getResources : ', error)
      });


  }
  ngOnInit() {
    this.subscriptionAudit = this.auditService.findAllAudits().subscribe(audits => {
      this.audits = audits;
      this.loading = false
    },
      error => {
        console.log('error findAllAudits : ', error)
      });

    this.subscriptionAuditAllocation = this.auditService.findAllAllocatedAudits().subscribe(allocatedAudits => {
      this.allocatedAudits = allocatedAudits;
      this.loading = false
    },
      error => {
        console.log('error findAllAudits : ', error)
      });

  }
  ngOnChanges(changes: SimpleChanges): void {
    // TO DO - Audits newly created/added to job should be stacked here as well. 
    
  }
  onActivityChange(event) {
    const value = event.target.value;
    if (value && value.trim().length) {
      const activity = parseInt(value);

      if (!isNaN(activity)) {
        this.table.filter(activity, 'activity', 'gte');
      }
    }
  }

  onDateSelect(value) {
    this.table.filter(this.formatDate(value), 'date', 'equals')
  }

  formatDate(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();

    if (month < 10) {
      month = '0' + month;
    }

    if (day < 10) {
      day = '0' + day;
    }

    return date.getFullYear() + '-' + month + '-' + day;
  }

  onRepresentativeChange(event) {
    this.table.filter(event.value, 'representative', 'in')
  }

  ngOnDestroy() {
    if(this.subscriptionResource != undefined)
      this.subscriptionResource.unsubscribe();

    if(this.subscriptionAudit != undefined)  
      this.subscriptionAudit.unsubscribe();

    if(this.subscriptionAuditAllocation != undefined)  
      this.subscriptionAuditAllocation.unsubscribe();
      
  }
  @ViewChild('auditDateDropDown') auditDateDropDown;
  isNaN: Function = Number.isNaN;
  approvedAmount: number;
  profit: number;
  profitPercentage: number;
  
  allocateResource(audit: Audit) {
    this.approvedAmount = audit.paymentReceived;

    this.targetList = [];
    if(audit.allocatedResources != null) {
      audit.auditDates.forEach(auditDate => {
        if(auditDate.code == this.auditDateDropDown.selectedOption.code) {
          this.allocatedAudits.forEach(aa => {
            if(aa.auditDate.id == auditDate.id) {
              if(!this.targetList.includes(aa.resource)) {
                this.targetList.push(aa.resource);
                this.profit = this.approvedAmount - aa.resource.paymentAmount;
                this.profitPercentage = (this.approvedAmount - aa.resource.paymentAmount) * 100 / this.approvedAmount;
              }
            }
          });
        }
      });
      //this.targetList = audit.allocatedResources;
    }
    else {
      this.targetList = [];
    }  
    this.showPickList = true;
  }
  updateResourceCount(resources: Resource[]) {
    resources.forEach(r => {
      if (r.resourceType == 'TL') {
        this.unAllocatedTLs--;
        this.allocatedTLs++;
      } else if (r.resourceType == 'Non TL') {
        this.unAllocatedResources--;
        this.allocatedResources++;
      }
    });
  }
  saveAllocateAuditAndResource(resource: Resource) {
    let aa: AuditAllocation = new AuditAllocation();
    aa.resource = resource;
    aa.resource.allocated = 'true';
    aa.audit = this.selectedAudit;
    if (this.selectedAuditDate == undefined) { // when user has NOT selected any dates in the row
      aa.audit.selectedAuditDate = aa.audit.auditDates[0]; // TO DO populate the auditDateId into the this.selectedAuditDate
      aa.auditDate = aa.audit.auditDates[0];
    } else {
      // aa.audit.selectedAuditDate = this.selectedAuditDate; // TO DO populate the auditDateId into the this.selectedAuditDate
      // aa.auditDate = this.selectedAuditDate;
      let index = aa.audit.auditDates.findIndex(t => t.code == this.selectedAuditDate);
      aa.audit.selectedAuditDate = aa.audit.auditDates[index];
      aa.auditDate = aa.audit.auditDates[index];
    }
    
    let saveAllocatedAudits: AuditAllocation[] = [];

    saveAllocatedAudits.push(aa);

    this.auditService.allocateAudits(saveAllocatedAudits).subscribe(data => {
      console.log('allocatedAudits response = ', data);
      this.audits.map(audit => {
        if(audit.id == aa.audit.id && audit.allocatedResources == null) {
          audit.allocatedResources = [];
          audit.allocatedResources.push(resource);
        } else if(audit.id == aa.audit.id && audit.allocatedResources != null) {
          audit.allocatedResources.push(resource);
        }
      });
    }); 
  }
  deleteAudit(audit: Audit) {
    this.auditService.deleteAudit(audit).subscribe(data => {
      console.log('deleteAudit response = ', data);
      this.audits.splice(this.audits.indexOf(audit), 1);
    });
  }

  onMoveToTarget(event) {
    console.log('event onMoveToTarget = ', event);
    event.items.forEach(item => {
      this.saveAllocateAuditAndResource(item);
    });
  }

  onMoveToSource(event) {
    console.log('event onMoveToSource = ', event);
    event.items.forEach(item => {
      this.unAllocateAuditAndResource(item);  
    });
    
  }
  onRowSelect(event) {
    console.log('event.data onRowSelect = ', event.data);
   
  }
  unAllocateAuditAndResource(resource: Resource) {
    let aa: AuditAllocation = new AuditAllocation();
    aa.resource = resource;
    aa.resource.allocated = 'false';
    aa.audit = this.selectedAudit;

    this.unAllocatedAudits.push(aa);
    this.auditService.unallocateAudits(this.unAllocatedAudits).subscribe(data => {
      console.log('unAllocatedAudits response = ', data);
      this.audits.map(audit => {
        if(audit.id == aa.audit.id && audit.allocatedResources != null) {
          let index: number = audit.allocatedResources.findIndex(r => r.id == resource.id);
          audit.allocatedResources.splice(index, 1);
        }
      });
    });
  } 

}
