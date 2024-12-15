package com.projet.Projet_ItServ.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BilanDétaiillé {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private String matricule;
    private String Nom;
    private String Prenom;
    private LocalDate date;
    private String absenceSeance1;
    private String absenceSeance2; 
    private String nbHeuresRetardSeance1;
    private String nbHeuresRetardSeance2;

    public BilanDétaiillé() {
    
    }

    public BilanDétaiillé(String matricule, String Nom, String Prenom, LocalDate date, String absenceSeance1, String absenceSeance2, String nbHeuresRetardSeance1, String nbHeuresRetardSeance2) {
        this.matricule = matricule;
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.date = date;
        this.absenceSeance1 = absenceSeance1;
        this.absenceSeance2 = absenceSeance2;
        this.nbHeuresRetardSeance1 = nbHeuresRetardSeance1;
        this.nbHeuresRetardSeance2 = nbHeuresRetardSeance2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAbsenceSeance1() {
        return absenceSeance1;
    }

    public void setAbsenceSeance1(String signe) {
        this.absenceSeance1 = signe;
    }

    public String getAbsenceSeance2() {
        return absenceSeance2;
    }

    public void setAbsenceSeance2(String signe) {
        this.absenceSeance2 = signe;
    }

    public String getNbHeuresRetardSeance1() {
        return nbHeuresRetardSeance1;
    }

    public void setNbHeuresRetardSeance1(String nbHeuresRetardSeance1) {
        this.nbHeuresRetardSeance1 = nbHeuresRetardSeance1;
    }

    public String getNbHeuresRetardSeance2() {
        return nbHeuresRetardSeance2;
    }

    public void setNbHeuresRetardSeance2(String nbHeuresRetardSeance2) {
        this.nbHeuresRetardSeance2 = nbHeuresRetardSeance2;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    @Override
    @JsonPropertyOrder({"matricule", "Nom", "Prenom", "id", "date", "absenceSeance1", "absenceSeance2", "nbHeuresRetardSeance1", "nbHeuresRetardSeance2"})
    public String toString() {
        return "BilanDétaiillé [ matricule=" + matricule + ", Nom=" + Nom + ", Prenom=" + Prenom
                + ", date=" + date + ", absenceSeance1=" + absenceSeance1 + ", absenceSeance2=" + absenceSeance2
                + ", nbHeuresRetardSeance1=" + nbHeuresRetardSeance1 + ", nbHeuresRetardSeance2="
                + nbHeuresRetardSeance2 + "]";
    }
}
