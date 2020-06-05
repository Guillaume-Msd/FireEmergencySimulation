package simulator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

import models.Coord;
import models.FireFighterHQ;

public class Launcher {

	public static void main(String[] args) throws IOException, InterruptedException {
		final EmergencySimulator simulateur = initSimulateur();

		new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
        		try {
					simulateur.cycle();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 }
        }    ,0,1000);
	}
	
	public static EmergencySimulator initSimulateur() throws IOException {
		EmergencySimulator simulateur = new EmergencySimulator();
		
		simulateur.removeAllHQ();
		
		Coord coordHQ1 = new Coord(40, 40);
		int capacityHQ = 3;
		
		FireFighterHQ hq1 = new FireFighterHQ(coordHQ1,capacityHQ);
	
		
		simulateur.addHQToMap(hq1);
		
		simulateur.addFFHQ(hq1);
		
		
		
		Coord coordHQ2 = new Coord(128, 128);
		FireFighterHQ hq2 = new FireFighterHQ(coordHQ2,capacityHQ);
		
		simulateur.addHQToMap(hq2);
		
		simulateur.addFFHQ(hq2);
		

		
		return simulateur;
	}
}
