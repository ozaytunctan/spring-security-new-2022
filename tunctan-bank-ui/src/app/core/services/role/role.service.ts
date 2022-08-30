import {environment} from "../../../../environments/environment";
import {AppConstants} from "../../constants/app-constants.const";
import {EMPTY, filter, map, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {StorageService} from "../storage/storage.service";
import {ActivatedRoute} from "@angular/router";
import {AuthenticationRequest} from "../../model/authentication-request.model";
import {LoggedInUserDto} from "../../model/logged-in-user-dto.model";
import {ServiceResultDto} from "../../model/service-result-dto.model";
import {Injectable} from "@angular/core";

@Injectable()
export class RoleService{
  private host: string = environment.backend_api_url;
  private basePath = AppConstants.ROLE_URL;

  constructor(private http: HttpClient,
              private localStorage: StorageService,
              private activatedRoute: ActivatedRoute) {
  }

  public getUserRoles(userId:number): Observable<any> {
    return this.http.get<ServiceResultDto<any>>(`${this.host}${this.basePath}/list/${userId}`)
      .pipe(filter(response => response && response.isSuccess),
        map(response => response.data));
  }
}
