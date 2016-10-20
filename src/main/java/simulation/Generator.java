package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representations.AllomorphDistribution;
import representations.InflectionClass;
import representations.InflectionClassSystem;

/**
 * Generates inflection systems for a given set of allomorphs.
 * @author MM
 *
 */
public class Generator {
	
	private static Map<InflectionClass, Integer> class2id;
	private static Map<Integer, InflectionClass> id2class;
	
	private static void init (Set<InflectionClass> classes) {
		class2id = new HashMap<InflectionClass, Integer>();
		id2class = new HashMap<Integer, InflectionClass>();
		int currId = -1;
		for (InflectionClass c : classes) {
			currId++;
			class2id.put(c, currId);
			id2class.put(currId, c);
		}
	}
	
	public static Set<InflectionClassSystem> getInflectionClassSystems(Set<InflectionClass> classes, AllomorphDistribution alloDistr, int size) {
		Set<InflectionClassSystem> set = new LinkedHashSet<InflectionClassSystem>();
		
		if (size == classes.size()) {
			InflectionClassSystem ics = new InflectionClassSystem("ICS", classes);
			if (ics.isCorrect(alloDistr)) 
				set.add(ics);
		} else if (size == 1) {
			int label = 0;
			for (InflectionClass c : classes) {
				Set<InflectionClass> singleton = new HashSet<InflectionClass>();
				singleton.add(c);
				InflectionClassSystem ics = new InflectionClassSystem("ICS "+label, singleton);
				if (ics.isCorrect(alloDistr)) 
					set.add(ics);
			}				
		} else if (size > 1 && size < classes.size()) {
			List<InflectionClass> list = new ArrayList<InflectionClass>(classes);
			SubsetIterator<InflectionClass> it = new SubsetIterator<InflectionClass>(list, size);
			int label = 0;
			while (it.hasNext()) {
				Set<InflectionClass> subset = it.next();
				InflectionClassSystem ics = new InflectionClassSystem("ICS "+label, subset);
				if (ics.isCorrect(alloDistr)) {
					set.add(ics);
					label++;
				}
			}
		}
		
		return set;		
	}
	
	public static Set<InflectionClassSystem> getInflectionClassSystems(Set<InflectionClass> classes, AllomorphDistribution alloDistr) {
		Set<InflectionClassSystem> set = new LinkedHashSet<InflectionClassSystem>();
		
		List<InflectionClass> list = new ArrayList<InflectionClass>(classes);
		int label = 0;
		int prevSize = 0;
		
		for (int subsetSize = alloDistr.calcLogicalMinSize(); subsetSize <= classes.size(); subsetSize++) {
//			System.out.println("subsetSize: "+subsetSize);
			SubsetIterator<InflectionClass> it = new SubsetIterator<InflectionClass>(list, subsetSize);
			
			while (it.hasNext()) {
				Set<InflectionClass> subset = it.next();
				InflectionClassSystem ics = new InflectionClassSystem("ICS "+label, subset);
				if (ics.isCorrect(alloDistr)) {
					set.add(ics);
					label++;
				}
			}
			
			System.out.println("subsetSize: "+subsetSize+"\t#: "+(set.size()-prevSize));
			prevSize = set.size();
		}
		
		return set;	
	}
	
	
	/**
	 * Generates all inflection classes which are possible for a given allomorph distribution.
	 * @param alloDistr Allomorph distribution.
	 * @return Set of inflection classes.
	 */
	public static Set<InflectionClass> getAllInflectionClasses(AllomorphDistribution alloDistr) {
		Set<InflectionClass> out = new LinkedHashSet<InflectionClass>();
		
		int noFeatures = alloDistr.getFeatureIndices().size();
		int noClasses = 1;
		for (int feature : alloDistr.getFeatureIndices())
			noClasses *= alloDistr.getExponentsByPos(feature).size();
		
		String[][] classes = new String[noClasses][noFeatures];
		
		Integer[] featuresAsList = alloDistr.getFeatureIndices().toArray(new Integer[alloDistr.getFeatureIndices().size()]);
		for (int f = 0; f < featuresAsList.length; f++) {
			int feature = featuresAsList[f];
			int blockSize = 1;
			for (int other = f + 1; other < featuresAsList.length; other++) {
				blockSize *= alloDistr.getExponentsByPos(featuresAsList[other]).size();
			}
			Set<String> exponents = alloDistr.getExponentsByPos(feature);
			ArrayList<String> exponentsAsList = new ArrayList<String>(exponents);
			Collections.sort(exponentsAsList);
			for (int i = 0; i < exponents.size(); i ++) {
				String exponent = exponentsAsList.get(i);
				for (int start = i * blockSize; start < noClasses; start += exponents.size() * blockSize) {
					for (int row = start; row < start + blockSize; row++)
						classes[row][f] = exponent;
				}
			}
		}
				
		for (int i = 0; i < classes.length; i++) {
			String[] exponents = classes[i];
			out.add(new InflectionClass("class "+i, exponents));
		}
		
		return out;
	}
	
	/**
	 * Generates all possible inflection classes for a given marker inventory and number of features.
	 * @param inventory Marker inventory.
	 * @param noFeatures Number of features.
	 * @return Set of inflection classes.
	 */
	public static Set<InflectionClass> getAllInflectionClasses(Set<String> inventory, int noFeatures) {
		Set<InflectionClass> out = new LinkedHashSet<InflectionClass>();
		int noClasses = (int) Math.pow(inventory.size(), noFeatures);
		
		String[][] classes = new String[noClasses][noFeatures];
		for (int f = 0; f < noFeatures; f++) {
			int blockSize = (int) Math.pow(inventory.size(), noFeatures-1-f);
			ArrayList<String> exponentsAsList = new ArrayList<String>(inventory);
			Collections.sort(exponentsAsList);
			for (int i = 0; i < inventory.size(); i++) {
				String exponent = exponentsAsList.get(i);
				for (int start = i * blockSize; start < noClasses; start += inventory.size() * blockSize) {
					for (int row = start; row < start + blockSize; row++) {
						classes[row][f] = exponent;
					}
				}
			}
		}
		
		for (int i = 0; i < classes.length; i++) {
			String[] exponents = classes[i];
			out.add(new InflectionClass("class "+i, exponents));
		}
		
		return out;
	}
	
	/**
	 * Prints a matrix to the console.
	 * @param matrix The input matrix.
	 */
	@SuppressWarnings("unused")
	private static void printMatrix(String[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
		    for (int j = 0; j < matrix[i].length; j++) {
		        System.out.print(matrix[i][j] + " ");
		    }
		    System.out.println();
		}
	}


//	
//	public static Set<Set<InflectionClass>> powerSet(Set<InflectionClass> originalSet) {
//	    Set<Set<InflectionClass>> sets = new HashSet<Set<InflectionClass>>();
//	    if (originalSet.isEmpty()) {
//	    	sets.add(new HashSet<InflectionClass>());
//	    	return sets;
//	    }
//	    List<InflectionClass> list = new ArrayList<InflectionClass>(originalSet);
//	    InflectionClass head = list.get(0);
//	    Set<InflectionClass> rest = new HashSet<InflectionClass>(list.subList(1, list.size())); 
//	    for (Set<InflectionClass> set : powerSet(rest)) {
//	    	Set<InflectionClass> newSet = new HashSet<InflectionClass>();
//	    	newSet.add(head);
//	    	newSet.addAll(set);
//	    	sets.add(newSet);
//	    	sets.add(set);
//	    }		
//	    return sets;
//	}

}
