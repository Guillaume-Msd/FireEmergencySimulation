package emergency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import utilities.Tools;

public abstract class AbstractVehicule extends Intervernors implements VehiculeInterface {
  private int id;
	
  private List<Staff> staff;
  
  private Integer tailleMaxStaff;

  private Integer OilQuantity;

  private EnumEtat etat;
  
  private Integer NormalSpeed;
	
  private Integer OilCapacity;
  
  private Integer NormalOilConsumption;  //En L/100km
  
  private List<Coord> path;
  
  private Coord coord;
  
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
  		this(45,600,10,8);
  	}
  	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getOilQuantity() {
		return OilQuantity;
	}

	public void setOilQuantity(Integer oilQuantity) {
		OilQuantity = oilQuantity;
	}

	public Integer getOilCapacity() {
		return OilCapacity;
	}

	public void setOilCapacity(Integer oilCapacity) {
		OilCapacity = oilCapacity;
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
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void putOutFire() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void manageStaff() {
		// TODO Auto-generated method stub
	
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
	
	public int getQuantityToFill() {
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
	public Integer getNormalOilConsumption() {
		return NormalOilConsumption;
	}
	
	public void updateVehiculeCoord() throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/addVehicule/"+this.getId());
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
	
	public void updateVehiculeStatut() throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/updateVehiculeStatut/"+this.getId());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(Tools.toJsonString(this.getStatut()));
        osw.flush();
        osw.close();
        connection.getInputStream();
	}
	
	public void addVehiculeView() throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/addVehicule/"+this.getCoord().x+"/"+this.getCoord().y);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(Tools.toJsonString(this));
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
	}
	
	public void deleteVehiculeView() throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/deleteVehicule/"+this.getId());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("DELETE");
	}
	
	@Override
	public String toString() {
		return "Etat: "+this.getEtat()+"\nPath: "+this.getPath()+"HQ: "+this.getCoord_HQ();
	}
}

