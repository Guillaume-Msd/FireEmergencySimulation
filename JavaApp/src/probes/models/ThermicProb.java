package probes.models;

import java.awt.Point;
import java.io.IOException;

import probes.simulation.tools.TypeSonde;


public class ThermicProb extends AbstractProb {
	public ThermicProb() {
		super();
	}

	public ThermicProb (float rate, double error,Point localisation, float range) {
		super(TypeSonde.Thermic, rate, error, localisation, range);
	}

	public void triggerAlarm() throws IOException {
		// TODO Auto-generated method stub
		
	}


}
