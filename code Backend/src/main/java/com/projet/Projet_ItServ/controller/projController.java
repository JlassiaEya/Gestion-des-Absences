package com.projet.Projet_ItServ.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.exceptions.CsvException;
import com.projet.Projet_ItServ.entity.Pointages;
import com.projet.Projet_ItServ.service.PointageService;

@RestController
@RequestMapping("/api")
public class projController {

    @Autowired
    private PointageService service;
     @CrossOrigin("*")
     @PostMapping("/listPointage")
    public ResponseEntity<?> analyzePointageFile(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, Map<Object, List<Pointages>>> groupedData = service.GrouperPointage(file);
            service.saveGroupedPointages(groupedData);
            return new ResponseEntity<>(groupedData, HttpStatus.OK);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            String errorMessage = "Erreur lors de l'analyse du fichier: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
