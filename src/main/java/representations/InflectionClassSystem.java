package representations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Models an inflection system, i.e. a set of inflection classes.
 * @author MM
 *
 */
public class InflectionClassSystem {
	
	/** Label of this inflection system. */
	private String label;
	/** Number of features that are realized by the exponents. */
	private int noFeatures = -1;
	/** The inflection classes within this system. */
	private Set<InflectionClass> inflClasses;
	
	/**
	 * Constructor. Creates a new inflection system.
	 * @param label Label of the new system.
	 * @param inflClasses Set of inflection classes.
	 * @throws IllegalArgumentException If inflection classes have a different number of features.
	 */
	public InflectionClassSystem(String label, Set<InflectionClass> inflClasses) throws IllegalArgumentException {
		this.label = label;
		this.inflClasses = new LinkedHashSet<InflectionClass>();
		
		for (InflectionClass inflClass : inflClasses) {
			int newNo = inflClass.getNoFeatures();
			if (noFeatures == -1) {
				noFeatures = newNo;
				this.inflClasses.add(inflClass);
			} else if (noFeatures == newNo) {
				this.inflClasses.add(inflClass);
			} else {
				throw new IllegalArgumentException("Each inflection class must have the same number of features.");
			}
		}
	}
	
	/**
	 * Checks whether an inflection class system is logically correct.
	 * All markers have to occur and (given an allomorph distribution) at the right slot.
	 * @param allomorphDistrib Allomorph distribution.
	 * @return true iff inflection class system is logically correct.
	 */
	public boolean isCorrect(Map<Integer, String[]> allomorphDistrib) {
		Map<Integer, Map<String, Boolean>> occAlloMap = new HashMap<Integer, Map<String, Boolean>>();
		for (Integer feature : allomorphDistrib.keySet()) {
			String[] allos = allomorphDistrib.get(feature);
			Map<String, Boolean> innerMap = new HashMap<String, Boolean>();
			for (String allo : allos) {
				innerMap.put(allo, false);
			}
			occAlloMap.put(feature, innerMap);
		}
		
		for (InflectionClass c : inflClasses) {
			String[] exponents = c.getExponents();
			for (int i=0; i < exponents.length; i++) {
				Map<String, Boolean> innerMap = occAlloMap.get((i+1));
				if (innerMap.keySet().contains(exponents[i])) 
					innerMap.put(exponents[i], true);
				else
					return false; // Exponent is not allowed
			}
		}
		
		boolean isCorrect = true;
		for (Integer feature : occAlloMap.keySet()) {
			for (String allo : occAlloMap.get(feature).keySet()) {
				isCorrect = isCorrect & occAlloMap.get(feature).get(allo);
			}
		}
		
		return isCorrect;
	}
	
	/**
	 * Checks whether an inflection class system is logically correct.
	 * All markers have to occur.
	 * @param allomorphs Allomorphs.
	 * @return true iff inflection class system is logically correct.
	 */
	public boolean isCorrect(Set<String> allomorphs) {
		Set<String> copy = new HashSet<String>();
		for (String allomorph : allomorphs) {
			copy.add(allomorph);
		}
		
		for (InflectionClass c : inflClasses) {
			String[] exponents = c.getExponents();
			for (String exponent : exponents) {
				if (allomorphs.contains(exponent))
					copy.remove(exponent);
				else
					return false; // Exponent is not allowed
			}
		}
				
		return copy.size() == 0;
	}
	
	

	/**
	 * @return the inflClasses
	 */
	public Set<InflectionClass> getInflClasses() {
		return inflClasses;
	}
	
	/**
	 * @return number of features
	 */
	public int getNoOfFeatures() {
		return this.noFeatures;
	}
	
	/**
	 * Returns the allomorphs of a given feature.
	 * @param feature Feature index beginning with 0.
	 * @return Allomorphs of that feature.
	 */
	public Set<String> getAllomorphs(int feature) {
		Set<String> allomorphs = new HashSet<String>();
		for (InflectionClass c : this.inflClasses) {
			allomorphs.add(c.getExponents()[feature]);
		}
		return allomorphs;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String classes = "";
		for (InflectionClass c : this.inflClasses) {
			classes += "\t"+c.toString()+"\n";
		}
		return "Inflection System: " + label + "\n"+ classes;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inflClasses == null) ? 0 : inflClasses.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InflectionClassSystem other = (InflectionClassSystem) obj;
		if (inflClasses == null) {
			if (other.inflClasses != null)
				return false;
		} else if (!inflClasses.equals(other.inflClasses))
			return false;
		return true;
	}
	
	
	
	

}
