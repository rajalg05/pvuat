import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { SelectItem } from 'primeng/api/selectitem';
 

declare var google: any;
@Component({
  selector: 'app-audit-status',
  templateUrl: './audit-status.component.html',
  styleUrls: ['./audit-status.component.css'],
  providers: [MessageService]
})
export class AuditStatusComponent implements OnInit {
  selectedJob: string;

  selectedTlNonTls: string;

  stepItems: MenuItem[];

  items: SelectItem[];

  item: string;

  jobs: any[];

  tlNonTls: any[];

  options: any;

  overlays: any[];

  dialogVisible: boolean;

  markerTitle: string;

  selectedPosition: any;

  infoWindow: any;

  draggable: boolean;
  constructor(private messageService: MessageService) { }

  ngOnInit(): void {

    this.stepItems = [
      {label: 'Job Created'},
      {label: 'Audits Created'},
      {label: 'Resources Allocated'},
      {label: 'Audits Created'},
      {label: 'Audit Started'},
      {label: 'Audits Completed'},
      {label: 'MIS Report sent'},
  ];

    this.jobs = [
      { name: "JOB1", code: "job1" },
      { name: "JOB2", code: "job2" },
      { name: "JOB3", code: "job3" },
      { name: "JOB4", code: "job4" },
      { name: "JOB5", code: "job5" },
      { name: "JOB6", code: "job6" },
    ];

    this.tlNonTls = [
      { name: "Rohit", code: "adhar1" },
      { name: "Vinit", code: "adhar2" },
      { name: "Keshav", code: "adhar3" },
    ];

    this.options = {
      center: { lat: 18.516726, lng: 73.856255 },
      zoom: 12
    };

    this.overlays = [
      new google.maps.Marker({ position: { lat: 36.879466, lng: 30.667648 }, title: "Konyaalti" }),
      new google.maps.Marker({ position: { lat: 36.883707, lng: 30.689216 }, title: "Ataturk Park" }),
      new google.maps.Marker({ position: { lat: 36.885233, lng: 30.702323 }, title: "Oldtown" }),
      new google.maps.Polygon({
        paths: [
          { lat: 36.9177, lng: 30.7854 }, { lat: 36.8851, lng: 30.7802 }, { lat: 36.8829, lng: 30.8111 }, { lat: 36.9177, lng: 30.8159 }
        ], strokeOpacity: 0.5, strokeWeight: 1, fillColor: '#1976D2', fillOpacity: 0.35
      }),
      new google.maps.Circle({ center: { lat: 36.90707, lng: 30.56533 }, fillColor: '#1976D2', fillOpacity: 0.35, strokeWeight: 1, radius: 1500 }),
      new google.maps.Polyline({ path: [{ lat: 36.86149, lng: 30.63743 }, { lat: 36.86341, lng: 30.72463 }], geodesic: true, strokeColor: '#FF0000', strokeOpacity: 0.5, strokeWeight: 2 })
    ];
  }

  handleMapClick(event) {
    this.dialogVisible = true;
    this.selectedPosition = event.latLng;
  }

  handleOverlayClick(event) {
    let isMarker = event.overlay.getTitle != undefined;

    if (isMarker) {
      let title = event.overlay.getTitle();
      this.infoWindow.setContent('' + title + '');
      this.infoWindow.open(event.map, event.overlay);
      event.map.setCenter(event.overlay.getPosition());

      this.messageService.add({ severity: 'info', summary: 'Marker Selected', detail: title });
    }
    else {
      this.messageService.add({ severity: 'info', summary: 'Shape Selected', detail: '' });
    }
  }

  addMarker() {
    this.overlays.push(new google.maps.Marker({ position: { lat: this.selectedPosition.lat(), lng: this.selectedPosition.lng() }, title: this.markerTitle, draggable: this.draggable }));
    this.markerTitle = null;
    this.dialogVisible = false;
  }

  handleDragEnd(event) {
    this.messageService.add({ severity: 'info', summary: 'Marker Dragged', detail: event.overlay.getTitle() });
  }

  zoomIn(map) {
    map.setZoom(map.getZoom() + 1);
  }

  zoomOut(map) {
    map.setZoom(map.getZoom() - 1);
  }

  clear() {
    this.overlays = [];
  }
}


