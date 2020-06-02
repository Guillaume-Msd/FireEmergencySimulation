package simulation;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

import controller.EventsController;

public class CreationTask extends TimerTask {
	
	private int mapSize;
	private EventsController eventsController;
	
	public CreationTask(int mapSize, EventsController eventsController) {
		this.mapSize = mapSize;
		this.eventsController = eventsController;
	}
	
	@Override
	public void run() {
		try {
		    System.out.println(new Date() + " Creation");
			Simulator.creationCycle(this.mapSize, this.eventsController);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
