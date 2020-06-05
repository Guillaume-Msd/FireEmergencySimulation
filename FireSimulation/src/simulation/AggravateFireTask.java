package simulation;

import java.io.IOException;
import java.util.Random;
import java.util.TimerTask;

import controller.SimulationController;
import model.Event;
import model.Fire;
import model.FireIntensity;


public class AggravateFireTask extends TimerTask {
	
	private SimulationController simulationController;
	
	public AggravateFireTask(SimulationController simulationController) {
		this.simulationController = simulationController;
	}
	
	@Override
	public void run() {
		try {
			Event[] listEvent = this.simulationController.getAllEvents();
			Random r = new Random();
			int i = r.nextInt(listEvent.length);
			Fire fire = (Fire) listEvent[i];
			if (fire.getIntensity() != FireIntensity.VeryHigh) {
				this.simulationController.updateEvent(fire, fire.aggravate(), "aggraver");
			}		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
