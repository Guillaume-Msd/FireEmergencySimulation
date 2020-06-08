package simulationWebService.controller;

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

import simulationWebService.model.CoordEntity;
import simulationWebService.model.FireEntity;
import simulationWebService.service.CoordService;
import simulationWebService.service.FireService;
import simulationWebService.utils.Tools;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FireRestController {
	
	
	@Autowired 
	FireService fireService;
	
	@Autowired
	CoordService coordService;
	
	/**
	 * Add a fire to the server
	 * @param fire
	 * @param x
	 * @param y
	 * @return int the id of the fire created
	 */
	@RequestMapping(value="FireWebService/add/{x}/{y}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public int addFire(@RequestBody FireEntity fire, @PathVariable String x, @PathVariable String y) {
		fire.addCoord(Integer.parseInt(x), Integer.parseInt(y));
		fireService.addFire(fire);
		return fire.getId();
	}
	
	/**
	 * Get the coord of all fire
	 * @return String Json List<Coord>
	 */
	@GetMapping("FireWebService/getAllCoords")
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
	
	/**
	 * Aggravate the fire specified
	 * @param coord
	 * @param id
	 * @param intensity
	 */
	@PostMapping("FireWebService/aggravation/{id}/{intensity}")
	public void aggrave(@RequestBody CoordEntity coord, @PathVariable String id, @PathVariable String intensity) {
		
		FireEntity fire = fireService.getFireById(Integer.parseInt(id));
		fire.addCoord(coord.getX(), coord.getY());
		fire.setIntensity(intensity);
		fireService.save(fire);
			
	}
	
	/**
	 * Attenuate the fire specified
	 * @param coord
	 * @param id
	 * @param intensity
	 */
	@PostMapping("FireWebService/attenuation/{id}/{intensity}")
	public void attenue(@RequestBody CoordEntity coord, @PathVariable String id, @PathVariable String intensity) {
		System.out.println(id);
		FireEntity fire = fireService.getFireById(Integer.parseInt(id));
		fire.removeCoord(coordService.getCoordToRemove(coord.getX(), coord.getY()));
		fire.setIntensity(intensity);
		fireService.save(fire);
		return;
			
	}
	
	/**
	 * Add a random fire 
	 */
	@GetMapping("FireWebService/addRandom")
	public void addRandomFire() {
		FireEntity fire = new FireEntity("Random", "Random");
		Random r = new Random();
		fire.addCoord(r.nextInt(255), r.nextInt(255));
		fireService.save(fire);
	}
	
	/**
	 * Remove the fire specified
	 * @param id
	 */
	@GetMapping("FireWebService/remove/{id}")
	public void removeFire(@PathVariable String id) {
		FireEntity fire = fireService.getFireById(Integer.parseInt(id));
		fireService.delete(fire);
	}
	
	/**
	 * Remove all fires
	 */
	@GetMapping("FireWebService/removeAll")
	public void removeAll() {
		fireService.removeAllFire();
	}
	
	/**
	 * Get all fires
	 * @return String Json List<Fire>
	 */
	@GetMapping("FireWebService/getAll")
	public String getAllFires() {
		return Tools.toJsonString(fireService.getAllFires());
	}
	
	
	
	
	
	

}
