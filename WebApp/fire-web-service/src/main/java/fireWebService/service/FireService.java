package fireWebService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fireWebService.model.FireEntity;
import fireWebService.repository.FireRepository;

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
	
	
	public void removeAllFire() {
		fireRepository.deleteAll();
	}

}
