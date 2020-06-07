package models;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHeadquarter implements HeadquarterInterface {
	
	private int id;
	
	private List<Staff> personnel;
	
	private Coord coord;
	
	private int nb_vehicules;


	
	public AbstractHeadquarter(Coord coord) {
		this.personnel = new ArrayList<Staff>();
		this.coord = new Coord(coord.x,coord.y);
	}
	
	public AbstractHeadquarter(Coord coord,int nb_vehicules) {
		this(coord);
		this.setNb_vehicules(nb_vehicules);
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

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord emplacement_headquarter) {
		this.coord = emplacement_headquarter;
	}

	


	private void addStaff(Staff staff) {
		this.personnel.add(staff);
	}

	/**
	 * Place le personnel disponible dans le vehicule spï¿½cifiï¿½
	 * @param v (AbstractVehicule)
	 * @param nombre_intervenants prï¿½cise le nombre de personnes ï¿½ envoyer sur l'intervention
	 */
	public void setStaffOnVehicule(AbstractVehicule v, Integer nombre_intervenants) {
		int i = Math.min(v.getTailleMaxStaff(),nombre_intervenants); // On limite le nombre de personnes dans un vï¿½hicule
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
		System.out.println("Pas assez de personnes disponibles et en capacitï¿½ d'intervenir");
	}

	/**
	 * Gï¿½re le retour d'un vï¿½hicule ï¿½ la caserne
	 * @param v
	 */
	public void retourVehicule(AbstractVehicule v) {
		for (Staff s : v.getStaff()) {
			s.setAvailable(true);
			s.seFatiguer();
		}
	}
	
	/**
	 * Permet de faire le plein sur une liste de véhicules
	 */
	public void supplyVehicules(List<AbstractVehicule> v) {
		for (AbstractVehicule vehicule : v) {
			vehicule.fillOil();
		}
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