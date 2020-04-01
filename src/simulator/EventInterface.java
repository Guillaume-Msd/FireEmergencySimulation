package simulator;

public interface EventInterface {

  /** 
   *  Permet d'envoyer les informations relatives aux �v�nement au serveur
   *  @return List<Event>
   */
  public void sendEvents();

  /** 
   *  R�cup�re des informations relatives aux �v�nements depuis le serveur
   *  @param Event
   */
  public void getEvents();

}