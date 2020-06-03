package io.sp.webservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Alerte {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private Coord coord;
	
	@Column
	private int valeur;
	
	@Column
	private String type;
	
	@Column
	private String etat;
	
	public Alerte() {
		
	}
	
	public Alerte(Coord coord, Integer valeur, String type) {
		this.setCoord(coord);
		this.setValeur(valeur);
		this.setType(type);
	}
	
	public Alerte(Integer valeur, String type, String etat) {
		this.setValeur(valeur);
		this.setType(type);
		this.setEtat(etat);
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Coord getCoord() {
		return coord;
	}
	public void setCoord(Coord coord) {
		this.coord = coord;
	}
	public int getValeur() {
		return valeur;
	}
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
	
	@Override
	public String toString() {
		return "Alerte "+this.id+": "+this.coord+", Valeur: "+this.valeur+", Type: "+this.type;
	}
}
