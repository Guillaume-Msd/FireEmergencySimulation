package simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import models.AbstractHeadquarter;
import models.AbstractVehicule;
import models.Alerte;
import models.Coord;
import models.EnumStatut;
import models.FireFighterHQ;
import models.VehiculePompier;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmergencySimulator {

	private List<AbstractHeadquarter> HQ = new ArrayList<AbstractHeadquarter>();
	

	public EmergencySimulator() {
	}
	

	
	public void cycle() throws MalformedURLException, IOException {
		majHeadquarters();
		
		//On r�cup�re les alertes du serveur
		List<Alerte> alertes = getAlertes();
		
		List<AbstractVehicule> vehicules = getVehicules();
		
		//On parcours ces alertes pour voir si il y en a des nouvelles
		parcoursAlertes(alertes,vehicules);
		
		//On d�place les v�hicules
		mooveAllVehiculesAndCheckArrivals(vehicules);
		
		//On renvoie les v�hicules qui ont finis leur intervention au HQ
		gestionFinDIntervention();
		
		System.err.println(this.getHQ().size());
		System.err.println(this.getVehicules().size());

	}
	
	public List<AbstractHeadquarter> getHQ() {
		return HQ;
	}



	public void setHQ(List<AbstractHeadquarter> hQ) {
		HQ = hQ;


	}



	private void majHeadquarters() throws IOException {
		List<AbstractHeadquarter> new_HQs = getHeadquartersFromServer();
		boolean trouve = false;
		for (AbstractHeadquarter HQ1 : new_HQs) {
			for (AbstractHeadquarter HQ2 : this.getHQ()) {
				if (HQ1.getId() == HQ2.getId()) {
					trouve = true;
				}
			}
			if (!trouve) {
				this.addHQ(HQ1);
			}
			trouve = false;
		}
	}

	public void addHQ(AbstractHeadquarter HQ1) {
		HQ1.updateVehiculeList();
		this.HQ.add(HQ1);
	}



	public List<AbstractHeadquarter> getHeadquartersFromServer() throws IOException {
		URL url = new URL("http://localhost:8082/HeadQuarterWebService/allHQs");
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
		
		FireFighterHQ[] HQs = mapper.readValue(response1.toString(), FireFighterHQ[].class);
		List<AbstractHeadquarter> HQ_list = new ArrayList<AbstractHeadquarter>();
		int i;
		for(i = 0; i < HQs.length; i++) {
			HQ_list.add(HQs[i]);
		}
		
		return HQ_list;
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
	
	public List<AbstractVehicule> getVehicules() {
		List<AbstractVehicule> vehicules = new ArrayList<AbstractVehicule>();//A modifier lorsque l'on aura plusieurs HQ
		for (AbstractHeadquarter HQ : this.getHQ()) {
			for (AbstractVehicule v : HQ.getVehicules()) {
				vehicules.add(v);
			}
		}
		return vehicules;
	}
	
	public void parcoursAlertes(List<Alerte> alertes,List<AbstractVehicule> vehicules) throws IOException {
		for (Alerte alerte : alertes) {
			if (alerte.getEtat().contentEquals("Nouvelle Alerte")) {
				gererNouvelleAlerte(alerte);
			}
			for (AbstractVehicule v : vehicules) {
				if (v.getCoord().equals(alerte.getCoord())) {
					alerte.delete();
				}
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
			//System.out.println(vehicule);
		}
	}
	
	public void gererNouvelleAlerte(Alerte alerte) throws IOException {
		//TODO ChoisirHQ()
		AbstractHeadquarter hq = ChoisirHQ(alerte);
		List<AbstractVehicule> vehicules = hq.ChoisirVehicule(alerte);
		if (!(vehicules.isEmpty())) {
			createIntervention(vehicules,hq.getCoord().x,hq.getCoord().y,
				alerte.getCoord().x,alerte.getCoord().y, alerte.getRange());
			AlerteEnCours(alerte);
		}
	}
	
	public void AlerteEnCours(Alerte alerte) throws IOException {
		URL url = new URL("http://localhost:8082/EmergencyWebService/updateAlertState/"+alerte.getId()+"/"+"EnvoieSecours");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
        connection.getInputStream();
	}
	
	public AbstractHeadquarter ChoisirHQ(Alerte alerte) {
		double distancemin = -1;
		double distance;
		AbstractHeadquarter HQ_choisi = new FireFighterHQ(new Coord(-1,-1));
		for (AbstractHeadquarter HQ : this.getHQ()) {
			if (distancemin == -1) {
				distancemin = Math.sqrt(
						Math.pow((HQ.getCoord().x-alerte.getCoord().x),2) + 
						Math.pow((HQ.getCoord().y-alerte.getCoord().y),2));
				HQ_choisi = HQ;
			}
			else {
				distance = Math.sqrt(
						Math.pow((HQ.getCoord().x-alerte.getCoord().x),2) + 
						Math.pow((HQ.getCoord().y-alerte.getCoord().y),2));
				if (distance < distancemin) {
					distancemin = distance;
					HQ_choisi = HQ;
				}
			}
		}
		return HQ_choisi;
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
	public void createIntervention(List<AbstractVehicule> vehicules, int xInit, int yInit, int xFinal, int yFinal, int range) throws JsonParseException, JsonMappingException, IOException {
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
			vehicule.setCoord_HQ(new Coord(xInit, yInit));
			vehicule.setPath(coordList);
			vehicule.setStatut(EnumStatut.EnRoutePourIntervention);
			vehicule.addVehiculeView(range);
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
		vehicule.setStatut(EnumStatut.RetourVersLeHQ);
		vehicule.updateVehiculeStatut();
		
	}
	
	public List<VehiculePompier> getVehiculesByStatut(EnumStatut statut) throws IOException {
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
		
		VehiculePompier[] vehicules = mapper.readValue(response1.toString(), VehiculePompier[].class);
		List<VehiculePompier> vehiculeList = new ArrayList<VehiculePompier>();
		int i;
		for(i = 0; i < vehicules.length; i++) {
			vehiculeList.add(vehicules[i]);
		}
		return vehiculeList;
	}
	
	public void gestionFinDIntervention() throws IOException {
		List<AbstractVehicule> vehiculesList = this.getVehicules();
		List<VehiculePompier> vehicules = getVehiculesByStatut(EnumStatut.FinDIntervention);
		for (VehiculePompier vehicule : vehicules) {
			for(AbstractVehicule v: vehiculesList) { 
				if(v.getId() == vehicule.getId()) {
					retourIntervention(v,v.getCoord().x,v.getCoord().y,v.getCoord_HQ().x,v.getCoord_HQ().y);
				}
			}
			
		}
	}


	public void addHQToMap(AbstractHeadquarter hq) throws IOException {
		URL url = new URL("http://localhost:8082/HeadQuarterWebService/add/" + hq.getCoord().x + "/" + hq.getCoord().y + "/" + hq.getNb_vehicules());
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); 
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.getInputStream();
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response1 = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                	response1.append(inputLine);
                } in .close();
                

         int id = Integer.parseInt(response1.toString());
         hq.setId(id);
        
        
	}



	public void removeAllHQ() throws IOException {
		URL url = new URL("http://localhost:8082/HeadQuarterWebService/removeAll");
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); 
        httpURLConnection.setRequestMethod("DELETE");
        httpURLConnection.getInputStream();
		
	}

}