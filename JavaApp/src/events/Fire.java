package events;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author Fabien Puissant
 *
 */
public class Fire extends Event implements FireInterface {

	private FireType type;
	private FireIntensity intensity;

	public Fire(Coord coord, FireType type, FireIntensity intensity) {
		super();
		this.addCoord(coord);
		this.intensity = intensity;
		this.type = type;

	}
	
	public Fire(@JsonProperty("id")String id, 
			@JsonProperty("type")String type, 
			@JsonProperty("intensity")String intensity, 
			@JsonProperty("location")Set<Coord> location) {
		super();
		this.setId(Integer.parseInt(id));
		this.setIntensity(FireIntensity.valueOf(intensity));
		this.setType(FireType.valueOf(type));
		this.setLocalisation(location);
	}


	public FireType getType() {
		return type;
	}


	/**
	 * @param type
	 */
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
			    //
			}
			
			else{//augmentation de l'intensité
				FireIntensity fireIntensity = FireIntensity.aggravation(this.getIntensity());
			    setIntensity(fireIntensity);
			}
			    
						
	}
	
	public void attenuation() {
		
		//si véhicule
			//TODO delete fire
		    if (getIntensity() == FireIntensity.Low) {    					//extinction si intensité min
		    	//Events.deleteEvent(Fire);
		    }
		    else {	//diminution de l'intensité (marche pas)
				FireIntensity fireIntensity = FireIntensity.attenuation(this.getIntensity());
			    setIntensity(fireIntensity);
				    }	

	}
	
	public String toJsonString() {
		return "{ \"intensity\" : \"" + this.intensity +"\", \"type\": \"" + this.type +"\" }";
		
		
		
	}
}