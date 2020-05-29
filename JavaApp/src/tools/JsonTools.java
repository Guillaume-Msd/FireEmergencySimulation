package tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import org.json.simple.JSONObject;


public class JsonTools {
	@SuppressWarnings("unchecked")
	public static JSONObject toJson(String data){
		//Create JSON Object
        JSONObject alarmDetails = new JSONObject();
		StringTokenizer ST = new StringTokenizer(data, "/");
		alarmDetails.put("Type", ST.toString());
		alarmDetails.put("Coord", ST.nextToken().toString());
		alarmDetails.put("Intensite", ST.nextToken().toString());
		
        //Write JSON file
        try (FileWriter file = new FileWriter("alarm.json")) {
            file.write(alarmDetails.toJSONString());
            file.flush();
            return alarmDetails;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
}
