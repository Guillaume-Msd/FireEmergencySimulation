package fireWebService.repository;

import org.springframework.data.repository.CrudRepository;

import fireWebService.model.FireEntity;

public interface FireRepository extends CrudRepository<FireEntity, Integer>{

	FireEntity getFireById(String id);

	FireEntity getFireById(int id);



}
