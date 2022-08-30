import {Component, OnInit} from '@angular/core';
import {LoggedInUserDto} from "../../../core/model/logged-in-user-dto.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../../../core/services/auth/authentication.service";
import {map, take} from "rxjs";
import {RoleService} from "../../../core/services/role/role.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;

  private loggedInUser!: LoggedInUserDto;


  constructor(private fb: FormBuilder,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private roleService:RoleService,
              private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.createLoginForm();
  }

  private createLoginForm(): void {
    this.loginForm = this.fb.group({
      userName: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });

  }

  public doLogin(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.loggedInUser = <LoggedInUserDto>this.loginForm.value;
    this.authenticationService
      .login(this.loggedInUser)
      .subscribe(response => {
          this.loginForm.reset();
          this.roleService.getUserRoles(1).subscribe(r=>{})
          this.authenticationService.setAuthenticated(response);
          // this.navigateToRedirectUrl();
        }
      );
  }

  private navigateToRedirectUrl(): void {
    this.authenticationService.getRedirectUrl()
      .pipe(take(1), map(redirectUrl => (redirectUrl == undefined && redirectUrl == "") ? "/" : redirectUrl))
      .subscribe((redirectUrl: string | any) => this.router.navigateByUrl(redirectUrl));
  }

}
