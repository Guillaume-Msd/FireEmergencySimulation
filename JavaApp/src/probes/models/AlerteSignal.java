package probes.models;

import probes.simulation.tools.TypeSonde;

public class AlerteSignal {
	private int valeur_detectee;
	private String type;
	private String etat;
	
	public int getValeur_detectee() {
		return valeur_detectee;
	}

	public void setValeur_detectee(int valeur_detectee) {
		this.valeur_detectee = valeur_detectee;
	}

	public String getType() {
		return type;
	}

	public String getEtat() {
		return etat;
	}

	public AlerteSignal(int i, String type, String etat) {
		this.valeur_detectee = i;
		this.type = type;
		this.etat = etat;
		/*this.setIntensity(valeur_detectee);
		this.setType(type);
		this.setEtat(etat);*/
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
