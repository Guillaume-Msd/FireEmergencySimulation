package simulation;

import java.io.IOException;
import java.util.TimerTask;

import controller.EventsController;
import model.Fire;

public class CreateFireTask extends TimerTask {
	
	private EventsController eventsController;
	private int mapSize;
	
	public CreateFireTask(int mapSize, EventsController eventsController) {
		this.mapSize = mapSize;
		this.eventsController = eventsController;
	}
	
	@Override
	public void run() {
		try {
			Fire feu = Simulator.newFire(mapSize);
			this.eventsController.createEvent(feu);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
