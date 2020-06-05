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
import models.VehiculeLutteIncendie;
import models.VehiculePompier;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmergencySimulator {

	private List<FireFighterHQ> FFHQ = new ArrayList<FireFighterHQ>();
	
	private List<Coord> stationsServices;
	
	public EmergencySimulator() {
	}
	
	
	public List<FireFighterHQ> getFFHQ() {
		return FFHQ;
	}
	public void setFFHQ(List<FireFighterHQ> FFHQ) {
		this.FFHQ = FFHQ;
	}
	public List<Coord> getStationsServices() {
		return stationsServices;
	}
	public void setStationsServices(List<Coord> stationsServices) {
		this.stationsServices = stationsServices;
	}
	

	
	public void cycle() throws MalformedURLException, IOException {
		//On r�cup�re les HQ depuis le serveur et on ajoute les nouveaux HQ si l'utilisateur en a ajout�
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
		

	}



	private void majHeadquarters() throws IOException {
		List<AbstractHeadquarter> new_HQs = getHeadquartersFromServer();
		boolean trouve = false;
		for (AbstractHeadquarter HQ1 : new_HQs) {
			for (AbstractHeadquarter HQ2 : this.getFFHQ()) {
				if (HQ1.getId() == HQ2.getId()) {
					trouve = true;
				}
			}
			if (!trouve && HQ1 instanceof FireFighterHQ) {
				this.addFFHQ((FireFighterHQ) HQ1);
			}
			trouve = false;
		}
	}

	public void addFFHQ(FireFighterHQ HQ1) {
		this.FFHQ.add(HQ1);
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
		List<AbstractVehicule> vehicules = new ArrayList<AbstractVehicule>();
		for (FireFighterHQ HQ : this.getFFHQ()) {
			for (AbstractVehicule v : ((FireFighterHQ) HQ).getVehicules()) {
				vehicules.add(v);
			}
		}
		//TODO ajouter les v�hicules des autres types de HQ si besoin
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
				else if (vehicule.getStatut().equals(EnumStatut.EnRoutePourRavitaillementEssence)) {
					vehicule.fillOil();
					retourIntervention(vehicule);
				}
			}
			//System.out.println(vehicule);
		}
	}
	
	public void gererNouvelleAlerte(Alerte alerte) throws IOException {
		FireFighterHQ hq = ChoisirFFHQ(alerte);
		List<VehiculeLutteIncendie> vehicules = hq.ChoisirVehiculeIncendie(alerte);
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
	
	public FireFighterHQ ChoisirFFHQ(Alerte alerte) {
		double distancemin = -1;
		double distance;
		FireFighterHQ HQ_choisi = new FireFighterHQ(new Coord(-1,-1));
		for (FireFighterHQ HQ : this.getFFHQ()) {
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
	
	public List<Coord> getPathFromServer(int xInit,int yInit,int xFinal,int yFinal) throws IOException {
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
		return coordList;
	}
	
	/**
	 * When alert is detected, set up an itinerary to the vehicule specified 
	 * @param i 
	 * @param AbstractVehicule
	 * @param int xInit
	 * @param int yInit
	 * @param int xAlert
	 * @param int yAlert
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public void createIntervention(List<VehiculeLutteIncendie> vehicules, int xInit, int yInit, int xFinal, int yFinal, int range) throws JsonParseException, JsonMappingException, IOException {
		List<Coord> coordList = getPathFromServer(xInit,yInit,xFinal,yFinal);
		double distance = calculDistance(xInit,yInit,xFinal,yFinal);
		for (VehiculePompier vehicule : vehicules) {
			vehicule.setPath(coordList);
			vehicule.setStatut(EnumStatut.EnRoutePourIntervention);
			vehicule.setOilQuantity(vehicule.getOilQuantity() - (distance*vehicule.getInterventionOilConsumption())/100);
			vehicule.addVehiculeView(range);
			System.err.println(vehicule.getOilQuantity());
		}
	}
	

	
	public void retourIntervention(AbstractVehicule vehicule) throws JsonParseException, JsonMappingException, IOException {
		List<Coord> coordList = getPathFromServer(vehicule.getCoord().x,vehicule.getCoord().y,vehicule.getCoord_HQ().x,vehicule.getCoord_HQ().y);
		double distance = calculDistance(vehicule.getCoord().x,vehicule.getCoord().y,vehicule.getCoord_HQ().x,vehicule.getCoord_HQ().y);
		
		vehicule.setPath(coordList);
		vehicule.setStatut(EnumStatut.RetourVersLeHQ);
		vehicule.setOilQuantity(vehicule.getOilQuantity() - (distance*vehicule.getNormalOilConsumption())/100);
		vehicule.updateVehiculeStatut();
		System.err.println(vehicule.getOilQuantity());
		
	}
	
	public void RavitaillementOil(AbstractVehicule vehicule) throws IOException {
		List<Coord> coordlist = this.getStationCoord();
		Coord coord = trouveElementLePlusProche(vehicule.getCoord(), coordlist);
		envoie_vehicule(vehicule,coord.x,coord.y);
	}
	
	public Coord trouveElementLePlusProche(Coord coord_element,List<Coord> liste_coord_elements) {
		double distancemin = -1;
		Coord coord_finale = new Coord(0,0);
		for (Coord c : liste_coord_elements) {
			if ( distancemin < 0 ) {
				distancemin = Math.sqrt(
						Math.pow((coord_element.x-c.x),2) + 
						Math.pow((coord_element.y-c.y),2));
				coord_finale = c;
			}
			else {
				double distance = Math.sqrt(
						Math.pow((coord_element.x-c.x),2) + 
						Math.pow((coord_element.y-c.y),2));
				if (distance < distancemin) {
					distancemin = distance;
					coord_finale = c;
				}
			}
		}
		return coord_finale;
	}
	
	public void envoie_vehicule(AbstractVehicule vehicule,int xFinal,int yFinal) throws IOException {
		List<Coord> path = getPathFromServer(vehicule.getCoord().x,vehicule.getCoord().y,xFinal,yFinal);
		double distance = calculDistance(vehicule.getCoord().x,vehicule.getCoord().y,xFinal,yFinal);
		vehicule.setPath(path);
		vehicule.setStatut(EnumStatut.EnRoutePourRavitaillementEssence);
		vehicule.updateVehiculeStatut();
		vehicule.setOilQuantity(vehicule.getOilQuantity() - (distance*vehicule.getNormalOilConsumption())/100);
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
					if (v.getOilQuantity() < v.getOilCapacity()/4) {
						RavitaillementOil(v);
					}
					else {
						retourIntervention(v);
					}
				}
			}
			
		}
	}


	public double calculDistance(int xInit,int yInit,int xFinal,int yFinal) throws IOException {
		//TODO Appel au web-service http://localhost:8083/MapWebService/getDistance/
		URL url = new URL("http://localhost:8083/MapWebService/getDistance/"+ xInit + "/" + yInit + "/" + xFinal + "/" + yFinal );
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); 
        httpURLConnection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response1 = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response1.append(inputLine);
		} in .close();
		return Double.parseDouble(response1.toString())/1000;
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
	
	private List<Coord> getStationCoord() throws IOException{
		URL url = new URL("http://localhost:8083/MapWebService/getGasStation");
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
		
		return coordList;
		
	}



	public void removeAllHQ() throws IOException {
		URL url = new URL("http://localhost:8082/HeadQuarterWebService/removeAll");
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); 
        httpURLConnection.setRequestMethod("DELETE");
        httpURLConnection.getInputStream();
		
	}

}