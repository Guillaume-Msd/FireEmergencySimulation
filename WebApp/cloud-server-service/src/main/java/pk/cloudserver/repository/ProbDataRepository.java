package pk.cloudserver.repository;

import org.springframework.data.repository.CrudRepository;

import pk.cloudserver.model.ProbDataEntity;

public interface ProbDataRepository extends CrudRepository<ProbDataEntity, Integer> {
	
	public ProbDataEntity findById(int id);

}
