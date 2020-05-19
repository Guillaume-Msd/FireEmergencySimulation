package io.sp.webservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.sp.webservice.models.Alerte;

public interface AlerteRepository extends CrudRepository<Alerte, Integer> {
	
	public Alerte findById(int id);
	
	public List<Alerte> findAll();
	
	public long count();
}
