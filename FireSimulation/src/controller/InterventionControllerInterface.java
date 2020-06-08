package controller;

import java.io.IOException;

import model.Element;
import model.Vehicule;

public interface InterventionControllerInterface {

	public Vehicule[] getVehicules() throws IOException;

	public void updateVehiculeStatut(Vehicule vehicule) throws IOException;

	public void addElement(Element element) throws IOException;

	public Element[] getAllElements() throws IOException;

}