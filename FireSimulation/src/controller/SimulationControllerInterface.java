package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import model.Coord;
import model.Event;

public interface SimulationControllerInterface {
	
	  public void createEvent(Event event) throws IOException;
	    	
	  public Event[] getAllEvents() throws IOException;
	  	
	  public void updateEvent(Event event,Coord coord, String state) throws IOException;

	  public void deleteEvent(Event event) throws IOException;
}