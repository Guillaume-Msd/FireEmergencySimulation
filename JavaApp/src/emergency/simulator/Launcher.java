package emergency.simulator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

import emergency.Coord;
import emergency.FireFighterHQ;
import emergency.VehiculePompier;

public class Launcher {

	public static void main(String[] args) throws IOException, InterruptedException {
		//TODO créer un petit scénario de base
		VehiculePompier v = new VehiculePompier(40,40);
		final EmergencySimulator simulateur = new EmergencySimulator();
		simulateur.setHQ(new FireFighterHQ(new Coord(3,40)));
		v.setCoord_HQ(simulateur.getHQ().getEmplacement_headquarter());
		v.setCoord(v.getCoord_HQ());
		simulateur.getHQ().addVehicule(v);
		
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
