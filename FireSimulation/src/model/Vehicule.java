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
	
	private int LiquidDecrease = 100;
	
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


	/**
	 * diminue la quantité de liquide du véhicule pour le type correspondant 
	 * @param liquidType
	 * @throws IOException 
	 */
	public void decreaseLiquid() throws IOException {

		if (this.getQuantiteEau() > 0) {
			this.setQuantiteEau(this.getQuantiteEau() - this.LiquidDecrease);
			this.updateVehiculeWater();
		}
	}

	private void updateVehiculeWater() throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/updateVehiculeWater/"+this.getId());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        System.out.println(Tools.toJsonString(this.getQuantiteEau()));
        osw.write(Tools.toJsonString(this.getQuantiteEau()));
        osw.flush();
        osw.close();
        connection.getInputStream();
		
	}

	public int getLiquidDecrease() {
		return LiquidDecrease;
	}

	public void setLiquidDecrease(int liquidDecrease) {
		LiquidDecrease = liquidDecrease;
	}
	
	

}
