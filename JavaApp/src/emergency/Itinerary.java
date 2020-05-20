package emergency;

import java.util.List;


public class Itinerary {

	private List<Coord> Path;

	public float Time;
	
	
	public Itinerary(List<Coord> path, float time) {
		this.Path = path;
		this.Time = time;
	}
	/** 
	*  Calculate the time to go to the itinerary in function of the type of the vehicule
	*  @param Vehicule
	*  @return double Time to travel
	*/
	public double calculTime(AbstractVehicule v) {
	  return 0.0;
  }

}