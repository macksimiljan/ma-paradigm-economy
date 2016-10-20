package representations;

import java.util.Arrays;

/**
 * Inflection class.
 * @author MM
 *
 */
public class InflectionClass {
	
	/** Label of this class. */
	private final String label;
	/** Exponents of this class. */
	private final String[] exponents;
	
	
	/**
	 * Constructor. Creates a new inflection class.
	 * @param label Label of the new class.
	 * @param exponents Exponents of the new class.
	 */
	public InflectionClass (String label, String[] exponents) {
		this.label = label;
		this.exponents = exponents;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * @return no of features
	 */
	public int getNoFeatures() {
		return this.exponents.length;
	}

	/**
	 * @return the exponents
	 */
	public String[] getExponents() {
		return exponents;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = this.label+":\t";
		for (int i=0; i<this.exponents.length-1; i++) {
			str += this.exponents[i]+"\t";
		}
		str += this.exponents[this.exponents.length-1];
		return str;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(exponents);
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
		InflectionClass other = (InflectionClass) obj;
		if (!Arrays.equals(exponents, other.exponents))
			return false;
		return true;
	}
	
	

}
