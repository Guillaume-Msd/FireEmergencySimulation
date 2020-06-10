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
		int creationInterval = simulator.timelapse(10);
		int updateInterval = simulator.timelapse(10);
		Timer timer = new Timer();
		
	    //timer.schedule(new CreateFireTask(mapSize, simulator), 0, 20000);
	    timer.schedule(new AggravateFireTask(simulator), 0, 5000);
	    //cycle vérifiant la présence de véhicule toutes les secondes
	    timer.schedule(new CheckInterventionTask(simulator), 0, 1000);
		
	}



}
