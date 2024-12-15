package com.projet.Projet_ItServ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.Projet_ItServ.entity.AbsenceResult;
@Repository
public interface AbsenceRepository extends JpaRepository<AbsenceResult, Long>{

	List<AbsenceResult> findByMatricule(String matricule);

}
