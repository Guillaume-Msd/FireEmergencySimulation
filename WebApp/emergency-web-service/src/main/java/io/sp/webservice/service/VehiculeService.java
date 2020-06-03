package io.sp.webservice.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.sp.webservice.models.EnumStatut;
import io.sp.webservice.models.Vehicule;
import io.sp.webservice.repository.VehiculeRepository;

@Service
public class VehiculeService {

	@Autowired
	private VehiculeRepository vehiculeRepository;
	
	public Vehicule getVehiculeById(String id) {
		return vehiculeRepository.findById(Integer.parseInt(id));
	}
	
	public List<Vehicule> getAll() {
		return vehiculeRepository.findAll();
	}
	
	public long getCount() {
		return vehiculeRepository.count();
	}
	
	public void addVehicule(Vehicule vehicule) {
		vehiculeRepository.save(vehicule);
	}
	
	public void updateVehicule(Vehicule vehicule) {
		vehiculeRepository.save(vehicule);
	}
	
	public void deleteVehicule(String id) {
		vehiculeRepository.delete(this.getVehiculeById(id));
	}

	public List<Vehicule> getVehiculesByStatut(EnumStatut statut) {
		List<Vehicule> vehicules = this.getAll();
		List<Vehicule> vehicules_retour = new LinkedList<Vehicule>();
		if (vehicules == null) {
			return new LinkedList<Vehicule>();
		}
		for (Vehicule v : vehicules) {
			if (v.getStatut().equals(statut)) {
				vehicules_retour.add(v);
			}
		}
		return vehicules_retour;
	}
}
