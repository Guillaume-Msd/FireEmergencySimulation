package model;


public class Element implements ElementInterface {
	
	private int id;
	
	private double capacity;
	
	private double quantity;
	
	private Coord location;
	
	private TypeElement type;
	
	public Element(Coord location, int maxCapacity, TypeElement type) {
		this.location = location;
		this.capacity = capacity;
		this.quantity = this.capacity;
		this.type = type;
	}
    
	@Override
	public void supplyVehicule(Vehicule vehicule, LiquidEnum liquidType) {
		vehicule.restoreLiquid(liquidType);
		this.decreaseQuantity(vehicule.getCapacity(liquidType));
	}
	
	public void decreaseQuantity(double liquid) {
		this.quantity -= liquid;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCapacity() {
		return capacity;
	}

	public Coord getLocation() {
		return location;
	}

	public void setLocation(Coord location) {
		this.location = location;
	}

	private void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
		return "{ \"capacity\" : \""  + this.capacity + "\", \"quantity\": \"" + this.quantity + "\", \"type\": \"" + this.type +"\" }";	
		
	}
}