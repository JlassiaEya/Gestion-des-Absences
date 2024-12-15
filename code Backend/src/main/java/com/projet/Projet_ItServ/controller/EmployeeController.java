package com.projet.Projet_ItServ.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.projet.Projet_ItServ.entity.Employees;
import com.projet.Projet_ItServ.repository.EmployesRepository;
import com.projet.Projet_ItServ.service.EmployeeService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class EmployeeController {
@Autowired
    private  EmployeeService empService;
@Autowired
    private  EmployesRepository employeeRepository;
    @PostMapping("/listemp")
    public ResponseEntity<List<Employees>> analyzeEmployeeFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            File convertedFile = convertMultipartFileToFile(file);

            List<Employees> employeesList = empService.getEmployeesFromFileAndSaveToDatabase(convertedFile);
            employeeRepository.saveAll(employeesList);

            return ResponseEntity.ok(employeesList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide ou nul");
        }

        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }
}
