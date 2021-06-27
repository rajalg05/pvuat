import { Component, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { PrimeNGConfig } from 'primeng/api';
import { Table } from 'primeng/table';
import { Audit } from '../model/audit';
import { AuditAllocation } from '../model/auditAllocation';
import { Resource } from '../model/resource';
import { AuditService } from '../service/audit.service';
import { ResourceService } from '../service/resource.service';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { AuditDate } from '../model/auditDate';
import * as moment from 'moment';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  providers: []
})
export class DashboardComponent implements OnInit {
  stateOptions: any[];
  value1: string = "Chart";
  // begin full calendar fields
  public events: any[] = [];

  options: any;

  private subscriptionAudit: any = null;

  private subscriptionAuditDate: any = null;

  audits: Audit[] = [];

  auditDates: AuditDate[] = [];

  loading: boolean = false;
  // end full calendar fields
  basicData: any;

  basicOptions: any;

  responsiveOptions;

  statuses: any[];

  constructor(resourceService: ResourceService,
    private primeNGConfig: PrimeNGConfig,
    private auditService: AuditService) {

    this.stateOptions = [
      { label: "Chart", value: "Chart" },
      { label: "Calendar", value: "Calendar" }
    ];
    this.responsiveOptions = [
      {
        breakpoint: '1024px',
        numVisible: 3,
        numScroll: 3
      },
      {
        breakpoint: '768px',
        numVisible: 2,
        numScroll: 2
      },
      {
        breakpoint: '560px',
        numVisible: 1,
        numScroll: 1
      }
    ];

  } 
  ngOnInit() {
    this.primeNGConfig.ripple = true;

    this.options = {
      plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
      defaultDate: '2021-06-01',
      header: {
        left: 'prev,next',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
      },
      eventClick: (eventClickEvent) => {
        console.log("EVENT CLICKED !!!", eventClickEvent);
      },
      eventMouseEnter : (eventClickEvent) => {
        this.showDialog();
        console.log("eventMouseEnter CLICKED !!!", eventClickEvent);
      },
      eventMouseLeave : (eventClickEvent) => {
        this.hideDialog();
        console.log("eventMouseLeave CLICKED !!!", eventClickEvent);
      }
    }
    this.statuses = [
      { label: 'Unqualified', value: 'unqualified' },
      { label: 'Qualified', value: 'qualified' },
      { label: 'New', value: 'new' },
      { label: 'Negotiation', value: 'negotiation' },
      { label: 'Renewal', value: 'renewal' },
      { label: 'Proposal', value: 'proposal' }
    ]
    this.basicData = {
      labels: ['Audit1', 'Audit2', 'Audit3', 'Audit4', 'Audit5', 'Audit6', 'Audit7'],
      datasets: [
        {
          label: 'Cost',
          backgroundColor: '#116fbf',
          data: [65, 59, 80, 81, 56, 55, 40]
        },
        {
          label: 'Profit',
          backgroundColor: '#c8c8c8',
          data: [28, 48, 40, 19, 86, 27, 90]
        }
      ]
    };
    this.subscriptionAudit = this.auditService.findAllAudits().subscribe(audits => {
      this.audits = audits;
      this.loading = false;

      this.subscriptionAuditDate = this.auditService.findAllAuditDates().subscribe(auditDates => {
        this.auditDates = auditDates;
        let id: number = 0;
        auditDates.map(auditDate => {
          let audit: Audit = this.audits.find(a => a.id == auditDate.auditId);
          this.events = [...this.events, {
            "id": id + 1,
            "title": audit.auditName,
            "start": moment(auditDate.auditDate).format("YYYY-MM-DD"),
            "auditId": audit.id
          }];
        }); 
      },
        error => {
          console.log('error findAllAudits : ', error)
        }); 
    },
      error => {
        console.log('error findAllAudits : ', error)
      });

    
    this.loading = false; 
  }
  handleDateClick(dateClickEvent) {
    dateClickEvent.preventDefault();
    console.log("DATE CLICKED !!!");
  
    // access member variables and functions using `this` keyword
  }
  display: boolean = false;

  showDialog() {
      this.display = true;
  }
  hideDialog() {
    this.display = false;
}
}




