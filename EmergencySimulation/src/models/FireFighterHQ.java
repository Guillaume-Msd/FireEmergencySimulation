package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
	
public class FireFighterHQ extends AbstractHeadquarter {
	
	public FireFighterHQ() {
		
	}
	
	private List<VehiculePompier> vehicules;
	
	
	public FireFighterHQ(Coord coord) {
		super(coord);
		this.vehicules = new ArrayList<VehiculePompier>();
	}
	
	public FireFighterHQ(Coord coord,int nb_vehicules) {
		super(coord,nb_vehicules);
		this.vehicules = new ArrayList<VehiculePompier>();
		for (int i=0;i<nb_vehicules;i++) {
			this.addVehicule(new VehiculePompier());
		}
		for (AbstractVehicule vehicule : this.getVehicules()) {
			vehicule.setCoord(super.getCoord());
			vehicule.setCoord_HQ(super.getCoord());
		}
	}

	public List<VehiculePompier> getVehicules() {
		return vehicules;
	}
	
	public void setVehicules(List<VehiculePompier> vehicules) {
		this.vehicules = vehicules;
	}
	
	public void addVehicule(VehiculePompier v) {
		this.vehicules.add(v);
	}
	
	public List<VehiculeLutteIncendie> getVehiculesIncendie() {
		List<VehiculePompier> tousVehicules = this.getVehicules();
		List<VehiculeLutteIncendie> vehiculesIncendie = new ArrayList<VehiculeLutteIncendie>();
		
		for (AbstractVehicule v : tousVehicules) {
			if (v instanceof VehiculeLutteIncendie) {
				vehiculesIncendie.add((VehiculeLutteIncendie) v);
			}
		}
		return vehiculesIncendie;
	}
	
	public List<VehiculeLutteIncendie> ChoisirVehiculeIncendie(Alerte alerte) throws IOException {
		int nb_vehicules;
		List<VehiculeLutteIncendie> vehicules= new ArrayList<VehiculeLutteIncendie> ();
		if (alerte.getIntensity() < 15) { //15 est une valeur arbitraire
			nb_vehicules = 1;
		}
		else {
			nb_vehicules = 2;
		}
		
		
		for (VehiculeLutteIncendie v: this.getVehiculesIncendie()) {
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
		System.out.println("Pas assez de v�hicules disponibles.");
		return vehicules;
	}
}