package simulation;

import java.io.IOException;
import java.util.TimerTask;

public class CheckInterventionTask extends TimerTask {
	
	private Simulator simulator;
	
	public CheckInterventionTask(Simulator simulator) {
		this.simulator = simulator;
	}
	
	@Override
	public void run() {
		try {
			this.simulator.manageIntervention();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.simulator.renvoieVehiculeSiBesoin();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
