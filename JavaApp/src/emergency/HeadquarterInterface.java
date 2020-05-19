package emergency;


public interface HeadquarterInterface {


  /** 
   *  Link the staff to the vehicules
   *  @param List<Staff>
   *  @param List<Vehicules>
   */
  public void setStaffOnVehicules();

  /** 
   *  Add some quantity of oil to vehicule(s)
   *  @param List<Vehicules>
   *  @param int quantity
   *  @return boolean True if all has been supply, False if there was not enough resources
   */
  public void supplyVehicules();

  /** 
   *  Smatly deploy a vehicule(s) in function of the event
   *  @param Event 
   *  @return True if vehicule has been deployed, False if there is no vehicules available
   */
  public void setVehiculesInEvent();

  /** 
   *  Calcul itinerary for the vehicule to go to the incident
   *  @param Vehicule
   *  @param Event
   *  @return Itinerary
   */
  public void calculItinerary();

}