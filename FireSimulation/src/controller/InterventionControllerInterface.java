package controller;

import java.io.IOException;

import model.Element;
import model.Vehicule;

public interface InterventionControllerInterface {

	/**
	 * recupere les vehicules envoyés en intervention
	 * @return Vehicule[]
	 * @throws IOException
	 */
	public Vehicule[] getVehicules() throws IOException;

	/**
	 * met a jour le statut du véhicule (fin d'intervention ou besoin de ravitailler en eau)
	 * @param vehicule
	 * @throws IOException
	 */
	public void updateVehiculeStatut(Vehicule vehicule) throws IOException;

	/**
	 * ajoute un element
	 * @param element
	 * @throws IOException
	 */
	public void addElement(Element element) throws IOException;

	/**
	 * recupere tous les elements crees
	 * @return Element[]
	 * @throws IOException
	 */
	public Element[] getAllElements() throws IOException;

}