package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import utilities.Tools;

public class VehiculeLutteIncendie extends VehiculePompier {

	private double quantiteEau;
	private final double capaciteEau;
	
	
	public VehiculeLutteIncendie(double capacite) throws IOException {
		this.capaciteEau = capacite;
		this.quantiteEau = capacite;
	}
	
	public VehiculeLutteIncendie() throws IOException {
		this(300.);
	}
	
	public double getQuantiteEau() {
		return quantiteEau;
	}

	public void setQuantiteEau(double quantiteEau) {
		this.quantiteEau = quantiteEau;
	}

	/**
	 * Mets la quantit� d'un liquide au maximum
	 * @param type
	 */
	public void fillWater() {
		this.quantiteEau = this.capaciteEau;
	}
	
	public void updateVehiculeWater() throws IOException {
		URL url = new URL("http://localhost:8082/VehiculeWebService/updateVehiculeWater/"+this.getId());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        System.out.println(Tools.toJsonString(this.getQuantiteEau()));
        osw.write(Tools.toJsonString(this.getQuantiteEau()));
        osw.flush();
        osw.close();
        connection.getInputStream();
	}
	
	@Override
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
		
		VehiculeLutteIncendie v = mapper.readValue(response1.toString(), VehiculeLutteIncendie.class);
		if(v!= null) {
			this.setCoord(v.getCoord());
			this.setStatut(v.getStatut());
			this.setQuantiteEau(v.getQuantiteEau());
		}
		
	}
}
