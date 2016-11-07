package models.linguistic;

import java.util.HashSet;
import java.util.Set;

import representations.AllomorphDistribution;
import representations.InflectionClassSystem;

/**
 * Principle frame.
 * 
 * @author MM
 *
 */
public abstract class Principle {
	
	/**
	 * Checks whether a given inflection class system follows the principle.
	 * @param ics Inflection class system.
	 * @return 'true' iff the inflection class system follows the principle.
	 */
	public abstract boolean checkICS(InflectionClassSystem ics);
	
	/**
	 * Calculates the predicted maximum number of inflection classes
	 * given the markers and features of that ICS. 
	 * @param ics Inflection class system.
	 * @return Maximum size.
	 */
	public abstract int calcMaxSize(InflectionClassSystem ics);
	
	/**
	 * Determines the marker inventory, i.e. the set of all markers occuring in the ICS.
	 * @param ics Inflection class system.
	 * @return Marker inventory.
	 */
	public Set<String> getMarkerInventory(InflectionClassSystem ics) {
		Set<String> inventory = new HashSet<String>();
		int noFeatures = ics.getNoOfFeatures();
		for (int feature = 0; feature < noFeatures; feature++)
			inventory.addAll(ics.getAllomorphs(feature));
		
		return inventory;
	}

}
