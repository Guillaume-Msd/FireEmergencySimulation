package models;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

import simulation.tools.TypeSonde;


public class CO2Prob extends AbstractProb {
	
	public CO2Prob() {
		super();
	}

	public CO2Prob (int rate, double error,Point localisation, int range) {
		super(TypeSonde.CO2, rate, error, localisation, range);
	}


}
