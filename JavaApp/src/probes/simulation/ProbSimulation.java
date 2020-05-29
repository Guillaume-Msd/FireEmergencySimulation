package probes.simulation;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import probes.models.*;



public class ProbSimulation {
//PARAMETERS
	private List<AbstractProb> probList = new ArrayList<AbstractProb>(); //liste des sondes
	
	
//CONSTRUCTOR
	public ProbSimulation (){
	}

	
//MAIN
	public static void main(String[] args) throws InterruptedException, IOException {
		ProbSimulation simulation = new ProbSimulation();
		simulation.initProbs(0);

		for (int i=0; i<10; i++) {
			System.out.print("\n" + "TIMER : " + i + "\n");
			for (AbstractProb prob: simulation.probList) {
				if (prob.getRateCount() == 0) {
					System.out.print(prob.toString());
					System.out.print("MEASURING : \n");
					prob.getInformation();
					prob.setRateCount(prob.getRate());
				}
				else {
					prob.setRateCount(prob.getRateCount()-1);
					System.out.print(prob.toString());
					System.out.print(prob.getRateCount() + "\n");
				}
			}
			TimeUnit.SECONDS.sleep(1);
		}	
	}
	
	
//METHODS
	//initialise des probs (a randomiser)
	public void initProbs(int num) {
		this.addProb("Smoke", 1, 0.1, new Point(11,11), 1);
		this.addProb("Thermic", 20, 0.1, new Point(5,20), 3);
		this.addProb("Smoke", 1, 0.1, new Point(30,30), 1);
		
		for (AbstractProb prob: this.probList) {
			prob.setRateCount(prob.getRate());
		}
	}
	
	//ajoute une probe
	public void addProb(String type, float rate, double error,Point localisation, float range) {
		if (type == "Smoke") {
			this.probList.add(new SmokeProb(rate, error, localisation, range));
		}
		if (type == "CO2") {
			this.probList.add(new CO2Prob(rate, error, localisation, range));
		}
		if (type == "Thermic") {
			this.probList.add(new ThermicProb(rate, error, localisation, range));
		}
	}	
}


