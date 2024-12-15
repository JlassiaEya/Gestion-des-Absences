import { Component } from '@angular/core';
import { BilanGlobalService } from '../Services/bilan-global.service';

@Component({
  selector: 'app-bilangloabal',
  templateUrl: './bilangloabal.component.html',
  styleUrls: ['./bilangloabal.component.css']
})
export class BilangloabalComponent {
  bilanGlobalResults: any[] = [];

  constructor(private bilanGlobalService: BilanGlobalService) {}

  submitForm(): void {
    this.bilanGlobalService.genererBilanGlobal().subscribe(
      data => {
        // Utiliser la fonction mapBilanGlobale pour filtrer les résultats du bilan global
        this.bilanGlobalResults = this.mapBilanGlobale(data);
      },
      error => {
        console.error('Error fetching Bilan Global:', error);
      }
    );
  }

  private mapBilanGlobale(data: any[]): any[] {
    const uniqueEntries = new Set();

    return data.map((bilanGlobalResults: any) => {
    const key = bilanGlobalResults.matricule + '-' + bilanGlobalResults.joursAbsences +
                '-' + bilanGlobalResults.totalHeuresRetardSeance1 +
                '-' + bilanGlobalResults.totalHeuresRetardSeance2;

      
      if (!uniqueEntries.has(key)) {
        uniqueEntries.add(key);
        
       return {
        matricule: bilanGlobalResults.matricule,
        nom: bilanGlobalResults.nom,
        prenom: bilanGlobalResults.prenom,
        totalJoursAbsence: bilanGlobalResults.totalJoursAbsence,
        totalHeuresRetardSeance1: bilanGlobalResults.totalHeuresRetardSeance1,
        totalHeuresRetardSeance2: bilanGlobalResults.totalHeuresRetardSeance2
      };
    }
      return null;
    }).filter(Boolean); 
  }
}
