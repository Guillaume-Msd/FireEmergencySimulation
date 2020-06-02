package simulation;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;

import controller.EventsController;
import model.Coord;
import model.Event;
import model.Fire;
import model.FireIntensity;
import model.FireType;

public class Simulator {
	
	private int mapSize;
	private int difficulty;
	private int sensitivity;
	
	public Simulator(int mapSize, int sensitivity) {
		
		this.mapSize = mapSize;
		this.sensitivity = sensitivity;
		double difficulty = 1; 	//float entre 0 et 1 rentré par l'utilisateur (requête pour récupérer)
		//générés à l'avenir par la méthode timelapse()
		int creationInterval = 5000;
		int updateInterval = 10000;
		
		EventsController eventsController = new EventsController();
		Timer timer = new Timer();
	    timer.schedule(new CreationTask(this.mapSize, eventsController), 3000, creationInterval);
	    timer.schedule(new UpdateTask(eventsController), 5000, updateInterval);
	    //timer.schedule(new CheckTask(eventsController), 500, 1000);
	}
	
	/**
	 * Renvoie le temps entre la création de chaque feu en fonction d'un coefficient de sensibilité
	 * fixe et de la difficulté choisie par l'utilisateur
	 * @param sensisitivity x
	 * @param difficulty y
	 * @return time
	 */
	public static long timelapse(int s, int d) {
		Random r = new Random();
		long time = (long) (1000*s/d + 0.5*(r.nextInt(1000*s*(int) ((2/d))) - 1000*s/d));
		return time;
	}
	
	
	//création d'un Feu d'une intensité aléatoire et à des coords aléatoires
	public static Fire newFire(int mapSize) {
		Random r = new Random();
		int x = r.nextInt(mapSize);
		int y = r.nextInt(mapSize);
		int i = r.nextInt(FireType.listTypes.size());
		FireType type = FireType.listTypes.get(i);
		return new Fire(new Coord(x, y), type, FireIntensity.Low);
	}
	
	
	public static Event compareCoord(Event[] listEvent, Coord coordVehicle) {
		
		for (Event event : listEvent) {
		    Iterator <Coord> it = event.getLocalisation().iterator();
		    while(it.hasNext()) {
			    Coord coordEvent = it.next();
				if (coordEvent == coordVehicle) {
					return event;
				}
		    }
		}
		return null;
		
	}
	
	
	public static void creationCycle(int mapSize, EventsController eventsController) throws IOException {
		Fire feu = newFire(mapSize);
		eventsController.createEvent(feu);
	}
	
	
	public static void updateCycle(EventsController eventsController) throws IOException {
		
		Event[] listEvent = eventsController.getAllEvents();
		Random r = new Random();
		int i = r.nextInt(listEvent.length);
		Fire fire = (Fire) listEvent[i];
		if (fire.getIntensity() != FireIntensity.VeryHigh) {
			eventsController.updateEvent(fire, fire.aggravate(), "aggraver");
		}
		
	}
	
	
	public static void checkCycle(EventsController eventsController) throws IOException {
		
		Event[] listEvent = eventsController.getAllEvents();
		Coord coordVehicle = eventsController.getVehicleCoord();
		Fire fireIntervention = (Fire) compareCoord(listEvent, coordVehicle);
		if (fireIntervention != null) {
			eventsController.updateEvent(fireIntervention, fireIntervention.attenuate(), "attenuer");
		}
		
	}

	
	public static void main(String[] args) throws InterruptedException, IOException {

	}
	
}
