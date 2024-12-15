import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PageAcceuilComponent } from "./page-acceuil/PageAcceuilComponent";
import RetardComponent from './retard/retard.component';
import { BilanDetailleComponent } from './bilan-detaille/bilan-detaille.component';
import { BilangloabalComponent } from './bilangloabal/bilangloabal.component';
import { AbsenceComponent } from './absence/absence.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    PageAcceuilComponent,
    RetardComponent,
    BilanDetailleComponent,
    BilangloabalComponent,
   AbsenceComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
