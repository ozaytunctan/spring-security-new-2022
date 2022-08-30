import {NgModule} from '@angular/core';

import {AuthRoutingModule} from './auth-routing.module';
import {AuthComponent} from './auth.component';
import {SharedModule} from "../../shared/shared.module";
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';


@NgModule({
  declarations: [
    AuthComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    SharedModule,
    AuthRoutingModule
  ]
})
export class AuthModule { }
