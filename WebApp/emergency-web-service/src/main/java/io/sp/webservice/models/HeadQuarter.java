package io.sp.webservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HeadQuarter {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column
	private Coord coord;
	
	@Column
	private int nb_vehicules;
	


	public HeadQuarter() {
	}
	
	public HeadQuarter(Coord coord) {
		this.setCoord(coord);
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

	public int getNb_vehicules() {
		return nb_vehicules;
	}

	public void setNb_vehicules(int nb_vehicules) {
		this.nb_vehicules = nb_vehicules;
	}	
	
	

}
