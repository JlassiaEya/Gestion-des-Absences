package com.projet.Projet_ItServ.entity;


import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AbsenceResult {
    @Id
    @GeneratedValue
    private Long id;

    private String matricule;
    private LocalDate joursAbsences;
    private int nbSeance;
    private String numSeance;

    public String getNumSeance() {
		return numSeance;
	}



	public void setNumSeance(String numSeance) {
		this.numSeance = numSeance;
	}



	public AbsenceResult() {
        
    }


	public AbsenceResult(Long id, String matricule, LocalDate joursAbsences, int nbSeance, String numSeance) {
		super();
		this.id = id;
		this.matricule = matricule;
		this.joursAbsences = joursAbsences;
		this.nbSeance = nbSeance;
		this.numSeance = numSeance;
	}
	
	



	public AbsenceResult(String matricule, LocalDate joursAbsences, String numSeance) {
		super();
		this.matricule = matricule;
		this.joursAbsences = joursAbsences;
		this.numSeance = numSeance;
	}
	public AbsenceResult(String matricule, LocalDate joursAbsences) {
		super();
		this.matricule = matricule;
		this.joursAbsences = joursAbsences;
	}


	public int getNbSeance() {
		return nbSeance;
	}

	public void setNbSeance(int nbSeance) {
		this.nbSeance = nbSeance;
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

   
    public LocalDate getJoursAbsences() {
        return joursAbsences;
    }

    public void setJoursAbsences(LocalDate joursAbsences) {
        this.joursAbsences = joursAbsences;
    }

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	@Override
	public String toString() {
		return "AbsenceResult [matricule=" + getMatricule() + ", joursAbsences=" + getJoursAbsences() + "]";
	}


	public void setJoursAbsences(Object joursAbsences2) {
	    if (joursAbsences2 instanceof LocalDate) {
	        this.joursAbsences = (LocalDate) joursAbsences2;
	    } else if (joursAbsences2 instanceof Date) {
	        this.joursAbsences = ((Date) joursAbsences2).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    } 
	}
}
