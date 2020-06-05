package simulation;

import java.io.IOException;
import java.util.TimerTask;

import controller.SimulationController;
import model.Fire;

public class CreateFireTask extends TimerTask {
	
	private SimulationController simulationController;
	private int mapSize;
	
	public CreateFireTask(int mapSize, SimulationController simulationController) {
		this.mapSize = mapSize;
		this.simulationController = simulationController;
	}
	
	@Override
	public void run() {
		try {
			Fire feu = Simulator.newFire(mapSize);
			this.simulationController.createEvent(feu);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
