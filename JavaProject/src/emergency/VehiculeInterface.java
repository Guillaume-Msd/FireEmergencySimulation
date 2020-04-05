package emergency;

public interface VehiculeInterface {

  /** 
   *  Follow the itinerary given by the headquarter with the speed depending of the vehicule
   *  @param Itinerary
   *  
   */
  public void move();

  /** 
   *  Put out the fire (efficiency depending of the type of the vehicule)
   *  @param Event
   *  @return Event new state of the event
   */
  public void putOutFire();

  /** 
   *  Manage tiredness of the staff in the vehicule
   */
  public void manageStaff();

}