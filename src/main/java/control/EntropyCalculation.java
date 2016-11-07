package control;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.ICSLoader;
import models.entropy.LowCondEntropyConjecture;
import representations.InflectionClassSystem;

public class EntropyCalculation {
	
	private static String loc = "src/main/resources/p2.txt";
	private static boolean hasHeader = true;
	private static boolean hasClassLabels = true;
	
	
	public static void main(String[] args) {
		
		ICSLoader loader = new ICSLoader(loc, hasHeader, hasClassLabels);
		try {
			InflectionClassSystem ics = loader.getICS("ICS");
			List<String> features = loader.getFeatureLables();
			
			LowCondEntropyConjecture lcec = new LowCondEntropyConjecture();
			Map<Integer, Float> entropies = lcec.calcParadigmCellEntropy(ics);
			
			for (int feature : entropies.keySet()) {
				System.out.println(features.get(feature)+": "+entropies.get(feature)+" bits");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
