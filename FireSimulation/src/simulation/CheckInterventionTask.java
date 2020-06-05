package simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import controller.EventsController;
import model.Coord;
import model.EnumStatut;
import model.Event;
import model.Fire;
import model.InterventionVehicule;

public class CheckInterventionTask extends TimerTask {
	
	private EventsController eventsController;
	
	public CheckInterventionTask(EventsController eventsController) {
		this.eventsController = eventsController;
	}
	
	@Override
	public void run() {
		try {
			Event[] events = this.eventsController.getAllEvents();
			List<Event> listEvent = new ArrayList<Event>();
			int i;
			for(i = 0; i < events.length; i++) {
				listEvent.add(events[i]);
			}
			
			InterventionVehicule[] listVehicules = this.eventsController.getAllVehicules();
			
			for (InterventionVehicule vehicule: listVehicules) {
				for (Event event: listEvent) {
				    Iterator <Coord> it = event.getLocalisation().iterator();
				    while(it.hasNext()) {
					    Coord coordEvent = it.next();
						if (coordEvent.isInRange(vehicule.getCoord(), vehicule.getRange())) {
							this.eventsController.updateEvent(event, ((Fire) event).attenuate(), "attenuer");
							
							if(event.getLocalisation().size() <= 1) {
								System.err.println(event.getLocalisation());
								this.eventsController.deleteEvent(event);
								vehicule.setStatut(EnumStatut.FinDIntervention);
								this.eventsController.updateVehiculeStatut(vehicule);
							}
						}
							
							
					}
					    
			    }
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
