import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {AuthenticationService} from "../services/auth/authentication.service";
import {AppConstants} from "../constants/app-constants.const";


@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  constructor(private authService: AuthenticationService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {


    let headers = req.headers;
    if (this.authService.isAuthenticated()){
      headers=headers.append(AppConstants.AUTHORIZATION_HEADER, `${AppConstants.BEARER}${this.authService.getToken()}`);
    }

    const modifiedRequest = req.clone({
        headers: headers,
        withCredentials: true
      }
    );

    return next.handle(modifiedRequest);
  }


}
