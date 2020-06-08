package simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import controller.InterventionController;
import controller.EventController;
import model.Coord;
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

	
	/**initialise le simulateur avec les controleurs gerant les elements et les evenements
	 * @throws IOException
	 */
	public Simulator() throws IOException {
		this.simulationController = new EventController();
		this.interventionController = new InterventionController();
	}

	/**
	 * Renvoie le temps entre la creation de chaque feu en fonction d'un coefficient de sensibilite
	 * fixe et de la difficulte choisie par l'utilisateur
	 * @param sensisitivity s
	 * @param difficulty d
	 * @return time
	 */
	public long timelapse(int s, int d) {
		Random r = new Random();
		long time = (long) (1000*s/d + 0.5*(r.nextInt(1000*s*(int) ((2/d))) - 1000*s/d));
		return time;
	}
	
	
	/**crée un Feu d'une intensité aléatoire et à des coords aléatoires
	 * @param mapSize
	 * @throws IOException
	 */
	public void newFire(int mapSize) throws IOException {
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
		this.simulationController.createEvent(fire);
	}
	
	private boolean isFireLocation(int x, int y) throws IOException {
		List<Coord> coordList = simulationController.getAllEventCoords();
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
		Event[] listEvent = this.simulationController.getAllEvents();
		Random r = new Random();
		int i = r.nextInt(listEvent.length);
		
	}
	
	/**
	 * gere l'intervention des vehicules: 
	 * - attenuation du feux detecte par la sonde
	 * - diminution du liquide utilise par le vehicule au cours de l'intervention
	 * - mise à jour du statut du vehicule apres intervention
	 * @throws IOException
	 */
	public void manageIntervention() throws IOException {
		Event[] events = this.simulationController.getAllEvents();
		List<Event> listEvent = new ArrayList<Event>();
		int i;
		for(i = 0; i < events.length; i++) {
			listEvent.add(events[i]);
		}
		Vehicule[] listVehicules = this.interventionController.getVehicules();
		for (Vehicule vehicule: listVehicules) {
			boolean vehiculeAEteint = false;
			for (Event event: listEvent) {
			    Iterator <Coord> it = event.getLocalisation().iterator();
			    while(it.hasNext()) {
				    Coord coordEvent = it.next();
					if (coordEvent.isInRange(vehicule.getCoord(), vehicule.getRange())) {
						vehiculeAEteint = true;
						this.simulationController.updateEvent(event, ((Fire) event).attenuate(), "attenuer");
						System.err.println(vehicule.getQuantiteEau());
						vehicule.decreaseLiquid();
						if(this.checkLiquidQuantity(vehicule)) {
							this.sendRavitaillement(vehicule);
						}
						else{
							if((event.getLocalisation().size() <= 1)) {
								this.simulationController.deleteEvent(event);
								vehicule.setStatut(EnumStatut.FinDIntervention);
								this.interventionController.updateVehiculeStatut(vehicule);
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
	public boolean checkLiquidQuantity(Vehicule vehicule) throws IOException {
		if(vehicule.getQuantiteEau() < vehicule.getLiquidDecrease()) {
			return true;
		} 
		return false;
		
	}
	
	public void sendRavitaillement(Vehicule vehicule) throws IOException {
		System.err.println("RAVITAILLEMENT");
		vehicule.setStatut(EnumStatut.BesoinRavitaillementEau);
		this.interventionController.updateVehiculeStatut(vehicule);
	}
}
