import { Component, ViewChild, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { PointageServiceService } from '../Services/pointage-service.service';
import { EmployeeService } from '../Services/employee.service';

@Component({
  selector: 'app-page-acceuil',
  templateUrl: './page-acceuil.component.html',
  styleUrls: ['./page-acceuil.component.css']
})
export class PageAcceuilComponent implements OnInit {
  pointage: any;
  @ViewChild('fileForm') fileForm!: NgForm;
  selectedFile: File | null = null;
  selectedEmployeeFile: File | null = null;
  groupedPointages: any;
  error: string | null = null;
  @ViewChild('employeeFileForm') employeeFileForm!: NgForm;
  constructor(
    private pointageService: PointageServiceService,
    private employeeService: EmployeeService
  ) {}

  ngOnInit() {
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  onEmployeeFileSelected(event: any): void {
    this.selectedEmployeeFile = event.target.files[0];
  }

  onEmployeeFileSubmit() {
    console.log('Submitting employee file for analysis...');

    if (this.selectedEmployeeFile) {
      this.employeeService.analyzeEmployeeFile(this.selectedEmployeeFile).subscribe(
        (data: any): void => {
          console.log('Employee data received:', data);
          
        },
        (error: any) => {
          console.error('Error during employee file analysis:', error);
          this.error = 'Une erreur s\'est produite lors de l\'analyse du fichier employé.';
        }
      );
    } else {
      console.error('No employee file selected for analysis.');
      this.error = 'Aucun fichier employé sélectionné pour l\'analyse.';
    }
  }

  onSubmit() {
    console.log('Submitting file for analysis...');
    if (this.selectedFile) {
      this.pointageService.analyzePointageFile(this.selectedFile).subscribe(
        (data: any): void => {
          console.log('Data received:', data);
          
        },
        (error: any) => {
          console.error('Error during analysis:', error);
          this.error = 'Une erreur s\'est produite lors de l\'analyse du fichier de pointage.';
        }
      );
    } else {
      console.error('No file selected for analysis.');
      this.error = 'Aucun fichier sélectionné pour l\'analyse.';
    }
  }
 
  
}
