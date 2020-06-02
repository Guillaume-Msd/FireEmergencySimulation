package simulation;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

import controller.EventsController;

public class CheckTask extends TimerTask {
	
	private EventsController eventsController;
	
	public CheckTask(EventsController eventsController) {
		this.eventsController = eventsController;
	}
	
	@Override
	public void run() {
		try {
		    System.out.println(new Date() + " Check");
			Simulator.checkCycle(this.eventsController);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
