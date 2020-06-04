package simulation;

import java.awt.Point;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import models.*;




public class ProbSimulation {
//PARAMETERS
	private List<AbstractProb> probList = new ArrayList<AbstractProb>(); //liste des sondes
	
	
//CONSTRUCTOR
	public ProbSimulation (){
	}

	
//MAIN
	public static void main(String[] args) throws InterruptedException, IOException {
		final ProbSimulation simulation = new ProbSimulation();
		simulation.initProbs(0);

		new Timer().scheduleAtFixedRate(new TimerTask(){
		    @Override
		    public void run(){
				for (AbstractProb prob: simulation.probList) {
					if (prob.getRateCount() == 0) {
						System.out.print(prob.toString());
						System.out.print("MEASURING : \n");
						try {
							prob.getInformation();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						prob.setRateCount(prob.getRate());
					}
					else {
						prob.setRateCount(prob.getRateCount()-1);
						System.out.print(prob.toString());
						System.out.print(prob.getRateCount() + "\n");
					}
				}
		    }
		}	,0,2000);
			
	}
	
	
//METHODS
	//initialise des probs (a randomiser)
	public void initProbs(int num) throws IOException {
		URL url = new URL("http://localhost:8081/ProbeWebService/removeAll"); 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
		connection.setRequestMethod("DELETE"); 
		connection.setDoOutput(false); 
		connection.getInputStream();
		
		
		this.addProb("Smoke", 1, 0.1, new Point(80,80), 10);
		this.addProb("Thermic", 1, 0.1, new Point(190, 190), 10);
		this.addProb("Smoke", 1, 0.1, new Point(80,190), 10);
		this.addProb("CO2", 1, 0.1, new Point(190,80), 10);
		this.addProb("CO2", 1, 0.1, new Point(160 ,160), 10);
		
		connection.getInputStream();
	
		
		for (AbstractProb prob: this.probList) {
			prob.setRateCount(prob.getRate());
		}
	}
	
	//ajoute une probe
	public void addProb(String type, int rate, double error,Point localisation, int range) throws IOException {
		if (type == "Smoke") {
			this.probList.add(new SmokeProb(rate, error, localisation, range));
		}
		if (type == "CO2") {
			this.probList.add(new CO2Prob(rate, error, localisation, range));
		}
		if (type == "Thermic") {
			this.probList.add(new ThermicProb(rate, error, localisation, range));
		}
		this.addProbToMap(type, localisation, range);
	}	
	
	public void addProbToMap(String type, Point localisation, int range) throws IOException {
		//envoie la position de la sonde a Simulation service
		URL url = new URL("http://localhost:8081/ProbeWebService/add/" + type.toString() + "/" + range + "/" + localisation.x + "/" + localisation.y); 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
		connection.setRequestMethod("GET"); 
		connection.setDoOutput(false); 
		connection.getInputStream();
	}
	
	
}



