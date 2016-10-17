package linguistic_principles;

import java.util.HashSet;
import java.util.Set;

import representations.InflectionClass;
import representations.InflectionClassSystem;

/**
 * No Blur Principle (NBP), Carstairs-McCarthy 1991.
 * @author MM
 *
 */
public class NoBlurPrinciple {
	
	/**
	 * Checks whether a given inflection class system follows the NBP.
	 * @param ics Inflection class system.
	 * @return 'true' iff inflection class system follows the NBP.
	 */
	public static boolean checkICS(InflectionClassSystem ics) {
		Set<InflectionClass> classes = ics.getInflClasses();
		int noFeatures = ics.getNoOfFeatures();
		
		for (int feature = 0; feature < noFeatures; feature++) {
			if (ics.getAllomorphs(feature).size() >= classes.size() - 1)
				continue; // for 3 (or 4) allomorphs over 4 classes there is no blurring
			else {
				String defaultExponent = null;
				Set<String> prevExponents = new HashSet<String>();
				for (InflectionClass c : classes) {
					String exponent = c.getExponents()[feature];
					if (!prevExponents.add(exponent)) {
						// exponent is already in the set
						if (defaultExponent != null && !exponent.equals(defaultExponent))
							return false; // there is already a default exponent
						else
							defaultExponent = exponent;
					}
				}
			}
		}
		
		return true;		
	}

}
