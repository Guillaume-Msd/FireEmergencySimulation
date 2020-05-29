package events;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;


public class Simulator {
	
	public static void main(String[] args) throws IOException, InterruptedException {		
		int i;
		Events events = new Events();
		Fire fire;
		for (i = 0; i < 1; i++) {
			Random r = new Random();
			int x = r.nextInt(16);
			int y = r.nextInt(16);
			fire = new Fire(new Coord(x, y), FireType.ClassA, FireIntensity.High);
			events.createEvent(fire);
			Thread.sleep(3000);
		}
		
		Event[] listEvent = events.getAllEvents();
		for(int j = 0; j < listEvent.length; j++) {
			System.out.println(listEvent[j]);

		}
		
		Iterator<Coord> it;
		Event event = listEvent[0];
		System.out.println(event.getId());
		it = event.getLocalisation().iterator();
		events.updateEvent(event, propagate(it.next()), "aggraver");
		
		Thread.sleep(3000);
		
		event = listEvent[0];
		it = event.getLocalisation().iterator();
		events.updateEvent(event, propagate(propagate(it.next())), "aggraver");
		Thread.sleep(3000);
		
		
		event = listEvent[0];
		it = event.getLocalisation().iterator();
		Coord coord = it.next();
		System.out.println(coord);
		events.updateEvent(event, coord, "attenuer");
		
		Thread.sleep(3000);
		
		events.deleteEvent(event);
		 
		

	}
	
	public static Coord propagate(Coord coord) {
		return new Coord(coord.x, coord.y + 1);
	}

}
