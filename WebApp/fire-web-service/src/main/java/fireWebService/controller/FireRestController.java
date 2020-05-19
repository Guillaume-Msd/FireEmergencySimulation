package fireWebService.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fireWebService.model.CoordEntity;
import fireWebService.model.FireEntity;
import fireWebService.service.FireService;
import fireWebService.utils.Tools;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FireRestController {
	
	
	@Autowired 
	FireService fireService;
	
	/**
	 * 
	 * @return 
	 */
	@GetMapping("FireWebService/add")
	public String init() {
		FireEntity fire = new FireEntity("doux", "faible");
		Random r = new Random();
		fire.addCoord(r.nextInt(63), r.nextInt(63));
		fireService.addFire(fire);
		List<FireEntity> fires = fireService.getAllFires();
		return fires.get(0).getLocation().toString();
		
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
	
	@GetMapping("FireWebService/removeAll")
	public void removeAll() {
		fireService.removeAllFire();
	}
	
	
	
	
	
	

}
