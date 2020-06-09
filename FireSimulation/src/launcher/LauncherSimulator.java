package launcher;

import java.io.IOException;
import java.util.Timer;
import simulation.AggravateFireTask;
import simulation.CheckInterventionTask;
import simulation.CreateFireTask;
import simulation.Simulator;

public class LauncherSimulator {

	public static void main(String[] args) throws IOException {
		
		int mapSize = 256;
		Simulator simulator = new Simulator();
		int creationInterval = simulator.timelapse(75);
		int updateInterval = simulator.timelapse(45);
		Timer timer = new Timer();
		
		//cycle de création des feux
	    //timer.schedule(new CreateFireTask(mapSize, simulator), 3000, creationInterval);
	    timer.schedule(new AggravateFireTask(simulator), 5000, updateInterval);
	    //cycle vérifiant la présence de véhicule toutes les secondes
	    timer.schedule(new CheckInterventionTask(simulator), 0, 1000);
		
	}

}
