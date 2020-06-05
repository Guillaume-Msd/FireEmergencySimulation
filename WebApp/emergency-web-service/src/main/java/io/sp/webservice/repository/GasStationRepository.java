package io.sp.webservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.sp.webservice.models.GasStation;


public interface GasStationRepository extends CrudRepository<GasStation, Integer> {
	
	public GasStation findById(int id);
	
	public List<GasStation> findAll();
	

}
