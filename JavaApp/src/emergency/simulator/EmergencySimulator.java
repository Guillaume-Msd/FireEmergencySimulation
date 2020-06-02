package emergency.simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import emergency.AbstractHeadquarter;
import emergency.AbstractVehicule;
import emergency.Alerte;
import emergency.Coord;
import emergency.EnumStatut;
import emergency.FireFighterHQ;
import emergency.VehiculePompier;
import utilities.Tools;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmergencySimulator {

	private AbstractHeadquarter HQ;
	
	public EmergencySimulator() {
	}
	
	public AbstractHeadquarter getHQ() {
		return HQ;
	}

	public void setHQ(AbstractHeadquarter hQ) {
		HQ = hQ;
	}
	
	public void cycle() throws MalformedURLException, IOException {
		//On récupère les alertes du serveur
		List<Alerte> alertes = getAlertes();
		
		//On parcours ces alertes pour voir si il y en a des nouvelles
		parcoursAlertes(alertes);
		
		List<AbstractVehicule> vehicules = HQ.getVehicules();//A modifier lorsque l'on aura plusieurs HQ
		
		//On déplace les véhicules
		mooveAllVehiculesAndCheckArrivals(vehicules);
		
		//On renvoie les véhicules qui ont finis leur intervention au HQ
		//gestionFinDIntervention();
	}
	
	public List<Alerte> getAlertes() throws IOException {
		URL url = new URL("http://localhost:8082/EmergencyWebService/allAlerts");
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response1 = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response1.append(inputLine);
		} in .close();
	
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		
		Alerte[] alertes = mapper.readValue(response1.toString(), Alerte[].class);
		List<Alerte> alertList = new ArrayList<Alerte>();
		int i;
		for(i = 0; i < alertes.length; i++) {
			alertList.add(alertes[i]);
		}
		
		return alertList;
	}
	
	public void parcoursAlertes(List<Alerte> alertes) throws IOException {
		for (Alerte alerte : alertes) {
			System.out.println(alerte);
			if (alerte.getEtat().contentEquals("Nouvelle Alerte")) {
				gererNouvelleAlerte(alerte);
			}
		}
	}
	
	public void mooveAllVehiculesAndCheckArrivals(List<AbstractVehicule> vehicules) throws IOException {
		for (AbstractVehicule vehicule : vehicules) {
			if ( !(vehicule.getPath().isEmpty())) {
				Coord coord = vehicule.getPath().remove(0);
				vehicule.setCoord(coord);
				vehicule.updateVehiculeCoord();
			}
			else {
				if (vehicule.getStatut().equals(EnumStatut.RetourVersLeHQ)) {
					vehicule.setStatut(EnumStatut.Disponible);
					vehicule.deleteVehiculeView();
				}
				else if (vehicule.getStatut().equals(EnumStatut.EnRoutePourIntervention)) {
					vehicule.setStatut(EnumStatut.EnCoursDIntervention);
					vehicule.updateVehiculeStatut();
				}
			}
			System.out.println(vehicule);
		}
	}
	
	public void gererNouvelleAlerte(Alerte alerte) throws IOException {
		//TODO
		AbstractHeadquarter hq = ChoisirHQ();
		createIntervention(hq.ChoisirVehicule(alerte),this.getHQ().getEmplacement_headquarter().x,this.getHQ().getEmplacement_headquarter().y,
				alerte.getCoord().x,alerte.getCoord().y);
		
		//AlerteEnCours(alerte);
	}
	
	public void AlerteEnCours(Alerte alerte) throws IOException {
		URL url = new URL("http://localhost:8082/EmergencyWebService/updateAlertState/"+alerte.getId()+"/"+"EnvoieSecours");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
        connection.getInputStream();
	}
	
	public AbstractHeadquarter ChoisirHQ() {
		return this.getHQ();
	}
	
	/**
	 * When alert is detected, set up an itinerarry to the vehicule specified 
	 * @param AbstractVehicule
	 * @param int xInit
	 * @param int yInit
	 * @param int xAlert
	 * @param int yAlert
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public void createIntervention(List<AbstractVehicule> vehicules, int xInit, int yInit, int xFinal, int yFinal) throws JsonParseException, JsonMappingException, IOException {
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
		
		coordList.add(new Coord(xFinal,yFinal));
		
		for (AbstractVehicule vehicule : vehicules) {
			vehicule.setPath(coordList);
			vehicule.setStatut(EnumStatut.EnRoutePourIntervention);
			System.out.println(vehicule);
			vehicule.addVehiculeView();
		}
		
	}
	

	
	public void retourIntervention(AbstractVehicule vehicule, int xInit, int yInit, int xFinal, int yFinal) throws JsonParseException, JsonMappingException, IOException {
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
		
		coordList.add(new Coord(xFinal,yFinal));
		
		vehicule.setPath(coordList);
		
	}
	
	public List<AbstractVehicule> getVehiculesByStatut(EnumStatut statut) throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/vehiculesByStatut/"+statut);
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
		
		AbstractVehicule[] vehicules = mapper.readValue(response1.toString(), AbstractVehicule[].class);
		List<AbstractVehicule> vehiculeList = new ArrayList<AbstractVehicule>();
		int i;
		for(i = 0; i < vehicules.length; i++) {
			vehiculeList.add(vehicules[i]);
		}
		return vehiculeList;
	}
	
	public void gestionFinDIntervention() throws IOException {
		List<AbstractVehicule> vehicules = getVehiculesByStatut(EnumStatut.FinDIntervention);
			for (AbstractVehicule v : vehicules) {
				retourIntervention(v,v.getCoord().x,v.getCoord().y,this.getHQ().getEmplacement_headquarter().x,this.getHQ().getEmplacement_headquarter().y);
			}
	}
}