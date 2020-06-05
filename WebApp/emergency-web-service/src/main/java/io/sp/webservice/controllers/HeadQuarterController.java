package io.sp.webservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.sp.webservice.models.Alerte;
import io.sp.webservice.models.Coord;
import io.sp.webservice.models.HeadQuarter;
import io.sp.webservice.service.HeadQuarterService;
import utilities.Tools;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class HeadQuarterController {
	
	@Autowired
	HeadQuarterService headQuarterService;
	
	@GetMapping("HeadQuarterWebService/add/{x}/{y}")
	public int add(@PathVariable String x, @PathVariable String y) {
		HeadQuarter headQuarter = new HeadQuarter(new Coord(Integer.parseInt(x), Integer.parseInt(y)));
		headQuarterService.addHeadQuarter(headQuarter);
		return headQuarter.getId();
		
	}
	
	@GetMapping("HeadQuarterWebService/remove/{id}")
	public void remove(@PathVariable String id) {
		headQuarterService.deleteHeadQuarter(id);
	}
	
	@GetMapping("HeadQuarterWebService/getAllCoords")
	public String getAllHQCoords() {
		List<HeadQuarter> hqList = headQuarterService.getAll();
		List<Coord> coordList = new ArrayList<Coord>();
		for(HeadQuarter hq: hqList) {
			coordList.add(hq.getCoord());
		}
		return Tools.toJsonString(coordList);
	}

}
