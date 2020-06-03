package io.sp.webservice.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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


import io.sp.webservice.models.Alerte;
import io.sp.webservice.models.Coord;
import io.sp.webservice.service.AlerteService;
import utilities.Tools;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AlerteController {

	@Autowired
	private AlerteService emergencyService;
	
	@GetMapping("EmergencyWebService/allAlerts")
	public String getAllAlerts() {
		List<Alerte> liste = emergencyService.getAll();
		return Tools.toJsonString(liste);
	}
	
	@GetMapping("EmergencyWebService/alert/{id}")
	public String getAlerte(@PathVariable String id) {
		return Tools.toJsonString(emergencyService.getAlerteById(id));
	}
	
	@RequestMapping(value="EmergencyWebService/addAlert/{x}/{y}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public int addAlerte(@RequestBody Alerte alerte, @PathVariable String x, @PathVariable String y) {
		Coord coord = new Coord(Integer.parseInt(x), Integer.parseInt(y));
		alerte.setCoord(coord);
		emergencyService.addAlerte(alerte);
		return alerte.getId();
	}
	
	@GetMapping("EmergencyWebService/updateAlertState/{id}/{etat}")
	public void updateAlerteEtat(@PathVariable String id,@PathVariable String etat) {
		Alerte alerte = emergencyService.getAlerteById(id);
		alerte.setEtat(etat);
		emergencyService.updateAlerte(alerte);
	}
	
	@DeleteMapping("EmergencyWebService/deleteAlert/{id}")
	public void DeleteAlerte(@PathVariable String id) {
		emergencyService.deleteAlerte(id);
	}
	
	
	@GetMapping("EmergencyWebService/getAllCoords")
	public String getAllFireCoords() {
		List<Alerte> alertList = emergencyService.getAll();
		List<Coord> coordList = new ArrayList<Coord>();
		for(Alerte alerte: alertList) {
			coordList.add(alerte.getCoord());
		}
		return Tools.toJsonString(coordList);
	}
}
