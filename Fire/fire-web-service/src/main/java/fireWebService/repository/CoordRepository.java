package fireWebService.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fireWebService.model.CoordEntity;
import fireWebService.model.FireEntity;

public interface CoordRepository extends CrudRepository<CoordEntity, Integer>{

	FireEntity getFireById(String id);

	FireEntity getFireById(int id);

	CoordEntity getCoordById(int id);

	List<CoordEntity> findByXAndY(int x, int y);

}
