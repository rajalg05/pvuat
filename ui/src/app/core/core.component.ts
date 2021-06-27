import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../service/authentication.service';
import { CoreService } from '../service/core.service';

@Component({
  selector: 'app-core',
  templateUrl: './core.component.html',
  styleUrls: ['./core.component.css']
})
export class CoreComponent implements OnInit {
 

  constructor(public _coreService: CoreService,
    public authenticationService: AuthenticationService,) {
      console.log('authenticationService.currentUserValue.role = ' , 
      authenticationService.currentUserValue ? authenticationService.currentUserValue.role: null);
     }

  ngOnInit(): void {
  }
  
}
