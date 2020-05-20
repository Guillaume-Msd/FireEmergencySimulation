package fireWebService.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fireWebService.model.CoordEntity;
import fireWebService.model.FireEntity;
import fireWebService.service.CoordService;
import fireWebService.service.FireService;
import fireWebService.utils.Tools;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FireRestController {
	
	
	@Autowired 
	FireService fireService;
	
	@Autowired
	CoordService coordService;
	
	/**
	 * 
	 * @return 
	 */
	@RequestMapping(value="FireWebService/add/{x}/{y}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void init(@RequestBody FireEntity fire, @PathVariable String x, @PathVariable String y) {
		fire.addCoord(Integer.parseInt(x), Integer.parseInt(y));
		fireService.addFire(fire);
		List<FireEntity> fires = fireService.getAllFires();
	}
	
	@PostMapping(value="FireWebService/propagate/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void propagateFire(@PathVariable String id) {
		FireEntity fire = fireService.getFireById(id);
	}
	
	
	@GetMapping("FireWebService/getAll")
	public String getAllFireCoords() {
		List<FireEntity> fireList = fireService.getAllFires();
		List<CoordEntity> coordList = new ArrayList<CoordEntity>();
		for(FireEntity fire: fireList) {
			Iterator<CoordEntity> it = fire.getLocation().iterator();
			while(it.hasNext()){
				coordList.add(it.next());
		     }
		}
		return Tools.toJsonString(coordList);
		
		
	}
	
	@PostMapping("FireWebService/aggravation/{id}/{intensity}")
	public void aggrave(@RequestBody CoordEntity coord, @PathVariable String id, @PathVariable String intensity) {
		
		FireEntity fire = fireService.getFireById(Integer.parseInt(id));
		fire.addCoord(coord.getX(), coord.getY());
		fire.setIntensity(intensity);
		fireService.save(fire);
			
	}
	
	@PostMapping("FireWebService/attenuation/{id}/{intensity}")
	public void attenue(@RequestBody CoordEntity coord, @PathVariable String id, @PathVariable String intensity) {
		System.out.println(id);
		FireEntity fire = fireService.getFireById(Integer.parseInt(id));
		fire.removeCoord(coordService.getCoordToRemove(coord.getX(), coord.getY()));
		fire.setIntensity(intensity);
		fireService.save(fire);
		return;
			
	}
	
	
	@GetMapping("FireWebService/remove/{id}")
	public void removeFire(@PathVariable String id) {
		FireEntity fire = fireService.getFireById(Integer.parseInt(id));
		fireService.delete(fire);
	}
	
	
	@GetMapping("FireWebService/removeAll")
	public void removeAll() {
		fireService.removeAllFire();
	}
	
	@GetMapping("FireWebService/events")
	public String getAllFires() {
		return Tools.toJsonString(fireService.getAllFires());
	}
	
	
	
	
	
	

}
