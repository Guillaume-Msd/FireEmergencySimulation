package emergency;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Alerte {
	private int id;
	private Coord coord;
	private int valeur;
	private String type;
	private String etat;
	
	public Alerte() {
		
	}
	
	//public Alerte(@JsonProperty("id")int id, @JsonProperty("coord_alerte")Coord coord, @JsonProperty("valeur_detectee")Integer valeur, @JsonProperty("type")String type, @JsonProperty("etat")String etat) {
	public Alerte(int id, Coord coord, int valeur, String type, String etat) {	
		this.setId(id);
		this.setCoord(coord);
		this.setValeur(valeur);
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

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
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
	
	@Override
	public String toString() {
		return "Alerte "+this.id+": "+this.coord+", Valeur: "+this.valeur+", Type: "+this.type+", Etat: "+this.etat;
	}
}
