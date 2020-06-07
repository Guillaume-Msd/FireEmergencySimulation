package io.sp.webservice.models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vehicule {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column
	private Coord coord;
	
	@Column
	private String type;
	
	@Column
	private EnumStatut statut;
	
	@Column
	private int range;
	
	@Column
	private LiquidEnum liquid;
	
	@Column
	private int liquidCapacity;
	
	@Column
	private int liquidQuantity;

	public Vehicule() {
	}
	
	public Vehicule(String type) {
		this.setType(type);
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
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
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

	public int getLiquidQuantity() {
		return liquidQuantity;
	}

	public void setLiquidQuantity(int liquidQuantity) {
		this.liquidQuantity = liquidQuantity;
	}
	
	


	
	
}
