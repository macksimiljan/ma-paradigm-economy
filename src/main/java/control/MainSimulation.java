package control;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.entropy.LowCondEntropyConjecture;
import representations.AllomorphDistribution;
import representations.InflectionClass;
import representations.InflectionClassSystem;
import simulation.Generator;

/**
 * 
 * @author MM
 *
 */
public class MainSimulation {

	public static void main(String[] args) throws IllegalAccessException {
		String[] phi1 = {"a"};
		String[] phi2 = {"b"};
		String[] phi3 = {"c"};
		String[] phi4 = {"d"};
		String[] phi5 = {"e"};
		// TODO: map exponents to integer
		
		AllomorphDistribution alloDistr = new AllomorphDistribution();
		alloDistr.addExponents("phi1", new HashSet<String>(Arrays.asList(phi1)));
		alloDistr.addExponents("phi2", new HashSet<String>(Arrays.asList(phi2)));
		alloDistr.addExponents("phi3", new HashSet<String>(Arrays.asList(phi3)));
		alloDistr.addExponents("phi4", new HashSet<String>(Arrays.asList(phi4)));
		alloDistr.addExponents("phi5", new HashSet<String>(Arrays.asList(phi5)));
		
		Set<InflectionClass> classes = Generator.getAllInflectionClasses(alloDistr);
		List<InflectionClass> classesAsList = new ArrayList<InflectionClass>(classes);
		System.out.println(classes.size());
		
		Set<InflectionClassSystem> systems = new LinkedHashSet<InflectionClassSystem>();
		int desiredSize = 4;
		
		
//		systems = calcSystems(classes, classesAsList, desiredSize, alloDistr);
		
		long start = System.currentTimeMillis();
		for (int i1 = 0; i1 < classes.size() - 3; i1++) {
			
			long end = System.currentTimeMillis();
			System.out.println("\t"+(end-start)+"ms");
			System.out.println("i1:"+i1);
			start = end;
			for (int i2 = i1+1; i2 < classes.size(); i2++) {
				for (int i3 = i2+1; i3 < classes.size(); i3++) {
					for (int i4 = i3+1; i4 < classes.size(); i4++) {
//						for (int i5 = i4+1; i5< classes.size(); i5++) {
//							for (int i6 = i5+1; i6 < classes.size(); i6++) {
							
								Set<InflectionClass> inflClasses = new LinkedHashSet<InflectionClass>();
								inflClasses.add(classesAsList.get(i1));
								inflClasses.add(classesAsList.get(i2));
								inflClasses.add(classesAsList.get(i3));
								inflClasses.add(classesAsList.get(i4));
//								inflClasses.add(classesAsList.get(i5));
//								inflClasses.add(classesAsList.get(i6));
								InflectionClassSystem ics = new InflectionClassSystem("ICS", inflClasses);
								if (ics.isCorrect(alloDistr))
									systems.add(ics);
//							}
//						}
					}
				}
			}
		}
		
		
		System.out.println("systems size: "+systems.size());
		
		try (PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter("src/main/resources/out_syncretism.txt")))) {
//			w.println("#syncretisms\ti-complexity [bits]");
			LowCondEntropyConjecture lcec = new LowCondEntropyConjecture();
			Map<Integer, Integer> syncrHisto = new HashMap<Integer, Integer>();
			Map<Integer, Float> sumIComplexity = new HashMap<Integer, Float>();
			Map<Integer, List<Float>> entropiesForSyncrSize = new HashMap<Integer, List<Float>>();
			Map<Integer, Integer> sumInterParadigSyncr = new HashMap<Integer, Integer>();
			Map<Integer, List<Integer>> interParadigSyncrForSyncrSize = new HashMap<Integer, List<Integer>>();
			
			for (InflectionClassSystem ics : systems) {
				int noSyncr = ics.calcNoSyncretims();
				int noInterParadigmSyncr = ics.calcNoInterParadigmSyncretims();
				
//				w.print(noSyncr+"\t");
				float[][] entropies = lcec.calcConditonalEntropy(ics);
				int dim = entropies.length;				
//				w.print(entropies[dim-1][dim-1]+"\n");
//				w.print(ics);
				
				Integer val = syncrHisto.get(noSyncr);
				if (val == null)
					syncrHisto.put(noSyncr, 1);
				else
					syncrHisto.put(noSyncr, val + 1);
				
				Float val1 = sumIComplexity.get(noSyncr);
				if (val1 == null)
					sumIComplexity.put(noSyncr, entropies[dim-1][dim-1]);
				else
					sumIComplexity.put(noSyncr, val1 + entropies[dim-1][dim-1]);

				List<Float> val2 = entropiesForSyncrSize.get(noSyncr);
				if (val2 == null) {
					val2 = new ArrayList<Float>();
					val2.add(entropies[dim-1][dim-1]);
					entropiesForSyncrSize.put(noSyncr, val2);
				} else {
					val2.add(entropies[dim-1][dim-1]);
					entropiesForSyncrSize.put(noSyncr, val2);
				}
				
				Integer val3 = sumInterParadigSyncr.get(noSyncr);
				if (val3 == null)
					sumInterParadigSyncr.put(noSyncr, noInterParadigmSyncr);
				else
					sumInterParadigSyncr.put(noSyncr, val3 + noInterParadigmSyncr);
				
				List<Integer> val4 = interParadigSyncrForSyncrSize.get(noSyncr);
				if (val4 == null) {
					val4 = new ArrayList<Integer>();
					val4.add(noInterParadigmSyncr);
					interParadigSyncrForSyncrSize.put(noSyncr, val4);
				} else {
					val4.add(noInterParadigmSyncr);
					interParadigSyncrForSyncrSize.put(noSyncr, val4);
				}
			}
			w.println(alloDistr);
			w.println("#ICSs: "+systems.size());
			w.println();
			w.println("histogram\n----------------");
			for (Integer key : syncrHisto.keySet())
				w.println(key+": "+syncrHisto.get(key));
			w.println();
			w.println("i-complexity\n----------------");
			for (Integer key : sumIComplexity.keySet()) {
				float avg = sumIComplexity.get(key)/syncrHisto.get(key);
				float dev = 0;
				for (Float v : entropiesForSyncrSize.get(key)) {
					dev += (float) Math.pow(v - avg, 2);
				}
				dev = (float) Math.sqrt(dev / (systems.size() -1 ));
				w.println(key+": "+avg+":"+dev);
			}
			w.println();
			w.println("inter-paradigm. syncretisms\n----------------");
			for (Integer key : sumInterParadigSyncr.keySet()) {
				float avg = sumInterParadigSyncr.get(key)/syncrHisto.get(key);
				float dev = 0;
				for (Integer v : interParadigSyncrForSyncrSize.get(key)) {
					dev += (float) Math.pow(v - avg, 2);
				}
				dev = (float) Math.sqrt(dev / (systems.size() -1 ));
				w.println(key+": "+avg+":"+dev);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		InflectionClassSystem initialSystem = new InflectionClassSystem("initial", classes);
//		Set<InflectionClassSystem> systems = new HashSet<InflectionClassSystem>();
//		Set<InflectionClassSystem> systemsToTest = new HashSet<InflectionClassSystem>();
//		systemsToTest.add(initialSystem);
//		
//		while (systemsToTest.size() > 0) {
//			System.out.println("toTestSize: "+systemsToTest.size());
//			Set<InflectionClassSystem> temp = new HashSet<InflectionClassSystem>();
//			for (InflectionClassSystem ics : systemsToTest) {
//				if (ics.isCorrect(alloDistr)) {
//					systems.add(ics);
//					for (InflectionClass c : ics.getInflClasses()) {
//						Set<InflectionClass> tempClasses = new HashSet<InflectionClass>();
//						for (InflectionClass c0 : ics.getInflClasses()) {
//							if (!c0.equals(c))
//								tempClasses.add(c0);
//						}
//						temp.add(new InflectionClassSystem("ICS"+label, tempClasses));
//						label++;
//					}
//				}				
//			}
//			systemsToTest = temp;
//		}
	}
	
	private static Set<InflectionClassSystem> calcSystems(Set<InflectionClass> classes, List<InflectionClass> classesAsList, int desiredSize, AllomorphDistribution alloDistr) {
		Set<InflectionClassSystem> systems = new LinkedHashSet<InflectionClassSystem>();
		List<Integer> numbers = new ArrayList<Integer>();
		int label = 0;
		for (int no = 0; no < Math.pow(2, classes.size()); no++) {
			if (Integer.bitCount(no) == desiredSize)
				numbers.add(no);
		}
		System.out.println("subset size: "+numbers.size());
		
		for (int no : numbers) {
			Set<InflectionClass> newClasses = new LinkedHashSet<InflectionClass>();
			for (int index : bitPositions(no)) {
				newClasses.add(classesAsList.get(index));
			}
			InflectionClassSystem ics = new InflectionClassSystem("ICS"+label, newClasses);
			if (ics.isCorrect(alloDistr)) {
				systems.add(ics);
				label++;
			}
		}
		return systems;
	}
	
	private static List<Integer> bitPositions(int number) {
	    List<Integer> positions = new ArrayList<>();
	    int position = 0;
	    while (number != 0) {
	        if ((number & 1) != 0) {
	            positions.add(position);
	        }
	        position++;
	        number = number >>> 1;
	    }
	    return positions;
	}

}
