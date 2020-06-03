package models;

import java.util.HashMap;
import java.util.Map;

public class VehiculeLutteIncendie extends VehiculePompier {

	private Map<LiquidEnum,Map<String,Integer>> liquids;
	
	public VehiculeLutteIncendie(Map<LiquidEnum,Map<String,Integer>> liquid) {
		
		this.liquids = liquid;
	}
	
	public VehiculeLutteIncendie() {
		this(new HashMap<LiquidEnum,Map<String,Integer>>());
	}
	
	/**
	 * Remplace un liquide par un autre
	 * @param type1 le liquide à remplacer
	 * @param type2 le type du nouveau liquide
	 * @param capacity la capacité en litre du nouveau liquide
	 */
	public void changeLiquid(LiquidEnum type1,LiquidEnum type2,Integer capacity) {
		this.liquids.remove(type1);
		addLiquid(type2,capacity);
	}
	
	/**
	 * Ajoute un liquide disponible dans le véhicule
	 * @param type type du liquide ajouté
	 * @param capacity quantité maximale du liquide ajouté
	 */
	public void addLiquid(LiquidEnum type, Integer capacity) {
		Map<String, Integer> m = new HashMap<String,Integer>();
		m.put("Quantity",capacity);
		m.put("Capacity",capacity);
		this.liquids.put(type,m);
	}
	
	public int getQuantity(LiquidEnum type) {
		Map<String,Integer> m =this.liquids.get(type);
		return m.get("Quantity");
	}
	
	/**
	 * Utilise une certaine quantité de liquide d'un certain type. 
	 * @param type
	 * @param quantity
	 * @return Renvoie true si il y avait suffisement de liquide, false sinon
	 */
	public boolean consumeLiquid(LiquidEnum type,Integer quantity) {
		Map<String,Integer> m =this.liquids.get(type);
		if (m.get("Quantity") < quantity) {
			m.put("Quantity",0);
			return false;
		}
		else {
			m.put("Quantity",m.get("Quantity") - quantity);
			return true;
		}
	}
	
	
	/**
	 * Mets la quantité d'un liquide au maximum
	 * @param type
	 */
	public void fillLiquid(LiquidEnum type) {
		Map<String,Integer> m =this.liquids.get(type);
		m.put("Quantity",m.get("Capacity"));
	}
	
	/**
	 * Remplis tous les liquides au maximum
	 */
	public void fillAllLiquids() {
		for (Map.Entry<LiquidEnum, Map<String,Integer>> entry : this.liquids.entrySet()) {
			fillLiquid(entry.getKey());
		}
	
	}
}
