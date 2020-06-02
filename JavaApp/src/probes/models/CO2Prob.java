package probes.models;

import java.awt.Point;
import java.io.IOException;

import probes.simulation.tools.TypeSonde;


public class CO2Prob extends AbstractProb {
	
	public CO2Prob() {
		super();
	}

	public CO2Prob (float rate, double error,Point localisation, float range) {
		super(TypeSonde.CO2, rate, error, localisation, range);
	}

	public void triggerAlarm() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
