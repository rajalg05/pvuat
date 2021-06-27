import { Component, OnInit } from '@angular/core';

import {DynamicDialogRef} from 'primeng/dynamicdialog';
import {DynamicDialogConfig} from 'primeng/dynamicdialog';
import { Resource } from 'src/app/model/resource';

@Component({
  selector: 'app-resource-dialog',
  templateUrl: './resource-dialog.component.html',
  styleUrls: ['./resource-dialog.component.css']
})
export class ResourceDialogComponent implements OnInit {
resource: Resource;
  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig) { }
  ngOnInit() {
    this.resource = this.config.data;
    console.log('dialog resource = ', this.config)
  }
 
}
