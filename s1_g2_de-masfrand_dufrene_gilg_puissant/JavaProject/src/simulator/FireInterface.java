package simulator;

public interface FireInterface {

  /** 
   *  Propagation des feux (création d'un nouveau feu de moindre importance à proximité
   *  du feu initial) en l’absence d'intervention
   *  @return void
   */
  public void propagation();

  /** 
   *  La vitesse d’atténuation du feu dépend du nombre de véhicules d’intervention à
   *  proximité et de leur type 
   *  @param List<Vehicule>  List of vehicules fighting the fire
   *  @return void
   */
  public void attenuation();

}