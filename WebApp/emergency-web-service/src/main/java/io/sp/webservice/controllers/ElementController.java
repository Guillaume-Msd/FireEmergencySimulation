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
import org.springframework.web.bind.annotation.RestController;

import io.sp.webservice.models.Coord;
import io.sp.webservice.models.Element;
import io.sp.webservice.service.ElementService;
import utilities.Tools;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ElementController {
	
	@Autowired
	private ElementService elementService;
	
	/**
	 * Return all elements 
	 * @return String Json List<Element>
	 */
	@GetMapping("ElementWebService/allElements")
	public String getAllVehicules() {
		List<Element> list = elementService.getAll();
		if (list != null) {
			return Tools.toJsonString(list);
		}
		else {
			return Tools.toJsonString(new ArrayList<Element>());
		}
	}
	
	/**
	 * Return the element by id
	 * @param id
	 * @return Json Element
	 */
	@GetMapping("ElementWebService/element/{id}")
	public String getVehicule(@PathVariable String id) {
		return Tools.toJsonString(elementService.getElementById(id));
	}
	
	/**
	 * Add element to the server
	 * @param element
	 * @param x
	 * @param y
	 * @return int the id of the element created
	 */
	@PostMapping("ElementWebService/addElement/{x}/{y}")
	public int addVehicule(@RequestBody Element element, @PathVariable String x, @PathVariable String y) {
		Coord coord = new Coord(Integer.parseInt(x), Integer.parseInt(y));
		element.setLocation(coord);
		elementService.addElement(element);
		return element.getId();
	}
	
	
	/**
	 * Delete all the elements
	 */
	@DeleteMapping("ElementWebService/removeAll")
	public void removeAll() {
		elementService.removeAll();
	}

}
