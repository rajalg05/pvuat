import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AlertService } from '../service/alert.service';
import { UserService } from '../service/user.service';
import { AuthenticationService } from '../service/authentication.service';
import { AESEncryptDecryptService } from '../service/aesencrypt-decrypt-service.service';

@Component({templateUrl: 'register.component.html'})
export class RegisterComponent implements OnInit {
    registerForm: FormGroup;
    loading = false;
    submitted = false;
    roleOptions = [
        {label: 'Non TL', value: 'Non TL'},
        {label: 'Sub Associate', value: 'Sub Associate'},
        {label: 'TL', value: 'TL'}
      ]
    
      selectedRoleValue = 1;
    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private authenticationService: AuthenticationService,
        private userService: UserService,
        private alertService: AlertService,
        private _AESEncryptDecryptService: AESEncryptDecryptService
    ) { 
        // redirect to home if already logged in
        if (this.authenticationService.currentUserValue) { 
            this.router.navigate(['/']);
        }
    }

    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            role: [this.roleOptions['id'], Validators.required],
            userName: ['', Validators.required],
            password: ['', [Validators.required, Validators.minLength(6)]],
            status: 'pending',
            comments: ''
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
       /*  if (this.registerForm.invalid) {
            return;
        } */

        this.loading = true;
        this.registerForm.patchValue(
            {
                "password":  this._AESEncryptDecryptService.encrypt(this.registerForm.get("password").value).toString()
            });
        console.log('password =', this.registerForm.get("password").value);
        this.userService.register(this.registerForm.value)
            .pipe(first())
            .subscribe(
                data => {
                    console.log('data = ', data);
                    if(data == 'user exists') {
                        this.alertService.error('Username ' + this.registerForm.get("userName")?.value + ' exists', true);
                    } else {
                        this.alertService.success('Registration successful', true);
                    }
                    
                    this.router.navigate(['/login']);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}
