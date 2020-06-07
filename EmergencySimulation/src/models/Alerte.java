package models;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Alerte {
	private int id;
	private Coord coord;
	private int intensity;
	private String type;
	private String etat;
	private int range;
	


	public Alerte() {
		
	}
	
	//public Alerte(@JsonProperty("id")int id, @JsonProperty("coord_alerte")Coord coord, @JsonProperty("valeur_detectee")Integer valeur, @JsonProperty("type")String type, @JsonProperty("etat")String etat) {
	public Alerte(int id, Coord coord, int intensity, String type, String etat) {	
		this.setId(id);
		this.setCoord(coord);
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

	public void setIntensity(int intensity) {
		this.intensity = intensity;
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
		return "Alerte "+this.id+": "+this.coord+", Valeur: "+this.intensity+", Type: "+this.type+", Etat: "+this.etat;
	}
	
	/**
	 * Supprime l'alerte du serveur
	 * @throws IOException
	 */
	public void delete() throws IOException {
		URL url = new URL("http://localhost:8082/EmergencyWebService/deleteAlert/"+this.getId());
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("DELETE");
	    connection.getInputStream();
		
	}
}
