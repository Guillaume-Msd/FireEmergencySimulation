package models;

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
}