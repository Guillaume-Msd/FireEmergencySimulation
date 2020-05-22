package io.sp.webservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import io.sp.webservice.models.HeadQuarter;

public interface HeadQuarterRepository extends CrudRepository<HeadQuarter, Integer> {
	
	public HeadQuarter findById(int id);
	
	public List<HeadQuarter> findAll();
	

}
