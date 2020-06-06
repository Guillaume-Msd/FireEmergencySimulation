package simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import controller.EventController;
import model.Coord;
import model.EnumStatut;
import model.Event;
import model.Fire;
import model.Vehicule;
import model.LiquidEnum;

public class CheckInterventionTask extends TimerTask {
	
	private Simulator simulator;
	
	public CheckInterventionTask(Simulator simulator) {
		this.simulator = simulator;
	}
	
	@Override
	public void run() {
		try {
			this.simulator.manageIntervention();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
