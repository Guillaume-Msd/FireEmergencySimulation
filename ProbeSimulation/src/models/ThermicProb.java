package models;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

import simulation.tools.TypeSonde;


public class ThermicProb extends AbstractProb {
	public ThermicProb() {
		super();
	}

	public ThermicProb (int rate, double error,Point localisation, int range) {
		super(TypeSonde.Thermic, rate, error, localisation, range);
	}





}
