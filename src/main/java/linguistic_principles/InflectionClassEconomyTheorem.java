package linguistic_principles;

import representations.InflectionClassSystem;

/**
 * Inflection Class Economy Theorem (ICET), M&uuml;ller 2007.
 * @author MM
 *
 */
public class InflectionClassEconomyTheorem extends Principle {
	
	@Override
	public boolean checkICS(InflectionClassSystem ics) {
		int actualSize = ics.getInflClasses().size();		
		
		return actualSize <= calcMaxSize(ics);
	}
	
	/**
	 * Checks whether a given inflection class system follows the ICET.
	 * @param ics Inflection class system.
	 * @param sizeInventory Size of the marker inventory.
	 * @return 'true' iff inflection class system follows the ICET.
	 */
	public boolean checkICS(InflectionClassSystem ics, int sizeInventory) {
		int actualSize = ics.getInflClasses().size();
			
		return actualSize <= calcMaxSize(sizeInventory);
	}
	
	@Override
	public int calcMaxSize(InflectionClassSystem ics) {
		int inventorySize = this.getMarkerInventory(ics).size();
		
		return (int) Math.pow(2, inventorySize - 1);
	}
	
	/**
	 * Calculates the predicted maximum number of inflection classes
	 * given the markers and features of that ICS.  
	 * @param inventorySize Size of the marker inventory.
	 * @return Maximum size.
	 */
	public int calcMaxSize(int inventorySize) {
		return (int) Math.pow(2, inventorySize - 1);
	}

}
