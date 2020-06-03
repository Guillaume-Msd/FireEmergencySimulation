package simulation;

import java.io.IOException;
import java.util.Iterator;
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
			Event[] listEvent = this.eventsController.getAllEvents();
			InterventionVehicule[] listVehicules = this.eventsController.getAllVehicules();
			for (Event event: listEvent) {
			    Iterator <Coord> it = event.getLocalisation().iterator();
			    while(it.hasNext()) {
				    Coord coordEvent = it.next();
				    System.out.println(coordEvent);
				    for (InterventionVehicule vehicule: listVehicules) {
						if (coordEvent == vehicule.getCoord()) {
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
