package com.projet.Projet_ItServ.service;

import com.opencsv.exceptions.CsvException;
import com.projet.Projet_ItServ.entity.RetardResult;
import com.projet.Projet_ItServ.repository.EmployesRepository;
import com.projet.Projet_ItServ.repository.RetardResultRepository;

import com.projet.Projet_ItServ.entity.Employees;
import com.projet.Projet_ItServ.entity.Pointages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.time.Duration;

@Service
public class RetardService {

    private static  LocalTime DEBUT_SEANCE_1 = LocalTime.of(8, 0);
    private static  LocalTime FIN_SEANCE_1 = LocalTime.of(12, 30);
    private static  LocalTime DEBUT_SEANCE_2 = LocalTime.of(14, 0);
	private static  LocalTime FIN_SEANCE_2 = LocalTime.of(18, 0);

    @Autowired
    private PointageService pointageService;
    @Autowired
    private RetardResultRepository retardrep;

    @Autowired
    private EmployesRepository employesRepository;

    public List<RetardResult> checkRetard() throws IOException, CsvException {
        List<RetardResult> retardResults = new ArrayList<>();
        List<LocalDate> joursFeries = getJoursFeries();

        List<Employees> employees = employesRepository.findAll();
        List<Pointages> pointagesList = pointageService.getGroupedPointages();

        if (employees != null && !employees.isEmpty()) {
            for (Employees employee : employees) {
                String matricule = employee.getMatricule();
                long totalRetardSeance1Seconds = 0;
                long totalRetardSeance2Seconds = 0;

                Set<Object> allDates = pointagesList.stream()
                        .map(Pointages::getDate)
                        .collect(Collectors.toSet());

                for (Object dateObject : allDates) {
                    LocalDate date = convertDateToLocalDate((Date) dateObject);

                    if (isWeekend(date) || isJourFerie(date, joursFeries)) {
                        continue;
                    }

                    List<Pointages> employeePointages = pointagesList.stream()
                            .filter(pointages -> pointages != null && pointages.getDate() != null && pointages.getMatricule() != null
                                    && pointages.getDate().equals(dateObject) && pointages.getMatricule().equals(matricule))
                            .collect(Collectors.toList());

                    if (!employeePointages.isEmpty()) {
                        Pointages firstPointageSeance1 = employeePointages.get(0);
                        if (firstPointageSeance1 != null && isBetween(firstPointageSeance1.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime().toLocalTime(), DEBUT_SEANCE_1, FIN_SEANCE_1)) {
                            LocalDateTime pointageDateTime1 = firstPointageSeance1.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
                            long retardSeconds = calculateDurationSeconds(LocalDateTime.of(date, DEBUT_SEANCE_1), pointageDateTime1);
                            System.out.println("Lemployee" + matricule + " a fait un retard dans le matin :" + retardSeconds + "dans le jour :" + firstPointageSeance1.getFullDate());
                            RetardResult retardResult1 = new RetardResult(matricule, date, pointageDateTime1, LocalDateTime.of(date, DEBUT_SEANCE_1));
                            retardResults.add(retardResult1);
                            totalRetardSeance1Seconds += retardSeconds;
                        }

                        Pointages firstPointageSeance2 = employeePointages.stream()
                                .filter(pointages -> "C/In".equals(pointages.getType()) && isBetween(pointages.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalTime(), DEBUT_SEANCE_2, FIN_SEANCE_2))
                                .findFirst()
                                .orElse(null);

                        if (firstPointageSeance2 != null) {
                            Pointages firstPointageBeforeS2 = employeePointages.stream()
                                    .filter(pointages -> isBefore(pointages.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalTime(), firstPointageSeance2.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalTime()))
                                    .max(Comparator.comparing(pointages -> pointages.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime()))
                                    .orElse(null);

                            if (firstPointageBeforeS2 != null) {
                                LocalDateTime pointageDateTime2 = firstPointageSeance2.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
                                LocalDateTime pointageDateTimeBeforeS2 = firstPointageBeforeS2.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();

                                if (isBetween(pointageDateTime2.toLocalTime(), DEBUT_SEANCE_2, FIN_SEANCE_2)
                                        && ! isBetween(pointageDateTimeBeforeS2.toLocalTime(), DEBUT_SEANCE_2, FIN_SEANCE_2)) {
                                    long retardSeconds = calculateDurationSeconds(LocalDateTime.of(date, DEBUT_SEANCE_2), pointageDateTime2);
                                    System.out.println("L'employé " + matricule + " a fait un retard dans S2: " + retardSeconds + " dans le jour : " + firstPointageSeance2.getFullDate());
                                    RetardResult retardResult1 = new RetardResult(matricule, date, pointageDateTime2, LocalDateTime.of(date, DEBUT_SEANCE_2));
                                    retardResults.add(retardResult1);
                                    totalRetardSeance2Seconds += retardSeconds;
                                }
                            }
                        }
                    }
                }

                System.out.println("Le nombre total des Retards dans s1 de l'employé " + matricule + " = " + Retard(totalRetardSeance1Seconds));
                System.out.println("Le nombre total des Retards dans s2 de l'employé " + matricule + " = " + Retard(totalRetardSeance2Seconds));
            }

            Collections.sort(retardResults, Comparator.comparing(RetardResult::getMatricule).thenComparing(RetardResult::getDate));
        } else {
            System.out.println("EmployeeService is not initialized or no employees found.");
        }

        retardrep.saveAll(retardResults);
        return retardResults;
    }

    private boolean isBetween(LocalTime time, LocalTime startTime, LocalTime endTime) {
        return !time.isBefore(startTime) && !time.isAfter(endTime);
    }
    private static boolean isBefore(LocalTime time1, LocalTime time2) {
        return time1.isBefore(time2);
    }

    private String Retard(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;

        return String.format("%02dh:%02dm:%02ds", hours, minutes, remainingSeconds);
    }

    private long calculateDurationSeconds(LocalDateTime localDateTime, LocalDateTime pointageDateTime) {
        System.out.println("localDateTime: " + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("pointageDateTime: " + pointageDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        long seconds = localDateTime.until(pointageDateTime, ChronoUnit.SECONDS);

        System.out.println("seconds: " + seconds);

        return seconds;
    }

    private LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean isJourFerie(LocalDate date, List<LocalDate> joursFeries) {
        return joursFeries.contains(date);
    }

    public static List<LocalDate> getJoursFeries() {
        List<LocalDate> joursFeries = new ArrayList<>();

        joursFeries.add(LocalDate.of(LocalDate.now().getYear(), 1, 1));
        joursFeries.add(LocalDate.of(LocalDate.now().getYear(), 3, 20));
        joursFeries.add(LocalDate.of(LocalDate.now().getYear(), 5, 1));
        joursFeries.add(LocalDate.of(LocalDate.now().getYear(), 7, 25));
        joursFeries.add(LocalDate.of(LocalDate.now().getYear(), 8, 13));
        joursFeries.add(LocalDate.of(LocalDate.now().getYear(), 10, 15));

        LocalDate aidElFitr = LocalDate.of(LocalDate.now().getYear(), 6, 21);
        LocalDate aidElAdha = LocalDate.of(LocalDate.now().getYear(), 6, 28);
        LocalDate nouvelAnIslamique = LocalDate.of(LocalDate.now().getYear(), 6, 6);

        joursFeries.add(aidElFitr);
        joursFeries.add(aidElAdha);
        joursFeries.add(nouvelAnIslamique);

        return joursFeries;
    }
    
    public List<RetardResult> filterRetardResultsS1(List<RetardResult> allRetardResults) {
        List<RetardResult> filteredResultsS1 = new ArrayList<>();

        for (RetardResult retardResult : allRetardResults) {
            if (isBetween(retardResult.getPointageDateTime().toLocalTime(), DEBUT_SEANCE_1, FIN_SEANCE_1)) {
                filteredResultsS1.add(retardResult);
            }
            
        }

        Collections.sort(filteredResultsS1, Comparator.comparing(RetardResult::getMatricule).thenComparing(RetardResult::getDate));
        retardrep.saveAll(filteredResultsS1);
        return filteredResultsS1;
    }
    public List<RetardResult> filterRetardResultsS2(List<RetardResult> allRetardResults) {
        List<RetardResult> filteredResultsS2 = new ArrayList<>();

        for (RetardResult retardResult : allRetardResults) {
            if (isBetween(retardResult.getPointageDateTime().toLocalTime(), DEBUT_SEANCE_2, FIN_SEANCE_2)) {
                filteredResultsS2.add(retardResult);
            }
        }

        Collections.sort(filteredResultsS2, Comparator.comparing(RetardResult::getMatricule).thenComparing(RetardResult::getDate));
        
        retardrep.saveAll(filteredResultsS2);

        return filteredResultsS2;
    }
    //////Bilan Globale
    public String calculateHeureRetardPourEmploye(String matricule, LocalTime debutSeance, LocalTime finSeance)
            throws IOException, CsvException {
        List<Pointages> listPointages = pointageService.getGroupedPointages();
        List<LocalDate> joursFeries = getJoursFeries();

        Map<LocalDate, Pointages> firstCInByDate = listPointages.stream()
                .filter(pointages -> matricule.equals(pointages.getMatricule()))
                .filter(pointages -> {
                    LocalDate date = convertDateToLocalDate(pointages.getDate());
                    return !isWeekend(date) && !isJourFerie(date, joursFeries);
                })
                .filter(pointages -> "C/In".equals(pointages.getType()))
                .collect(Collectors.toMap(
                        pointages -> convertDateToLocalDate(pointages.getDate()),
                        Function.identity(),
                        (existing, replacement) -> {
                            LocalDateTime existingDateTime = existing.getFullDate()
                                    .toInstant()
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDateTime();

                            LocalDateTime replacementDateTime = replacement.getFullDate()
                                    .toInstant()
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDateTime();

                            if (existingDateTime.isBefore(replacementDateTime)) {
                                return existingDateTime.toLocalTime().isAfter(debutSeance) &&
                                        existingDateTime.toLocalTime().isBefore(finSeance) ? existing : replacement;
                            } else {
                                return replacementDateTime.toLocalTime().isAfter(debutSeance) &&
                                        replacementDateTime.toLocalTime().isBefore(finSeance) ? replacement : existing;
                            }
                        },

                        TreeMap::new
                ));

        long totalRetardSeconds = firstCInByDate.values().stream()
                .filter(pointages -> {
                    LocalDateTime pointageDateTime = pointages.getFullDate()
                            .toInstant()
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDateTime();

                    return pointageDateTime.toLocalTime().isAfter(debutSeance) &&
                            pointageDateTime.toLocalTime().isBefore(finSeance);
                })
                .mapToLong(pointages -> {
                    LocalDateTime pointageDateTime = pointages.getFullDate()
                            .toInstant()
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDateTime();
                    return calculateDurationSeconds(LocalDateTime.of(convertDateToLocalDate(pointages.getDate()), debutSeance), pointageDateTime);
                })
                .sum();

        return Retard(totalRetardSeconds);
    }

    public String CalculerHeureRetardPourEmployeS1(String matricule) throws IOException, CsvException {
        return calculateHeureRetardPourEmploye(matricule, DEBUT_SEANCE_1, FIN_SEANCE_1);
    }

    public String CalculerHeureRetardPourEmployeS2(String matricule) throws IOException, CsvException {
        return calculateHeureRetardPourEmploye(matricule, DEBUT_SEANCE_2, FIN_SEANCE_2);
    }
    
    /////Bilan Détaillé
    
    public String CalculerHeureRetardPourEmployeS1ChaqueJour(String matricule, LocalDate dateSouhaitee) throws IOException, CsvException {
        List<Pointages> pointagesList = pointageService.getGroupedPointages();
        List<LocalDate> joursFeries = getJoursFeries();
        Map<String, Map<Date, List<Pointages>>> pointagesMap = pointagesList.stream()
                .collect(Collectors.groupingBy(Pointages::getMatricule, Collectors.groupingBy(Pointages::getDate)));
        
        Set<Object> allDates = pointagesMap.values().stream()
                .flatMap(innerMap -> innerMap.keySet().stream())
                .collect(Collectors.toSet());
        

        Duration totalRetard = Duration.ZERO;

        for (Object dateObject : allDates) {
            LocalDate date = convertDateToLocalDate((Date) dateObject);

            if (date.equals(dateSouhaitee) && !(isWeekend(date) || isJourFerie(date, joursFeries))) {
                List<Pointages> pointages = pointagesMap.getOrDefault(matricule, Map.of()).getOrDefault(dateObject, new ArrayList<>());

                for (Pointages pointage : pointages) {
                    String substringEntréeSortie = pointage.getType();
                    int substringMachine = pointage.getMachine();

                    LocalDateTime pointageDateTime = pointage.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();

                    if ("C/In".equals(substringEntréeSortie) && substringMachine == 2 && isBetween(pointageDateTime.toLocalTime(), DEBUT_SEANCE_1, FIN_SEANCE_1)) {
                        long retardSeconds = calculateDurationSeconds(LocalDateTime.of(date, DEBUT_SEANCE_1), pointageDateTime);
                        totalRetard = totalRetard.plusSeconds(retardSeconds);
                        break;  
                    }
                }
                break;  
            }
        }

        return Retard(totalRetard.getSeconds());
    }
    public String calculerHeureRetardPourEmployeS2ChaqueJour(String matricule, LocalDate dateSouhaitee) throws IOException, CsvException {
        List<Pointages> pointagesList = pointageService.getGroupedPointages();
        List<LocalDate> joursFeries = getJoursFeries();

        Map<String, Map<Date, List<Pointages>>> pointagesMap = pointagesList.stream()
                .collect(Collectors.groupingBy(Pointages::getMatricule, Collectors.groupingBy(Pointages::getDate)));

        Set<Object> allDates = pointagesMap.values().stream()
                .flatMap(innerMap -> innerMap.keySet().stream())
                .collect(Collectors.toSet());

        Duration totalRetard = Duration.ZERO;

        for (Object dateObject : allDates) {
            LocalDate date = convertDateToLocalDate((Date) dateObject);

            if (date.equals(dateSouhaitee) && !(isWeekend(date) || isJourFerie(date, joursFeries))) {
                List<Pointages> pointages = pointagesMap.getOrDefault(matricule, Collections.emptyMap()).getOrDefault(dateObject, new ArrayList<>());

                // Additional condition: Check if the employee has exited after 14:00
                boolean hasExitedAfter14 = pointages.stream()
                        .anyMatch(pointage ->
                                pointage.getMachine() == 4 &&
                                        pointage.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalTime().isAfter(FIN_SEANCE_2)
                                        && pointage.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalTime().isBefore(DEBUT_SEANCE_2)
                        );

                if (!hasExitedAfter14) {
                    // Rechercher le premier pointage dans la séance spécifiée
                    for (int i = 0; i < pointages.size(); i++) {
                        Pointages pointage = pointages.get(i);

                        if ("C/In".equals(pointage.getType()) && pointage.getMachine() == 2 && isBetween(pointage.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalTime(), DEBUT_SEANCE_2, FIN_SEANCE_2)) {
                            // Vérifier si le pointage précédent est après 14:00
                            if (i > 0) {
                                Pointages previousPointage = pointages.get(i - 1);
                                LocalDateTime previousPointageDateTime = previousPointage.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();

                                if ((isBetween(pointage.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalTime(), DEBUT_SEANCE_2, FIN_SEANCE_2))&&! isBetween(previousPointageDateTime.toLocalTime(), DEBUT_SEANCE_2, FIN_SEANCE_2)) {
                                    long retardSeconds = calculateDurationSeconds(LocalDateTime.of(date, DEBUT_SEANCE_2), pointage.getFullDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
                                    totalRetard = totalRetard.plusSeconds(retardSeconds);
                                }
                            }
                            break;
                        }
                    }
                }
                break;
            }
        }

        return Retard(totalRetard.getSeconds());
    }

}