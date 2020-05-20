package emergency;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractVehicule extends Intervernors implements VehiculeInterface {

  private List<Staff> staff;
  
  private Integer tailleMaxStaff;

  private Integer OilQuantity;

  private EnumEtat etat;
  
  private Integer NormalSpeed;
	
  private Integer OilCapacity;
  
  private Integer NormalOilConsumption;  //En L/100km
	
  	protected AbstractVehicule(Integer speed, Integer oilCapacity, Integer consumption, Integer tailleMaxStaff) {
  		this.NormalSpeed = speed;
  		this.NormalOilConsumption = consumption;
  		this.OilCapacity = oilCapacity;
  		this.tailleMaxStaff = tailleMaxStaff;
  		
  		this.staff = new LinkedList<Staff>();
  		this.etat = EnumEtat.Neuf;
  		this.OilQuantity = this.OilCapacity;
  	}
  	
  	protected AbstractVehicule() {
  		this(45,600,10,8);
  	}
  	
	@Override
	public void sendInformation() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void getInformation() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void putOutFire() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void manageStaff() {
		// TODO Auto-generated method stub
	
	}

	/**
	 * @return the etat
	 */
	public EnumEtat getEtat() {
		return etat;
	}

	/**
	 * @param etat the etat to set
	 */
	public void setEtat(EnumEtat etat) {
		this.etat = etat;
	}

	/**
	 * @return the staff
	 */
	public List<Staff> getStaff() {
		return staff;
	}

	/**
	 * @param staff the staff to set
	 */
	public void setStaff(List<Staff> staff) {
		this.staff = staff;
	}
	
	/**
	 * 
	 * @param s personne à ajouter au staff
	 */
	public void addToStaff(Staff s) {
		this.staff.add(s);
	}
	
	/**
	 * 
	 * @param staff liste des personnes à ajouter au staff
	 */
	public void addToStaff(List<Staff> staff) {
		for (Staff s : staff) {
			this.staff.add(s);
		}
	}
	
	public int getQuantityToFill() {
		return this.OilCapacity - this.OilQuantity;
	}
	
	public void fillOil() {
		this.OilQuantity = this.OilCapacity;
	}

	/**
	 * @return the tailleMaxStaff
	 */
	public Integer getTailleMaxStaff() {
		return tailleMaxStaff;
	}

	/**
	 * @return the normalSpeed
	 */
	public Integer getNormalSpeed() {
		return NormalSpeed;
	}

	/**
	 * @return the normalOilConsumption
	 */
	public Integer getNormalOilConsumption() {
		return NormalOilConsumption;
	}
}
