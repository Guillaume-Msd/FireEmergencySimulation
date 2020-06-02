package map.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.LegStep;
import com.mapbox.api.directions.v5.models.RouteLeg;
import com.mapbox.api.directions.v5.models.StepIntersection;
import com.mapbox.geojson.Point;

import map.util.Tools;
import retrofit2.Response;

public class Direction {
	
	
	private double minY = -3.725051879882813;
	
	private double minX = 40.43303293799863;
	
	private double maxX = 40.40035936701489;
	
	private double maxY = -3.6821365356445317;

	
	private double height = Math.abs(this.maxX - this.minX);
	
	private double width = Math.abs(this.maxY - this.minY);
	
	private int rows = 256;
	
	private int column = 256;
	
	private String MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoiZmFiaWVucHVpc3NhbnQiLCJhIjoiY2s5YTJtdWY4MDAyazNtcXVodjczcGwxcCJ9.K49PEwo4aFG5oQUXaTnubg";
	
	public String itinerary(int xInit, int yInit, int xFinal, int yFinal) throws IOException {

	    MapboxDirections.Builder builder = MapboxDirections.builder();

	    // 1. Pass in all the required information to get a simple directions route.
	    builder.accessToken(this.MAPBOX_ACCESS_TOKEN);
	    
	    List<Double> coordInit = this.convertGridToCoord(xInit, yInit);
	    List<Double> coordFinal = this.convertGridToCoord(xFinal, yFinal);
	    
	    
	    builder.origin(Point.fromLngLat(coordInit.get(1), coordInit.get(0)));
	    builder.destination(Point.fromLngLat(coordFinal.get(1), coordFinal.get(0)));
	    builder.steps(true);

	    // 2. That's it! Now execute the command and get the response.
	    Response<DirectionsResponse> response = builder.build().executeCall();

	    List<Coord> coordList = new ArrayList<Coord>();
	    List<RouteLeg> LegList = response.body().routes().get(0).legs();
	    RouteLeg Routeleg = LegList.get(0);
	    List<LegStep> stepList = Routeleg.steps();
	    List<StepIntersection> intersectList;
	    for(LegStep step : stepList) {
	    	intersectList = step.intersections();
	    	
	    	for(StepIntersection intersect: intersectList) {
	    		double latitude = intersect.location().latitude();
	    		double longitude = intersect.location().longitude();
	    		Coord coord = convertCoordToGrid(longitude, latitude);
	    		coordList.add(coord);
	    	
	    	}
	    }
	    
	    return Tools.toJsonString(coordList);
	    
	}
	
	private Coord convertCoordToGrid(double longitude, double latitude) {
		int x = (int) ((latitude - this.minX)/(this.height/this.column));
		int y = (int) ((longitude - this.minY)/(this.width/this.rows));
		return new Coord(Math.abs(x), Math.abs(y));

		
	}
	
	private List<Double> convertGridToCoord(int x, int y) {
		
		List<Double> coord = new ArrayList<Double>();
		coord.add(this.minX - x * (this.height/this.column));
		coord.add(this.minY + y * (this.width/this.rows));
		
		return coord;
	}

	public String realItinerary(int xInit, int yInit, int xFinal, int yFinal) throws IOException {
		MapboxDirections.Builder builder = MapboxDirections.builder();

	    // 1. Pass in all the required information to get a simple directions route.
	    builder.accessToken(this.MAPBOX_ACCESS_TOKEN);
	    
	    
	    List<Double> coordInit = this.convertGridToCoord(xInit, yInit);
	    List<Double> coordFinal = this.convertGridToCoord(xFinal, yFinal);
	    
	    
	    builder.origin(Point.fromLngLat(coordInit.get(1), coordInit.get(0)));
	    builder.destination(Point.fromLngLat(coordFinal.get(1), coordFinal.get(0)));
	    builder.steps(true);
	    builder.steps(true);

	    // 2. That's it! Now execute the command and get the response.
	    Response<DirectionsResponse> response = builder.build().executeCall();

	    List<RealCoord> coordList = new ArrayList<RealCoord>();
	    List<RouteLeg> LegList = response.body().routes().get(0).legs();
	    RouteLeg Routeleg = LegList.get(0);
	    List<LegStep> stepList = Routeleg.steps();
	    List<StepIntersection> intersectList;
	    for(LegStep step : stepList) {
	    	intersectList = step.intersections();
	    	for(StepIntersection intersect: intersectList ) {
	    		double latitude = intersect.location().latitude();
	    		double longitude = intersect.location().longitude();
	    		RealCoord coord = new RealCoord(latitude, longitude);
	    		coordList.add(coord);
	    	}
	    }
	    
	    return Tools.toJsonString(coordList);
	    

	}
	

}
