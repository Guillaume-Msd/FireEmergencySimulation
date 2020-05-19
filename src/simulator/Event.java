package simulator;

import java.util.HashSet;
import java.util.Set;

import utilities.Coord;

public class Event {
	
	private Set <Coord> localisation;
	private int id;
	private static int idCounter = 0;
	
	public Event(Coord coord) {
		this.id =idCounter++; 
		this.localisation= new HashSet<Coord>();
		this.addCoord(coord);
	}
	
	public int getId() {
		return id;
	}

	public void addCoord(Coord coord) {
		this.localisation.add(coord);
	}
	
	protected Set<Coord> getLocalisation() {
		return localisation;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " : " + getLocalisation();
	}

	public static void main(String[] args) {		
		Fire Feu1 = new Fire(new Coord(0,0), FireType.ClassA, FireIntensity.High);
		System.out.println(Feu1);
		Feu1.attenuation();
		System.out.println(Feu1);

	}

}