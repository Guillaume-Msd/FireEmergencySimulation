package probes.models;

import java.awt.Point;

import probes.simulation.tools.TypeSonde;


public class ThermicProb extends AbstractProb {
	public ThermicProb() {
		super();
	}

	public ThermicProb (float rate, double error,Point localisation, float range) {
		super(TypeSonde.Thermic, rate, error, localisation, range);
	}


}
