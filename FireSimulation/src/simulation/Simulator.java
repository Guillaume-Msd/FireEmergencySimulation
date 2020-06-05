package simulation;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;

import javax.lang.model.element.TypeElement;

import controller.SimulationController;
import model.Coord;
import model.EnvironmentElement;
import model.Fire;
import model.FireIntensity;
import model.FireType;

public class Simulator {
	
	private int mapSize;
	private int difficulty;
	private int sensitivity;
	
	public Simulator(int mapSize, int sensitivity) {
		
		this.mapSize = mapSize;
		this.sensitivity = sensitivity;
		double difficulty = 1; 	//float entre 0 et 1 rentré par l'utilisateur (requête pour récupérer)
		
		//générés à l'avenir par la méthode timelapse()
		int creationInterval = 10000;
		int updateInterval = 20000;
		
		SimulationController simulationController = new SimulationController();
		Timer timer = new Timer();
	    //timer.schedule(new CreateFireTask(this.mapSize, simulationController), 3000, creationInterval);
	    //timer.schedule(new AggravateFireTask(simulationController), 5000, updateInterval);
	    timer.schedule(new CheckInterventionTask(simulationController), 1000, 1000);
	    
	    EnvironmentElement element = new EnvironmentElement(new Coord(50,50), 100, TypeElement.BoucheIncendie);
	    
	}
	
	/**
	 * Renvoie le temps entre la création de chaque feu en fonction d'un coefficient de sensibilité
	 * fixe et de la difficulté choisie par l'utilisateur
	 * @param sensisitivity x
	 * @param difficulty y
	 * @return time
	 */
	public static long timelapse(int s, int d) {
		Random r = new Random();
		long time = (long) (1000*s/d + 0.5*(r.nextInt(1000*s*(int) ((2/d))) - 1000*s/d));
		return time;
	}
	
	
	//création d'un Feu d'une intensité aléatoire et à des coords aléatoires
	public static Fire newFire(int mapSize) {
		Random r = new Random();
		int x = r.nextInt(mapSize);
		int y = r.nextInt(mapSize);
		int i = r.nextInt(FireType.listTypes.size());
		FireType type = FireType.listTypes.get(i);
		return new Fire(new Coord(x, y), type, FireIntensity.Low);
	}

}
