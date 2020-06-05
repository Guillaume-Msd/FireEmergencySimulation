package controller;

import java.awt.Point;
import java.io.IOException;

import simulation.ProbSimulation;


public class ProbRestController {
	
	
	private ProbSimulation simulation = new ProbSimulation();
	
	public ProbRestController() {
	}
	
	public void newProb(String type,  Point pos) throws IOException {
		simulation.addProb(1, type, 10, 0.1, pos, 5); //(type sonde, rate freq, error, localistaion, range)
	}

	public void initRandom(int num) throws IOException {
		simulation.initProbs(num); //(type sonde, rate freq, error, localistaion, range)
	}

}
