package control;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.ICSLoader;
import models.entropy.LowCondEntropyConjecture;
import representations.InflectionClassSystem;

public class MainEvaluation {
	
	private static String loc = "src/main/resources/p_syncretism_low.txt";
	private static boolean hasHeader = true;
	private static boolean hasClassLabels = false;
	
	
	public static void main(String[] args) {
		
		ICSLoader loader = new ICSLoader(loc, hasHeader, hasClassLabels);
		try {
			System.out.println("Loading "+loc+" ... ");
			InflectionClassSystem ics = loader.getICS("ICS");
			List<String> labels = loader.getFeatureLables();
			
			System.out.println("# Syncretisms:\n\t"+ics.calcNoSyncretims());
			System.out.println("# Syncretisms, interparadigm.:\n\t"+ics.calcNoInterParadigmSyncretims());
			
			// linguistic principles ... 
		
			
			LowCondEntropyConjecture lcec = new LowCondEntropyConjecture();
			float inflEntropy = lcec.calcInflectionEntropy(ics);
			System.out.println();
			System.out.println("Inflection Entropy\n\t"+inflEntropy+" bits");
			
			System.out.println();
			System.out.println("Paradigm Cell Entropy");
			Map<Integer, Float> cellEntropy = lcec.calcParadigmCellEntropy(ics);
			for (int feature : cellEntropy.keySet()) {
				System.out.println("\t"+labels.get(feature)+": "+cellEntropy.get(feature)+" bits");
			}
			
			System.out.println();
			System.out.println("Conditional Entropy");
			float[][] condEntropyMatrix = lcec.calcConditonalEntropy(ics);
			System.out.print("\t");
			for (String label : labels)
				System.out.print("\t"+label);
			System.out.print("\tE[row]");
			System.out.print("\n");
			for (int row = 0; row < condEntropyMatrix.length; row++) {
				System.out.print("\t");
				System.out.print((row < condEntropyMatrix.length - 1) ? labels.get(row) : "E[col]");
				for (float cell : condEntropyMatrix[row])
					System.out.print("\t"+cell);
				System.out.print("\n");
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
