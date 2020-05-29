package simulator.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import simulator.model.Coord;
import simulator.model.Event;

public interface EventsInterface {
	
	  public void createEvent(Event event) throws IOException;
	  
	  public void sendEvents(List<Event> eventList) throws IOException;
  
	  public Event[] getEvents(URL url) throws IOException;
	
	  public Event[] getAllEvents() throws IOException;
	  
	  public Event getOneEvent(Event event) throws IOException;
	
	  public void updateEvent(Event event,Coord coord, String state) throws IOException;

	  public void deleteEvent(Event event) throws IOException;


}