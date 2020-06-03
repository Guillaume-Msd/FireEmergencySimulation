package pk.cloudserver.service;

import org.springframework.beans.factory.annotation.Autowired;

import pk.cloudserver.model.ProbDataEntity;
import pk.cloudserver.repository.ProbDataRepository;


public class CloudServerService {
	
	@Autowired
	private ProbDataRepository repository;
	
	public boolean addEntity(ProbDataEntity prob) {
		if(repository.findById(prob.getId()) == null) {
			repository.save(prob);
			return true;
		}
		return false;
	}
	
	public boolean removeEntity() {
		return false;
	}

}
