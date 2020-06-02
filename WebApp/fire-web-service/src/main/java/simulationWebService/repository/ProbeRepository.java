package simulationWebService.repository;

import org.springframework.data.repository.CrudRepository;

import simulationWebService.model.ProbeEntity;

public interface ProbeRepository extends CrudRepository<ProbeEntity, Integer>{

	ProbeEntity getProbeById(String id);

	ProbeEntity getProbeById(int id);



}