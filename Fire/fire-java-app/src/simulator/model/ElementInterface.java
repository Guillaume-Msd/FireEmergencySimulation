package simulator.model;

public interface ElementInterface {

  /** 
   *  Ravitailler les v�hicules lorsque ces derniers sont � proximit� (vitesse et temps de
   *  ravitaillement sont des extensions possibles
   *  @param int Temps de ravitaillement
   */
  public void supplyVehicule();

}