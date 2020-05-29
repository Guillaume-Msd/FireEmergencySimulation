package probes.models;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.simple.JSONObject;

import probes.simulation.tools.GetFromFireServ;
import probes.simulation.tools.ProbMeasureInterface;
import probes.simulation.tools.ProbServerInterface;
import probes.simulation.tools.TypeSonde;
import tools.JsonTools;


public abstract class AbstractProb implements ProbMeasureInterface,  ProbServerInterface {
	protected TypeSonde type;
	protected float rate;
	protected float ratecount;
	protected double error;
	protected Point localisation;
	protected float range;
	

//CONSTRUCTORS
	public AbstractProb() {
		
	}
	
	public AbstractProb(TypeSonde type, float rate, double error,Point localisation, float range) {
		this.type = type;
		this.rate = rate;
		this.error = error;
		this.localisation = localisation;
		this.range = range;
	}
	

//METHODS
	public void triggerAlarm() {
		//envoie l'info a emergency
		System.out.print(JsonTools.toJson(this.type +"/"+ this.localisation + "\n"));
	}
		
	public void sendInformation(Point feu) {
		System.out.print("feu au coord :");
		System.out.print(feu + "\n");
		this.triggerAlarm();
		//-> this.sendMeasures()
	}
		
	public void getInformation() throws IOException {
		 List<Point> listFeux = new ArrayList<Point>();
	        listFeux = this.collectData();

	        for (Point feu: listFeux) {
	            if ( (Math.abs(feu.x - this.localisation.x) < this.range) &&
	                (Math.abs(feu.y - this.localisation.y) < this.range) ) {

	                    if (this.applyErrors() == true) {
	                        this.sendInformation(feu);
	                    }
	            }
	        }
	}
		
	public List<Point> collectData() throws IOException {
		return GetFromFireServ.fireList();
	}
		
	public boolean applyErrors() {
		double erreur = Math.random(); // on genere un nombre entre 0 et 1
		System.out.print(this.error*erreur + "\n");
		if (erreur * this.error < 0.07) { // on multiplie l'erreur aleatoire par l'error de la sonde (qui sera aussi compris entre 0 et 1)
			return true;				// si l'erreur finle (produit des deux erreur) est inferieur a 20%
		}
		return false;
	}
		
	public void sendMeasures() {
		//envoie l'info au cloud analyser
	}
	
	public String toString() {
		return "[" + this.type + 
				" | rate:" + this.rate + 
				" | err:" + this.error + 
				" | loc:" + this.localisation.x + "," + this.localisation.y + "]" + 
				" | range:" + this.range +  "\n";
	}
	

//SETTERS AND GETTERS
	public float getRate() {
		return this.rate;
	}
	
	public float getRateCount() {
		return this.ratecount;
	}
	
	public void setRateCount(float nrate) {
		this.ratecount = nrate;
	}

}