package models.entropy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representations.InflectionClassSystem;

/**
 * Entropy-based paradigm views.
 * 
 * @author MM
 *
 */
public class LowCondEntropyConjecture {

	/**
	 * 
	 * @param ics Inflection class system.
	 * @return Inflection entropy.
	 */
	public float calcInflectionEntropy(InflectionClassSystem ics) {
		float p = 1.0f / ics.getInflClasses().size();
		float entropy = (float) (- Math.log(p) / Math.log(2));
		
		return entropy;
	}
	
	/**
	 * 
	 * @param ics Inflection class system.
	 * @return Map from feature ID to cell entropy.
	 */
	public Map<Integer, Float> calcParadigmCellEntropy(InflectionClassSystem ics) {
		Map<Integer, Float> entropies = new HashMap<Integer, Float>();
		
		for (int feature : ics.getFeatures()) {
			float entropy = 0;
			for (String exponent : ics.getAllomorphs(feature)) {
				int noHits = ics.reduceInflectionClasses(feature, exponent).size();
				float p = noHits * 1.0f / ics.getInflClasses().size();
				entropy += p * (float) (- Math.log(p) / Math.log(2));
			}
			entropies.put(feature, entropy);
		}
		
		return entropies;
	}
	
	public float[][] calcConditonalEntropy(InflectionClassSystem ics) {
		int dimension = ics.getNoOfFeatures();
		int totalNumber = ics.getInflClasses().size();
		
		Map<Integer, Float> paradigmCellEntropy = calcParadigmCellEntropy(ics);
				
		float[][] matrix = new float[dimension][dimension];
		for (int givenFeature = 0; givenFeature < dimension; givenFeature++) {
			String[] givenExponents = ics.getAllomorphsPerInflClass(givenFeature);
			for (int predictedFeature = 0; predictedFeature < dimension; predictedFeature++) {
				String[] predictedExponents = ics.getAllomorphsPerInflClass(predictedFeature);
				float entropy = Math.round(calcCondEntropyBetweenTwoCells(givenExponents, predictedExponents) * 1000f) / 1000f;
				matrix[givenFeature][predictedFeature] = entropy;
			}		
		}	
		
		return matrix;
	}
	
	public float calcCondEntropyBetweenTwoCells(String[] antecedentExponents, String[] consequenceExponents) {
		float entropy = 0;
		
		Map<String, Integer> occurrencesAntec = new HashMap<String, Integer>();
		for (String antecendet : antecedentExponents) {
			Integer val = occurrencesAntec.get(antecendet);
			if (val == null) 
				occurrencesAntec.put(antecendet, 1);
			else
				occurrencesAntec.put(antecendet, (val+1));	
		}
		
		for (String antecedent : occurrencesAntec.keySet()) {
			float innerEntropy = 0;
			List<String> relevantConsequences = new ArrayList<String>();
			for (int i = 0; i < antecedentExponents.length; i++) {
				if (antecedentExponents[i].equals(antecedent))
					relevantConsequences.add(consequenceExponents[i]);
			}
			Map<String, Integer> occurrencesCons = new HashMap<String, Integer>();
			for (String consequence : relevantConsequences) {
				Integer val = occurrencesCons.get(consequence);
				if (val == null) 
					occurrencesCons.put(consequence, 1);
				else
					occurrencesCons.put(consequence, (val+1));				
			}
			
			for (String key : occurrencesCons.keySet()) {
				float p = 1f * occurrencesCons.get(key) / relevantConsequences.size();
				innerEntropy += p * (float) (Math.log(p) / Math.log(2));
			}
			entropy += innerEntropy * occurrencesAntec.get(antecedent) / antecedentExponents.length;
		}
		entropy *= -1;
		return entropy;
	}
}
