package models;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

import simulation.tools.TypeSonde;


public class SmokeProb extends AbstractProb {
	public SmokeProb() {
		super();
	}

	public SmokeProb (int rate, double error, Point localisation, int range) {
		super(TypeSonde.Smoke, rate, error, localisation, range);
	}

}
