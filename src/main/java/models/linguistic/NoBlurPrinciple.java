package models.linguistic;

import java.util.HashSet;
import java.util.Set;

import representations.InflectionClass;
import representations.InflectionClassSystem;

/**
 * No Blur Principle (NBP), Carstairs-McCarthy 1991.
 * @author MM
 *
 */
public class NoBlurPrinciple extends Principle {
	
	@Override
	public boolean checkICS(InflectionClassSystem ics) {
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
	
	@Override
	public int calcMaxSize(InflectionClassSystem ics) {
		int inventorySize = this.getMarkerInventory(ics).size();
		int noFeatures = ics.getNoOfFeatures();
		
		return (inventorySize - 1) * noFeatures + 1;
	}

}
