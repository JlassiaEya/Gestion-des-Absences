package com.projet.Projet_ItServ.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.exceptions.CsvException;
import com.projet.Projet_ItServ.entity.AbsenceResult;
import com.projet.Projet_ItServ.service.AbsenceService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AbsenceController {

 @Autowired
    AbsenceService absenceService;
 @GetMapping("/listAbsenceS1")
 public ResponseEntity<List<AbsenceResult>> listeabsS1()throws IOException, CsvException {
      
         List<AbsenceResult> absenceResultsS1 = absenceService.checkAbsenceS1();
         return new ResponseEntity<>(absenceResultsS1, HttpStatus.OK);
     
     }
 @GetMapping("/listAbsenceS2")
 public ResponseEntity<List<AbsenceResult>> listeabsS2()throws IOException, CsvException {
      
         List<AbsenceResult> absenceResultsS2 = absenceService.checkAbsenceS2();
         return new ResponseEntity<>(absenceResultsS2, HttpStatus.OK);
     
     }
 @GetMapping("/listAbsence")
 public ResponseEntity<List<AbsenceResult>> listeabs()throws IOException, CsvException {
      
         List<AbsenceResult> absenceResults = absenceService.checkAbsence();
         return new ResponseEntity<>(absenceResults, HttpStatus.OK);
     
     }
 
}