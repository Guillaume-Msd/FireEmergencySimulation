package model;

import java.util.Iterator;
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

	public Coord aggravate() {
		Iterator <Coord> it = this.getLocalisation().iterator();
		Coord coord = it.next();
		switch (this.getIntensity()) {
			case Low:
				return new Coord(coord.x+1, coord.y);
			case Medium:
				return new Coord(coord.x, coord.y+1);
			case High:
				return new Coord(coord.x-1, coord.y);
			case VeryHigh:
				//TODO new Fire
			default : 
				return null;
		}
	}
	
	public Coord attenuate() {
		
		Iterator <Coord> it = this.getLocalisation().iterator();
		return it.next();
	}
	
	public void increaseIntensity() {
			
		FireIntensity fireIntensity = FireIntensity.aggravation(this.getIntensity());
	    setIntensity(fireIntensity);
						
	}
	
	public void decreaseIntensity() {
		
		//TODO delete fire
	    //if (getIntensity() == FireIntensity.Low) {    
	    //}
		
		FireIntensity fireIntensity = FireIntensity.attenuation(this.getIntensity());
	    setIntensity(fireIntensity);

	}
	
	public String toJsonString() {
		return "{ \"intensity\" : \"" + this.intensity +"\", \"type\": \"" + this.type +"\" }";
		
		
		
	}
}