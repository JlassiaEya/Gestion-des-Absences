import { Component, OnInit } from '@angular/core';
import { AbsenceService } from '../Services/absence.service';

@Component({
  selector: 'app-absence',
  templateUrl: './absence.component.html',
  styleUrls: ['./absence.component.css']
})
export class AbsenceComponent implements OnInit {
  absences: any[] | undefined;

  constructor(private absenceService: AbsenceService) {}

  ngOnInit(): void {}

  private mapAbsences(data: any[]): any[] {
    const uniqueAbsences = new Set();

    return data.map(absence => {
      const key = absence.matricule + '-' + absence.joursAbsences;
      
      if (!uniqueAbsences.has(key)) {
        uniqueAbsences.add(key);
        
        return {
          matricule: absence.matricule,
          joursAbsences: absence.joursAbsences,
          numSeance: absence.numSeance
        };
      }

      return null; 
    }).filter(absence => !!absence);
  }

  loadAbsencesS1(): void {
    this.absenceService.listeabsS1().subscribe(
      (data: any[]) => {
        this.absences = this.mapAbsences(data);
      },
    );
  }

  loadAbsencesS2(): void {
    this.absenceService.listeabsS2().subscribe(
      (data: any[]) => {
        this.absences = this.mapAbsences(data);
      },
    );
  }

  loadAbsences(): void {
    this.absenceService.listeabs().subscribe(
      (data: any[]) => {
        this.absences = this.mapAbsences(data);
      },
    );
  }
}
