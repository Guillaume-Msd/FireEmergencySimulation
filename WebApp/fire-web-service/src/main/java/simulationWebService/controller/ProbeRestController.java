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
	 * Add a probe to the server
	 * @param type
	 * @param range
	 * @param x
	 * @param y
	 * @return int the id of the probe created
	 */
	@GetMapping("ProbeWebService/add/{type}/{range}/{x}/{y}")
	public int addProbe(@PathVariable String type, @PathVariable String range, @PathVariable String x, @PathVariable String y) {
		ProbeEntity probe = new ProbeEntity(type, Integer.parseInt(x), Integer.parseInt(y));
		probe.setRange(Integer.parseInt(range));
		probe.setType(type);
		probeService.save(probe);
		return probe.getId();
		
	}
	
	/**
	 * Get the coord of all probes
	 * @return String Json List<Coord>
	 */
	@GetMapping("ProbeWebService/getAllCoords")
	public String getAllFireCoords() {
		List<ProbeEntity> probeList = probeService.getAllProbes();
		List<CoordEntity> coordList = new ArrayList<CoordEntity>();
		for(ProbeEntity probe: probeList) {
			coordList.add(new CoordEntity(probe.getX(), probe.getY()));
		}
		return Tools.toJsonString(coordList);
	}
	

	/**
	 * Get all probes
	 * @return String Json List<Probe>
	 */
	@GetMapping("ProbeWebService/getAll")
	public String getAll() {
		return Tools.toJsonString(probeService.getAllProbes());
	}
	
	/**
	 * Delete all the probes
	 */
	@DeleteMapping("ProbeWebService/removeAll")
	public void removeAll() {
		probeService.removeAllProbes();
	}
	
	
	
	
	
	

}
