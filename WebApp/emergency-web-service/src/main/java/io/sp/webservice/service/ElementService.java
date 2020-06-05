package io.sp.webservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import io.sp.webservice.models.Element;
import io.sp.webservice.repository.ElementRepository;

public class ElementService {
	
	@Autowired
	public ElementRepository elementRepository;
	
	public Element getElementById(String id) {
		return elementRepository.findById(Integer.parseInt(id));	
	}
	
	public List<Element> getAll() {
		return elementRepository.findAll();
	}
	
	public void addElement(Element element) {
		elementRepository.save(element);
	}
	
	public void updateElement(Element element) {
		elementRepository.save(element);
	}
	
	public void deleteElement(String id) {
		elementRepository.delete(this.getElementById(id));
	}
}
