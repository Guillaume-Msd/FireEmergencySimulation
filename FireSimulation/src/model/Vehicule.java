package model;

import java.util.HashMap;
import java.util.Map;

public class Vehicule {
	
	private int id;

	private Coord coord;
	
	private String type;
	
	private EnumStatut statut;

	private int range;

	private Map<LiquidEnum,Map<String,Double>> liquids;
	
	//TODO decrease liquid
	
	public Vehicule() {
	}
	
	public Vehicule(String type) {
		this.setType(type);
		this.liquids = new HashMap<LiquidEnum,Map<String,Double>>();

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
	
	public int getX() {
		return this.coord.x;
	}

	public int getY() {
		return this.coord.y;
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
	
	public Double getQuantity(LiquidEnum liquidType) {
		Map<String, Double> m =this.liquids.get(liquidType);
		return m.get("Quantity");
	}
	
	public Double getCapacity(LiquidEnum liquidType) {
		Map<String, Double> m =this.liquids.get(liquidType);
		return m.get("Capacity");
	}

	public void restoreLiquid(LiquidEnum liquidType) {
		Map<String, Double> m =this.liquids.get(liquidType);
		m.put("Quantity",m.get("Capacity"));
	}
	
	//diminue la quantité de liquide de 10% de sa capacité totale
	public void decreaseLiquid(LiquidEnum liquidType) {
		if (this.getQuantity(liquidType) > 0) {
			Map<String, Double> m =this.liquids.get(liquidType);
			m.put("Quantity",m.get("Capacity")*(1-1/10));
		}
	}
}
