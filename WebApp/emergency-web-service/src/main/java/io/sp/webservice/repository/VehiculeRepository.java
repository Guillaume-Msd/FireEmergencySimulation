package io.sp.webservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.sp.webservice.models.Vehicule;

public interface VehiculeRepository extends CrudRepository<Vehicule,Integer> {
	public Vehicule findById(int id);
	
	public List<Vehicule> findAll();
	
	public long count();

}
