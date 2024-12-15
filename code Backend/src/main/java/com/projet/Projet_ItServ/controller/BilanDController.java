package com.projet.Projet_ItServ.controller;

import com.opencsv.exceptions.CsvException;
import com.projet.Projet_ItServ.entity.BilanDétaiillé;
import com.projet.Projet_ItServ.service.BilanDetailleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BilanDController {

    @Autowired
    private BilanDetailleService bilanDetaillé;

    @GetMapping("/bilan_detaille")
    public ResponseEntity<?> generateBilanDétaillé(@RequestParam(required = false) String matricule)
            throws IOException, CsvException {
        try {
            if (matricule == null || matricule.isEmpty()) {
                return new ResponseEntity<>("Matricule parameter is required.", HttpStatus.BAD_REQUEST);
            }

            List<BilanDétaiillé> bilanDetailleList = bilanDetaillé.genererBilanDetaille(matricule);
            return new ResponseEntity<>(bilanDetailleList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
