package emergency;

import java.util.List;

public interface HeadquarterInterface {


  /** 
   *  Link the staff to the vehicules
   *  @param Vehicule
   *  @param Integer
   */
  public void setStaffOnVehicule(AbstractVehicule v, Integer nombre_intervenants);

  /** 
   *  Add some quantity of oil to vehicule(s)
   *  @param List<Vehicules>
   *  @param int quantity
   *  @return boolean True if all has been supply, False if there was not enough resources
   */
  public void supplyVehicules(List<AbstractVehicule> v);

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