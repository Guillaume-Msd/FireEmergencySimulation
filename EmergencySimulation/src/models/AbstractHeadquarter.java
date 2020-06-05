package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractHeadquarter extends AbstractIntervention implements HeadquarterInterface {
	
	private int id;
	
	private List<Staff> personnel;
	
	private Coord coord;
	
	private List<AbstractVehicule> vehicules;
	
	private int nb_vehicules;


	
	public AbstractHeadquarter(Coord coord) {
		this.personnel = new LinkedList<Staff>();
		this.coord = new Coord(coord.x,coord.y);
		this.vehicules = new LinkedList<AbstractVehicule>();
	}
	

	
	public void updateVehiculeList() {
		for (int i=0;i<this.nb_vehicules;i++) {
			this.addVehicule(new VehiculePompier());
			for (int j=0;j<8;j++) {
				this.addStaff(new Staff());
			}
			
		}
		
		for (AbstractVehicule vehicule : this.getVehicules()) {
			vehicule.setCoord(this.getCoord());
			vehicule.setCoord_HQ(this.getCoord());
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

	public List<AbstractVehicule> getVehicules() {
		return vehicules;
	}

	public void setVehicules(List<AbstractVehicule> vehicules) {
		this.vehicules = vehicules;
	}
	
	public void addVehicule(AbstractVehicule v) {
		this.vehicules.add(v);
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

	public List<AbstractVehicule> ChoisirVehicule(Alerte alerte) throws IOException {
		int nb_vehicules;
		List<AbstractVehicule> vehicules= new ArrayList<AbstractVehicule> ();
		if (alerte.getIntensity() < 15) { //15 est une valeur arbitraire
			nb_vehicules = 1;
		}
		else {
			nb_vehicules = 2;
		}
		
		
		for (AbstractVehicule v: this.getVehicules()) {
			if (nb_vehicules <= 0) {
				return vehicules;
			}
			if (v.getStatut().equals(EnumStatut.Disponible)) {
				vehicules.add(v);
				nb_vehicules = nb_vehicules - 1;
			}
		}
		if (nb_vehicules <= 0) {
			return vehicules;
		}
		System.out.println("Pas assez de véhicules disponibles.");
		return vehicules;
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