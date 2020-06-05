package models;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHeadquarter extends AbstractIntervention implements HeadquarterInterface {
	
	private int id;
	
	private List<Staff> personnel;
	
	private Coord emplacement_headquarter;
	
	private int nb_vehicules;


	
	public AbstractHeadquarter(Coord coord) {
		this.personnel = new ArrayList<Staff>();
		this.emplacement_headquarter = new Coord(coord.x,coord.y);
	}
	
	public AbstractHeadquarter(Coord coord,int nb_vehicules) {
		this(coord);
		
		for (int i=0;i<nb_vehicules;i++) {
			for (int j=0;j<8;j++) {
				this.addStaff(new Staff());
			}
		}
	}
	

	public AbstractHeadquarter() {
		this(new Coord(0,0));
	}
	
	public List<Staff> getPersonnel() {
		return personnel;
	}

	public void setPersonnel(List<Staff> personnel) {
		this.personnel = personnel;
	}

	public Coord getEmplacement_headquarter() {
		return emplacement_headquarter;
	}

	public void setEmplacement_headquarter(Coord emplacement_headquarter) {
		this.emplacement_headquarter = emplacement_headquarter;
	}

	


	private void addStaff(Staff staff) {
		this.personnel.add(staff);
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
	 * Place le personnel disponible dans le vehicule spécifié
	 * @param v (AbstractVehicule)
	 * @param nombre_intervenants précise le nombre de personnes à envoyer sur l'intervention
	 */
	public void setStaffOnVehicule(AbstractVehicule v, Integer nombre_intervenants) {
		int i = Math.min(v.getTailleMaxStaff(),nombre_intervenants); // On limite le nombre de personnes dans un véhicule
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
		System.out.println("Pas assez de personnes disponibles et en capacité d'intervenir");
	}

	/**
	 * Gère le retour d'un véhicule à la caserne
	 * @param v
	 */
	public void retourVehicule(AbstractVehicule v) {
		for (Staff s : v.getStaff()) {
			s.setAvailable(true);
			s.seFatiguer();
		}
	}
	
	public void supplyVehicules(List<AbstractVehicule> v) {
		// TODO Auto-generated method stub
		for (AbstractVehicule vehicule : v) {
			vehicule.fillOil();
		}
	}

	public void setVehiculesInEvent() {
		// TODO Auto-generated method stub
		
	}

	public void calculItinerary() {
		// TODO Auto-generated method stub
		
	}

	public int getNb_vehicules() {
		return nb_vehicules;
	}

	public void setNb_vehicules(int nb_vehicules) {
		this.nb_vehicules = nb_vehicules;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}