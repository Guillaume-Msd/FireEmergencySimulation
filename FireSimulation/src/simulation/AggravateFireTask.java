package simulation;

import java.io.IOException;
import java.util.TimerTask;


public class AggravateFireTask extends TimerTask {
	
	private Simulator simulator;
	
	public AggravateFireTask(Simulator simulator) {
		this.simulator = simulator;
	}
	
	@Override
	public void run() {
		try {
			this.simulator.aggravateFire();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
