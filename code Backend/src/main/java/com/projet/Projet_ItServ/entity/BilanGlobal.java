package com.projet.Projet_ItServ.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonPropertyOrder({"matricule", "prenom", "nom", "totalJoursAbsence", "totalHeuresRetardSeance1", "totalHeuresRetardSeance2"})
public class BilanGlobal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private String matricule;
    
    private String Nom;
    
    private String Prenom;
    
    private double totalJoursAbsence;

    private String totalHeuresRetardSeance1;

    private String totalHeuresRetardSeance2;

    
    public BilanGlobal() {
        super();
    }

    public BilanGlobal(String matricule, String prenom, String nom, double totalJoursAbsence, String totalHeuresRetardSeance1, String totalHeuresRetardSeance2) {
        this.matricule = matricule;
        this.Prenom = prenom;
        this.Nom = nom;
        this.totalJoursAbsence = totalJoursAbsence;
        this.totalHeuresRetardSeance1 = totalHeuresRetardSeance1;
        this.totalHeuresRetardSeance2 = totalHeuresRetardSeance2;
    }
    public BilanGlobal(String matricule, double totalJoursAbsence, String totalHeuresRetardSeance1, String totalHeuresRetardSeance2) {
    	 this.matricule = matricule;
    	this.totalJoursAbsence = totalJoursAbsence;
        this.totalHeuresRetardSeance1 = totalHeuresRetardSeance1;
        this.totalHeuresRetardSeance2 = totalHeuresRetardSeance2; 
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

	
    public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public double getTotalJoursAbsence() {
		return totalJoursAbsence;
	}

	public void setTotalJoursAbsence(int totalJoursAbsence) {
		this.totalJoursAbsence = totalJoursAbsence;
	}

	public String getTotalHeuresRetardSeance1() {
		return totalHeuresRetardSeance1;
	}

	public void setTotalHeuresRetardSeance1(String totalHeuresRetardSeance1) {
		this.totalHeuresRetardSeance1 = totalHeuresRetardSeance1;
	}

	public String getTotalHeuresRetardSeance2() {
		return totalHeuresRetardSeance2;
	}

	public void setTotalHeuresRetardSeance2(String totalHeuresRetardSeance2) {
		this.totalHeuresRetardSeance2 = totalHeuresRetardSeance2;
	}

	@Override
	public String toString() {
		return "BilanGlobal [id=" + id + ", matricule=" + matricule + ", Nom=" + Nom + ", Prenom=" + Prenom
				+ ", totalJoursAbsence=" + totalJoursAbsence + ", totalHeuresRetardSeance1=" + totalHeuresRetardSeance1
				+ ", totalHeuresRetardSeance2=" + totalHeuresRetardSeance2 + "]";
	}
}
