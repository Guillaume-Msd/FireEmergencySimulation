package simulationWebService.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import simulationWebService.model.CoordEntity;
import simulationWebService.model.FireEntity;
import simulationWebService.repository.FireRepository;

@Service
public class FireService {
	
	@Autowired
	private FireRepository fireRepository;
	
	public void addFire(FireEntity fire) {
		this.fireRepository.save(fire);
	}
	
	
	public List<FireEntity> getAllFires(){
		return (List<FireEntity>) fireRepository.findAll();
	}


	public FireEntity getFireById(String id) {
		return fireRepository.getFireById(id);
	}
	
	public void save(FireEntity fire) {
		fireRepository.save(fire);
	}
	
	
	public void removeAllFire() {
		fireRepository.deleteAll();
	}


	public FireEntity getFireById(int id) {
		return fireRepository.getFireById(id);
	}


	public void delete(FireEntity fire) {
		fireRepository.delete(fire);
		
	}


}
