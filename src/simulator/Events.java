package simulator;

import java.util.HashSet;
import java.util.List;

import utilities.Coord;

public class Events implements EventInterface {

	private List<Event> EventList;

	public void createEvent(HashSet <Coord> localisation) {
		Event event = new Event(localisation);
		this.EventList.add(event);
	}
	
	public void deleteEvent(Event event) {
		  event = null;
		  this.EventList.remove(event);
	}
	
	public void updateEvent(Event event) {
	
	}
	
	@Override
	public void sendEvents() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void getEvents() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {		
		Fire Feu1 = new Fire(new HashSet<Coord>(), FireType.ClassA, FireIntensity.Medium);
		System.out.println(Feu1);
		Feu1.attenuation();
		System.out.println(Feu1);
	}

}