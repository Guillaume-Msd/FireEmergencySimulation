package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Element;
import model.Vehicule;
import utilities.Tools;

public class InterventionController implements InterventionControllerInterface {
	
	@Override
	public Vehicule[] getVehicules() throws IOException {
		
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
	
	@Override
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

	@Override
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
	
	@Override
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

	
	/*
	public void disableEnvironmentElement(EnvironmentElement element) throws IOException {
		
		URL url = new URL("http://localhost:8082/ElementWebService/disable/" + element.getId());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
        connection.getInputStream();
	}*/
}
