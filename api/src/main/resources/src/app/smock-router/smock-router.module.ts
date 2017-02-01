import { NgModule } from '@angular/core';
import { RouterModule, Routes }  from '@angular/router';

import { ServiceContainerComponent } from '../service-container/service-container.component';

const appRoutes: Routes = [
  {path: '', component: ServiceContainerComponent},
  {path: '**', component: ServiceContainerComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  declarations: [],
  exports: [
    RouterModule
  ]
})
export class SmockRouterModule { }
