package models;

public class FireFighterHQ extends AbstractHeadquarter {
	
	public FireFighterHQ() {
		
	}
	
	public FireFighterHQ(Coord coord) {
		super(coord);
	}
	
	public FireFighterHQ(Coord coord,int nb_vehicules) {
		super(coord);
		this.setNb_vehicules(nb_vehicules);
	}
}