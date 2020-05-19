package emergency;

import java.util.List;

public class AbstractVehicule extends Intervernors implements VehiculeInterface {

  private Integer StaffNeeded;

  private LiquidEnum Liquid;

  private List<Integer> LiquidQuantity;

  private List<Integer> OilQuantity;

  private Integer Speed;

	
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
}
