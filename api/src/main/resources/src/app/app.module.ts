import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MaterialModule } from '@angular/material';
import 'hammerjs';

import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { ServiceContainerComponent } from './service-container/service-container.component';
import { ServiceListComponent } from './service-list/service-list.component';
import { ServiceContentComponent } from './service-content/service-content.component';
import { SmockRouterModule } from './smock-router/smock-router.module';
import { HighlightJsModule, HighlightJsService } from 'angular2-highlight-js';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    ServiceContainerComponent,
    ServiceListComponent,
    ServiceContentComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MaterialModule.forRoot(),
    SmockRouterModule,
    HighlightJsModule
  ],
  providers: [
    HighlightJsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
