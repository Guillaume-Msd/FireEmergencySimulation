package io.sp.webservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.sp.webservice.models.Coord;
import io.sp.webservice.models.EnumStatut;
import io.sp.webservice.models.Vehicule;
import io.sp.webservice.service.VehiculeService;
import utilities.Tools;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VehiculeController {

	@Autowired
	private VehiculeService vehiculeService;
	
	/**
	 * Return all vehicules
	 * @return String Json List<Vehicule>
	 */
	@GetMapping("VehiculeWebService/allVehicules")
	public String getAllVehicules() {
		List<Vehicule> liste = vehiculeService.getAll();
		if (liste != null) {
			return Tools.toJsonString(liste);
		}
		else {
			return Tools.toJsonString(new ArrayList<Vehicule>());
		}
	}
	
	/**
	 * Get the vehicule by id
	 * @param id
	 * @return String Json Vehicule
	 */
	@GetMapping("VehiculeWebService/vehicule/{id}")
	public String getVehicule(@PathVariable String id) {
		return Tools.toJsonString(vehiculeService.getVehiculeById(id));
	}
	
	/**
	 * Get all the vehicules which have the statut specified 
	 * @param statut
	 * @return String Json List<Vehicule>
	 */
	@GetMapping("VehiculeWebService/vehiculesByStatut/{statut}")
	public String getVehiculeByStatut(@PathVariable EnumStatut statut) {
		List<Vehicule> liste = vehiculeService.getVehiculesByStatut(statut);
		if (liste != null) {
			return Tools.toJsonString(liste);
		}
		else {
			return Tools.toJsonString(new ArrayList<Vehicule>());
		}
	}
	
	/**
	 * Add a vehicule to the server
	 * @param vehicule
	 * @param x
	 * @param y
	 * @return the id of the vehicule created
	 */
	@PostMapping("VehiculeWebService/addVehicule/{x}/{y}")
	public int addVehicule(@RequestBody Vehicule vehicule, @PathVariable String x, @PathVariable String y) {
		Coord coord = new Coord(Integer.parseInt(x), Integer.parseInt(y));
		vehicule.setCoord(coord);
		vehicule.convertDestinationToCoord();
		vehiculeService.addVehicule(vehicule);
		return vehicule.getId();
	}
	
	/**
	 * Update the coord of the vehicule which id is those specified
	 * @param id
	 * @param coord
	 */
	@RequestMapping("VehiculeWebService/updateVehiculeCoord/{id}")
	public void updateVehiculeCoord(@PathVariable String id,@RequestBody Coord coord) {
		Vehicule vehicule = vehiculeService.getVehiculeById(id);
		vehicule.setCoord(coord);
		vehiculeService.updateVehicule(vehicule);
	}
	
	/**
	 * Update the statut of th evehicule <hich id is those specified
	 * @param id
	 * @param statut
	 */
	@RequestMapping("VehiculeWebService/updateVehiculeStatut/{id}")
	public void updateVehiculeStatut(@PathVariable String id, @RequestBody EnumStatut statut) {
		Vehicule vehicule = vehiculeService.getVehiculeById(id);
		vehicule.setStatut(statut);
		vehiculeService.updateVehicule(vehicule);
	}
	
	@RequestMapping("VehiculeWebService/updateVehiculeWater/{id}")
	public void updateVehiculeWater(@PathVariable String id,@RequestBody double quantiteEau) {
		Vehicule vehicule = vehiculeService.getVehiculeById(id);
		vehicule.setQuantiteEau(quantiteEau);
		vehiculeService.updateVehicule(vehicule);
	}
	
	@DeleteMapping("VehiculeWebService/deleteVehicule/{id}")
	public void deleteVehicule(@PathVariable String id ) {
		vehiculeService.deleteVehicule(id);
	}
	
	/**
	 * *Get the coord of all vehicules
	 * @return String Json List<Coord>
	 */
	@GetMapping("VehiculeWebService/getAllCoords")
	public String getAllFireCoords() {
		List<Vehicule> vehiculeList = vehiculeService.getAll();
		List<Coord> coordList = new ArrayList<Coord>();
		for(Vehicule vehicule: vehiculeList) {
			coordList.add(vehicule.getCoord());
		}
		return Tools.toJsonString(coordList);
	}
}
