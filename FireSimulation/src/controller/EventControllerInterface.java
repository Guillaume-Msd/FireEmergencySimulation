package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import model.Coord;
import model.Event;

public interface EventControllerInterface {
	
	  /**
	   * Envoie l'évènement cree
	 * @param event
	 * @throws IOException
	 */
	public void createEvent(Event event) throws IOException;
	    	
	  /**
	   * Recupere tous les evenements du Fire Web Service
	 * @return Event[]
	 * @throws IOException
	 */
	public Event[] getAllEvents() throws IOException;
	  	
	  /**
	   * Envoie l'element mis à jour (attenuation ou aggravation)
	 * @param event
	 * @param coord
	 * @param state
	 * @throws IOException
	 */
	public void updateEvent(Event event,Coord coord, String state) throws IOException;

	  /**
	   * supprime l'element
	 * @param event
	 * @throws IOException
	 */
	public void deleteEvent(Event event) throws IOException;
}