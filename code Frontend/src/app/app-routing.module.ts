import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AbsenceComponent } from './absence/absence.component';
import { BilanDetailleComponent } from './bilan-detaille/bilan-detaille.component';
import { BilangloabalComponent } from './bilangloabal/bilangloabal.component';
import { PageAcceuilComponent } from './page-acceuil/PageAcceuilComponent';
import RetardComponent from './retard/retard.component';
const routes: Routes = [
  { path: 'absence', component: AbsenceComponent },
  { path: 'retard', component: RetardComponent },
  { path: 'bilan-global', component: BilangloabalComponent },
  { path: 'bilan-detaille', component: BilanDetailleComponent },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
