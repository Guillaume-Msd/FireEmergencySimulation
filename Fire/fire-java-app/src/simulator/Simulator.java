package simulator;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import simulator.controller.EventsController;
import simulator.model.Coord;
import simulator.model.Event;
import simulator.model.Fire;
import simulator.model.FireIntensity;
import simulator.model.FireType;



public class Simulator {
	
	/**
	 * Renvoie le temps entre la création de chaque feu en fonction d'un coefficient de sensibilité
	 * fixe et de la difficulté choisie par l'utilisateur
	 * @param sensisitivity x
	 * @param difficulty y
	 * @return time
	 */
	
	public static int timelapse(int x, double y) {
		Random r = new Random();
		int time = (int)(1000*x/y + 0.5*(r.nextInt(1000*x*(int) ((2/y))) - 1000*x/y));
		return time;
	}
	
	
	public static Fire newFire(int mapSize) {
		Random r = new Random();
		int x = r.nextInt(mapSize);
		int y = r.nextInt(mapSize);
		int t = r.nextInt(FireType.listTypes.size());
		FireType type = FireType.listTypes.get(t);
		//int i = r.nextInt(FireIntensity.listIntensities.size());
		//FireIntensity intensity = FireIntensity.listIntensities.get(i);
		return new Fire(new Coord(x, y), type, FireIntensity.Low);
	}
	
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		int nbFire = 5;
		int mapSize = 16;
		double difficulty = 1; 		//float entre 0 et 1 rentré par l'utilisateur
		int sensitivity = 10; 		//coeff de sensibilité
		int aggravationTime = 3000;
		
		EventsController events = new EventsController();
		for (int i = 0; i < nbFire; i++) {
			Fire feu = newFire(mapSize);
			events.createEvent(feu);
			//Thread.sleep(timelapse(sensitivity, difficulty));
		}
		
		//Timer pour l'aggravation des feux
		Thread.sleep(aggravationTime);

		Event[] listEvent = events.getAllEvents();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < listEvent.length; j++) {
				Fire fire = (Fire) listEvent[j];
				events.updateEvent(fire, fire.propagate(), "aggraver");
			}
			
			Thread.sleep(aggravationTime);
		}
		
		Event event = listEvent[0];
		Iterator <Coord> it = event.getLocalisation().iterator();
		events.updateEvent(event, it.next(), "attenuer");
		events.deleteEvent(event);

	}

}
