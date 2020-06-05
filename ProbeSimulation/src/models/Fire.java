package models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;



public class Fire {
	private int id;
	private String type;
	private String intensity;
	private List<CoordEntity> location = new ArrayList<CoordEntity>();
	
	public Fire() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIntensity() {
		return intensity;
	}

	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}

	public List<CoordEntity> getLocation() {
		return location;
	}

	public void setLocation(List<CoordEntity> location) {
		this.location = location;
	}



	
}
