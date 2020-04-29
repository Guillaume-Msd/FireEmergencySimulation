package simulator;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;


public enum FireIntensity {
	Low, Medium, High, VeryHigh;
	public static Set<FireIntensity> intensities = EnumSet.allOf(FireIntensity.class);
	public static List<FireIntensity> listeIntensities = new ArrayList<FireIntensity>(EnumSet.allOf(FireIntensity.class));

}
