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
import models.InterventionServerInterface;
import models.VehiculeLutteIncendie;
import models.VehiculePompier;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmergencySimulator implements InterventionServerInterface {

	private List<FireFighterHQ> FFHQ = new ArrayList<FireFighterHQ>();
	
	private List<Coord> stationsServices;
	
	public EmergencySimulator() {
	}
	
	//----------------Getters and Setters----------------//
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
	

	/**
	 * Fonction qui implémente le cycle de la simulation, toutes les actions récurrentes de la simulation sont appelées dans ce cycle
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void cycle() throws MalformedURLException, IOException {
		//On rï¿½cupï¿½re les HQ depuis le serveur et on ajoute les nouveaux HQ si l'utilisateur en a ajoutï¿½
		majHeadquarters();
		
		//On rï¿½cupï¿½re les alertes du serveur
		List<Alerte> alertes = getAlertes();
		
		List<AbstractVehicule> vehicules = getVehicules();
		
		//On parcours ces alertes pour voir si il y en a des nouvelles
		parcoursAlertes(alertes,vehicules);
		
		//On dï¿½place les vï¿½hicules
		mooveAllVehiculesAndCheckArrivals(vehicules);
		
		//On renvoie les vï¿½hicules qui ont finis leur intervention au HQ
		gestionFinDIntervention();
		gestionRavitaillement();

	}

	/**
	 * Mets à jour les HQ en comparant ceux présents sur le serveur avec ceux déjà présent dans la simulation
	 * @throws IOException
	 */
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


	/**
	 * Fais un appel au serveur Emergency pour récupérer la liste de HQ
	 * @return
	 * @throws IOException
	 */
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
	
	/**
	 * Récupère toutes les alertes du serveur Emergency
	 * @return
	 * @throws IOException
	 */
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
	
	/**
	 *
	 * @return tous les véhicules de la simulation
	 */
	public List<AbstractVehicule> getVehicules() {
		List<AbstractVehicule> vehicules = new ArrayList<AbstractVehicule>();
		for (FireFighterHQ HQ : this.getFFHQ()) {
			for (AbstractVehicule v : ((FireFighterHQ) HQ).getVehicules()) {
				vehicules.add(v);
			}
		}
		//TODO ajouter les vï¿½hicules des autres types de HQ si besoin
		return vehicules;
	}
	
	/**
	 * On parcours les alertes pour gérer les nouvelles alertes et supprimer celles qui ont été gérées
	 * @param alertes
	 * @param vehicules
	 * @throws IOException
	 */
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
	
	/**
	 * Permet de déplacer tous les véhicules de la simulation tout en mettant à jour leur statut (si besoin) et met à jour les informations du serveur
	 * @param vehicules
	 * @throws IOException
	 */
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
					if (vehicule instanceof VehiculeLutteIncendie) {
						((VehiculeLutteIncendie) vehicule).fillWater();
						((VehiculeLutteIncendie) vehicule).updateVehiculeWater();
					}
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
				else if (vehicule.getStatut().equals(EnumStatut.EnRoutePourRavitaillementEau)) {
					((VehiculeLutteIncendie) vehicule).fillWater();
					((VehiculeLutteIncendie) vehicule).updateVehiculeWater();
					vehicule.setStatut(EnumStatut.EnCoursDIntervention);
					vehicule.updateVehiculeStatut();
				}
			}
		}
	}
	
	/**
	 * Fonction qui demande l'envoie de véhicules au niveau de l'alerte
	 * @param alerte
	 * @throws IOException
	 */
	public void gererNouvelleAlerte(Alerte alerte) throws IOException {
		FireFighterHQ hq = ChoisirFFHQ(alerte);
		List<VehiculeLutteIncendie> vehicules = hq.ChoisirVehiculeIncendie(alerte.getIntensity());
		if (!(vehicules.isEmpty())) {
			createIntervention(vehicules,hq.getCoord().x,hq.getCoord().y,
				alerte.getCoord().x,alerte.getCoord().y, alerte.getRange());
			AlerteEnCours(alerte);
		}
	}
	
	/**
	 * Fonction qui met à jour le statut de l'alerte sur le serveur, cela permet de ne gérer qu'une fois chaque alerte distincte
	 * @param alerte
	 * @throws IOException
	 */
	public void AlerteEnCours(Alerte alerte) throws IOException {
		URL url = new URL("http://localhost:8082/EmergencyWebService/updateAlertState/"+alerte.getId()+"/"+"EnvoieSecours");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
        connection.getInputStream();
	}
	
	/**
	 * Permet de choisir la caserne la plus proche pour l'envoie dans véhicule pour éteindre l'incendie
	 * @param Alerte alerte
	 * @return
	 */
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
	
	/**
	 * Demande au serveur le chemin (liste de coordonnées que doit emprunter le camion pour se rendre aux coordonnées final
	 * @param int xInit
	 * @param int yInit
	 * @param int xFinal
	 * @param int yFinal
	 */
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
	 * Envoies les véhicules spécifiés sur le lieu indiqué par les coordonnées finales (xFinal,yFinal)
	 * @param List<AbstractVehicule> vehicules
	 * @param int xInit
	 * @param int yInit
	 * @param int xAlert
	 * @param int yAlert
	 * @param int range
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
	

	/**
	 * Renvoie le véhicule spécifié à son HQ
	 * @param AbstractVehicule vehicule
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void retourIntervention(AbstractVehicule vehicule) throws JsonParseException, JsonMappingException, IOException {
		List<Coord> coordList = getPathFromServer(vehicule.getCoord().x,vehicule.getCoord().y,vehicule.getCoord_HQ().x,vehicule.getCoord_HQ().y);
		double distance = calculDistance(vehicule.getCoord().x,vehicule.getCoord().y,vehicule.getCoord_HQ().x,vehicule.getCoord_HQ().y);
		
		vehicule.setPath(coordList);
		vehicule.setStatut(EnumStatut.RetourVersLeHQ);
		vehicule.setOilQuantity(vehicule.getOilQuantity() - (distance*vehicule.getNormalOilConsumption())/100);
		vehicule.updateVehiculeStatut();
		System.err.println(vehicule.getOilQuantity());
		
	}
	
	/**
	 * Envoie le véhicule spécifié à la station service la plus proche pour faire le plein
	 * @param vehicule
	 * @throws IOException
	 */
	public void RavitaillementOil(AbstractVehicule vehicule) throws IOException {
		List<Coord> coordlist = this.getStationCoord();
		Coord coord = trouveElementLePlusProche(vehicule.getCoord(), coordlist);
		envoie_vehicule(vehicule,coord.x,coord.y,vehicule.getNormalOilConsumption());
		vehicule.setStatut(EnumStatut.EnRoutePourRavitaillementEssence);
		vehicule.updateVehiculeStatut();
	}
	
	/**
	 * Trouve l'élément le plus proche en distance du 1er élément parmi une liste d'éléments
	 * @param coord_element
	 * @param liste_coord_elements
	 * @return
	 */
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
	
	/**
	 * Envoie un véhicule à la coordonnée spécifiée
	 * @param vehicule
	 * @param xFinal
	 * @param yFinal
	 * @throws IOException
	 */
	public void envoie_vehicule(AbstractVehicule vehicule,int xFinal,int yFinal,double consommation) throws IOException {
		List<Coord> path = getPathFromServer(vehicule.getCoord().x,vehicule.getCoord().y,xFinal,yFinal);
		double distance = calculDistance(vehicule.getCoord().x,vehicule.getCoord().y,xFinal,yFinal);
		vehicule.setPath(path);
		vehicule.setOilQuantity(vehicule.getOilQuantity() - (distance*consommation)/100);
	}
	
	/**
	 * Récupère tous les véhicules d'un certain statut depuis le serveur
	 * @param statut
	 * @return
	 * @throws IOException
	 */
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
	
	/**
	 * Récupère les véhicules en statut FinDIntervention puis vérifie si ils ont besoin de faire le plein avant de les renvoyer au HQ
	 * @throws IOException
	 */
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

	/**
	 * Récupère les véhicules à ravitailler depuis le serveur et gère leur ravitaillement
	 * @throws IOException
	 */
	public void gestionRavitaillement() throws IOException {
		List<AbstractVehicule> vehiculesSimu = this.getVehicules();
		List<VehiculePompier> vehiculesServeur = getVehiculesByStatut(EnumStatut.BesoinRavitaillementEau);
		for (AbstractVehicule vehiculeSimu : vehiculesSimu) {
			for (VehiculePompier vehiculeServeur : vehiculesServeur) {
				if (vehiculeSimu.getId() == vehiculeServeur.getId()) {
					RavitaillementEau((VehiculeLutteIncendie) vehiculeSimu);
				}
			}
		}
	}
	
	/**
	 * Envoie le véhicule se ravitailler à la bouche à incendie la plus proche
	 * @param vehicule
	 * @throws IOException
	 */
	public void RavitaillementEau(VehiculeLutteIncendie vehicule) throws IOException {
		List<Coord> bouchesAIncendie = getBouchesAIncendie();
		Coord boucheLaPlusProche = trouveElementLePlusProche(vehicule.getCoord(), bouchesAIncendie);
		envoieVehiculeAllerRetour(vehicule,boucheLaPlusProche.x,boucheLaPlusProche.y,vehicule.getInterventionOilConsumption());
		vehicule.setStatut(EnumStatut.EnRoutePourRavitaillementEau);
		vehicule.updateVehiculeStatut();
	}
	
	/**
	 * Pareil que la fonction envoieVehicule mais permet de faire un aller-retour
	 * @param vehicule
	 * @param xFinal
	 * @param yFinal
	 * @param consommation
	 * @throws IOException
	 */
	public void envoieVehiculeAllerRetour(AbstractVehicule vehicule,int xFinal,int yFinal,int consommation) throws IOException {
		List<Coord> pathAller = getPathFromServer(vehicule.getCoord().x,vehicule.getCoord().y,xFinal,yFinal);
		double distanceAller = calculDistance(vehicule.getCoord().x,vehicule.getCoord().y,xFinal,yFinal);
		List<Coord> pathRetour = getPathFromServer(vehicule.getCoord().x,vehicule.getCoord().y,xFinal,yFinal);
		double distanceRetour = calculDistance(vehicule.getCoord().x,vehicule.getCoord().y,xFinal,yFinal);
		for (int i=0;i<5;i++) {
			pathAller.add(new Coord(xFinal,yFinal));
		}
		for (Coord c : pathRetour) {
			pathAller.add(c);
		}
		vehicule.setPath(pathAller);
		vehicule.setOilQuantity(vehicule.getOilQuantity() - ((distanceAller+distanceRetour)*consommation)/100);
	}
	
	/**
	 * Calcul la distance en m pour aller du point (xInit,yInit) au point (xFinal,yFinal) en suivant les routes
	 * @param xInit
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @return
	 * @throws IOException
	 */
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

	/**
	 * Envoie les informations du HQ au serveur Emergency
	 * @param hq
	 * @throws IOException
	 */
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
	
	/**
	 * Récupère les coordonnées de toutes les stations services depuis le serveur
	 * @return
	 * @throws IOException
	 */
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
	
	private List<Coord> getBouchesAIncendie() throws IOException{
		URL url = new URL("http://localhost:8083/MapWebService/getBouchesAIncendie");
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
	
	/**
	 * Enlève tous les HQ de la base de donnée du serveur
	 * @throws IOException
	 */
	public void removeAllHQ() throws IOException {
		URL url = new URL("http://localhost:8082/HeadQuarterWebService/removeAll");
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); 
        httpURLConnection.setRequestMethod("DELETE");
        httpURLConnection.getInputStream();
		
	}

}