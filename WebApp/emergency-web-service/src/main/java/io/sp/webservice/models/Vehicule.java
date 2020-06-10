package io.sp.webservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.sp.webservice.models.Coord;

@Entity
public class Vehicule {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column
	private Coord coord;
	
	@Column
	private String type;
	
	@Column
	private EnumStatut statut;
	
	@Column
	private int range;
	
	@Column
	private double quantiteEau;
	
	@Column 
	private Coord destination;
	
	@Column
	private int destinationX;
	
	@Column
	private int destinationY;

	public Vehicule() {
	}
	
	public Vehicule(String type) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public EnumStatut getStatut() {
		return statut;
	}

	public void setStatut(EnumStatut statut) {
		this.statut = statut;
	}
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public double getQuantiteEau() {
		return quantiteEau;
	}

	public void setQuantiteEau(double quantiteEau) {
		this.quantiteEau = quantiteEau;
	}
	

	public Coord getDestination() {
		return destination;
	}

	public void setDestination(Coord destination) {
		this.destination = destination;
	}

	public int getDestinationX() {
		return destinationX;
	}

	public void setDestinationX(int destinationX) {
		this.destinationX = destinationX;
	}

	public int getDestinationY() {
		return destinationY;
	}

	public void setDestinationY(int destinationY) {
		this.destinationY = destinationY;
	}

	public void convertDestinationToCoord() {
		this.destination = new Coord(this.destinationX, this.destinationY);
		
	}
	
	


	

	
}
