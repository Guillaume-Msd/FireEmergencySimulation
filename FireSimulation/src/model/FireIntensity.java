package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public enum FireIntensity {
	Low, Medium, High, VeryHigh;
	
	public static Set<FireIntensity> intensities = EnumSet.allOf(FireIntensity.class);
	public static List<FireIntensity> listIntensities = new ArrayList<FireIntensity>(EnumSet.allOf(FireIntensity.class));
	
	/**
	 * @param currentIntensity
	 * @return l'intensite dimninuee
	 */
	public static FireIntensity attenuation(FireIntensity currentIntensity) {
		//List<FireIntensity> listIntensity = Arrays.asList(FireIntensity.values().clone());
        Collections.reverse(listIntensities);
		Iterator <FireIntensity> it = listIntensities.iterator();
		while(it.hasNext()) {
			FireIntensity intensity = it.next();
			if (intensity.equals(currentIntensity)) {
				return intensity;
		    }
		}	
		return null;

	}
	
	/**
	 * @param currentIntensity
	 * @return l'intensite augmentee
	 */
	public static FireIntensity aggravation (FireIntensity currentIntensity) {
		Iterator <FireIntensity> it = FireIntensity.listIntensities.iterator();
		while(it.hasNext()) {
			FireIntensity intensity = it.next();
			if (intensity.equals(currentIntensity)) {
			   	return intensity;
		    	}	
			}
		   	
		return null;
	}
}
