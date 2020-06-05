package io.sp.webservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.sp.webservice.models.Element;

public interface ElementRepository extends CrudRepository<Element, Integer> {
	
	public Element findById(int id);
	
	public List<Element> findAll();
	
}
