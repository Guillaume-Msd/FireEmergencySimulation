package model;

public class InterventionVehicule {
	
	private int id;

	private Coord coord;
	
	private String type;
	
	private EnumStatut statut;

	private int range;
	
	public InterventionVehicule() {
	}
	
	public InterventionVehicule(String type) {
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


}
