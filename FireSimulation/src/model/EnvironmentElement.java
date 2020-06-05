package model;


public class EnvironmentElement implements ElementInterface {

	private int maxCapacity;
	
	private int actualCapacity;
	
	private Coord location;
	
	private TypeElement type;
	
	public EnvironmentElement(Coord location, int maxCapacity, TypeElement type) {
		this.location = location;
		this.maxCapacity = maxCapacity;
		this.actualCapacity = this.maxCapacity;
		this.setType(type);
	}
    
	@Override
	public void supplyVehicule(InterventionVehicule vehicule) {
		//TODO Set le liquide du camion à la valeur de sa capactité max
		
	}
	
	public void decreaseActualCapacity(int liquid) {
		this.actualCapacity -= liquid;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	private void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getActualCapacity() {
		return actualCapacity;
	}

	public void setActualCapacity(int actualCapacity) {
		this.actualCapacity = actualCapacity;
	}

	public Coord getLocation() {
		return location;
	}

	public void setLocation(Coord location) {
		this.location = location;
	}

	public TypeElement getType() {
		return type;
	}

	private void setType(TypeElement type) {
		this.type = type;
	}
	
}