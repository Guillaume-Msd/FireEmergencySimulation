package models;

public class Staff extends Intervenors {

	private TiredStateEnum TiredState;

	private boolean Available;
	
	private String nom;
	

	/**
	 * 
	 * @param nom
	 * Cette fonction crée un membre du personnel (en pleine forme et disponible)
	 */
	public Staff(String nom) {
		this.setTiredState(TiredStateEnum.En_pleine_forme);
		this.setAvailable(true);
		this.setNom(nom);
	}

	public Staff() {
		this("Inconnu");
	}

	/**
	 * @return the tiredState
	 */
	public TiredStateEnum getTiredState() {
		return TiredState;
	}

	/**
	 * @param tiredState the tiredState to set
	 */
	private void setTiredState(TiredStateEnum tiredState) {
		TiredState = tiredState;
	}
	
	/**
	 * 
	 */
	public void seFatiguer() {
		if (this.getTiredState() == TiredStateEnum.Incapacite_a_intervenir) {
			return;
		}
		this.setTiredState(TiredStateEnum.values()[this.getTiredState().ordinal()+1]);
	}
	
	/**
	 * Remets l'état de fatigue à en pleine Forme
	 */
	public void seReposer() {
		this.setTiredState(TiredStateEnum.En_pleine_forme);
	}
	
	/**
	 * @return the available
	 */
	public boolean isAvailable() {
		return Available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(boolean available) {
		Available = available;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	
	/*
	 * @returns a string that describes the object staff
	 */
	@Override
	public String toString() {
		String str = "";
		str = str + "Nom:	" + this.getNom() + "\n";
		str = str + "Disponible:	" + this.isAvailable() + "\n";
		str = str + "Etat de fatigue:	" + this.getTiredState().toString() + "\n";
		return str; 
	}
	
	/**
	 * Test unitaire
	 */
	public static void main(String[] args) {
		Staff personnel = new Staff();
		personnel.setTiredState(TiredStateEnum.Incapacite_a_intervenir);
		personnel.seFatiguer();
		System.out.println(personnel);
	}
}