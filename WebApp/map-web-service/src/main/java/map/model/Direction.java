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
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.BoundingBox;
import com.mapbox.geojson.Point;

import map.util.Tools;
import retrofit2.Response;

public class Direction {
	
	//SW long
	private double minY = -3.7357807159423833;
	
	//NE lat
	private double minX = 40.42813291388417;
	
	//SW lat
	private double maxX = 40.40526141415211;
	
	//NE long
	private double maxY = -3.6714076995849614;
	
	
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
	
	private List<Coord> smooth(List<Coord> coordList){
		List<Coord> smoothCoordList = new ArrayList<Coord>();
		int i;
		for(i = 0; i < smoothCoordList.size() - 1; i++) {
			if (smoothCoordList.get(i) == smoothCoordList.get(i + 1 ) ) {
				
			}
				
		}
		return null;
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

	public String distance(int xInit, int yInit, int xFinal, int yFinal) throws IOException {

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
	    return response.body().routes().get(0).distance().toString();
	    
	}
	
	public List<Coord> gasStation() throws IOException{
		
		
		MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
				.bbox(minY, maxX, maxY, minX)
				.accessToken(MAPBOX_ACCESS_TOKEN)
				.query("Gas station")
				.build();
		
		List<Coord> coordList = new ArrayList<Coord>();
		double latitude, longitude;
		List<CarmenFeature> featureList = mapboxGeocoding.executeCall().body().features();
		for(CarmenFeature feature : featureList) {
			latitude = feature.center().latitude();
    		longitude = feature.center().longitude();
    		Coord coord = convertCoordToGrid(longitude, latitude);
    		coordList.add(coord);
		}
		
		return coordList;
	}
		
	public List<Coord> getFireHydrant() throws IOException{
			
			/*
			MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
					.bbox(minY, maxX, maxY, minX)
					.accessToken(MAPBOX_ACCESS_TOKEN)
					.query("Fire hydrant")
					.build();
			
			List<Coord> coordList = new ArrayList<Coord>();
			double latitude, longitude;
			List<CarmenFeature> featureList = mapboxGeocoding.executeCall().body().features();
			for(CarmenFeature feature : featureList) {
				latitude = feature.center().latitude();
	    		longitude = feature.center().longitude();
	    		Coord coord = convertCoordToGrid(longitude, latitude);
	    		coordList.add(coord);
			}
			
			return coordList;
		}*/
		
		List<Coord> coordList = new ArrayList<Coord>();
		coordList.add(new Coord(52 ,63));
		coordList.add(new Coord(170 ,30));
		coordList.add(new Coord(170, 170));
		coordList.add(new Coord(25, 195));
		coordList.add(new Coord(138, 138));
		coordList.add(new Coord(170, 80));
		return coordList;
	
	}
}
