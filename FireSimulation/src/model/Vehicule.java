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

	private double quantiteEau;
	
	private int LiquidDecrease = 30;
	
	private int destinationX;
	
	private int destinationY;
	
	private Coord destination;

	
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

	

	public double getQuantiteEau() {
		return quantiteEau;
	}

	public void setQuantiteEau(double quantiteEau) {
		this.quantiteEau = quantiteEau;
	}

	public void decreaseWater() throws IOException {

		if (this.getQuantiteEau() > 0) {
			this.setQuantiteEau(this.getQuantiteEau() - this.LiquidDecrease);
		}
	}
	

	public int getLiquidDecrease() {
		return LiquidDecrease;
	}

	public void setLiquidDecrease(int liquidDecrease) {
		LiquidDecrease = liquidDecrease;
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
	
	
	
	
	
	

}
