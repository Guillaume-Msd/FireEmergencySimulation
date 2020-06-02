package simulationWebService.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import simulationWebService.model.CoordEntity;
import simulationWebService.model.ProbeEntity;
import simulationWebService.service.ProbeService;
import simulationWebService.utils.Tools;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProbeRestController {
	
	
	@Autowired 
	ProbeService probeService;
	
	/**
	 * 
	 * @return 
	 */
	@GetMapping("ProbeWebService/add/{type}/{x}/{y}")
	public int addProbe(@PathVariable String type, @PathVariable String x, @PathVariable String y) {
		ProbeEntity probe = new ProbeEntity(type, Integer.parseInt(x), Integer.parseInt(y));
		probeService.save(probe);
		return probe.getId();
		
	}
	
	
	@GetMapping("ProbeWebService/getAllCoords")
	public String getAllFireCoords() {
		List<ProbeEntity> probeList = probeService.getAllProbes();
		List<CoordEntity> coordList = new ArrayList<CoordEntity>();
		for(ProbeEntity probe: probeList) {
			coordList.add(new CoordEntity(probe.getX(), probe.getY()));
		}
		return Tools.toJsonString(coordList);
	}
	

	@DeleteMapping("ProbeWebService/removeAll")
	public void removeAll() {
		probeService.removeAllProbes();
	}
	
	
	
	
	
	

}