package linguistic_principles;

import java.util.HashSet;
import java.util.Set;

import representations.InflectionClassSystem;

/**
 * Inflection Class Economy Theorem (ICET), M&uuml;ller 2007.
 * @author MM
 *
 */
public class InflectionClassEconomyTheorem {
	
	/**
	 * Checks whether a given inflection class system follows the ICET.
	 * @param ics Inflection class system.
	 * @return 'true' iff inflection class system follows the ICET.
	 */
	public static boolean checkICS(InflectionClassSystem ics) {
		int actualSize = ics.getInflClasses().size();
		Set<String> inventory = new HashSet<String>();
		int noFeatures = ics.getNoOfFeatures();
		for (int feature = 0; feature < noFeatures; feature++)
			inventory.addAll(ics.getAllomorphs(feature));
		
		return actualSize <= Math.pow(2, inventory.size() - 1);
	}
	
	/**
	 * Checks whether a given inflection class system follows the ICET.
	 * @param ics Inflection class system.
	 * @param sizeInventory Size of the marker inventory.
	 * @return 'true' iff inflection class system follows the ICET.
	 */
	public static boolean checkICS(InflectionClassSystem ics, int sizeInventory) {
		int actualSize = ics.getInflClasses().size();
			
		return actualSize <= Math.pow(2, sizeInventory - 1);
	}

}
