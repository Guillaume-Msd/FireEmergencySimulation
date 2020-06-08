package model;

public class Element {
	
	private int id;
	
	private Coord location;
	
	private TypeElement type;
	
	public Element(Coord location, int maxCapacity, TypeElement type) {
		this.location = location;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Coord getLocation() {
		return location;
	}

	public void setLocation(Coord location) {
		this.location = location;
	}

	public int getX() {
		return this.location.x;
	}
	
	public int getY() {
		return this.location.y;
	}
	
	public TypeElement getType() {
		return type;
	}

	private void setType(TypeElement type) {
		this.type = type;
	}
	
	public String toJsonString() {
		return "{ \"capacity\" : \"" + this.type +"\" }";	
		
	}
}