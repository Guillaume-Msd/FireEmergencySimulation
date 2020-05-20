package io.sp.webservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Vehicule {

	@Id
	@GeneratedValue
	private int id;
	
	@Column
	private Coord coord;
	
	@Column
	private typeVehicule type;

	
	public Vehicule() {
	}
	
	public Vehicule(int id,Coord coord,typeVehicule type) {
		this.setId(id);
		this.setCoord(coord);
		this.setType(type);
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

	public typeVehicule getType() {
		return type;
	}

	public void setType(typeVehicule type) {
		this.type = type;
	}
}
