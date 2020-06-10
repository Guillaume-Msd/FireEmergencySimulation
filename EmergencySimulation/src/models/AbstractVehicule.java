package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.Coord;
import utilities.Tools;

public abstract class AbstractVehicule extends Intervenors implements VehiculeInterface {
  private int id;
	
  private List<Staff> staff;
  
  private Integer tailleMaxStaff;

  private double OilQuantity;

  private EnumEtat etat;
  
  private Integer NormalSpeed;
	
  private final double OilCapacity;
  
  private double NormalOilConsumption;  //En L/100km
  
  private List<Coord> path;
  
  private Coord coord;
  
  private int range;
  
  private double distance;
  
  private int destinationX;
  
  private int destinationY;
  
  private Coord destination;


public Coord getCoord_HQ() {
	return coord_HQ;
}

public void setCoord_HQ(Coord coord_HQ) {
	this.coord_HQ = coord_HQ;
}

private Coord coord_HQ;
  
  private EnumStatut statut;
  
	protected AbstractVehicule(Integer speed, Integer oilCapacity, Integer consumption, Integer tailleMaxStaff) {
  		this.NormalSpeed = speed;
  		this.NormalOilConsumption = consumption;
  		this.OilCapacity = oilCapacity;
  		this.tailleMaxStaff = tailleMaxStaff;
  		
  		this.staff = new LinkedList<Staff>();
  		this.etat = EnumEtat.Neuf;
  		this.OilQuantity = this.OilCapacity;
  		this.statut = EnumStatut.Disponible;
  		this.path = new LinkedList<Coord>();
  	}
  	
  	protected AbstractVehicule() {
  		this(45,10,50,8);
  	}
  	
  	
	
	public Coord getDestination() {
		return destination;
	}

	public void setDestination(Coord destination) {
		this.destination = destination;
	}

	public int getDestinationX() {
		return destinationX;
	}

	public void setDestinationX(int destinationX) {
		this.destinationX = destinationX;
	}

	public int getDestinationY() {
		return destinationY;
	}

	public void setDestinationY(int destinationY) {
		this.destinationY = destinationY;
	}

	public int getId() {
		return id;
	}
	
	  public int getRange() {
			return range;
		}

		public void setRange(int range) {
			this.range = range;
		}

	public void setId(int id) {
		this.id = id;
	}

	public double getOilQuantity() {
		return OilQuantity;
	}

	public void setOilQuantity(double d) {
		OilQuantity = d;
	}

	public double getOilCapacity() {
		return OilCapacity;
	}

	public List<Coord> getPath() {
		return path;
	}

	public void setPath(List<Coord> path) {
		this.path = path;
	}

	public void setTailleMaxStaff(Integer tailleMaxStaff) {
		this.tailleMaxStaff = tailleMaxStaff;
	}

	public void setNormalSpeed(Integer normalSpeed) {
		NormalSpeed = normalSpeed;
	}

	public void setNormalOilConsumption(Integer normalOilConsumption) {
		NormalOilConsumption = normalOilConsumption;
	}
	
  	public EnumStatut getStatut() {
  		return statut;
  	}

  	public void setStatut(EnumStatut statut) {
  		this.statut = statut;
  	}

	/**
	 * @return the etat
	 */
	public EnumEtat getEtat() {
		return etat;
	}

	/**
	 * @param etat the etat to set
	 */
	public void setEtat(EnumEtat etat) {
		this.etat = etat;
	}

	/**
	 * @return the staff
	 */
	public List<Staff> getStaff() {
		return staff;
	}

	/**
	 * @param staff the staff to set
	 */
	public void setStaff(List<Staff> staff) {
		this.staff = staff;
	}
	
	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	/**
	 * 
	 * @param s personne à ajouter au staff
	 */
	public void addToStaff(Staff s) {
		this.staff.add(s);
	}
	
	/**
	 * 
	 * @param staff liste des personnes à ajouter au staff
	 */
	public void addToStaff(List<Staff> staff) {
		for (Staff s : staff) {
			this.staff.add(s);
		}
	}
	
	public double getQuantityToFill() {
		return this.OilCapacity - this.OilQuantity;
	}
	
	public void fillOil() {
		this.OilQuantity = this.OilCapacity;
	}

	/**
	 * @return the tailleMaxStaff
	 */
	public Integer getTailleMaxStaff() {
		return tailleMaxStaff;
	}

	/**
	 * @return the normalSpeed
	 */
	public Integer getNormalSpeed() {
		return NormalSpeed;
	}

	/**
	 * @return the normalOilConsumption
	 */
	public double getNormalOilConsumption() {
		return NormalOilConsumption;
	}
	
	/**
	 * Mets à jour les coordonnées du véhicule sur le serveur
	 * @throws IOException
	 */
	public void updateVehiculeCoord() throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/updateVehiculeCoord/"+this.getId());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(Tools.toJsonString(this.getCoord()));
        osw.flush();
        osw.close();
        connection.getInputStream();

	}
	
	public void majVehiculeInfo() throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/vehicule/"+this.getId() );
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); 
        httpURLConnection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response1 = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response1.append(inputLine);
		} in .close();
	
		ObjectMapper mapper = new ObjectMapper();
		
		AbstractVehicule v = mapper.readValue(response1.toString(), AbstractVehicule.class);
		this.setCoord(v.getCoord());
		this.setStatut(v.getStatut());
	}
	
	/**
	 * Met à jour le statut des véhicules sur le serveur
	 */
	public void updateVehiculeStatut() throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/updateVehiculeStatut/"+this.getId());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        System.err.println(Tools.toJsonString(this.getStatut()));
        osw.write(Tools.toJsonString(this.getStatut()));
        osw.flush();
        osw.close();
        connection.getInputStream();
	}
	
	
	/**
	 * Ajoute un véhicule à la BDD du serveur pour pouvoir l'afficher
	 * @param range
	 * @throws IOException
	 */
	public void addVehiculeView(int range) throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/addVehicule/"+this.getCoord().x+"/"+this.getCoord().y);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();

		JSONObject json = new JSONObject();
		json.put("type", this.getClass().getSimpleName());
		json.put("range", range);
		json.put("destinationX", this.getDestinationX());
		json.put("destinationY", this.getDestinationY());
		
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(json.toJSONString());
        osw.flush();
        osw.close();
        connection.getInputStream();
        
        BufferedReader in = new BufferedReader(
        new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response1 = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response1.append(inputLine);
        } in .close();
        

        int id = Integer.parseInt(response1.toString());
        this.setId(id);
        
        this.updateVehiculeStatut();
	}
	
	/**
	 * Supprime le véhicule du serveur
	 * @throws IOException
	 */
	public void deleteVehiculeView() throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/deleteVehicule/"+this.getId());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("DELETE");
		connection.getInputStream();
	}
	

	@Override
	public String toString() {
		return "Etat: "+this.getEtat()+"\n\tPath: "+this.getPath()
		+"\n\tCoord: "+this.getCoord()+"\n\tStatut: "+this.getStatut()+"\n\tHQ: "+this.getCoord_HQ();
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	

	
	
}

