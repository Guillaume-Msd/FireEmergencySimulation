package simulator;

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
import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.Coord;
import models.FireFighterHQ;
import utilities.Tools;

public class Launcher {

	public static void main(String[] args) throws IOException, InterruptedException {
		final EmergencySimulator simulateur = initSimulateur();
		
		initGasStations();
		
		initFireHydrant();

		new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
        		try {
					simulateur.cycle();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 }
        }    ,0,1000);
	}
	
	
	private static void initGasStations() throws IOException {
		URL url = new URL("http://localhost:8082/GasStationWebService/removeAll");
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); 
		httpURLConnection.setRequestMethod("DELETE");
		httpURLConnection.getInputStream();
		
		url = new URL("http://localhost:8083/MapWebService/getGasStation");
		httpURLConnection = (HttpURLConnection) url.openConnection(); 
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
		int i;
		for(i = 0; i < coords.length; i++) {
			
			url = new URL("http://localhost:8082/GasStationWebService/add/" + coords[i].x + "/" + coords[i].y);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			connection.setRequestMethod("GET");
	        connection.getInputStream();
		}
		
	}
	
	private static void initFireHydrant() throws IOException {
		URL url = new URL("http://localhost:8082/ElementWebService/removeAll");
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); 
		httpURLConnection.setRequestMethod("DELETE");
		httpURLConnection.getInputStream();
		
		url = new URL("http://localhost:8083/MapWebService/getBouchesAIncendie");
		httpURLConnection = (HttpURLConnection) url.openConnection(); 
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
		int i;
		for(i = 0; i < coords.length; i++) {
			
			url = new URL("http://localhost:8082/ElementWebService/addElement/" + coords[i].x + "/" + coords[i].y);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			connection.setRequestMethod("GET");
	        connection.getInputStream();
		}
		
	}

	public static EmergencySimulator initSimulateur() throws IOException {
		EmergencySimulator simulateur = new EmergencySimulator();
		
		simulateur.removeAllHQ();
		
		Coord coordHQ = new Coord(40, 40);
		int capacityHQ = 3;
		
		FireFighterHQ hq = new FireFighterHQ(coordHQ,capacityHQ);
	
		
		simulateur.addHQToMap(hq);
		
		simulateur.addFFHQ(hq);
	
		
		coordHQ = new Coord(128, 128);
		hq = new FireFighterHQ(coordHQ,capacityHQ);
		
		simulateur.addHQToMap(hq);
		
		simulateur.addFFHQ(hq);
		
		coordHQ = new Coord(170, 40);
		hq = new FireFighterHQ(coordHQ,capacityHQ);
		
		simulateur.addHQToMap(hq);
		
		simulateur.addFFHQ(hq);
		
		coordHQ = new Coord(30, 170);
		hq = new FireFighterHQ(coordHQ,capacityHQ);
		
		simulateur.addHQToMap(hq);
		
		simulateur.addFFHQ(hq);
		
		coordHQ = new Coord(170, 190);
		hq = new FireFighterHQ(coordHQ,capacityHQ);
		
		simulateur.addHQToMap(hq);
		
		simulateur.addFFHQ(hq);
		

		

		
		return simulateur;
	}
}
