package probes.controller;

import java.awt.Point;

import probes.simulation.ProbSimulation;


public class ProbRestController {
	
	
	private ProbSimulation simulation = new ProbSimulation();
	
	public ProbRestController() {
	}
	
	public void newProb(String type,  Point pos) {
		simulation.addProb(type, 10, 0.1, pos, 5); //(type sonde, rate freq, error, localistaion, range)
	}

	public void initRandom(int num) {
		simulation.initProbs(num); //(type sonde, rate freq, error, localistaion, range)
	}

}
