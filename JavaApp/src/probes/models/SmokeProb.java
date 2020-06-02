package probes.models;

import java.awt.Point;
import java.io.IOException;

import probes.simulation.tools.TypeSonde;


public class SmokeProb extends AbstractProb {
	public SmokeProb() {
		super();
	}

	public SmokeProb (float rate, double error,Point localisation, float range) {
		super(TypeSonde.Smoke, rate, error, localisation, range);
	}

	public void triggerAlarm() throws IOException {
		// TODO Auto-generated method stub
		
	}
}
