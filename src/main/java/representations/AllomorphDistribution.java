package representations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AllomorphDistribution {
	
	private Map<Integer, Set<String>> alloDistr;
	
	private Map<String, Integer> feature2index;
	
	private Map<Integer, String> index2feature;
	
	private int currIndex = -1;
	
	
	
	public AllomorphDistribution() {
		this.alloDistr = new HashMap<Integer, Set<String>>();
		this.feature2index = new HashMap<String, Integer>();
		this.index2feature = new HashMap<Integer, String>();
	}
	
	public AllomorphDistribution(List<Set<String>> alloDistr) {
		this.alloDistr = new HashMap<Integer, Set<String>>();
		this.feature2index = new HashMap<String, Integer>();
		this.index2feature = new HashMap<Integer, String>();
		for (int i = 0; i < alloDistr.size(); i++) {
			this.alloDistr.put(i, alloDistr.get(i));
		}
	}
	
	public void addExponents(String feature, Set<String> exponents) throws IllegalAccessException {
		if (this.feature2index.containsKey(feature))
			throw new IllegalAccessException("The feature '"+feature+"' is already mapped!");
		
		currIndex++;
		this.feature2index.put(feature, currIndex);
		this.index2feature.put(currIndex, feature);
		this.alloDistr.put(currIndex, exponents);		
	}
	
	public Set<String> getExponentsByPos(int index) {
		return alloDistr.get(index);
	}
	
	public Set<String> getExponentsByFeature(String feature) {
		return alloDistr.get(this.feature2index.get(feature));
	}
	
		
	public Set<Integer> getFeatureIndices() {
		return alloDistr.keySet();
	}
	
	public Set<String> getFeatures() {
		return this.feature2index.keySet();
	}
	
	public int calcLogicalMinSize() {
		int maxVariation = Integer.MIN_VALUE;
		for (Integer feature : alloDistr.keySet()) {
			if (maxVariation < alloDistr.get(feature).size())
				maxVariation = alloDistr.get(feature).size();
		}
		return maxVariation;
	}
	
	@Override
	public String toString() {
		String str = "Allomorph Distribution\n";
		for (int feature : alloDistr.keySet()) {
			str += feature + ": \t" + alloDistr.get(feature) + "\n";
		}
		
		str.substring(0, str.length()-2);
		
		return str;
	}

}
