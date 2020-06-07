package io.sp.webservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.sp.webservice.models.Coord;
import io.sp.webservice.models.GasStation;
import io.sp.webservice.service.GasStationService;
import utilities.Tools;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class GasStationController {
	
	@Autowired
	GasStationService gasStationService;
	
	/**
	 * Add a Gas station
	 * @param x
	 * @param y
	 * @return int the id of the gas station created
	 */
	@GetMapping("GasStationWebService/add/{x}/{y}")
	public int add(@PathVariable String x, @PathVariable String y) {
		GasStation gasStation = new GasStation(new Coord(Integer.parseInt(x), Integer.parseInt(y)));
		gasStationService.addgasStation(gasStation);
		return gasStation.getId();
		
	}
	
	/**
	 * Delete the gas station
	 * @param id
	 */
	@GetMapping("GasStationWebService/remove/{id}")
	public void remove(@PathVariable String id) {
		gasStationService.deleteGasStation(id);
	}
	
	/**
	 * Return the coord of all the gas stations
	 * @return String Json List<Coord>
	 */
	@GetMapping("GasStationWebService/getAllCoords")
	public String getAllHQCoords() {
		List<GasStation> hqList = gasStationService.getAll();
		List<Coord> coordList = new ArrayList<Coord>();
		for(GasStation hq: hqList) {
			coordList.add(hq.getCoord());
		}
		return Tools.toJsonString(coordList);
	}
	
	/**
	 * Get all Gas stations
	 * @return String Json List<GasSation>
	 */
	@GetMapping("GasStationWebService/allGasSation")
	public String getAll() {
		return Tools.toJsonString(gasStationService.getAll());
	}
	
	/**
	 * Delete all the gas station
	 */
	@DeleteMapping("GasStationWebService/removeAll")
	public void removeAll() {
		gasStationService.removeAll();
	}

}
