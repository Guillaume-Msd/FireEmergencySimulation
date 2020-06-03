package simulation;

import java.io.IOException;
import java.util.Random;
import java.util.TimerTask;

import controller.EventsController;
import model.Event;
import model.Fire;
import model.FireIntensity;


public class AggravateFireTask extends TimerTask {
	
	private EventsController eventsController;
	
	public AggravateFireTask(EventsController eventsController) {
		this.eventsController = eventsController;
	}
	
	@Override
	public void run() {
		try {
			Event[] listEvent = this.eventsController.getAllEvents();
			Random r = new Random();
			int i = r.nextInt(listEvent.length);
			Fire fire = (Fire) listEvent[i];
			if (fire.getIntensity() != FireIntensity.VeryHigh) {
				this.eventsController.updateEvent(fire, fire.aggravate(), "aggraver");
			}		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
