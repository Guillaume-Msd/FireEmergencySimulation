package model;

public interface FireInterface {

  /** 
   *  Propagation des feux (cr�ation d'un nouveau feu de moindre importance � proximit�
   *  du feu initial) en l�absence d'intervention
   *  @return void
   */
  public Coord aggravate();

  /** 
   *  La vitesse d�att�nuation du feu d�pend du nombre de v�hicules d�intervention �
   *  proximit� et de leur type 
   *  @param List<Vehicule>  List of vehicules fighting the fire
   *  @return void
   */
  public Coord attenuate();

}