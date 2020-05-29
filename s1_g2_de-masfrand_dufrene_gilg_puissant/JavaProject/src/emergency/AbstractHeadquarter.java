package emergency;

import java.util.LinkedList;
import java.util.List;

public class AbstractHeadquarter extends AbstractIntervention implements HeadquarterInterface {
	
	List<Staff> personnel;
	
	Coord emplacement_headquarter;
	
	List<VehiculeInterface> vehicules;

	public AbstractHeadquarter(Coord coord) {
		this.personnel = new LinkedList<Staff>();
		this.emplacement_headquarter = new Coord(coord.x,coord.y);
		this.vehicules = new LinkedList<VehiculeInterface>();
	}
	
	public AbstractHeadquarter() {
		this(new Coord(0,0));
	}
	
	@Override
	public void sendInformation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getInformation() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Place le personnel disponible dans le vehicule sp�cifi�
	 * @param v (AbstractVehicule)
	 * @param nombre_intervenants pr�cise le nombre de personnes � envoyer sur l'intervention
	 */
	public void setStaffOnVehicule(AbstractVehicule v, Integer nombre_intervenants) {
		int i = Math.min(v.getTailleMaxStaff(),nombre_intervenants); // On limite le nombre de personnes dans un v�hicule
		for (Staff personne: this.personnel) {
			if (i <= 0) {
				return;
			}
			if (personne.isAvailable() && personne.getTiredState() != TiredStateEnum.Incapacite_a_intervenir) {
				personne.setAvailable(false);
				v.addToStaff(personne);
				i = i - 1;
			}
		}
		System.out.println("Pas assez de personnes disponibles et en capacit� d'intervenir");
	}

	/**
	 * G�re le retour d'un v�hicule � la caserne
	 * @param v
	 */
	public void retourVehicule(AbstractVehicule v) {
		for (Staff s : v.getStaff()) {
			s.setAvailable(true);
			s.seFatiguer();
		}
	}
	
	@Override
	public void supplyVehicules(List<AbstractVehicule> v) {
		// TODO Auto-generated method stub
		for (AbstractVehicule vehicule : v) {
			vehicule.fillOil();
		}
	}

	@Override
	public void setVehiculesInEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculItinerary() {
		// TODO Auto-generated method stub
		
	}
}