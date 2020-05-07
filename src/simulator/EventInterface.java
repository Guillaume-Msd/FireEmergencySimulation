package simulator;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface EventInterface {

  public void sendEvents(List<Event> eventList) throws IOException;
  
  public Event[] getEvents(URL url) throws IOException;

  public Event[] getAllEvents() throws IOException;
  
  public Event getOneEvent(Event event) throws IOException;
  
  public void aggravateEvent(Event event) throws IOException;
  
  public void attenuateEvent(Event event) throws IOException;

}