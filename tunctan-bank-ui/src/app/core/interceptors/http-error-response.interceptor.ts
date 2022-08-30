import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from "@angular/common/http";
import {catchError, map, Observable, throwError} from "rxjs";
import {ServiceResultDto} from "../model/service-result-dto.model";
import {ErrorDto} from "../model/error-dto.model";
import {Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {ToastrService} from "ngx-toastr";

/**
 * @author ozay.tunctan
 */
@Injectable()
export class HttpErrorResponseInterceptor implements HttpInterceptor {

  constructor(private router: Router,
              private toastrService: ToastrService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
      .pipe(
        map(res => {
          //status ok olanların body faile olanar kontrol edilecek.
          if (res instanceof HttpResponse) {
            let result = <ServiceResultDto<any>>res.body;
            if (!result.isSuccess && result.errors) {
              this.handleErrors(result.errors);
            }
          }
          return res
        }),
        catchError((error: HttpErrorResponse) => {
          let errorMsg = '';
          if (error.error instanceof ErrorEvent) {
            console.log('This is client side error');
            errorMsg = `Error: ${error.error.message}`;
          } else {
            console.log('This is server side error');
            errorMsg = `Error Code: ${error.status},  Message: ${error.message}`;
          }
          console.log(errorMsg);
          return throwError(errorMsg);
        })
      )
  }

  handleErrors(errors: ErrorDto[]) {
    //error messajlar toast mesajı olarak görüntülenecek.
    errors.forEach(error => {

        switch (error.status) {
          case 401:
            debugger;
            this.toastrService.error(error.message, "Authentication Error");
            this.navigateLoginPage();
            break;
          default:
            break;
        }
      }
    );

  }

  public navigateLoginPage(): void {
    this.router.navigate(['auth', 'login']);
  }
}

