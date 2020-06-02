package simulation;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

import controller.EventsController;


public class UpdateTask extends TimerTask {
	
	private EventsController eventsController;
	
	public UpdateTask(EventsController eventsController) {
		this.eventsController = eventsController;
	}
	
	@Override
	public void run() {
		try {
		    System.out.println(new Date() + " Update");
			Simulator.updateCycle(this.eventsController);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
