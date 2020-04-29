package simulator;

import java.util.HashSet;

import utilities.Coord;

public class Event {

	private HashSet <Coord> localisation;
	
	public Event(HashSet <Coord> localisation) {
		this.localisation=localisation;
		
	}
	
	protected HashSet<Coord> getLocalisation() {
		return localisation;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " : " + getLocalisation();
	}

	public static void main(String[] args) {		
		Fire Feu1 = new Fire(new HashSet<Coord>(), FireType.ClassA, FireIntensity.Medium);
		System.out.println(Feu1);
		Feu1.attenuation();
		System.out.println(Feu1);
	}

}