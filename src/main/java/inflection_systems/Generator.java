package inflection_systems;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representations.InflectionClass;
import representations.InflectionClassSystem;

/**
 * Generates inflection systems for a given set of allomorphs.
 * @author MM
 *
 */
public class Generator {

	/**
	 * MAIN
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("start");
		
		// allomorphs
		Map<Integer, String[]> allomorphMap = new HashMap<Integer, String[]>();
		String[] allomorphs1 = {"a1", "a2", "a3"};
		String[] allomorphs2 = {"b1", "b2", "b3", "b4"};
		String[] allomorphs3 = {"c1"};
		allomorphMap.put(1, allomorphs1);
		allomorphMap.put(2, allomorphs2);
		allomorphMap.put(3, allomorphs3);
		
		int i = 1;
		List<InflectionClass> classes = new ArrayList<InflectionClass>();
		for (String a1 : allomorphs1) {
			for (String a2 : allomorphs2) {
				for (String a3 : allomorphs3) {
					String[] exponents = {a1, a2, a3};
					InflectionClass c = new InflectionClass(i+"", exponents);
					classes.add(c);
					i++;
				}
			}
		}
		System.out.println("|Grundmenge| = "+classes.size());
		System.out.println("Berechne Potenzmenge ... ");
		Set<Set<InflectionClass>> powerSet = powerSet(new HashSet<InflectionClass>(classes));
		
		i = 1;
		List<InflectionClassSystem> systems = new ArrayList<InflectionClassSystem>();
		for (int size = Math.max(allomorphs1.length, Math.max(allomorphs2.length, allomorphs3.length)); 
				size <= (allomorphs1.length * allomorphs2.length * allomorphs3.length); 
				size++) {
			System.out.println("size:"+size);
			for (Set<InflectionClass> inflClasses : powerSet) {
				if (inflClasses.size() == size) {
					InflectionClassSystem inflSystem = new InflectionClassSystem(i+", size: "+size, inflClasses);
					
					if (inflSystem.isCorrect(allomorphMap)) {
						systems.add(inflSystem);
						i++;
					}
				}
			}
		}
		
		HashMap<Integer, Integer> size2Number = new HashMap<Integer, Integer>();
		try (PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter("./src/main/resources/out.txt")))) {
			for (InflectionClassSystem s : systems) {
				int key = s.getInflClasses().size();
				if (size2Number.containsKey(key)) {
					int val = size2Number.get(key);
					val++;
					size2Number.put(key, val);
				} else {
					size2Number.put(key, 1);
				}
				w.println(s);
			}
		}
		
		for (int key : size2Number.keySet()) {
			System.out.println(key+"\t"+size2Number.get(key));
		}
		
		System.out.println("end");
	}
	
	public static Set<Set<InflectionClass>> powerSet(Set<InflectionClass> originalSet) {
	    Set<Set<InflectionClass>> sets = new HashSet<Set<InflectionClass>>();
	    if (originalSet.isEmpty()) {
	    	sets.add(new HashSet<InflectionClass>());
	    	return sets;
	    }
	    List<InflectionClass> list = new ArrayList<InflectionClass>(originalSet);
	    InflectionClass head = list.get(0);
	    Set<InflectionClass> rest = new HashSet<InflectionClass>(list.subList(1, list.size())); 
	    for (Set<InflectionClass> set : powerSet(rest)) {
	    	Set<InflectionClass> newSet = new HashSet<InflectionClass>();
	    	newSet.add(head);
	    	newSet.addAll(set);
	    	sets.add(newSet);
	    	sets.add(set);
	    }		
	    return sets;
	}

}
