package com.projet.Projet_ItServ.service;

import com.opencsv.exceptions.CsvException;
import com.projet.Projet_ItServ.entity.AbsenceResult;
import com.projet.Projet_ItServ.entity.Employees;
import com.projet.Projet_ItServ.entity.Pointages;
import com.projet.Projet_ItServ.repository.AbsenceRepository;
import com.projet.Projet_ItServ.repository.EmployesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AbsenceService {
	 @Autowired
	    private AbsenceRepository absenceRepository;
	 
	private static  LocalTime DEBUT_SEANCE_1 = LocalTime.of(8, 0);
	   
	private static  LocalTime FIN_SEANCE_1 = LocalTime.of(12, 30);

	private static  LocalTime DEBUT_SEANCE_2 = LocalTime.of(14, 0);
	
	private static  LocalTime FIN_SEANCE_2 = LocalTime.of(18, 0);

    @Autowired
    private EmployesRepository employesRepository;

    @Autowired
    private PointageService pointageService;

    public List<AbsenceResult> checkAbsenceS1() throws IOException, CsvException {
        List<AbsenceResult> absenceResultsS1 = new ArrayList<>();
        List<LocalDate> holidays = getJoursFeries();

        List<Pointages> pointagesList = pointageService.getGroupedPointages();

        Map<String, Map<Object, List<Pointages>>> pointagesMap = groupPointagesByEmployeeAndDate(pointagesList);

        Set<Object> allDates = getAllDates(pointagesMap);

        for (Employees employee : employesRepository.findAll()) {
            String matricule = employee.getMatricule();

            for (Object dateObject : allDates) {
                LocalDate date = convertDateToLocalDate((Date) dateObject);

                if (isWeekend(date) || isJourFerie(date, holidays)) {
                    continue;
                }

                List<Pointages> employeePointages = pointagesMap.getOrDefault(matricule, Collections.emptyMap())
                        .getOrDefault(dateObject, new ArrayList<>());

                boolean isAbsent = estAbsentS1(employeePointages);
                AbsenceResult absenceResult = new AbsenceResult(matricule, date);

                if (isAbsent) {
                    absenceResultsS1.add(absenceResult);
                    System.out.println("Matricule " + matricule + " is absent on S1: " + date);
                }
            }
        }

        absenceResultsS1.sort(Comparator
                .comparing(AbsenceResult::getMatricule)
                .thenComparing(AbsenceResult::getJoursAbsences));
        absenceRepository.saveAll(absenceResultsS1);

        return absenceResultsS1;
    }

    
    
    
    private Map<String, Map<Object, List<Pointages>>> groupPointagesByEmployeeAndDate(List<Pointages> pointagesList) {
        return pointagesList.stream()
                .collect(Collectors.groupingBy(Pointages::getMatricule,
                        Collectors.groupingBy(Pointages::getDate)));
    }

    private Set<Object> getAllDates(Map<String, Map<Object, List<Pointages>>> pointagesMap) {
        return pointagesMap.values().stream()
                .flatMap(innerMap -> innerMap.keySet().stream())
                .collect(Collectors.toSet());
    }
    boolean estAbsentS1(List<Pointages> pointagesList) {
        if (!pointagesList.isEmpty()) {
            Pointages premierPointage = pointagesList.get(0);
            return isSeance1(premierPointage.getFullDate()) && pointagesList.stream()
                    .anyMatch(pointage -> isSeance1(pointage.getFullDate()));
        }
        return true;
    }

    private boolean isSeance1(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime dateTime = instant.atZone(zoneId).toLocalDateTime();

        return dateTime.toLocalTime().isAfter(FIN_SEANCE_1);
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean isJourFerie(LocalDate date, List<LocalDate> joursFeries) {
        return joursFeries.contains(date);
    }

    public static List<LocalDate> getJoursFeries() {
        List<LocalDate> joursFeries = new ArrayList<>();

        joursFeries.add(LocalDate.of(LocalDate.now().getYear(), 1, 1)); // Jour de l'An
        joursFeries.add(LocalDate.of(LocalDate.now().getYear(), 3, 20)); // Fête de l'Indépendance
        joursFeries.add(LocalDate.of(LocalDate.now().getYear(), 5, 1)); // Fête du Travail
        joursFeries.add(LocalDate.of(LocalDate.now().getYear(), 7, 25)); // Journée de la République
        joursFeries.add(LocalDate.of(LocalDate.now().getYear(), 8, 13)); // Fête de la Femme
        joursFeries.add(LocalDate.of(LocalDate.now().getYear(), 10, 15)); // Journée de l'Évacuation

        LocalDate aidElFitr = LocalDate.of(LocalDate.now().getYear(), 4, 21);
        LocalDate aidElAdha = LocalDate.of(LocalDate.now().getYear(), 6, 28);
        LocalDate nouvelAnIslamique = LocalDate.of(LocalDate.now().getYear(), 6, 6);

        joursFeries.add(aidElFitr);
        joursFeries.add(aidElAdha);
        joursFeries.add(nouvelAnIslamique);

        return joursFeries;
    }

    private LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    
    ////////s2
    public List<AbsenceResult> checkAbsenceS2() throws IOException, CsvException {
        List<AbsenceResult> absenceResultsS2 = new ArrayList<>();
        List<LocalDate> holidays = getJoursFeries();

        List<Pointages> pointagesList = pointageService.getGroupedPointages();

        Map<String, Map<Object, List<Pointages>>> pointagesMap = groupPointagesByEmployeeAndDate(pointagesList);

        Set<Object> allDates = getAllDates(pointagesMap);

        for (Employees employee : employesRepository.findAll()) {
            String matricule = employee.getMatricule();

            for (Object dateObject : allDates) {
                LocalDate date = convertDateToLocalDate((Date) dateObject);

                if (isWeekend(date) || isJourFerie(date, holidays)) {
                    continue;
                }

                List<Pointages> employeePointages = pointagesMap.getOrDefault(matricule, Collections.emptyMap())
                        .getOrDefault(dateObject, new ArrayList<>());

                boolean isAbsent = estAbsentS2(employeePointages);
                AbsenceResult absenceResult = new AbsenceResult(matricule, date);

                if (isAbsent) {
                    absenceResultsS2.add(absenceResult);
                    System.out.println("Matricule " + matricule + " is absent on S2: " + date);
                }
            }
        }

        absenceResultsS2.sort(Comparator
                .comparing(AbsenceResult::getMatricule)
                .thenComparing(AbsenceResult::getJoursAbsences));
        absenceRepository.saveAll(absenceResultsS2);

        return absenceResultsS2;
    }
  
     private boolean estAbsentS2(List<Pointages> pointagesList) {
        if (!pointagesList.isEmpty()) {
            boolean auMoinsUnPointageS2= pointagesList.stream()
                    .anyMatch(pointage -> isSeance2(pointage.getFullDate()));
            if (auMoinsUnPointageS2) {
                return false;
            }
            return estSortiPendantS2(pointagesList);
        }
        return true; 
    }
    
     private boolean estSortiPendantS2(List<Pointages> pointagesList) {
        List<Pointages> pointagesS2 = pointagesList.stream()
                .filter(pointage -> isDuring(pointage.getFullDate()))
                .collect(Collectors.toList());

        return pointagesS2.size() % 2 == 0;
    }

    private boolean isDuring(Date date) {
    	 Instant instant = date.toInstant();
         ZoneId zoneId = ZoneId.systemDefault();
         LocalDateTime dateTime = instant.atZone(zoneId).toLocalDateTime(); 
        return dateTime.toLocalTime().isAfter(DEBUT_SEANCE_1) && dateTime.toLocalTime().isBefore(DEBUT_SEANCE_2);
    }
    
    private boolean isSeance2(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime dateTime = instant.atZone(zoneId).toLocalDateTime(); 
        return (dateTime.toLocalTime().isAfter(DEBUT_SEANCE_2)
                && dateTime.toLocalTime().isBefore(FIN_SEANCE_2));
    }
///////Absence toute la journée
    public List<AbsenceResult> checkAbsence() throws IOException, CsvException {
        List<AbsenceResult> abs1 = checkAbsenceS1();
        List<AbsenceResult> abs2 = checkAbsenceS2();

        // Filtrer les absences communes à la fois pour S1 et S2 dans le même jour
        List<AbsenceResult> absences = abs1.stream()
                .filter(abs1Entry -> abs2.stream()
                        .anyMatch(abs2Entry ->
                                abs1Entry.getMatricule().equals(abs2Entry.getMatricule())
                                        && abs1Entry.getJoursAbsences().equals(abs2Entry.getJoursAbsences())
                        )
                )
                .collect(Collectors.toList());

        // Affichez les absences communes
        absences.forEach(result -> System.out.println("Matricule: " + result.getMatricule() + ", Date d'Absence: " + result.getJoursAbsences()));

        // Sauvegarder les résultats
        absenceRepository.saveAll(absences);

        return absences;
    }
    ///////bilan globale
    public double calculerAbsencePourEmploye(String matricule) throws IOException, CsvException {
        List<Pointages> listPointages = pointageService.getGroupedPointages();
        List<LocalDate> joursFeries = getJoursFeries();
        Map<String, Map<Object, List<Pointages>>> pointagesMap = listPointages.stream()
                .collect(Collectors.groupingBy(Pointages::getMatricule,
                        Collectors.groupingBy(Pointages::getDate)));
        double countAbsencePourEmploye = 0;

        Set<Object> allDates = pointagesMap.values().stream()
                .flatMap(innerMap -> innerMap.keySet().stream())
                .collect(Collectors.toSet());

        for (Object dateObject : allDates) {
            LocalDate date = convertDateToLocalDate((Date) dateObject);

            if (isWeekend(date) || isJourFerie(date, joursFeries)) {
                continue;
            }
            List<Pointages> pointagesList = pointagesMap.getOrDefault(matricule, Map.of()).getOrDefault(dateObject,
                    new ArrayList<>());

            boolean isAbsentOnDate = pointagesList.isEmpty();
            boolean estAbsentS1 = estAbsentS1(pointagesList);
            boolean estAbsentS2 = estAbsentS2(pointagesList);
            if (isAbsentOnDate) {
                System.out.println("Employé " + matricule + " est absent le " + date);
                countAbsencePourEmploye++;}
                else if((estAbsentS1)||(estAbsentS2))  {
                	countAbsencePourEmploye=countAbsencePourEmploye+0.5;
                } 
            else {
                System.out.println("Employé " + matricule + " n'est pas absent le " + date);
            }
        }

        System.out.println("Nombre total d'absences pour l'employé " + matricule + " : " + countAbsencePourEmploye);
        return countAbsencePourEmploye;
    }
    
    /////// Bilan Détaillé
    public boolean estAbsentS1PourChaqueJour(String matricule, LocalDate dateSouhaite) throws IOException, CsvException {
    	List<Pointages> pointagesList = pointageService.getGroupedPointages();
        Map<String, Map<Object, List<Pointages>>> pointagesMap = groupPointagesByEmployeeAndDate(pointagesList);
            List<LocalDate> joursFeries = getJoursFeries();

         Set<Object> allDates = pointagesMap.values().stream()
                 .flatMap(innerMap -> innerMap.keySet().stream())
                 .collect(Collectors.toSet());

         for (Object dateObject : allDates) {
             LocalDate date = convertDateToLocalDate((Date) dateObject);

             if (date.equals(dateSouhaite) && !isWeekend(date) && !isJourFerie(date, joursFeries)) {
                 List<Pointages> pointages = pointagesMap.getOrDefault(matricule, Map.of()).getOrDefault(dateObject,
                         new ArrayList<>());

                 if (pointages.isEmpty()) {
                     return true;
                 } else {
                     Pointages premierPointage = pointages.get(0);
                     if (isAfterSeance1(premierPointage.getFullDate())) {
                         return true;
                     }
                 }
             }
         }
         return false;
     }
    private boolean isAfterSeance1(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime dateTime = instant.atZone(zoneId).toLocalDateTime();

        return dateTime.toLocalTime().isAfter(FIN_SEANCE_1);
    }
    
    public boolean estAbsentS2PourChaqueJour(String matricule, LocalDate dateSouhaite) throws IOException, CsvException {
    	List<Pointages> pointagesList = pointageService.getGroupedPointages();
        Map<String, Map<Object, List<Pointages>>> pointagesMap = groupPointagesByEmployeeAndDate(pointagesList);
         List<LocalDate> joursFeries = getJoursFeries();

         Set<Object> allDates = pointagesMap.values().stream()
                 .flatMap(innerMap -> innerMap.keySet().stream())
                 .collect(Collectors.toSet());

        for (Object dateObject : allDates) {
            LocalDate date = convertDateToLocalDate((Date) dateObject);
            if (date.equals(dateSouhaite) && !isWeekend(date) && !isJourFerie(date, joursFeries)) {
                List<Pointages> pointages = pointagesMap.getOrDefault(matricule, Collections.emptyMap())
                        .getOrDefault(dateObject, Collections.emptyList());
                if (pointages.isEmpty()) {
                    return true; 
                } else if (estAbsentS2(pointages)) {
                    return true;
                }
            }
        }
        return false; 
    }
}
