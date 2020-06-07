package simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class Launcher {

	public static void main(String[] args) throws IOException, InterruptedException {
		final EmergencySimulator simulateur = initSimulateur();
		
		initGasStations();

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
		int i;
		for(i = 0; i < coords.length; i++) {
			
			url = new URL("http://localhost:8082/GasStationWebService/add/" + coords[i].x + "/" + coords[i].y);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			connection.setRequestMethod("GET");
	        connection.getInputStream();
		}
		
	}

	public static EmergencySimulator initSimulateur() throws IOException {
		EmergencySimulator simulateur = new EmergencySimulator();
		
		simulateur.removeAllHQ();
		
		Coord coordHQ1 = new Coord(40, 40);
		int capacityHQ = 3;
		
		FireFighterHQ hq1 = new FireFighterHQ(coordHQ1,capacityHQ);
	
		
		simulateur.addHQToMap(hq1);
		
		simulateur.addFFHQ(hq1);
		
		
		
		Coord coordHQ2 = new Coord(128, 128);
		FireFighterHQ hq2 = new FireFighterHQ(coordHQ2,capacityHQ);
		
		simulateur.addHQToMap(hq2);
		
		simulateur.addFFHQ(hq2);
		

		
		return simulateur;
	}
}
