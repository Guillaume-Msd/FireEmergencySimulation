package io.sp.webservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
