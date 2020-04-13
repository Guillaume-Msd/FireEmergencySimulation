package simulator;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

import utilities.Coord;

public class Fire extends Event implements FireInterface {

	private FireType type;
	private FireIntensity intensity;

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
		/*if() {					//si pas de véhicule
	
			Set<FireIntensity> intensities = EnumSet.allOf(FireIntensity.class);  //augmentation de l'intensité
				Iterator <FireIntensity> it = intensities.iterator();
			    if ( it != null && it.hasNext() ) 
			      {
			    	setIntensity(it.next());
			      }
			    if (getIntensity() == FireIntensity.VeryHigh) {    					//nouveau feu si intensité max
			    	new Fire(new Coord(0,1), FireType.ClassA, FireIntensity.Low);
			    }
		}*/
	}
	
	public void attenuation() {
		/*if () {						//si véhicule
			
		    if (getIntensity() == FireIntensity.Low) {    					//extinction si intensité min
		    	
		    }
		}*/
	}

}