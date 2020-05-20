package io.sp.webservice.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.sp.webservice.models.Coord;
import io.sp.webservice.models.Vehicule;
import io.sp.webservice.service.VehiculeService;
import utilities.Tools;

@RestController
public class VehiculeController {

	private VehiculeService vehiculeService;
	
	@GetMapping("/Vehicule/allvehicules")
	public String getAllVehicules() {
		List<Vehicule> liste = vehiculeService.getAll();
		String vehicules = "";
		for (Vehicule v:liste) {
			vehicules = vehicules + Tools.toJsonString(v) + "/";
		}
		return vehicules.substring(0, vehicules.length()-1);
	}
	
	@GetMapping("/Vehicule/vehicule/{id}")
	public String getVehicule(@PathVariable String id) {
		return Tools.toJsonString(vehiculeService.getVehiculeById(id));
	}
	
	@PostMapping("/Vehicule/addVehicule")
	public void addVehicule(@RequestBody Vehicule vehicule) {
		vehiculeService.addVehicule(vehicule);
	}
	
	@RequestMapping("/Vehicule/updateVehiculeCoord/{id}")
	public void updateVehiculeCoord(@PathVariable String id,@RequestBody Coord coord) {
		Vehicule vehicule = vehiculeService.getVehiculeById(id);
		vehicule.setCoord(coord);
		vehiculeService.updateVehicule(vehicule);
	}
	
	@DeleteMapping("/Vehicule/deleteVehicule/{id}")
	public void deleteVehicule(@PathVariable String id ) {
		vehiculeService.deleteVehicule(id);
	}
}
