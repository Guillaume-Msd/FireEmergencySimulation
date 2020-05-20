package io.sp.webservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Alerte {
	
	@Id
	@GeneratedValue()
	private int id;
	
	@Column
	private Coord coord_alerte;
	
	@Column
	private int valeur_detectee;
	
	@Column
	private TypeSonde type;
	
	@Column
	private EtatIntervention etat;
	
	public Alerte() {
		
	}
	
	public Alerte(Coord coord,Integer valeur,TypeSonde type) {
		this.setCoord_alerte(coord);
		this.setValeur_detectee(valeur);
		this.setType(type);
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
	public TypeSonde getType() {
		return type;
	}
	public void setType(TypeSonde type) {
		this.type = type;
	}

	public EtatIntervention getEtat() {
		return etat;
	}

	public void setEtat(EtatIntervention etat) {
		this.etat = etat;
	}

}
