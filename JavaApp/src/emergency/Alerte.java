package emergency;


public class Alerte {
	private int id;
	private Coord coord;
	private int valeur;
	private String type;
	private String etat;
	
	public Alerte(int id,Coord coord, Integer valeur, String type) {
		this.setId(id);
		this.setCoord(coord);
		this.setValeur(valeur);
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
	public void setCoord(Coord coord_alerte) {
		this.coord = coord_alerte;
	}
	public int getValeur() {
		return valeur;
	}
	public void setValeur(int valeur_detectee) {
		this.valeur = valeur_detectee;
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
