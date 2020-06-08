package map.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import map.model.Direction;
import map.util.Tools;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class mapRestController {
	
	/**
	 * Get the itinerary converted for the grid points
	 * @param xInit
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @return String Json List<Coord>
	 * @throws IOException
	 */
	@GetMapping("MapWebService/getItinerary/{xInit}/{yInit}/{xFinal}/{yFinal}")
	public String itinerary(@PathVariable String xInit, @PathVariable String yInit, @PathVariable String xFinal, @PathVariable String yFinal) throws IOException {
		Direction direction = new Direction();
		return direction.itinerary(Integer.parseInt(xInit), Integer.parseInt(yInit), Integer.parseInt(xFinal), Integer.parseInt(yFinal));
	}
	
	/**
	 * Get the real itinerary points contains longitude and latitude
	 * @param xInit
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @return String Json List<Coord>
	 * @throws IOException
	 */
	@GetMapping("MapWebService/getRealItinerary/{xInit}/{yInit}/{xFinal}/{yFinal}")
	public String Realitinerary(@PathVariable String xInit, @PathVariable String yInit, @PathVariable String xFinal, @PathVariable String yFinal) throws IOException {
		Direction direction = new Direction();
		return direction.realItinerary(Integer.parseInt(xInit), Integer.parseInt(yInit), Integer.parseInt(xFinal), Integer.parseInt(yFinal));
	}
	
	/**
	 * Get the distance of the trip
	 * @param xInit
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @return String distance
	 * @throws IOException
	 */
	@GetMapping("MapWebService/getDistance/{xInit}/{yInit}/{xFinal}/{yFinal}")
	public String distance(@PathVariable String xInit, @PathVariable String yInit, @PathVariable String xFinal, @PathVariable String yFinal) throws IOException {
		Direction direction = new Direction();
		return direction.distance(Integer.parseInt(xInit), Integer.parseInt(yInit), Integer.parseInt(xFinal), Integer.parseInt(yFinal));
	}
	
	/**
	 * Get all the gas station on the map
	 * @return String Json List<Coord>
	 * @throws IOException
	 */
	@GetMapping("MapWebService/getGasStation")
	public String distance() throws IOException {
		Direction direction = new Direction();
		return Tools.toJsonString(direction.gasStation());
	}
	
	/**
	 * Get all the gas Fire hydrant on the map
	 * @return String Json List<Coord>
	 * @throws IOException
	 */
	@GetMapping("MapWebService/getBouchesAIncendie")
	public String getFireHydrant() throws IOException {
		Direction direction = new Direction();
		return Tools.toJsonString(direction.getFireHydrant());
	}
	




}
