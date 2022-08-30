import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {AuthenticationService} from "../services/auth/authentication.service";

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private authenticationService: AuthenticationService,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (this.authenticationService.isAuthenticated()) {
      return true;
    }
    let redirectUrl = state.url;
    this.router.navigate(['auth', 'login'], {queryParams: {"redirect_url":redirectUrl}});
    throw new Error("Lütfen Giriş Yapınız");
  }
}
