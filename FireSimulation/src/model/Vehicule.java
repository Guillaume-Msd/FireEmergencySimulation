package model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import utilities.Tools;

public class Vehicule {
	
	private int id;

	private Coord coord;
	
	private String type;
	
	private EnumStatut statut;

	private int range;

	private double waterQuantity;
	
	private int waterDecrease = 100;
	
	public Vehicule() {
	}
	
	public Vehicule(String type) {
		this.setType(type);
	}
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
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
	
	public int getX() {
		return this.coord.x;
	}

	public int getY() {
		return this.coord.y;
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

	public double getWaterQuantity() {
		return waterQuantity;
	}

	public void setWaterQuantity(double quantiteEau) {
		this.waterQuantity = quantiteEau;
	}

	public void decreaseWater() throws IOException {

		if (this.getWaterQuantity() > 0) {
			this.setWaterQuantity(this.getWaterQuantity() - this.waterDecrease);
		}
	}

	public int getWaterDecrease() {
		return waterDecrease;
	}

	public void setWaterDecrease(int liquidDecrease) {
		waterDecrease = liquidDecrease;
	}
	
	

}
