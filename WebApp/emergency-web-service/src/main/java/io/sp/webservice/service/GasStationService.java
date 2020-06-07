package io.sp.webservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.sp.webservice.models.GasStation;
import io.sp.webservice.repository.GasStationRepository;

@Service
public class GasStationService {

	@Autowired
	private GasStationRepository gasStationRepository;
	
	public GasStation getgasStationById(String id) {
		return gasStationRepository.findById(Integer.parseInt(id));
	}
	
	public List<GasStation> getAll() {
		return gasStationRepository.findAll();
	}
	
	public long getNumber() {
		return gasStationRepository.count();
	}
	
	public void addgasStation(GasStation gasStation) {
		gasStationRepository.save(gasStation);
	}
	
	public void updateGasStation(GasStation gasStation) {
		gasStationRepository.save(gasStation);
	}
	
	public void deleteGasStation(String gasStation) {
		gasStationRepository.delete(gasStationRepository.findById(Integer.parseInt(gasStation)));
	}

	public void removeAll() {
		gasStationRepository.deleteAll();
		
	}

}
