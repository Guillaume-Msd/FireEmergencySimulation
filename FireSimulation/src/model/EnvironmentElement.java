package model;


public class EnvironmentElement implements ElementInterface {

	private int capacity;
	
	private int quantity;
	
	private Coord location;
	
	private TypeElement type;
	
	public EnvironmentElement(Coord location, int maxCapacity, TypeElement type) {
		this.location = location;
		this.capacity = capacity;
		this.quantity = this.capacity;
		this.type = type;
	}
    
	@Override
	public void supplyVehicule(InterventionVehicule vehicule, LiquidEnum liquidType) {
		vehicule.restoreLiquid(liquidType);
		this.decreaseQuantity(vehicule.getCapacity(liquidType));
	}
	
	public void decreaseQuantity(int liquid) {
		this.quantity -= liquid;
	}

	public int getCapacity() {
		return capacity;
	}

	private void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getQuantity() {
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