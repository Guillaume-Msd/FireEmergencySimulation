package models;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

//import simulation.tools.GetAlertFromServ;
import simulation.tools.GetFromFireServ;
import simulation.tools.ProbMeasureInterface;
import simulation.tools.ProbServerInterface;
import simulation.tools.Tools;
import simulation.tools.TypeSonde;
import models.Fire;



public class AbstractProb implements ProbMeasureInterface,  ProbServerInterface {
	protected TypeSonde type;
	private int rate;
	private int ratecount;
	private double error;
	private Point localisation;
	protected int range;
	private AlerteSignal alerte;
	private int id;
	private int x;
	private int y;
	private int real_intensity;
	private int previousIntensity = 0;

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
	//Recupere la liste des feux (coordonn�es)
	public List<Fire> collectData() throws IOException {
		return GetFromFireServ.fireList();
	}
	
	//Verifie si un feu est dans le rayon d'action d'une sonde, applique l'erreur, envoie l'information
	public void getInformation() throws IOException {
		List<Fire> listFeux = new ArrayList<Fire>();
	    listFeux = this.collectData();
	   
	    if(this.previousIntensity > this.real_intensity) {
	    	this.previousIntensity = this.real_intensity;
	    }
		
		this.alerte.resetIntensity();	//on reset l'intensite
		this.real_intensity = 0;
		
	    for (Fire feu: listFeux) {		//on recalcul l'intensite
	    	for (CoordEntity coord: feu.getLocation()) {
		    	if ( (Math.abs(coord.getX() - this.localisation.x) < this.range) &&
		    			(Math.abs(coord.getY() - this.localisation.y) < this.range) ) {
		    		int intensity = this.getIntensityFromFire(feu);
		    		this.addRealIntensity(intensity);
		    		if (this.isDetectable(feu) == true) {
		    			System.out.println("Detectable");
			    		if (this.applyErrors() == true) {
			    			this.alerte.setIntensity(intensity);	
			    		}
		    		}
	            }		
	        }
	    }
	    System.err.println(previousIntensity + "/" + this.real_intensity + "/" + this.alerte.getIntensity() );
		    if (this.alerte.getIntensity() > previousIntensity) {
		    	this.previousIntensity = this.real_intensity;	//on recupere l'intensite mesuree precedement
		    	System.err.println("Alarme");
		    	this.triggerAlarm();	//si il y a eu aggravation de l'etat du feu
		    }
	}


	private void addRealIntensity(int intensity) {
		this.real_intensity += intensity;
		
	}

	//Determine une valeure d'intensité a partir du champ intensité du feu
    public int getIntensityFromFire(Fire feu){
        if (feu.getIntensity().contentEquals("Low")){
            return 1;
        }
        if (feu.getIntensity().contentEquals("Medium")){
            return 2;
        }
        if (feu.getIntensity().contentEquals("High")){
            return 3;
        }
        if (feu.getIntensity().contentEquals("VeryHigh")){
            return 4;
        }
        else{
            return 1;
        }
    }

	//verifie si feu detectable selon type de sonde
	public boolean isDetectable(Fire feu) {
		double isDetected = Math.random();
		/*
		 * isDetected est compris entre 0 et 1
		 * la notation isDetected>=0 signifie qu'un feu de Type X sera toujours detecte pour le type Sonde choisi (car condition tjrs vrai)
		 * ainsi, la notation isDetected>0.5 signifie que cette sonde a une chance sur deux de detecter ce type de feu
		 */
		if (this.type == TypeSonde.Smoke) {
			//classes toujours detectes
			if ( (feu.getType().contentEquals("ClassA")||feu.getType().contentEquals("ClassB")) && isDetected>=0 ) {
				return true;
			}
			//classes moins bien detectées
			if (feu.getType().contentEquals("ClassC") && isDetected>=0.5) {
				return true;
			}
			if (feu.getType().contentEquals("ClassD") && isDetected>=0.3) {
				return true;
			}
		}
		
		if (this.type == TypeSonde.CO2) {
			//classe toujours detecte
			if (feu.getType().contentEquals("ClassC") && isDetected>=0) {
				return true;
			}
			//classes moins bien detectées
			if (feu.getType().contentEquals("ClassA") && isDetected>=0.2) {
				return true;
			}
			if (feu.getType().contentEquals("ClassB") && isDetected>=0.4) {
				return true;
			}
			if (feu.getType().contentEquals("ClassD") && isDetected>=0.8) {
				return true;
			}
		}
		
		if (this.type == TypeSonde.Thermic) {
			//classes toujours detectes
			if (feu.getType().contentEquals("ClassD") && isDetected>=0) {
				return true;
			}
			//classes moins bien detectées
			if (feu.getType().contentEquals("ClassA") && isDetected>=0.2) {
				return true;
			}
			if (feu.getType().contentEquals("ClassB") && isDetected>=0.2) {
				return true;
			}
			if (feu.getType().contentEquals("ClassC") && isDetected>=0.8) {
				return true;
			}
		}
		
		return false;
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
		
		BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer(); 
		while ((inputLine = in.readLine()) != null) { 
			response.append(inputLine); 
		} 
		in .close();
		


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

	public TypeSonde getType() {
		return type;
	}

	public void setType(TypeSonde type) {
		this.type = type;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public Point getLocalisation() {
		return localisation;
	}

	public void setLocalisation(Point localisation) {
		this.localisation = localisation;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void convertCoord() {
		this.localisation = new Point(this.x, this.y);
		
	}

}
