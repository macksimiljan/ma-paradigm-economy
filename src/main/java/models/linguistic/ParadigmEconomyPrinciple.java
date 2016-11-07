package models.linguistic;

import java.util.Set;

import representations.InflectionClass;
import representations.InflectionClassSystem;

/**
 * Paradigm Economy Principle (PEP), Carstairs 1983/86.
 * 
 * @author MM
 *
 */
public class ParadigmEconomyPrinciple extends Principle {
	
	
	@Override
	public boolean checkICS(InflectionClassSystem ics) {
		Set<InflectionClass> classes = ics.getInflClasses();
		int actualSize = classes.size();
		
		return actualSize <= calcGreatestAllomVariation(ics);
	}

	@Override
	public int calcMaxSize(InflectionClassSystem ics) {
		return calcGreatestAllomVariation(ics);
	}
	
	/**
	 * Calculates the greatest allomorphic variation over all paradigm cells.
	 * @param ics Inflection class system.
	 * @return Number of the greatest allomorphic variation.
	 */
	private int calcGreatestAllomVariation(InflectionClassSystem ics) {
		int greatestAllomVariation = Integer.MIN_VALUE;
		for (int i = 0; i < ics.getNoOfFeatures(); i++) {
			int currVar = ics.getAllomorphs(i).size();
			if (greatestAllomVariation < currVar)
				greatestAllomVariation = currVar;
		}
		return greatestAllomVariation;
	}

}
