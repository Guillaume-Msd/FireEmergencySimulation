package models;

public class VehiculePompier extends AbstractVehicule {
	
	private Integer InterventionSpeed;
	
	private Integer InterventionOilConsumption;  //En L/100km
	
	private String type = "VehiculePompier";

	
	public VehiculePompier(Integer speed, Integer consumption) {
		super();
		this.InterventionSpeed = speed;
		this.InterventionOilConsumption = consumption;
	}
	
	public VehiculePompier() {
		this(75,40);
	}
	
	/**
	 * @return the interventionOilConsumption
	 */
	public Integer getInterventionOilConsumption() {
		return InterventionOilConsumption;
	}
	
	/**
	 * 
	 * @return the InterventionSpeed
	 */
	public Integer getInterventionSpeed() {
		return InterventionSpeed;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}