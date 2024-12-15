package com.projet.Projet_ItServ.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Pointages {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	  private  String matricule;
	    private  int machine;
	    private  Date date;
	    private  String type;
	    private  Date fullDate;

	    public Pointages() {
			super();
		}
	    public Pointages(LocalDateTime fullDate) {
	        this.fullDate = java.sql.Timestamp.valueOf(fullDate); 
	    }
		public Pointages(Long id, String matricule, int machine, Date date, String type, Date fullDate) {
			super();
			this.id = id;
			this.matricule = matricule;
			this.machine = machine;
			this.date = date;
			this.type = type;
			this.fullDate = fullDate;
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

		public int getMachine() {
			return machine;
		}

		public void setMachine(int machine) {
			this.machine = machine;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		/*public Date getTime() {
	        return fullDate;
	    }
		*/
		public Date getFullDate() {
			return fullDate;
		}

		public void setFullDate(Date fullDate) {
			this.fullDate = fullDate;
		}

		@Override
		public String toString() {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd 00:00:00 z yyyy");
			return "Pointages [matricule=" + matricule + ", machine=" + machine + ", date=" + dateFormat.format(date)  + ", type=" + type
					+ ", heure=" + fullDate + "]";
		}
		
		
	    
	}  
