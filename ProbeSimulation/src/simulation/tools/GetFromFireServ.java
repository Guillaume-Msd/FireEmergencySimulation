package simulation.tools;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.Fire;


public class GetFromFireServ {
	//collecte tous les feux
	public static List<Fire> fireList() throws IOException {
		URL obj = new URL("http://localhost:8081/FireWebService/getAll");
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
		
		List<Fire> pointList = new ArrayList<Fire>();
		
		ObjectMapper mapper = new ObjectMapper();

        Fire[] events= mapper.readValue(response.toString(), Fire[].class);
        int i;
        for (i = 0 ; i < events.length; i++) {
        	pointList.add(events[i]);
        }
		return pointList;
	}
	
	public static void main(String argv) {
		List<Point> listFeu = new ArrayList<Point>();
		System.out.println(listFeu.get(0).toString());
	}
}
