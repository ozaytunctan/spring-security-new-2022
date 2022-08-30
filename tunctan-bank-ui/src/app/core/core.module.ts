import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FilterPipe} from './pipes/filter.pipe';
import {AuthenticationService} from "./services/auth/authentication.service";
import {HttpRequestInterceptor} from "./interceptors/http-request.interceptor";
import {AuthLayoutComponent} from "./layouts/auth-layout/auth-layout.component";
import {EmptyLayoutComponent} from "./layouts/empty-layout/empty-layout.component";
import {MainLayoutComponent} from "./layouts/main-layout/main-layout.component";
import {RouterModule} from "@angular/router";
import {HttpErrorResponseInterceptor} from "./interceptors/http-error-response.interceptor";
import {StorageService} from "./services/storage/storage.service";
import {AuthGuard} from "./guards/auth.guard";
import {ToastrModule} from "ngx-toastr";
import {HasRole} from "./directives/has-role.directive";
import {HasRoleGuard} from "./guards/has-role.quard";
import {RoleService} from "./services/role/role.service";


@NgModule({
  declarations: [
    MainLayoutComponent,
    AuthLayoutComponent,
    EmptyLayoutComponent,

    //Pipe
    FilterPipe,

    //Directives
    HasRole

  ],
  imports: [
    //
    RouterModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ToastrModule.forRoot({
      closeButton: true,
      enableHtml: true,
      newestOnTop: true,
      maxOpened: 5,
      preventDuplicates: true
    }),
    // ToastContainerModule,
  ],

  providers: [

    {
      provide: HTTP_INTERCEPTORS,
      multi: true,
      useClass: HttpRequestInterceptor
    },
    {
      provide: HTTP_INTERCEPTORS,
      multi: true,
      useClass: HttpErrorResponseInterceptor
    },
    AuthenticationService,
    RoleService,
    StorageService,
    AuthGuard,
    HasRoleGuard

  ],

  exports: [

    //Components
    MainLayoutComponent,
    AuthLayoutComponent,
    EmptyLayoutComponent,

    //Pipe
    FilterPipe,

    //Directives
    HasRole
  ]
})
export class CoreModule {
}
