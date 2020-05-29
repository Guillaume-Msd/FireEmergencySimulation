package emergency;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Alerte {
	private int id;
	private Coord coord_alerte;
	private int valeur_detectee;
	private String type;
	private String etat;
	
	public Alerte() {
		
	}
	
	//public Alerte(@JsonProperty("id")int id, @JsonProperty("coord_alerte")Coord coord, @JsonProperty("valeur_detectee")Integer valeur, @JsonProperty("type")String type, @JsonProperty("etat")String etat) {
	public Alerte(int id, Coord coord_alerte, int valeur_detectee, String type, String etat) {	
		this.setId(id);
		this.setCoord_alerte(coord_alerte);
		this.setValeur_detectee(valeur_detectee);
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
