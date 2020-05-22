package emergency.simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import emergency.AbstractVehicule;
import emergency.Alerte;
import emergency.Coord;
import emergency.VehiculePompier;
import simulator.Fire;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmergencySimulator {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//TODO créer un petit scénario de base
		
		
		
		
	}
	
	public void cycle() throws MalformedURLException, IOException {
		//On récupère les alertes du serveur
		Alerte[] alertes = getAlertes();
		
		//On parcours ces alertes pour voir si il y en a des nouvelles
		parcoursAlertes(alertes);
		
		AbstractVehicule[] vehicules = null;
		//On déplace les véhicules
		mooveAllVehiculesAndCheckArrivals(vehicules);
	}
	
	public Alerte[] getAlertes() throws IOException {
		URL url = new URL("http://localhost:8082/EmergencyWebService/allAlerts");
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
		
		Alerte[] alertes = mapper.readValue(response1.toString(), Alerte[].class);
		return alertes;
	}
	
	public void parcoursAlertes(Alerte[] alertes) throws IOException {
		for (Alerte alerte : alertes) {
			if (alerte.getEtat().equals("Nouvelle alerte")) {
				gererNouvelleAlerte(alerte);
			}
		}
	}
	
	public void mooveAllVehiculesAndCheckArrivals(AbstractVehicule[] vehicules) {
		for (AbstractVehicule vehicule : vehicules) {
			Coord coord = vehicule.getPath().remove(0);
			if ( coord != null) {
				vehicule.setCoord(coord);
				vehicule.updateVehiculeCoord();
			}
		}
	}
	
	public void gererNouvelleAlerte(Alerte alerte) throws IOException {
		//TODO
		AlerteEnCours(alerte);
	}
	
	public void AlerteEnCours(Alerte alerte) throws IOException {
		URL url = new URL("http://localhost:8082/EmergencyWebServiceupdateAlertState/"+alerte.getId()+"/"+"EnvoieSecours");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.flush();
        osw.close();
        connection.getInputStream();	
        
	}
	
	/**
	 * When alert is detected, set up an itinerarry to the vehicule specified 
	 * @param AbstractVehicule
	 * @param int xInit
	 * @param int yInit
	 * @param int xAlert
	 * @param int yAlert
	 */
	public void createIntervention(AbstractVehicule vehicule, int xInit, int yInit, int xFinal, int yFinal) {
		URL url = new URL("http://localhost:8083/MapWebService/getItinerary/"+ xInit + "/" + yInit + "/" + xFinal + "/" + yFinal );
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
		
		Coord[] coords = mapper.readValue(response1.toString(), Coord[].class);
		List<Coord> coordList = new ArrayList<Coord>();
		int i;
		for(i = 0; i < coords.length; i++) {
			coordList.add(coords[i]);
		}
		
		vehicule.setPath(coordList);
		
	}
	
}