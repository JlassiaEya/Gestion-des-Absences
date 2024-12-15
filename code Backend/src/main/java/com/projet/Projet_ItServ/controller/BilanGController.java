package com.projet.Projet_ItServ.controller;

import com.opencsv.exceptions.CsvException;
import com.projet.Projet_ItServ.entity.BilanGlobal;
import com.projet.Projet_ItServ.service.BilanGlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BilanGController {

    @Autowired
    private BilanGlobalService bilanGlobalService;

    @GetMapping("/bilanglobal")
    public ResponseEntity<List<BilanGlobal>> generateBilanGlobal() throws IOException, CsvException {
        List<BilanGlobal> bilanGloble = bilanGlobalService.genererBilanGlobal();
        return new ResponseEntity<>(bilanGloble, HttpStatus.OK);
    }
}
