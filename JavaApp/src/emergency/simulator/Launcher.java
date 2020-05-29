package emergency.simulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import emergency.AbstractVehicule;
import emergency.Alerte;
import emergency.Coord;
import emergency.FireFighterHQ;
import emergency.VehiculePompier;

public class Launcher {

	public static void main(String[] args) throws IOException, InterruptedException {
		//TODO créer un petit scénario de base
		VehiculePompier v = new VehiculePompier(40,40);
		EmergencySimulator simulateur = new EmergencySimulator();
		simulateur.setHQ(new FireFighterHQ(new Coord(3,40)));
		simulateur.getHQ().addVehicule(v);
		
		
		simulateur.cycle();
		//simulateur.cycle();
	}
}
