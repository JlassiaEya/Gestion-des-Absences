import { Component } from '@angular/core';
import { BilanDetailleService } from '../Services/bilan-detaillé.service';

@Component({
  selector: 'app-bilan-detaille',
  templateUrl: './bilan-detaille.component.html',
  styleUrls: ['./bilan-detaille.component.css']
})
export class BilanDetailleComponent {
  matricule: string = '';
  bilanDetailleResults: any[] = [];

  constructor(private bilanDetailleService: BilanDetailleService) {}

  generateBilanDetaille() {
    if (this.matricule.trim() === '') {
      alert("Veuillez saisir un matricule avant de générer le Bilan Détaillé.");}
    if (!this.matricule) {
      console.error('Matricule is required.');
      return;
    }

    this.bilanDetailleService.generateBilanDetaille(this.matricule)
      .subscribe(
        (data: any) => {
          this.bilanDetailleResults = this.mapBilanDetaille(data);
        },
        (error) => {
          console.error('Error generating bilan détaillé:', error);
        }
      );
  }

  private mapBilanDetaille(data: any[]): any[] {
    const uniqueBilanDetaille = new Set();
  
    return data.map(detaille => {
      const formattedDate = this.formatDate(detaille.date);
      const key = detaille.matricule + '-' + formattedDate;
  
      if (!uniqueBilanDetaille.has(key)) {
        uniqueBilanDetaille.add(key);
  
        return {
          matricule: detaille.matricule,
          date: formattedDate,
          absenceSeance1: detaille.absenceSeance1,
          absenceSeance2: detaille.absenceSeance2,
          nbHeuresRetardSeance1: detaille.nbHeuresRetardSeance1,
          nbHeuresRetardSeance2: detaille.nbHeuresRetardSeance2,
        };
      }
  
      return null;
    }).filter(detaille => !!detaille);
  }
  
  private formatDate(date: string): string {
    const parsedDate = new Date(date);
    const formattedDate = parsedDate.toLocaleDateString('en-US');
    return formattedDate;
  }
}
