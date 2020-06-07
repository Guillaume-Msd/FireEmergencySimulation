package models;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import utilities.Tools;

public class VehiculeLutteIncendie extends VehiculePompier {

	private double quantiteEau;
	private final double capaciteEau;
	
	public VehiculeLutteIncendie(double capacite) {
		this.capaciteEau = capacite;
		this.quantiteEau = capacite;
	}
	
	public VehiculeLutteIncendie() {
		this(300.);
	}
	
	/**
	 * Mets la quantité d'un liquide au maximum
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
        //System.err.println(Tools.toJsonString(this.quantiteEau));
        osw.write(Tools.toJsonString(this.quantiteEau));
        osw.flush();
        osw.close();
        connection.getInputStream();
	}
}
