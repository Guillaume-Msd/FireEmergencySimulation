package simulator;

import java.util.EnumSet;
import java.util.Set;

public enum FireIntensity {
	Faible, Moyen, Fort, TrèsFort;
	public static Set<FireIntensity> etats = EnumSet.allOf(FireIntensity.class);
}
