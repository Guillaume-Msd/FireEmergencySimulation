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
	private Coord coord_alerte;
	
	@Column
	private int valeur_detectee;
	
	@Column
	private String type;
	
	@Column
	private String etat;
	
	public Alerte() {
		
	}
	
	public Alerte(Coord coord, Integer valeur, String type) {
		this.setCoord_alerte(coord);
		this.setValeur_detectee(valeur);
		this.setType(type);
	}
	
	public Alerte(Integer valeur, String type, String etat) {
		this.setValeur_detectee(valeur);
		this.setType(type);
		this.setEtat(etat);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Coord getCoord_alerte() {
		return coord_alerte;
	}
	public void setCoord_alerte(Coord coord_alerte) {
		this.coord_alerte = coord_alerte;
	}
	public int getValeur_detectee() {
		return valeur_detectee;
	}
	public void setValeur_detectee(int valeur_detectee) {
		this.valeur_detectee = valeur_detectee;
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

}
