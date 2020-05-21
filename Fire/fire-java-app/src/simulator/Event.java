package simulator;

import java.util.HashSet;
import java.util.Set;


public class Event {
	
	private Set <Coord> localisation;
	private int id;
	private static int idCounter = 0;
	
	public Event() {
		this.id =idCounter++; 
		this.localisation= new HashSet<Coord>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void addCoord(Coord coord) {
		this.localisation.add(coord);
	}
	
	protected Set<Coord> getLocalisation() {
		return localisation;
	}
	
	protected void setLocalisation(Set<Coord> location) {
		this.localisation = location;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " : " + getLocalisation();
	}



}