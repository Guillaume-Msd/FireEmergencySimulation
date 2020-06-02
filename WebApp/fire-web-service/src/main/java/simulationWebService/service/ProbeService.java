package simulationWebService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import simulationWebService.model.ProbeEntity;
import simulationWebService.repository.ProbeRepository;

@Service
public class ProbeService {
	
	@Autowired
	private ProbeRepository probeRepository;
	
	public void addFire(ProbeEntity probe) {
		this.probeRepository.save(probe);
	}
	
	
	public List<ProbeEntity> getAllProbes(){
		return (List<ProbeEntity>) probeRepository.findAll();
	}


	public ProbeEntity getProbeById(String id) {
		return probeRepository.getProbeById(id);
	}
	
	public void save(ProbeEntity prob) {
		probeRepository.save(prob);
	}
	
	
	public void removeAllProbes() {
		probeRepository.deleteAll();
	}


	public ProbeEntity getProbeById(int id) {
		return probeRepository.getProbeById(id);
	}


	public void delete(ProbeEntity probe) {
		probeRepository.delete(probe);
		
	}


}
