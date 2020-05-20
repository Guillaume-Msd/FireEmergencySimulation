package emergency;

public class VehiculePompier extends AbstractVehicule {
	
	private Integer InterventionSpeed;
	
	private Integer InterventionOilConsumption;  //En L/100km

	
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
}