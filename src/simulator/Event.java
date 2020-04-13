package simulator;

import utilities.Coord;

public class Event {

	private Coord localisation;
	
	public Event(Coord localisation) {
		this.localisation = localisation;
	}

	
	
	public int getX() {
		return this.localisation.x;
	}

	public int getY() {
		return this.localisation.y;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " (" + getX() + "," + getY() + ")";
	}

	public static void main(String[] args) {		
		Fire Feu1 = new Fire(new Coord(0,0), FireType.ClassA, FireIntensity.VeryHigh);
		System.out.println(Feu1);
		Feu1.propagation();
		System.out.println(Feu1);

	}

}