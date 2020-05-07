package simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.gson.reflect.TypeToken;

import utilities.Coord;
import utilities.Tools;

public class Events implements EventInterface {

	private List<Event> eventList;

	public void createEvent(Coord coord) {
		Event event = new Event(coord);
		eventList.add(event);
	}
	
	public void deleteEvent(Event event) {
		eventList.remove(event);

	}
	
	public void updateEvent() {
	
	}
	
	@Override
	public void sendEvents(List<Event> eventList) throws IOException {
		
		URL url = new URL("http://localhost:8080/FireSimulator/events");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();
		os.flush();
		os.close();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        for (Event event : eventList) {
            osw.write(Tools.toJsonString(event));
        }
        osw.flush();
        osw.close();
		
	}
	
	@Override
	public void getAllEvents() throws IOException {
		
		URL url = new URL("http://localhost:8080/FireSimulator/events");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response1 = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response1.append(inputLine);
		} in .close();
		
	    //TODO Conversion en objets Event 
         
	}
	
	
	public void getOneEvent(Event event) throws IOException {
		
		int idEvent = event.getId();
		
		//Comment passer l'id dans l'URI
		URL url = new URL("http://localhost:8080/FireSimulator/events?id=idEvent");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response1 = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response1.append(inputLine);
		} in .close();
		
	    //TODO Conversion en objet Event 

	}
		
}