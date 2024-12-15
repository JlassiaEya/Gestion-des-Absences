import { Component, OnInit } from '@angular/core';
import { RetardService } from '../Services/retard.service';

@Component({
  selector: 'app-retard',
  templateUrl: './retard.component.html',
  styleUrls: ['./retard.component.css']
})
export default class RetardComponent implements OnInit {
  retards: any[] | undefined;
  constructor(private retardService: RetardService) {}

  ngOnInit(): void {
  }

  checkRetard(): void {
    this.retardService.listretard().subscribe(
      (data: any[]) => {
        this.retards = this.mapRetards(data);
      },
    );
  }

  checkRetardS1(): void {
    this.retardService.listretardS1().subscribe(
      (data: any[]) => {
        this.retards = this.mapRetards(data);
      },
    );
  }

  checkRetardS2(): void {
    this.retardService.listretardS2().subscribe(
      (data: any[]) => {
        this.retards = this.mapRetards(data);
      },
    );
  }
  private mapRetards(data: any[]): any[] {
    const uniqueRetards = new Set();
  
    return data.map(retard => {
      const key = retard.matricule + '-' + retard.retard;
  
      if (!uniqueRetards.has(key)) {
        uniqueRetards.add(key);
  
        return {
          matricule: retard.matricule,
          retard: retard.retard,
          pointageDateTime: new Date(retard.pointageDateTime)
        };
      }
  
      return null;
    }).filter(retard => !!retard);
  }
  
}
