package simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import model.TypeElement;

import controller.InterventionController;
import controller.EventController;
import model.Coord;
import model.Element;
import model.EnumStatut;
import model.Event;
import model.Fire;
import model.FireIntensity;
import model.FireType;
import model.LiquidEnum;
import model.Vehicule;

public class Simulator {
	
	private EventController simulationController;
	private InterventionController interventionController;

	
	public Simulator() throws IOException {
		this.simulationController = new EventController();
		this.interventionController = new InterventionController();
		this.initEnvironment();
	}
	
	private void initEnvironment() throws IOException {
	    Element element = new Element(new Coord(50,50), 100, TypeElement.BoucheIncendie);
	    this.interventionController.addElement(element);
	}

	/**
	 * Renvoie le temps entre la création de chaque feu en fonction d'un coefficient de sensibilité
	 * fixe et de la difficulté choisie par l'utilisateur
	 * @param sensisitivity x
	 * @param difficulty y
	 * @return time
	 */
	public long timelapse(int s, int d) {
		Random r = new Random();
		long time = (long) (1000*s/d + 0.5*(r.nextInt(1000*s*(int) ((2/d))) - 1000*s/d));
		return time;
	}
	
	
	//création d'un Feu d'une intensité aléatoire et à des coords aléatoires
	public void newFire(int mapSize) throws IOException {
		Random r = new Random();
		int x = r.nextInt(mapSize);
		int y = r.nextInt(mapSize);
		int i = r.nextInt(FireType.listTypes.size());
		FireType type = FireType.listTypes.get(i);
		Fire fire = new Fire(new Coord(x, y), type, FireIntensity.Low);
		this.simulationController.createEvent(fire);
	}
	
	public void aggravateFire() throws IOException {
		Event[] listEvent = this.simulationController.getAllEvents();
		Random r = new Random();
		int i = r.nextInt(listEvent.length);
		Fire fire = (Fire) listEvent[i];
		if (fire.getIntensity() != FireIntensity.VeryHigh) {
			this.simulationController.updateEvent(fire, fire.aggravate(), "aggraver");
		}
	}
	
	public void manageIntervention() throws IOException {
		Event[] events = this.simulationController.getAllEvents();
		List<Event> listEvent = new ArrayList<Event>();
		int i;
		for(i = 0; i < events.length; i++) {
			listEvent.add(events[i]);
		}
		Vehicule[] listVehicules = this.interventionController.getVehicules();
		for (Vehicule vehicule: listVehicules) {
			this.checkVehiculeAtElement(vehicule);
			for (Event event: listEvent) {
			    Iterator <Coord> it = event.getLocalisation().iterator();
			    while(it.hasNext()) {
				    Coord coordEvent = it.next();
					if (coordEvent.isInRange(vehicule.getCoord(), vehicule.getRange())) {
						this.simulationController.updateEvent(event, ((Fire) event).attenuate(), "attenuer");
						vehicule.decreaseLiquid(LiquidEnum.Eau);
						this.checkLiquidQuantity(vehicule,LiquidEnum.Eau);
						
						if(event.getLocalisation().size() <= 1) {
							System.err.println(event.getLocalisation());
							this.simulationController.deleteEvent(event);
							vehicule.setStatut(EnumStatut.FinDIntervention);
							this.interventionController.updateVehiculeStatut(vehicule);
						}
					}				
				}
		    }
		}				
	}
	
	public void checkLiquidQuantity(Vehicule vehicule, LiquidEnum liquidType) throws IOException {
		if (vehicule.getQuantity(liquidType) == 0){
			vehicule.setStatut(EnumStatut.AuRavitaillement);
			this.interventionController.updateVehiculeStatut(vehicule);
			Element[] elements = interventionController.getAllElements();
			Coord coord = assignElement(elements, vehicule);
			this.interventionController.sendElementCoord(coord);
		}
	}


	private Coord assignElement(Element[] elements, Vehicule vehicule) throws IOException {
		List<Coord> coordElements = new ArrayList<Coord>();
		for (int i = 0; i < elements.length; i++) {
			coordElements.add(elements[i].getLocation());
		}
		return vehicule.getCoord().findClosestCoord(coordElements);
	}
	
	public void checkVehiculeAtElement(Vehicule vehicule) throws IOException {
		Element[] elements = this.interventionController.getAllElements();
		for (int i = 0; i < elements.length; i++) {
			if (elements[i].getLocation() == vehicule.getCoord()) {
				vehicule.restoreLiquid(LiquidEnum.Eau);
				elements[i].decreaseQuantity(vehicule.getCapacity(LiquidEnum.Eau));
			}
		}
	}

}
