import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanLoad,
  Route,
  RouterStateSnapshot,
  UrlSegment,
  UrlTree
} from "@angular/router";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {AppConstants} from "../constants/app-constants.const";
import {AuthenticationService} from "../services/auth/authentication.service";


@Injectable()
export class HasRoleGuard implements CanActivate, CanLoad {


  constructor(private authenticationService: AuthenticationService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let permissions: string[] = route.data[AppConstants.PERMISSIONS];
    return this.authenticationService.hasRole(...permissions);
  }

  canLoad(route: Route, segments: UrlSegment[]): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let data = route.data;
    if (data) {
      let permissions: string[] = data[AppConstants.PERMISSIONS];
      return this.authenticationService.hasRole(...permissions);
    }

    return true;
  }


}
