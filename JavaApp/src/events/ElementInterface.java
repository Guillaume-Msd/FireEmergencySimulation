package events;


public interface ElementInterface {

  /** 
   *  Ravitailler les véhicules lorsque ces derniers sont à proximité (vitesse et temps de
   *  ravitaillement sont des extensions possibles
   *  @param int Temps de ravitaillement
   */
  public void supplyVehicule();

}
