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
	
	/**
	 * Get all alert in the server
	 * @return String Json of all alerts
	 */
	@GetMapping("EmergencyWebService/allAlerts")
	public String getAllAlerts() {
		List<Alerte> liste = emergencyService.getAll();
		return Tools.toJsonString(liste);
	}
	
	/**
	 * Get the alert by id
	 * @param id
	 * @return String Json of the alert
	 */
	@GetMapping("EmergencyWebService/alert/{id}")
	public String getAlerte(@PathVariable String id) {
		return Tools.toJsonString(emergencyService.getAlerteById(id));
	}
	
	/**
	 * Add alert to the server
	 * @param alerte
	 * @param x
	 * @param y
	 * @param range
	 * @return int the id of the alert added
	 */
	@RequestMapping(value="EmergencyWebService/addAlert/{x}/{y}/{range}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public int addAlerte(@RequestBody Alerte alerte, @PathVariable String x, @PathVariable String y, @PathVariable String range) {
		Coord coord = new Coord(Integer.parseInt(x), Integer.parseInt(y));
		alerte.setCoord(coord);
		alerte.setRange(Integer.parseInt(range));
		emergencyService.addAlerte(alerte);
		return alerte.getId();
	}
	
	/**
	 * Update the alert state
	 * @param id
	 * @param etat
	 */
	@GetMapping("EmergencyWebService/updateAlertState/{id}/{etat}")
	public void updateAlerteEtat(@PathVariable String id,@PathVariable String etat) {
		Alerte alerte = emergencyService.getAlerteById(id);
		alerte.setEtat(etat);
		emergencyService.updateAlerte(alerte);
	}
	
	/**
	 * Delete the id
	 * @param id
	 */
	@DeleteMapping("EmergencyWebService/deleteAlert/{id}")
	public void DeleteAlerte(@PathVariable String id) {
		emergencyService.deleteAlerte(id);
	}
	
	/**
	 * Get the coord of all alert
	 * @return String Json List<Coord>
	 */
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
