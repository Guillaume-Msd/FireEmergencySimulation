package io.sp.webservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.sp.webservice.models.Alerte;
import io.sp.webservice.repository.AlerteRepository;

@Service
public class AlerteService {
	
	@Autowired
	private AlerteRepository alerteRepository;
	
	public Alerte getAlerteById(String id) {
		return alerteRepository.findById(Integer.parseInt(id));
	}
	
	public List<Alerte> getAll() {
		return alerteRepository.findAll();
	}
	
	public long getNumber() {
		return alerteRepository.count();
	}
	
	public void addAlerte(Alerte alerte) {
		alerteRepository.save(alerte);
	}
	
	public void updateAlerte(Alerte alerte) {
		alerteRepository.save(alerte);
	}
	
	public void deleteAlerte(String id) {
		alerteRepository.delete(alerteRepository.findById(Integer.parseInt(id)));
	}
}
