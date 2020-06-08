package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Coord;
import model.EnumStatut;
import model.Element;
import model.Event;
import model.Fire;
import model.Vehicule;
import utilities.Tools;


public class EventController implements EventControllerInterface {
	
	
	public void createEvent(Event event) throws IOException {

		Iterator<Coord> it = event.getLocalisation().iterator();
		Coord coord = it.next();
		
		URL url = new URL("http://localhost:8081/FireWebService/add/"+coord.x+"/"+coord.y);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(((Fire) event).toJsonString());
        osw.flush();
        osw.close();
        connection.getInputStream();
        
	}
	
	public void deleteEvent(Event event) throws IOException {
	
		URL url = new URL("http://localhost:8081/FireWebService/remove/" + event.getId());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
        connection.getInputStream();

	}	
	
	public Event[] getAllEvents() throws IOException {
		
		URL url = new URL("http://localhost:8081/FireWebService/getAll");
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

        Fire[] events= mapper.readValue(response.toString(), Fire[].class);
		return events;		
	}
	
	public void updateEvent(Event event, Coord coord, String state) throws IOException {
		
		int idEvent = event.getId();
		URL url;
		
		if(state.equals("aggraver")) {
			((Fire) event).increaseIntensity();
			url = new URL("http://localhost:8081/FireWebService/aggravation/"+idEvent+"/"+ ((Fire) event).getIntensity());
		}
		
		else {
			((Fire) event).decreaseIntensity();
			 url = new URL("http://localhost:8081/FireWebService/attenuation/"+idEvent+"/"+((Fire) event).getIntensity());			
		}
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write("{ \"x\" : \""+ coord.x + "\",  \"y\": \""+ coord.y + "\" }");
        osw.flush();
        osw.close();
        connection.getInputStream();
		
	}
	
	public List<Coord> getAllEventCoords() throws IOException{
		Event[] eventArray = this.getAllEvents();
		List<Coord> coordList = new ArrayList<Coord>();
		int i;
		for (i = 0; i < eventArray.length; i++) {
			Set<Coord> setCoord = eventArray[i].getLocalisation();
			Iterator<Coord> it = setCoord.iterator();
			while(it.hasNext()) {
				coordList.add(it.next());
			}
		}
		
		return coordList;
	}
}