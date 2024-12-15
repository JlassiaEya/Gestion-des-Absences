package com.projet.Projet_ItServ.service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.exceptions.CsvException;
import com.projet.Projet_ItServ.entity.BilanDétaiillé;
import com.projet.Projet_ItServ.entity.Employees;
import com.projet.Projet_ItServ.entity.Pointages;
import com.projet.Projet_ItServ.repository.BilanDRepository;

@Service
public class BilanDetailleService {

    @Autowired
    private AbsenceService absenceService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PointageService pointageService;
    @Autowired
    private RetardService retardService;
    @Autowired
    private BilanDRepository bilan;
    
    public List<BilanDétaiillé> genererBilanDetaille(String matricule) throws IOException, CsvException {
        List<BilanDétaiillé> bilanDetailleResults = new ArrayList<>();
        Employees employee = employeeService.getEmployeeByMatricule(matricule);

        if (employee != null) {
            String nomEmploye = employee.getNom();
            String prenomEmploye = employee.getPrenom();
            List<Pointages> pointages = pointageService.getGroupedPointages();
            Set<LocalDate> joursFeries = new HashSet<>(RetardService.getJoursFeries());

            Map<LocalDate, List<Pointages>> pointagesParDate = pointages.stream()
                    .collect(Collectors.groupingBy(pointage -> pointage.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));

            for (LocalDate currentDate : pointagesParDate.keySet()) {
                if (isWeekendOrJourFerie(currentDate, joursFeries)) {
                    continue;
                }

                BilanDétaiillé bilanDetaille = new BilanDétaiillé();
                bilanDetaille.setMatricule(matricule);
                bilanDetaille.setNom(nomEmploye);
                bilanDetaille.setPrenom(prenomEmploye);
                bilanDetaille.setDate(currentDate);

                boolean isAbsentS1 = absenceService.estAbsentS1PourChaqueJour(matricule, currentDate);
                boolean isAbsentS2 = absenceService.estAbsentS2PourChaqueJour(matricule, currentDate);

                String heureRetardS1 = retardService.CalculerHeureRetardPourEmployeS1ChaqueJour(matricule, currentDate);
                String heureRetardS2 = retardService.calculerHeureRetardPourEmployeS2ChaqueJour(matricule, currentDate);

                bilanDetaille.setAbsenceSeance1(isAbsentS1 ? "O" : "N");
                bilanDetaille.setAbsenceSeance2(isAbsentS2 ? "O" : "N");
                bilanDetaille.setNbHeuresRetardSeance1(heureRetardS1);
                bilanDetaille.setNbHeuresRetardSeance2(heureRetardS2);

                bilanDetailleResults.add(bilanDetaille);
            }

        }
        bilanDetailleResults.sort(Comparator.comparing(BilanDétaiillé::getDate));
        bilan.saveAll(bilanDetailleResults);
        return bilanDetailleResults;
      
    }

		private boolean isWeekendOrJourFerie(LocalDate currentDate, Set<LocalDate> joursFeries) {
		    return currentDate.getDayOfWeek() == DayOfWeek.SATURDAY
		            || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY
		            || (joursFeries != null && joursFeries.contains(currentDate));
		}

	}


