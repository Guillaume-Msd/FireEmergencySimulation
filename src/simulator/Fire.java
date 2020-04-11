package simulator;

import utilities.Coord;

public class Fire extends Event implements FireInterface {

	private FireType type;
	private FireIntensity intensity;
	private Fire Feu;
	private List<Vehicule> ActiveVehicules;

	public Fire(Coord localisation, FireType type, FireIntensity intensity) {
		super(localisation);
		this.intensity = intensity;
		this.type = type;
	}
	

	public FireType getType() {
		return this.type;
	}



	public void setType(FireType type) {
		this.type = type;
	}



	public FireIntensity getIntensity() {
		return this.intensity;
	}



	public void setIntensity(FireIntensity intensity) {
		this.intensity = intensity;
	}



	public String toString() {
		return super.toString() + ", " + getType() + ", " + getIntensity();
	}
	
	public void propagation() {
		//if (setVehiculesInEvent() == false) {   //provisoire, je savais pas quoi mettre pour l'instant
		//}
	}
	
	public void attenuation(List<Vehicule> ActiveVehicules) {
		
	}

}