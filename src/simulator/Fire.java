package simulator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

import utilities.Coord;

public class Fire extends Event implements FireInterface {

	private FireType type;
	private FireIntensity intensity;

	public Fire(Set <Coord> localisation, FireType type, FireIntensity intensity) {
		super(localisation);
		this.intensity = intensity;
		this.type = type;
	}
	

	public FireType getType() {
		return type;
	}


	public void setType(FireType type) {
		this.type = type;
	}


	public FireIntensity getIntensity() {
		return intensity;
	}


	public void setIntensity(FireIntensity intensity) {
		this.intensity = intensity;
	}


	public String toString() {
		return super.toString() + ", " + getType() + ", " + getIntensity();
	}
	
	public void aggravation() {
			
			//TODO create new fire
			if (getIntensity() == FireIntensity.VeryHigh) {    					//nouveau feu si intensité max
			    //Events.createEvent(Fire);
			}
			
			else{	//augmentation de l'intensité
				Iterator <FireIntensity> it = FireIntensity.intensities.iterator();
				while(it.hasNext()) {
				   	FireIntensity intensity = it.next();
				    if (intensity == this.getIntensity()) {
				    	setIntensity(it.next());
				    	break;
			    	}
				}
			}
	}
	
	public void attenuation() {
		
		//si véhicule
			//TODO delete fire
		    if (getIntensity() == FireIntensity.Low) {    					//extinction si intensité min
		    	//Events.deleteEvent(Fire);
		    }
		    else {	//diminution de l'intensité (marche pas)
				ListIterator <FireIntensity> it = FireIntensity.listeIntensities.listIterator();
				while(it.hasPrevious()) {
				   	FireIntensity intensity = it.previous();
				    if (intensity == this.getIntensity()) {
				    	setIntensity(it.previous());
				    	break;
				    }
				}	
		    }
	}

}