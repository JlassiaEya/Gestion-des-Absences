package com.projet.Projet_ItServ.service;

import com.opencsv.exceptions.CsvException;
import com.projet.Projet_ItServ.entity.BilanGlobal;
import com.projet.Projet_ItServ.entity.Employees;
import com.projet.Projet_ItServ.repository.EmployesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BilanGlobalService {

    @Autowired
    private AbsenceService absenceService;

    @Autowired
    private RetardService retardService;

    @Autowired
    private EmployesRepository employesRepository;

    public List<BilanGlobal> genererBilanGlobal() {
        List<BilanGlobal> bilanGlobalResults = new ArrayList<>();

        try {
            List<Employees> employees = employesRepository.findAll();

            for (Employees employee : employees) {
                String matricule = employee.getMatricule();
                String nomEmploye = employee.getNom();
                String prenomEmploye = employee.getPrenom();

                double totalJoursAbsence = absenceService.calculerAbsencePourEmploye(matricule);
                String totalHeuresRetardSeance1 = retardService.CalculerHeureRetardPourEmployeS1(matricule);
                String totalHeuresRetardSeance2 = retardService.CalculerHeureRetardPourEmployeS2(matricule);

                BilanGlobal bilanGlobalResult = new BilanGlobal(matricule, totalJoursAbsence, totalHeuresRetardSeance1, totalHeuresRetardSeance2);
                bilanGlobalResult.setNom(nomEmploye);
                bilanGlobalResult.setPrenom(prenomEmploye);
                bilanGlobalResults.add(bilanGlobalResult);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return bilanGlobalResults;
    }
}
