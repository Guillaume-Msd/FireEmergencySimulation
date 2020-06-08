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
		int sensitivity = 100;;
		double difficulty = 1;
		//générés à l'avenir par la méthode timelapse()
		int creationInterval = 10000;
		int updateInterval = 10000;
		
		Simulator simulator = new Simulator();
		Timer timer = new Timer();
		
		//cycle de création des feux
	    //timer.schedule(new CreateFireTask(mapSize, simulator), 3000, creationInterval);
	    timer.schedule(new AggravateFireTask(simulator), 5000, updateInterval);
	    //cycle vérifiant la présence de véhicule toutes les secondes
	    timer.schedule(new CheckInterventionTask(simulator), 0, 1000);
		
	}

}
