package probes.models;

import probes.simulation.tools.TypeSonde;

public class AlerteSignal {
	private int valeur_detectee;
	private String type;
	private String etat;
	
	public AlerteSignal(int i, String string, String string2) {
		this.setIntensity(valeur_detectee);
		this.setType(type);
		this.setEtat(etat);
	}

	public AlerteSignal() {
		
	}
	
	public void setIntensity(int value) {
		this.valeur_detectee = value;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setEtat(String etat) {
		this.etat = etat;
	}
}
