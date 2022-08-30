import { Component } from '@angular/core';
import {RoleConstants} from "./core/constants/role.constants.const";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'tunctan-bank-ui';
  RoleConstants=RoleConstants;
}
