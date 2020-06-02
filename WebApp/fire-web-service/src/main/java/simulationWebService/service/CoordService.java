package simulationWebService.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import simulationWebService.model.CoordEntity;
import simulationWebService.repository.CoordRepository;


@Service
public class CoordService {
	
	@Autowired
	private CoordRepository coordRepository;
	

	
	public void save(CoordEntity coord) {
		coordRepository.save(coord);
	}
	
	public void delete(CoordEntity coord) {
		coordRepository.delete(coord);
	}
	

	public CoordEntity getCoordById(int id) {
		return coordRepository.getCoordById(id);
	}

	public CoordEntity getCoordToRemove(int x, int y) {
		List<CoordEntity> list = coordRepository.findByXAndY(x, y);
		return list.get(0);
	}





}
