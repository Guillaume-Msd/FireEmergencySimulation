package map.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import map.model.Direction;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class mapRestController {
	
	@GetMapping("MapWebService/getItinerary/{xInit}/{yInit}/{xFinal}/{yFinal}")
	public String itinerary(@PathVariable String xInit, @PathVariable String yInit, @PathVariable String xFinal, @PathVariable String yFinal) throws IOException {
		Direction direction = new Direction();
		return direction.itinerary(Integer.parseInt(xInit), Integer.parseInt(yInit), Integer.parseInt(xFinal), Integer.parseInt(yFinal));
	}
	
	@GetMapping("MapWebService/getRealItinerary/{xInit}/{yInit}/{xFinal}/{yFinal}")
	public String Realitinerary(@PathVariable String xInit, @PathVariable String yInit, @PathVariable String xFinal, @PathVariable String yFinal) throws IOException {
		Direction direction = new Direction();
		return direction.realItinerary(Integer.parseInt(xInit), Integer.parseInt(yInit), Integer.parseInt(xFinal), Integer.parseInt(yFinal));
	}


}
