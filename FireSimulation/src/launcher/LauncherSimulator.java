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
		int updateInterval = 20000;
		
		Simulator simulator = new Simulator();
		Timer timer = new Timer();
	    //timer.schedule(new CreateFireTask(mapSize, simulator), 3000, creationInterval);
	    //timer.schedule(new AggravateFireTask(simulator), 5000, updateInterval);
	    timer.schedule(new CheckInterventionTask(simulator), 1000, 1000);
		
	}

}
