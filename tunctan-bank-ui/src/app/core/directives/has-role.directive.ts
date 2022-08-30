import {Directive, Input, OnInit, TemplateRef, ViewContainerRef} from "@angular/core";
import {AuthenticationService} from "../services/auth/authentication.service";


@Directive({selector: '[hasRole]'})
export class HasRole implements OnInit {

  userRoles: string[] = [];

  constructor(
    private templateRef: TemplateRef<any>,
    private authService: AuthenticationService,
    private viewContainer: ViewContainerRef
  ) {
  }

  @Input("hasRole")
  set hasRole(roles: string[]) {
    if (!roles || roles.length==0) {
      throw new Error('Roles value is empty or missed');
    }

    this.userRoles = roles;
  }

  ngOnInit() {
    let hasAccess = false;
    if (this.authService.isAuthenticated() && this.userRoles) {
      hasAccess = this.authService.hasRole(...this.userRoles);
    }

    if (hasAccess) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }
}
