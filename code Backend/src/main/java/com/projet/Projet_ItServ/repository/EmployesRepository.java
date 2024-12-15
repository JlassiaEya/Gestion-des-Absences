package com.projet.Projet_ItServ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.Projet_ItServ.entity.Employees;

@Repository
public interface EmployesRepository extends JpaRepository<Employees, Long> {

	List<Employees> findByMatricule(String matricule);
    
}
