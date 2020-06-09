package model;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;


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
	
	/**
	 * aggrave un feu en le propageant
	 *@return Coord
	 */
	public Coord aggravate() {
		Iterator <Coord> it = this.getLocalisation().iterator();
		Coord coord = null;
		while(it.hasNext()) {
			coord = it.next();
		}
		Random r = new Random();
		int randint = r.nextInt(3);
		switch(randint) {
			case 0:
				return new Coord(coord.x+3, coord.y);
			case 1:
				return new Coord(coord.x, coord.y+3);
			case 2:
				return new Coord(coord.x-3, coord.y);
			default:
				return new Coord(coord.x, coord.y-3);				
		}
	}
	
	/**
	 * renvoie la coord à supprimer
	 *@return Coord
	 */
	public Coord attenuate() {
		
		Iterator <Coord> it = this.getLocalisation().iterator();
		return it.next();
	}
	
	public void increaseIntensity() {		
		FireIntensity fireIntensity = FireIntensity.aggravation(this.getIntensity());
	    setIntensity(fireIntensity);
	}
	
	public void decreaseIntensity() {
		FireIntensity fireIntensity = FireIntensity.attenuation(this.getIntensity());
	    setIntensity(fireIntensity);

	}
	
	public String toJsonString() {
		return "{ \"intensity\" : \"" + this.intensity +"\", \"type\": \"" + this.type +"\" }";	
		
	}
}