package models;



public class AlerteSignal {
	private int intensity;
	private String type;
	private String etat;
	
	public AlerteSignal(int intensity, String type, String etat) {
		this.setIntensity(intensity);
		this.setType(type);
		this.setEtat(etat);
	}

	public AlerteSignal() {
		
	}
	
	public void setIntensity(int value) {
		this.intensity += value;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setEtat(String etat) {
		this.etat = etat;
	}

	public int getIntensity() {
		return intensity;
	}

	public String getType() {
		return type;
	}

	public String getEtat() {
		return etat;
	}

	public void resetIntensity() {
		this.intensity = 0;
	}

}

