package events;

import java.util.EnumSet;
import java.util.Set;

enum FireType {
	ClassA, ClassB, ClassC, ClassD, ClassF;
	
	public static Set<FireType> fire_types = EnumSet.allOf(FireType.class);
	
}

	