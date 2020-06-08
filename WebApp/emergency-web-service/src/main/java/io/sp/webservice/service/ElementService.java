package io.sp.webservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.sp.webservice.models.Element;
import io.sp.webservice.repository.ElementRepository;

@Service
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
	
	public void deleteElement(String id) {
		elementRepository.delete(this.getElementById(id));
	}

	public void removeAll() {
		elementRepository.deleteAll();
		
	}
}
