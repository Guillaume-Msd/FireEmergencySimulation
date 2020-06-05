package io.sp.webservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.sp.webservice.models.HeadQuarter;
import io.sp.webservice.repository.HeadQuarterRepository;

@Service
public class HeadQuarterService {
	
	@Autowired
	private HeadQuarterRepository headQuarterRepository;
	
	public HeadQuarter getHeadQuarterById(String id) {
		return headQuarterRepository.findById(Integer.parseInt(id));
	}
	
	public List<HeadQuarter> getAll() {
		return headQuarterRepository.findAll();
	}
	
	public long getNumber() {
		return headQuarterRepository.count();
	}
	
	public void addHeadQuarter(HeadQuarter headQuarter) {
		headQuarterRepository.save(headQuarter);
	}
	
	public void HeadQuarter(HeadQuarter headQuarter) {
		headQuarterRepository.save(headQuarter);
	}
	
	public void deleteHeadQuarter(String id) {
		headQuarterRepository.delete(headQuarterRepository.findById(Integer.parseInt(id)));
	}

	public void removeAll() {
		headQuarterRepository.deleteAll();
		
	}
}
