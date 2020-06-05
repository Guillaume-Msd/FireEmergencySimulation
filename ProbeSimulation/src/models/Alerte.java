package models;


public class Alerte {

	private int id;
	
	private CoordEntity coord;
	
	private int intensity;
	
	private String type;
	
	private String etat;
	
	private int range;


	public Alerte() {
		
	}
	
	public Alerte(CoordEntity coord, Integer valeur, String type) {
		this.setCoord(coord);
		this.setIntensity(valeur);
		this.setType(type);
	}
	
	public Alerte(Integer intensity, String type, String etat) {
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
	public CoordEntity getCoord() {
		return coord;
	}
	public void setCoord(CoordEntity coord) {
		this.coord = coord;
	}
	public int getIntensity() {
		return intensity;
	}
	public void setIntensity(int valeur) {
		this.intensity = valeur;
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
		return "Alerte "+this.id+": "+this.coord+", Valeur: "+this.intensity+", Type: "+this.type;
	}
}
