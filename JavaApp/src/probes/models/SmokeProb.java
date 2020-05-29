package probes.models;

import java.awt.Point;

import probes.simulation.tools.TypeSonde;


public class SmokeProb extends AbstractProb {
	public SmokeProb() {
		super();
	}

	public SmokeProb (float rate, double error,Point localisation, float range) {
		super(TypeSonde.Smoke, rate, error, localisation, range);
	}
}
