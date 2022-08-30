import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainLayoutComponent} from "./core/layouts/main-layout/main-layout.component";
import {AuthLayoutComponent} from "./core/layouts/auth-layout/auth-layout.component";
import {EmptyLayoutComponent} from "./core/layouts/empty-layout/empty-layout.component";
import {AuthGuard} from "./core/guards/auth.guard";
import {RoleConstants} from "./core/constants/role.constants.const";
import {HasRoleGuard} from "./core/guards/has-role.quard";


const routes: Routes = [


  {
    path: '',
    component: MainLayoutComponent,
    children: [
      {
        path: '',
        redirectTo: '/',
        pathMatch: 'full'
      },
      {
        path: '',
        loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule)
      },
    ]
  },
  {
    path: 'auth',
    component: AuthLayoutComponent,
    loadChildren: () => import('./pages/auth/auth.module').then(m => m.AuthModule)
  },

  {
    path: 'delivery',
    canActivate: [AuthGuard],
    component: EmptyLayoutComponent,
    // loadChildren: () => import('./pages/delivery/delivery.module').then(m => m.DeliveryModule)
  },

  {
    path: 'payment',
    canActivate: [AuthGuard, HasRoleGuard],
    data: {
      permissions: [RoleConstants.USER, RoleConstants.ADMIN]
    },
    component: EmptyLayoutComponent,
    // loadChildren: () => import('./pages/payment/payment.module').then(m => m.PaymentModule)
  },


  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes,
    {
      anchorScrolling: "enabled",
      scrollPositionRestoration: "enabled"
    })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
