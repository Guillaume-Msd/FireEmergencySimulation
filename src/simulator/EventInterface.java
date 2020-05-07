package simulator;

import java.io.IOException;
import java.util.List;

public interface EventInterface {

  /** 
   *  Permet d'envoyer les informations relatives aux �v�nement au serveur
   *  @return List<Event>
 * @throws IOException 
   */
  public void sendEvents(List<Event> eventList) throws IOException;

  /** 
   *  R�cup�re des informations relatives aux �v�nements depuis le serveur
   *  @param Event
   */
  public void getAllEvents() throws IOException;

}