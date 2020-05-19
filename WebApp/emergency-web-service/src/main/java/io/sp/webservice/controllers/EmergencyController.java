package io.sp.webservice.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.sp.webservice.models.Alerte;
import io.sp.webservice.service.EmergencyService;
import utilities.Tools;

@RestController
public class EmergencyController {

	private EmergencyService emergency_service;
	
	@GetMapping("/Emergency/allalertes")
	public String getAllvehicules() {
		List<Alerte> liste = emergency_service.getAll();
		String alertes = "";
		for (Alerte a:liste) {
			alertes = alertes + Tools.toJsonString(a) + "/";
		}
		return alertes.substring(0, alertes.length()-1);
	}
	
	@GetMapping("/Emergency/alerte/{id}")
	public String getAlerte(@PathVariable String id) {
		return Tools.toJsonString(emergency_service.getAlerteById(id));
	}
	
	@PostMapping("/Emergency/addAlerte")
	public void addAlerte(@RequestBody Alerte alerte) {
		emergency_service.addAlerte(alerte);
	}
	
	@DeleteMapping("/Emergency/alerte/{id}")
	public void DeleteAlerte(@PathVariable String id) {
		emergency_service.deleteAlerte(id);
	}
}
