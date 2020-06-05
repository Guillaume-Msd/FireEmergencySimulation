package simulation.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.Alerte;

public class GetAlertFromServ {
	
	public static List<Alerte> getAlertFromServ() throws IOException {
		URL obj = new URL("http://localhost:8082/EmergencyWebService/allAlerts");
		HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
		httpURLConnection.setRequestMethod("GET"); 
		httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		BufferedReader in = new BufferedReader( new InputStreamReader(httpURLConnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer(); 
		while ((inputLine = in.readLine()) != null) { 
			response.append(inputLine); 
		} 
		in .close();
		
		List<Alerte> alertList = new ArrayList<Alerte>();
		
		ObjectMapper mapper = new ObjectMapper();

        Alerte[] events= mapper.readValue(response.toString(), Alerte[].class);
        int i;
        for (i = 0 ; i < events.length; i++) {
        	Alerte alerte = events[i];
        	alertList.add(alerte);
        }
		return alertList;
	}

}
