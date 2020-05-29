package simulator;

public interface EventInterface {

  /** 
   *  Permet d'envoyer les informations relatives aux événement au serveur
   *  @return List<Event>
   */
  public void sendEvents();

  /** 
   *  Récupère des informations relatives aux événements depuis le serveur
   *  @param Event
   */
  public void getEvents();

}