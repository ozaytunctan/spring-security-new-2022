import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {LoggedInUserDto} from "../../model/logged-in-user-dto.model";
import {EMPTY, filter, map, Observable} from "rxjs";
import {environment} from "../../../../environments/environment";
import {AuthenticationRequest} from "../../model/authentication-request.model";
import {AppConstants} from "../../constants/app-constants.const";
import {StorageKey} from "../../enums/storage-key.enum";
import {StorageService} from "../storage/storage.service";
import {ServiceResultDto} from "../../model/service-result-dto.model";
import {ActivatedRoute} from "@angular/router";

@Injectable()
export class AuthenticationService {

  private host: string = environment.backend_api_url;
  private basePath = AppConstants.AUTH_URL;

  private authenticated: boolean = false;

  private redirectUrl: string = '/';

  private redirectUrlObs: Observable<string> = EMPTY;

  constructor(private http: HttpClient,
              private localStorage: StorageService,
              private activatedRoute: ActivatedRoute) {
  }

  public login(request: AuthenticationRequest): Observable<LoggedInUserDto> {
    return this.http.post<ServiceResultDto<LoggedInUserDto>>(`${this.host}${this.basePath}/login`, request)
      .pipe(filter(response => response && response.isSuccess),
        map(response => response.data));
  }


  public setAuthenticated(loggedInUser: LoggedInUserDto): void {
    if (loggedInUser) {
      this.localStorage.set(StorageKey.LOGGED_IN_USER, loggedInUser.userName);
      this.localStorage.set(StorageKey.TOKEN, loggedInUser.token);
      this.localStorage.set(StorageKey.USER_ROLES, loggedInUser.roles);
      this.authenticated = true;
    } else {
      this.clearLogin();
    }
  }

  public getUserRoles(): string[] {
    let roles: string[] = this.localStorage.get(StorageKey.USER_ROLES);
    if (roles) {
      return roles;
    }
    return [];
  }

  clearLogin(): void {
    this.authenticated = false;
    this.localStorage.remove(StorageKey.LOGGED_IN_USER);
    this.localStorage.remove(StorageKey.TOKEN);
  }

  isAuthenticated(): boolean {
    if (this.authenticated) {
      return true;
    }
    return this.hasToken();
  }

  private hasToken(): boolean {
    return !(this.getToken() == null || this.getToken() == undefined);
  }

  public getToken(): string {
    let token=this.localStorage.get(StorageKey.TOKEN);
    return token ;
  }

  public getRedirectUrl(): Observable<string | null> {
    return this.activatedRoute
      .queryParamMap
      .pipe(map(params => params.get(AppConstants.REDIRECT_URL)));
  }


  public hasRole(...roles: string[]): boolean {
    if (roles && roles.length > 0) {
      let userRoles = this.getUserRoles();
      if (userRoles && userRoles.length > 0) {
        return this.isAuthenticated() && userRoles.some(userRole => roles.some(p => p == userRole));
      }
    }
    return false;
  }


}
