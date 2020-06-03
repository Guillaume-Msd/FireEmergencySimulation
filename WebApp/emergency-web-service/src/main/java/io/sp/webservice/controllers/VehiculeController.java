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
	
	@GetMapping("VehiculeWebService/vehicule/{id}")
	public String getVehicule(@PathVariable String id) {
		return Tools.toJsonString(vehiculeService.getVehiculeById(id));
	}
	
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
	
	@PostMapping("VehiculeWebService/addVehicule/{x}/{y}")
	public int addVehicule(@RequestBody Vehicule vehicule, @PathVariable String x, @PathVariable String y) {
		Coord coord = new Coord(Integer.parseInt(x), Integer.parseInt(y));
		vehicule.setCoord(coord);
		vehiculeService.addVehicule(vehicule);
		return vehicule.getId();
	}
	
	@RequestMapping("VehiculeWebService/updateVehiculeCoord/{id}")
	public void updateVehiculeCoord(@PathVariable String id,@RequestBody Coord coord) {
		Vehicule vehicule = vehiculeService.getVehiculeById(id);
		vehicule.setCoord(coord);
		vehiculeService.updateVehicule(vehicule);
	}
	
	@RequestMapping("VehiculeWebService/updateVehiculeStatut/{id}")
	public void updateVehiculeStatut(@PathVariable String id,@RequestBody EnumStatut statut) {
		Vehicule vehicule = vehiculeService.getVehiculeById(id);
		vehicule.setStatut(statut);
		vehiculeService.updateVehicule(vehicule);
	}
	
	@DeleteMapping("VehiculeWebService/deleteVehicule/{id}")
	public void deleteVehicule(@PathVariable String id ) {
		vehiculeService.deleteVehicule(id);
	}
	
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
