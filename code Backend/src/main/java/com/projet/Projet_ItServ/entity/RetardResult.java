package com.projet.Projet_ItServ.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RetardResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matricule;
    private LocalDate date;
    private LocalDateTime pointageDateTime;  
    private String retard;  
  

    public RetardResult(String matricule, LocalDate date, LocalDateTime pointageDateTime, LocalDateTime debutSeance) {
        this.matricule = matricule;
        this.date = date;
        this.pointageDateTime = pointageDateTime;
        this.retard = calculateRetard(pointageDateTime, debutSeance);  
       
    }

    private String calculateRetard(LocalDateTime pointageDateTime, LocalDateTime debutSeance) {
        long seconds = ChronoUnit.SECONDS.between(debutSeance.toLocalTime(), pointageDateTime.toLocalTime());
        LocalTime retardTime = LocalTime.ofSecondOfDay(seconds);
        return String.format("%02dh:%02dm:%02ds", retardTime.getHour(), retardTime.getMinute(), retardTime.getSecond());
    }

    public RetardResult() {
        super();
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
    @JsonIgnore
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRetard() {
        return retard;
    }

    public void setRetard(String retard) {
        this.retard = retard;
    }

    public LocalDateTime getPointageDateTime() {
        return pointageDateTime;
    }

    public void setPointageDateTime(LocalDateTime pointageDateTime) {
        this.pointageDateTime = pointageDateTime;
    }

    @Override
    public String toString() {
        return "RetardResult [id=" + id + ", matricule=" + matricule + ", pointageDateTime=" + pointageDateTime + ", retard=" + retard + "]";
    }



   
   
}
