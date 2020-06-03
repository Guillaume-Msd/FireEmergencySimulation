package simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import controller.EventsController;
import model.Coord;
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
			for (Event event: listEvent) {
			    Iterator <Coord> it = event.getLocalisation().iterator();
			    while(it.hasNext()) {
				    Coord coordEvent = it.next();
				    for (InterventionVehicule vehicule: listVehicules) {
						if (coordEvent.x < (vehicule.getCoord().x  + 10 ) && coordEvent.y < (vehicule.getCoord().y  + 10 )) {
							this.eventsController.updateEvent(event, ((Fire) event).attenuate(), "attenuer");
							this.eventsController.updateVehiculeStatut(vehicule);
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
