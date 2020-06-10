package simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import controller.InterventionController;
import controller.EventController;
import model.Coord;
import model.EnumStatut;
import model.Event;
import model.Fire;
import model.FireIntensity;
import model.FireType;
import model.Vehicule;

public class Simulator {
	
	private EventController eventController;
	private InterventionController interventionController;

	
	/**
	 * initialise le simulateur avec les controleurs gerant les elements et les evenements
	 * @throws IOException
	 */
	public Simulator() throws IOException {
		this.eventController = new EventController();
		this.interventionController = new InterventionController();
	}

	/**
	 * Renvoie le temps aleatoire entre la creation de chaque feu en fonction d'une valeur moyenne
	 * @param mean 
	 * @return time
	 */
	public int timelapse(int mean) {
		Random r = new Random();
		int time = mean*1000 + (r.nextInt(2*mean*1000) - 1000*mean)/3;
		return time;
	}
	
	
	/**cr�e un Feu d'une intensit� al�atoire et � des coords al�atoires

	 * @param mapSize
	 * @throws IOException
	 */
	public void newFire(int mapSize) throws IOException {
		
		System.err.println("CREATE FIRE");
		Random r = new Random();
		int x = r.nextInt(mapSize);
		int y = r.nextInt(mapSize);
		
		while(this.isFireLocation(x, y)) {
			x = r.nextInt(mapSize);
			y = r.nextInt(mapSize);
		}
		
		int i = r.nextInt(FireType.listTypes.size());
		FireType type = FireType.listTypes.get(i);
		int pick = new Random().nextInt(FireIntensity.values().length);
		Fire fire = new Fire(new Coord(x, y), type, FireIntensity.values()[pick]);
		this.eventController.createEvent(fire);

	}
	
	/**
	 * verifie que les feux crees sont suffisamment espaces
	 * @param x
	 * @param y
	 * @return
	 * @throws IOException
	 */
	private boolean isFireLocation(int x, int y) throws IOException {
		List<Coord> coordList = eventController.getAllEventCoords();
		for(Coord coord: coordList) {
			if(Math.abs(coord.x - x) < 5 && Math.abs(coord.y - y) < 5) {
				return true;
			}
		}
		return false;
	}

	/**
	 * aggrave et propage le feu
	 * @throws IOException
	 */
	public void aggravateFire() throws IOException {
		Event[] listEvent = this.eventController.getAllEvents();
		for (Event f : listEvent) {
			if (f instanceof Fire) {
				if (SAggrave(((Fire) f).getIntensity())) {
					if(f != null) {
						eventController.updateEvent(f, ((Fire) f).aggravate(), "aggraver");
					}
				}
			}
		}
	}
	
	/**
	 * gere l'intervention des vehicules: 
	 * - attenuation du feux detecte par la sonde
	 * - diminution du liquide utilise par le vehicule au cours de l'intervention
	 * - mise � jour du statut du vehicule apres intervention
	 * @throws IOException
	 */
	public void manageIntervention() throws IOException {
		Event[] events = this.eventController.getAllEvents();
		List<Event> listEvent = new ArrayList<Event>();
		int i;
		for(i = 0; i < events.length; i++) {
			listEvent.add(events[i]);
		}
		
		this.renvoieVehiculeSiBesoin();
		Vehicule[] listVehicules = this.interventionController.getVehiculesEnIntervention();
		for (Vehicule vehicule: listVehicules) {
			boolean vehiculeAEteint = false;
			for (Event event: listEvent) {
			    Iterator <Coord> it = event.getLocalisation().iterator();
			    while(it.hasNext()) {
				    Coord coordEvent = it.next();
					if (coordEvent.isInRange(vehicule.getCoord(), vehicule.getRange())) {
						vehiculeAEteint = true;
						if(this.checkNeedLiquid(vehicule)) {
							this.sendRavitaillement(vehicule);
							
						}
						else {
							if (estEfficace(((Fire) event).getIntensity())) {
								System.err.println("ATTENUATION");
								this.eventController.updateEvent(event, ((Fire) event).attenuate(), "attenuer");
								System.err.println(vehicule.getQuantiteEau());
								vehicule.decreaseWater();
								this.interventionController.updateVehiculeWater(vehicule);
								if((event.getLocalisation().size() <= 1)) {
									this.eventController.deleteEvent(event);
									vehicule.setStatut(EnumStatut.FinDIntervention);
									this.interventionController.updateVehiculeStatut(vehicule);
								}
							}
						}
					
							
						
					}				
				}
		    }
			if(!vehiculeAEteint) {
				vehicule.setStatut(EnumStatut.FinDIntervention);
				this.interventionController.updateVehiculeStatut(vehicule);
			}
			
		}				
	}
	
	/**
	 * verifie le reservoir du vehicule et met a jour son statut s'il est vide
	 * @param vehicule
	 * @param liquidType
	 * @throws IOException
	 */
	public boolean checkNeedLiquid(Vehicule vehicule) throws IOException {
		if(vehicule.getQuantiteEau() < vehicule.getLiquidDecrease()) {
			return true;
		} 
		return false;
		
	}
	
	/**
	 * envoie le vehicule a une bouche d'incendie en changeant son statut
	 * @param vehicule
	 * @throws IOException
	 */
	public void sendRavitaillement(Vehicule vehicule) throws IOException {
		System.err.println("RAVITAILLEMENT");
		vehicule.setStatut(EnumStatut.BesoinRavitaillementEau);
		this.interventionController.updateVehiculeStatut(vehicule);
	}
	
	public boolean estEfficace(FireIntensity intensite) {
		Random r = new Random();
		if (intensite.equals(FireIntensity.VeryHigh) && r.nextInt(100) < 10) {
			return true;
		}
		else if (intensite.equals(FireIntensity.High) && r.nextInt(100) < 40) {
			return true;
		}
		else if (intensite.equals(FireIntensity.Medium) && r.nextInt(100) < 70) {
			return true;
		}
		else if (intensite.equals(FireIntensity.Low) && r.nextInt(100) < 90) {

			return true;
		}
		else {
			return false;
		}
	}
	

	/**
	 * aggravation du feu de façon aleatoire
	 * @param intensite
	 * @return
	 */
	public boolean SAggrave(FireIntensity intensite) {
		Random r = new Random();
		if (intensite.equals(FireIntensity.VeryHigh) && r.nextInt(100) < 30) {
			return true;
		}
		else if (intensite.equals(FireIntensity.High) && r.nextInt(100) < 15) {
			return true;
		}
		else if (intensite.equals(FireIntensity.Medium) && r.nextInt(100) < 10) {
			return true;
		}
		else if (intensite.equals(FireIntensity.Low) && r.nextInt(100) < 5) {
			return true;
		}
		else {
			return false;
		}
	}

	public void renvoieVehiculeSiBesoin() throws IOException {
		Event[] events = this.eventController.getAllEvents();
		List<Event> listEvent = new ArrayList<Event>();
		int i;
		for(i = 0; i < events.length; i++) {
			listEvent.add(events[i]);
		}
		List<Vehicule> listVehicules = this.interventionController.getVehiculesEnRoutePourIntervention();
		for (Vehicule vehicule: listVehicules) {
			boolean feuTrouve = false;
			for (Event event: listEvent) {
			    Iterator <Coord> it = event.getLocalisation().iterator();
			    while(it.hasNext()) {
				    Coord coordEvent = it.next();
					if (coordEvent.isInRange(vehicule.getDestination(), vehicule.getRange())) {
						feuTrouve = true;
					}
			    }
			}
			if (!feuTrouve) {
				vehicule.setStatut(EnumStatut.FinDIntervention);
				this.interventionController.updateVehiculeStatut(vehicule);
			}
		}
		
	}
}




