package com.projet.Projet_ItServ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.Projet_ItServ.entity.BilanGlobal;

@Repository
public interface BilanGRepository extends JpaRepository<BilanGlobal, Long> {

}
