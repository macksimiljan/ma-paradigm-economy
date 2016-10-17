package linguistic_principles;

import java.util.Set;

import representations.InflectionClass;
import representations.InflectionClassSystem;

/**
 * Paradigm Economy Principle (PEP), Carstairs 1983/86.
 * 
 * @author MM
 *
 */
public class ParadigmEconomyPrinciple {
	
	/**
	 * Checks whether a given inflection class system follows the PEP.
	 * @param ics Inflection class system.
	 * @return 'true' iff inflection class system follows the PEP.
	 */
	public static boolean checkICS(InflectionClassSystem ics) {
		Set<InflectionClass> classes = ics.getInflClasses();
		int actualSize = classes.size();
		
		int greatestAllomVariation = Integer.MIN_VALUE;
		for (int i = 0; i < ics.getNoOfFeatures(); i++) {
			int currVar = ics.getAllomorphs(i).size();
			if (greatestAllomVariation < currVar)
				greatestAllomVariation = currVar;
		}
		
		return actualSize <= greatestAllomVariation;
	}

}
