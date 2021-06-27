import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Associate } from 'src/app/model/associateMaster';

@Component({
  selector: 'app-associate-dialog',
  templateUrl: './associate-dialog.component.html',
  styleUrls: ['./associate-dialog.component.css']
}) 
export class AssociateDialogComponent implements OnInit {
  associate: Associate;
    constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig) { }
    ngOnInit() {
      this.associate = this.config.data;
      console.log('dialog resource = ', this.config)
    }
   
  }