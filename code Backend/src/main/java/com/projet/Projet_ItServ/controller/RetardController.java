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
import com.projet.Projet_ItServ.entity.RetardResult;
import com.projet.Projet_ItServ.service.RetardService;
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RetardController {

    @Autowired
    private RetardService retardService;

    @GetMapping("/check")
    public ResponseEntity<List<RetardResult>> checkRetard() throws IOException, CsvException {
        List<RetardResult> retardResults = retardService.checkRetard();
        return new ResponseEntity<>(retardResults, HttpStatus.OK);
    }
    @GetMapping("/checkS1")
    public ResponseEntity<List<RetardResult>> getRetardsS1() throws IOException, CsvException {
        List<RetardResult> allRetardResults = retardService.checkRetard();
        List<RetardResult> filteredResultsS1 = retardService.filterRetardResultsS1(allRetardResults);
        return new ResponseEntity<>(filteredResultsS1, HttpStatus.OK);
    }

    @GetMapping("/checkS2")
    public ResponseEntity<List<RetardResult>> getRetardsS2() throws IOException, CsvException {
        List<RetardResult> allRetardResults = retardService.checkRetard();
        List<RetardResult> filteredResultsS2 = retardService.filterRetardResultsS2(allRetardResults);
        return new ResponseEntity<>(filteredResultsS2, HttpStatus.OK);
    }
}
