package model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/*
 * Types de feu
 * Classe A : feux "secs", braises, pouvant être activvés par le moindre coup de vent issus de la
 * 			  combustion lente de matériaux solides très inflammables
 * Classe B : feux "gras" issus de la combustion rapide de liquides et solides liquéfiables et
 * 			  provoquant un dégagement de gaz toxiques et/ou corrosifs
 * Classe C : feux de gaz pouvant provoquer une explosion
 * Classe D : feux de métaux issus d'une combustion violente provoquant un dégagement d'hydrogène 
 * 			  en contact avec de l'eau
 * Classe F : feux liés aux huiles et graisses des dans les appareils de cuisson nécessitant des
 * 			  extincteurs
 */

public enum FireType {
	ClassA, ClassB, ClassC, ClassD, ClassF;
	
	public static Set<FireType> types = EnumSet.allOf(FireType.class);
	public static List<FireType> listTypes = new ArrayList<FireType>(EnumSet.allOf(FireType.class));

	
}

	