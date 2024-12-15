package com.projet.Projet_ItServ.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.exceptions.CsvException;
import com.projet.Projet_ItServ.entity.Pointages;
import com.projet.Projet_ItServ.repository.PointagesRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class PointageService {
	 @Autowired
	    private PointagesRepository pointagesRepository;
 
	public Map<String, Map<Object, List<Pointages>>> GrouperPointage(MultipartFile file) throws IOException, CsvException {
		 List<Pointages> pointagesList = readPointageFromCsv(file);

	        Map<String, Map<Object, List<Pointages>>> result = pointagesList.stream()
	                .filter(pointage -> pointage.getMatricule() != null && pointage.getDate() != null)
	                .collect(Collectors.groupingBy(
	                        Pointages::getMatricule,
	                        TreeMap::new,
	                        Collectors.groupingBy(
	                                Pointages::getDate,
	                                TreeMap::new,
	                                Collectors.toList()
	                        )
	                ));

	        return result;
	    
	}
	    

    private List<Pointages> readPointageFromCsv(MultipartFile file) throws IOException, CsvException {
        List<Pointages> PointageList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // Skip header row if needed
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split("\\|");
                Pointages pointage = mapRowToPointage(row);
                PointageList.add(pointage);
            }
        } catch (Exception e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        return PointageList;
    }

    private Pointages mapRowToPointage(String[] row) {
        Pointages pointages = new Pointages();
        SimpleDateFormat dateFormatFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        dateFormatFull.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            pointages.setFullDate(dateFormatFull.parse(row[0]));
            pointages.setDate(dateFormat.parse(row[0]));
            pointages.setMatricule(row[1]);
            pointages.setMachine(Integer.parseInt(row[2]));
            pointages.setType(row[3]);

            // Map other fields as needed
            System.out.println("++++++++++++" + pointages.toString());
        } catch (Exception e) {
            System.out.println("Error mapping CSV row to Pointages: " + e.getMessage());
        }

        return pointages;
    }
    // Enregistrez les données groupées dans la base de données
    public void saveGroupedPointages(Map<String, Map<Object, List<Pointages>>> groupedData) {
     
        for (Map.Entry<String, Map<Object, List<Pointages>>> entry : groupedData.entrySet()) {
            for (Map.Entry<Object, List<Pointages>> innerEntry : entry.getValue().entrySet()) {
                for (Pointages pointages : innerEntry.getValue()) {
                	
                    // Enregistrez chaque pointage dans la base de données
                    pointagesRepository.save(pointages);
                }
            }
        }
    }
    public List<Pointages> getGroupedPointages() {
        
        return pointagesRepository.findAll(); 
    }
}
