	package simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	 * Fonction qui impl�mente le cycle de la simulation, toutes les actions r�currentes de la simulation sont appel�es dans ce cycle
	 * @throws MalformedURLException
	 * @throws IOException
	 */
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
		gestionRavitaillement();

	}

	/**
	 * Mets � jour les HQ en comparant ceux pr�sents sur le serveur avec ceux d�j� pr�sent dans la simulation
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
				FireFighterHQ hq = new FireFighterHQ(HQ1.getId(), HQ1.getCoord(), HQ1.getNb_vehicules());
				this.addFFHQ(hq);
			}
			trouve = false;
		}
	}

	public void addFFHQ(FireFighterHQ HQ1) {
		this.FFHQ.add(HQ1);
	}


	/**
	 * Fais un appel au serveur Emergency pour r�cup�rer la liste de HQ
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
	 * R�cup�re toutes les alertes du serveur Emergency
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
	 * @return tous les v�hicules de la simulation
	 */
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
	
	/**
	 * On parcours les alertes pour g�rer les nouvelles alertes et supprimer celles qui ont �t� g�r�es
	 * @param alertes
	 * @param vehicules
	 * @throws IOException
	 */
	public void parcoursAlertes(List<Alerte> alertes,List<AbstractVehicule> vehicules) throws IOException {
		for (Alerte alerte : alertes) {
			if (alerte.getEtat().contentEquals("Nouvelle Alerte")) {
				gererNouvelleAlerte(alerte,vehicules);
			}
			for (AbstractVehicule v : vehicules) {
				if (v.getCoord().equals(alerte.getCoord())) {
					alerte.delete();
				}
			}
		}
	}
	
	/**
	 * Permet de d�placer tous les v�hicules de la simulation tout en mettant � jour leur statut (si besoin) et met � jour les informations du serveur
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
						//((VehiculeLutteIncendie) vehicule).fillWater();
						//((VehiculeLutteIncendie) vehicule).updateVehiculeWater();
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
	 * Fonction qui met � jour le statut de l'alerte sur le serveur, cela permet de ne g�rer qu'une fois chaque alerte distincte
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
	 * Permet de choisir la caserne la plus proche pour l'envoie dans v�hicule pour �teindre l'incendie
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
	
	
	public List<VehiculeLutteIncendie> VehiculesIncendieParProximite(Alerte alerte,List<AbstractVehicule> vehiculesSimu) throws IOException {
        List<VehiculeLutteIncendie> vehicules = new ArrayList<VehiculeLutteIncendie>();
        for (AbstractVehicule v : vehiculesSimu) {
            if (v instanceof VehiculeLutteIncendie) {
                v.majVehiculeInfo();
                if (v.getStatut().equals(EnumStatut.Disponible) || v.getStatut().equals(EnumStatut.RetourVersLeHQ))
                vehicules.add((VehiculeLutteIncendie) v);
                v.setDistance(calculDistance(v.getCoord().x,v.getCoord().y,alerte.getCoord().x,alerte.getCoord().y));
            }
        }
        
        vehicules.sort(new Comparator<VehiculeLutteIncendie>() {
            @Override
            public int compare(VehiculeLutteIncendie v1, VehiculeLutteIncendie v2) {
                if(v1.getDistance() < v2.getDistance()){
                    return -1;
                }
                else if (v1.getDistance() > v2.getDistance()) {
                    return 1;
                }
                return 0;
             }
        });
        return vehicules;
    }
	
	public void gererNouvelleAlerte(Alerte alerte,List<AbstractVehicule> vehiculesSimu) throws IOException {
        List<VehiculeLutteIncendie> vehicules = this.VehiculesIncendieParProximite(alerte,vehiculesSimu);
        int nb_camions_envoyes =0;
       // for (int i=0;i<=alerte.getIntensity()/4;i++) {
            if (!vehicules.isEmpty()) {
                VehiculeLutteIncendie v = vehicules.remove(0);
                if(v.getStatut().toString().contentEquals((EnumStatut.Disponible).toString())) {
                	createIntervention(v,alerte.getCoord().x,alerte.getCoord().y,alerte.getRange());
                } else {
                	redirectIntervention(v,alerte.getCoord().x,alerte.getCoord().y,alerte.getRange());
                }
                List<Coord> coordList = v.getPath();
                Collections.reverse(coordList);
                int j;
                for(j = 0; j < nb_camions_envoyes + 1; j++) {
                	coordList.add(new Coord(v.getCoord_HQ().getX(), v.getCoord().getY()));
                }
                Collections.reverse(coordList);
                v.setPath(coordList);
                nb_camions_envoyes = nb_camions_envoyes +1;
            }
        //}
        if (nb_camions_envoyes != 0) {
            AlerteEnCours(alerte);
        }
    }
	
	/**
	 * Demande au serveur le chemin (liste de coordonn�es que doit emprunter le camion pour se rendre aux coordonn�es final
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
	 * Envoies les v�hicules sp�cifi�s sur le lieu indiqu� par les coordonn�es finales (xFinal,yFinal)
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
	public void createIntervention(VehiculeLutteIncendie vehicule, int xFinal, int yFinal, int range) throws JsonParseException, JsonMappingException, IOException {
		List<Coord> coordList = getPathFromServer(vehicule.getCoord().x,vehicule.getCoord().y,xFinal,yFinal);
		double distance = calculDistance(vehicule.getCoord().x,vehicule.getCoord().y,xFinal,yFinal);
		vehicule.setDestinationX(xFinal);
		vehicule.setDestinationY(yFinal);
		vehicule.setPath(coordList);
		vehicule.setStatut(EnumStatut.EnRoutePourIntervention);
		vehicule.setOilQuantity(vehicule.getOilQuantity() - (distance*vehicule.getInterventionOilConsumption())/100);
		vehicule.addVehiculeView(range);
		vehicule.fillWater();
		System.err.println(vehicule.getOilQuantity());
		System.err.println(vehicule.getQuantiteEau());
		((VehiculeLutteIncendie) vehicule).updateVehiculeWater();
	}
	
	
	/**
	 * Envoies les v�hicules sp�cifi�s sur le lieu indiqu� par les coordonn�es finales (xFinal,yFinal)
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
	public void redirectIntervention(VehiculeLutteIncendie vehicule, int xFinal, int yFinal, int range) throws JsonParseException, JsonMappingException, IOException {
		List<Coord> coordList = getPathFromServer(vehicule.getCoord().x,vehicule.getCoord().y,xFinal,yFinal);
		double distance = calculDistance(vehicule.getCoord().x,vehicule.getCoord().y,xFinal,yFinal);
		vehicule.setDestinationX(xFinal);
		vehicule.setDestinationY(yFinal);
		vehicule.setPath(coordList);
		vehicule.setStatut(EnumStatut.EnRoutePourIntervention);
		vehicule.setOilQuantity(vehicule.getOilQuantity() - (distance*vehicule.getInterventionOilConsumption())/100);
		System.err.println(vehicule.getOilQuantity());
	
	}

	/**
	 * Renvoie le v�hicule sp�cifi� � son HQ
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
	 * Envoie le v�hicule sp�cifi� � la station service la plus proche pour faire le plein
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
	 * Trouve l'�l�ment le plus proche en distance du 1er �l�ment parmi une liste d'�l�ments
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
	 * Envoie un v�hicule � la coordonn�e sp�cifi�e
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
		((VehiculeLutteIncendie) vehicule).updateVehiculeWater();
	}
	
	/**
	 * R�cup�re tous les v�hicules d'un certain statut depuis le serveur
	 * @param statut
	 * @return
	 * @throws IOException
	 */
	public List<VehiculeLutteIncendie> getVehiculesByStatut(EnumStatut statut) throws IOException {
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
		
		VehiculeLutteIncendie[] vehicules = mapper.readValue(response1.toString(), VehiculeLutteIncendie[].class);
		List<VehiculeLutteIncendie> vehiculeList = new ArrayList<VehiculeLutteIncendie>();
		int i;
		for(i = 0; i < vehicules.length; i++) {
			vehiculeList.add(vehicules[i]);
		}
		return vehiculeList;
	}
	
	/**
	 * R�cup�re les v�hicules en statut FinDIntervention puis v�rifie si ils ont besoin de faire le plein avant de les renvoyer au HQ
	 * @throws IOException
	 */
	public void gestionFinDIntervention() throws IOException {
		List<AbstractVehicule> vehiculesList = this.getVehicules();
		List<VehiculeLutteIncendie> vehicules = getVehiculesByStatut(EnumStatut.FinDIntervention);
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
	 * R�cup�re les v�hicules � ravitailler depuis le serveur et g�re leur ravitaillement
	 * @throws IOException
	 */
	public void gestionRavitaillement() throws IOException {
		List<AbstractVehicule> vehiculesSimu = this.getVehicules();
		List<VehiculeLutteIncendie> vehiculesServeur = getVehiculesByStatut(EnumStatut.BesoinRavitaillementEau);
		for (AbstractVehicule vehiculeSimu : vehiculesSimu) {
			for (VehiculePompier vehiculeServeur : vehiculesServeur) {
				if (vehiculeSimu.getId() == vehiculeServeur.getId()) {
					RavitaillementEau((VehiculeLutteIncendie) vehiculeSimu);
				}
			}
		}
	}
	
	/**
	 * Envoie le v�hicule se ravitailler � la bouche � incendie la plus proche
	 * @param vehicule
	 * @throws IOException
	 */
	public void RavitaillementEau(VehiculeLutteIncendie vehicule) throws IOException {
		List<Coord> bouchesAIncendie = getBouchesAIncendieAndHQ();
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
		List<Coord> pathRetour = getPathFromServer(xFinal,yFinal,vehicule.getCoord().x,vehicule.getCoord().y);
		double distanceRetour = calculDistance(xFinal,yFinal,vehicule.getCoord().x,vehicule.getCoord().y);
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
	 * R�cup�re les coordonn�es de toutes les stations services depuis le serveur
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
	
	private List<Coord> getBouchesAIncendieAndHQ() throws IOException{
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
		for (AbstractHeadquarter HQ : this.getFFHQ()) {
			coordList.add(HQ.getCoord());
		}
		return coordList;
		
	}
	
	/**
	 * Enl�ve tous les HQ de la base de donn�e du serveur
	 * @throws IOException
	 */
	public void removeAllHQ() throws IOException {
		URL url = new URL("http://localhost:8082/HeadQuarterWebService/removeAll");
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); 
        httpURLConnection.setRequestMethod("DELETE");
        httpURLConnection.getInputStream();
	}
}