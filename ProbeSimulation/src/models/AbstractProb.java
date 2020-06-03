package models;

import java.awt.Point;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.json.simple.JSONObject;

import simulation.tools.GetFromFireServ;
import simulation.tools.ProbMeasureInterface;
import simulation.tools.ProbServerInterface;
import simulation.tools.Tools;
import simulation.tools.TypeSonde;



public abstract class AbstractProb implements ProbMeasureInterface,  ProbServerInterface {
	protected TypeSonde type;
	private int rate;
	private int ratecount;
	private double error;
	private Point localisation;
	protected int range;
	private AlerteSignal alerte;
	

//CONSTRUCTORS
	public AbstractProb() {
		
	}
	
	public AbstractProb(TypeSonde type, int rate, double error,Point localisation, int range) {
		this.type = type;
		this.rate = rate;
		this.error = error;
		this.localisation = localisation;
		this.range = range;
		this.alerte = new AlerteSignal(0,this.type.toString(),"Nouvelle Alerte");
	}
	

//METHODS
	//Recupere la liste des feux (coordonnées)
	public List<Point> collectData() throws IOException {
		return GetFromFireServ.fireList();
	}
	
	//Verifie si un feu est dans le rayon d'action d'une sonde, applique l'erreur, envoie l'information
	public void getInformation() throws IOException {
		 List<Point> listFeux = new ArrayList<Point>();
	     listFeux = this.collectData();
	     
		int previousIntensity = this.alerte.getIntensity();	//on recupere l'intensité mesurée précédement
		this.alerte.resetIntensity();	//on reset l'intensité
		
	    for (Point feu: listFeux) {
	    	if ( (Math.abs(feu.x - this.localisation.x) < this.range) &&
	    			(Math.abs(feu.y - this.localisation.y) < this.range) ) {

	    		if (this.applyErrors() == true) {
	    			this.alerte.setIntensity(1);		//on recalcul l'intensité
	            }
	        }
	    }
	    if (this.alerte.getIntensity() > previousIntensity) {
	    	this.triggerAlarm();	//si il y a eu aggravation de l'etat du feu
	    }
	}
	

	//Applique une erreur sur la detection du feu
	public boolean applyErrors() {
		/*
		double erreur = Math.random(); // on genere un nombre entre 0 et 1
		System.out.print(this.error*erreur + "\n");
		if (erreur * this.error < 0.07) { // on multiplie l'erreur aleatoire par l'error de la sonde (qui sera aussi compris entre 0 et 1)
			return true;				// si l'erreur finle (produit des deux erreur) est inferieur a 20%
		}
		return false;
		*/
		return true;
	}
	
	
	//envoie un signal d'alarme a emergencyService et serverCloud
	public void triggerAlarm() throws IOException {
		System.out.print("feu au coord :");
		System.out.print(this.alerte + "\n");
		
		this.sendInformation();
		//this.sendMeasures();
	}
	
	//Envoi l'information vers EmergencyService 
	public void sendInformation() throws IOException {
		System.out.println(Tools.toJsonString(this.alerte));
		URL url = new URL("http://localhost:8082/EmergencyWebService/addAlert/" + this.localisation.x + "/" + this.localisation.y + "/" + this.range); 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
		connection.setRequestMethod("POST"); 
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); 
		connection.setDoOutput(true); 
		
		OutputStream os = connection.getOutputStream(); 
		OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8"); 
		osw.write(Tools.toJsonString(this.alerte)); 
		osw.flush(); 
		osw.close();
		
		connection.getInputStream();


	}
	
	//Envoi l'information au CloudServer
	public void sendMeasures() throws IOException {
		JSONObject obj = new JSONObject();
		
		obj.put("type", this.type.toString());
		obj.put("rate", this.rate);
		obj.put("error", this.error);
		obj.put("localisation", this.localisation);
		obj.put("range", this.range);
		obj.put("intensity", this.alerte.getIntensity());
		String jsonString =obj.toJSONString();

		System.out.print(obj);
			   
		
		URL url = new URL("http://localhost:8084/EmergencyWebService/add/"); 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
		connection.setRequestMethod("POST"); 
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); 
		connection.setDoOutput(true); 
		
		OutputStream os = connection.getOutputStream(); 
		OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8"); 
		osw.write(jsonString); 
		osw.flush(); 
		osw.close();
		connection.getInputStream();
	}
	

//SETTERS, GETTERS AND ToString   
	public String toString() {
		return "[" + this.type + 
				" | rate:" + this.rate + 
				" | err:" + this.error + 
				" | loc:" + this.localisation.x + "," + this.localisation.y + "]" + 
				" | range:" + this.range +  "\n";
	}
	
	public int getRate() {
		return this.rate;
	}
	
	public int getRateCount() {
		return this.ratecount;
	}
	
	public void setRateCount(int nrate) {
		this.ratecount = nrate;
	}

}
