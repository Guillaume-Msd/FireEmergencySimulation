package model;

import java.util.Map;

public class InterventionVehicule {
	
	private int id;

	private Coord coord;
	
	private String type;
	
	private EnumStatut statut;

	private int range;

	private LiquidEnum liquid;
	
	private int liquidCapacity;
	
	private int liquidQuantity;
	
	
	
	public InterventionVehicule() {
	}
	
	public InterventionVehicule(String type) {
		this.setType(type);
	}
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public EnumStatut getStatut() {
		return statut;
	}

	public void setStatut(EnumStatut statut) {
		this.statut = statut;
	}
	
	
	public LiquidEnum getLiquid() {
		return liquid;
	}

	public void setLiquid(LiquidEnum liquid) {
		this.liquid = liquid;
	}

	public int getLiquidCapacity() {
		return liquidCapacity;
	}

	public void setLiquidCapacity(int liquidCapacity) {
		this.liquidCapacity = liquidCapacity;
	}

	//A MODIFIER
	public int getQuantity() {
		return this.getLiquidQuantity();
	}
	
	public int getCapacity() {
		return this.getCapacity();
	}

	public void restoreLiquid() {
		this.liquidQuantity = this.liquidCapacity;
	}
	
	//diminue la quantité de liquide de 10% de sa capacité totale
	public void decreaseLiquid() {
		this.liquidQuantity = this.liquidQuantity - this.liquidQuantity/10;
	}

	public int getLiquidQuantity() {
		return liquidQuantity;
	}

	public void setLiquidQuantity(int liquidQuantity) {
		this.liquidQuantity = liquidQuantity;
	}
}
