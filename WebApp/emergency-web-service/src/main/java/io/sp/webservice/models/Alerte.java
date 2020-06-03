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
	private int intensity;
	
	@Column
	private String type;
	
	@Column
	private String etat;
	
	@Column
	private int range;


	public Alerte() {
		
	}
	
	public Alerte(Coord coord, Integer valeur, String type) {
		this.setCoord(coord);
		this.setIntensity(valeur);
		this.setType(type);
	}
	
	public Alerte(Integer intensity, String type, String etat) {
		this.setIntensity(intensity);
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
	public int getIntensity() {
		return intensity;
	}
	public void setIntensity(int valeur) {
		this.intensity = valeur;
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
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
	
	@Override
	public String toString() {
		return "Alerte "+this.id+": "+this.coord+", Valeur: "+this.intensity+", Type: "+this.type;
	}
}
