package simulator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

import models.Coord;
import models.FireFighterHQ;
import models.VehiculePompier;

public class Launcher {

	public static void main(String[] args) throws IOException, InterruptedException {
		//TODO créer un petit scénario de base
		VehiculePompier v = new VehiculePompier(40,40);
		final EmergencySimulator simulateur = new EmergencySimulator();
		simulateur.setHQ(new FireFighterHQ(new Coord(3,40)));
		v.setCoord_HQ(simulateur.getHQ().getEmplacement_headquarter());
		v.setCoord(v.getCoord_HQ());
		simulateur.getHQ().addVehicule(v);
		/*
		VehiculePompier v2 = new VehiculePompier(40, 40);
		v2.setCoord_HQ(simulateur.getHQ().getEmplacement_headquarter());
		v2.setCoord(v.getCoord_HQ());
		simulateur.getHQ().addVehicule(v2);*/
		
		
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
}
