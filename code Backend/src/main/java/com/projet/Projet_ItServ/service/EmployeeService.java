package com.projet.Projet_ItServ.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.projet.Projet_ItServ.entity.Employees;
import com.projet.Projet_ItServ.repository.EmployesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
	@Autowired
    private EmployesRepository employesRepository;

    public List<Employees> getEmployeesFromFileAndSaveToDatabase(File file) throws IOException, CsvException {
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            List<String[]> employeeData = csvReader.readAll();
            List<Employees> employeesList = employeeData.stream()
                    .skip(1) // skip header
                    .map(this::diviserLigne)
                    .collect(Collectors.toList());

            employesRepository.saveAll(employeesList);

            return employeesList;
        } catch (IOException | CsvException e) {
            throw e; 
        }
    }

    private Employees diviserLigne(String[] row) {
        Employees employee = new Employees();
            String input = row[0];
            String[] parts = input.split("\\|");
            employee.setMatricule(parts[0]);
            employee.setNom(parts[1]);
            employee.setPrenom(parts[2]);

            System.out.println("++++++++++++" + employee.toString());
         
        return employee;
    }

    public Employees getEmployeeByMatricule(String matricule) {
        List<Employees> employees = employesRepository.findByMatricule(matricule);
        if (!employees.isEmpty()) {
            return employees.get(0);  
        }
        return null;
    }

}
