import { stringify } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from './model/user';
import { AuditService } from './service/audit.service';
import { AuthenticationService } from './service/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [AuditService]
})
export class AppComponent implements OnInit {
  title = 'pv';
  toggled = false;
  currentUser: User;

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) {
        this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    }
  ngOnInit() {
  }

    logout() {
        this.authenticationService.logout();
        this.router.navigate(['/login']);
    }

    // $(function(){
    //   $("#menu-toggle").click(function(e) {
    //       e.preventDefault();
    //       $("#wrapper").toggleClass("toggled");
    //   });

    //   $(window).resize(function(e) {
    //     if($(window).width()<=768){
    //       $("#wrapper").removeClass("toggled");
    //     }else{
    //       $("#wrapper").addClass("toggled");
    //     }
    //   });
    // });
  
}
