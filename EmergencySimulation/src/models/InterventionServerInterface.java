package models;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface InterventionServerInterface {

	List<Coord> getPathFromServer(int xInit,int yInit,int xFinal,int yFinal) throws IOException;
	void createIntervention(VehiculeLutteIncendie vehicules, int xFinal, int yFinal, int range) throws JsonParseException, JsonMappingException, IOException;
	void retourIntervention(AbstractVehicule vehicule) throws JsonParseException, JsonMappingException, IOException;
}