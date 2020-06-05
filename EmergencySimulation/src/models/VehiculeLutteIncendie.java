package models;

import java.util.HashMap;
import java.util.Map;

public class VehiculeLutteIncendie extends VehiculePompier {

	private Map<LiquidEnum,Map<String,Double>> liquids;
	
	public VehiculeLutteIncendie(Map<LiquidEnum,Map<String,Double>> liquid) {
		
		this.liquids = liquid;
	}
	
	public VehiculeLutteIncendie() {
		this(new HashMap<LiquidEnum,Map<String,Double>>());
	}
	
	/**
	 * Remplace un liquide par un autre
	 * @param type1 le liquide � remplacer
	 * @param type2 le type du nouveau liquide
	 * @param capacity la capacit� en litre du nouveau liquide
	 */
	public void changeLiquid(LiquidEnum type1,LiquidEnum type2,Double capacity) {
		this.liquids.remove(type1);
		addLiquid(type2,capacity);
	}
	
	/**
	 * Ajoute un liquide disponible dans le v�hicule
	 * @param type type du liquide ajout�
	 * @param capacity quantit� maximale du liquide ajout�
	 */
	public void addLiquid(LiquidEnum type, Double capacity) {
		Map<String, Double> m = new HashMap<String,Double>();
		m.put("Quantity",capacity);
		m.put("Capacity",capacity);
		this.liquids.put(type,m);
	}
	
	public void deleteliquid(LiquidEnum type) {
		this.liquids.remove(type);
		
	}
	
	public Double getQuantity(LiquidEnum type) {
		Map<String,Double> m =this.liquids.get(type);
		return m.get("Quantity");
	}
	
	/**
	 * Utilise une certaine quantit� de liquide d'un certain type. 
	 * @param type
	 * @param quantity
	 * @return Renvoie true si il y avait suffisement de liquide, false sinon
	 */
	public boolean consumeLiquid(LiquidEnum type,Double quantity) {
		Map<String,Double> m =this.liquids.get(type);
		if (m.get("Quantity") < quantity) {
			m.put("Quantity",0.);
			return false;
		}
		else {
			m.put("Quantity",m.get("Quantity") - quantity);
			return true;
		}
	}
	
	
	/**
	 * Mets la quantit� d'un liquide au maximum
	 * @param type
	 */
	public void fillLiquid(LiquidEnum type) {
		Map<String,Double> m =this.liquids.get(type);
		m.put("Quantity",m.get("Capacity"));
	}
	
	/**
	 * Remplis tous les liquides au maximum
	 */
	public void fillAllLiquids() {
		for (Map.Entry<LiquidEnum, Map<String,Double>> entry : this.liquids.entrySet()) {
			fillLiquid(entry.getKey());
		}
	
	}
}
