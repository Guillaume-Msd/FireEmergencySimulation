package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Element;
import model.Vehicule;
import utilities.Tools;

public class InterventionController implements InterventionControllerInterface {
	
	public Vehicule[] getVehiculesEnIntervention() throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/vehiculesByStatut/" + "EnCoursDIntervention");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
		} in .close();
		
		ObjectMapper mapper = new ObjectMapper();
        Vehicule[] vehicles = mapper.readValue(response.toString(), Vehicule[].class);		
		return vehicles;
	}
	
	public List<Vehicule> getVehiculesEnRoutePourIntervention() throws IOException {
		
		URL url = new URL("http://localhost:8082/VehiculeWebService/vehiculesByStatut/" + "EnRoutePourIntervention");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.getInputStream();
        BufferedReader in = new BufferedReader(
        new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
		} in .close();
		
		ObjectMapper mapper = new ObjectMapper();
        Vehicule[] vehicules = mapper.readValue(response.toString(), Vehicule[].class);        
		URL url2 = new URL("http://localhost:8082/VehiculeWebService/vehiculesByStatut/" + "EnRoutePourRavitaillementEau");
        HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
        httpURLConnection2.setRequestMethod("GET");
        httpURLConnection2.getInputStream();
        BufferedReader in2 = new BufferedReader(
        new InputStreamReader(httpURLConnection2.getInputStream()));
        String inputLine2;
        StringBuffer response2 = new StringBuffer();
        while ((inputLine2 = in2.readLine()) != null) {
        	response2.append(inputLine2);
		} in2.close();
		
		ObjectMapper mapper2 = new ObjectMapper();
        Vehicule[] vehicules2 = mapper2.readValue(response2.toString(), Vehicule[].class);
       
        
        List<Vehicule> Allvehicles = new ArrayList<Vehicule>();
        for (int i=0;i<vehicules.length;i++) {
        	Allvehicles.add(vehicules[i]);
        }
        for(int i=0;i<vehicules2.length;i++) {
        	Allvehicles.add(vehicules2[i]);
        }
		return Allvehicles;
	}
	
	
	public void updateVehiculeStatut(Vehicule vehicule) throws IOException {
		
		URL url = new URL("http://localhost:8082/VehiculeWebService/updateVehiculeStatut/"+vehicule.getId());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(Tools.toJsonString(vehicule.getStatut()));
        osw.flush();
        osw.close();
        connection.getInputStream();
    }
	
	public void updateVehiculeWater(Vehicule vehicule) throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/updateVehiculeWater/"+vehicule.getId());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        System.out.println(Tools.toJsonString(vehicule.getQuantiteEau()));
        osw.write(Tools.toJsonString(vehicule.getQuantiteEau()));
        osw.flush();
        osw.close();
        connection.getInputStream();
		
	}

	public void addElement(Element element) throws IOException {
		
		URL url = new URL("http://localhost:8082/ElementWebservice/addElement/"+element.getX()+"/"+element.getY());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(element.toJsonString());
        osw.flush();
        osw.close();
        connection.getInputStream();
	}
	
	public Element[] getAllElements() throws IOException {
		
		URL url = new URL("http://localhost:8082/ElementWebService/allElements");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
		} in .close();
		
		ObjectMapper mapper = new ObjectMapper();

        Element[] elements= mapper.readValue(response.toString(), Element[].class);
		return elements;	
	}
}
