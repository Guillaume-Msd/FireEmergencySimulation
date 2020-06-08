package simulation;

import java.io.IOException;
import java.util.TimerTask;

public class CreateFireTask extends TimerTask {
	
	private int mapSize;
	private Simulator simulator;
	
	public CreateFireTask(int mapSize, Simulator simulator) {
		this.mapSize = mapSize;
		this.simulator = simulator;
	}
	
	@Override
	public void run() {
		try {
			this.simulator.newFire(this.mapSize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
