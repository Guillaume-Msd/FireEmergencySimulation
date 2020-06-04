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
        }    ,0,500);
	}
	
	public static EmergencySimulator initSimulateur() {
		EmergencySimulator simulateur = new EmergencySimulator();
		simulateur.addHQ(new FireFighterHQ(new Coord(40,40),3));
		
		simulateur.addHQ(new FireFighterHQ(new Coord(100,180),3));
		
		return simulateur;
	}
}
