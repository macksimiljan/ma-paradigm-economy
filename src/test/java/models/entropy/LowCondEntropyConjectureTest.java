package models.entropy;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import io.ICSLoader;
import representations.InflectionClass;
import representations.InflectionClassSystem;

/**
 * Tests for {@link LowCondEntropyConjecture}.
 * @author MM
 *
 */
public class LowCondEntropyConjectureTest {
	
	InflectionClassSystem system;
	
	/**
	 * Initialization.
	 */
	@Before
	public void init() {
		String[] exponents1 = {"{ }", "{ }", "{ }"};
		String[] exponents2 = {"-e", "-di", "{ }"};
		String[] exponents3 = {"-e", "-da", "-s"};
		String[] exponents4 = {"-bum", "-du", "-s"};
		
		Set<InflectionClass> classes = new HashSet<InflectionClass>(2);
		classes.add(new InflectionClass("class 1", exponents1));
		classes.add(new InflectionClass("class 2", exponents2));
		classes.add(new InflectionClass("class 3", exponents3));
		classes.add(new InflectionClass("class 4", exponents4));		
		system = new InflectionClassSystem("Test System", classes);
	}

	/**
	 * for {@link LowCondEntropyConjecture#calcInflectionEntropy(InflectionClassSystem)}.
	 */
	@Test
	public void test_calcInflectionEntropy() {		
		LowCondEntropyConjecture lcec = new LowCondEntropyConjecture();
		float actual = lcec.calcInflectionEntropy(system);
		float expected = 2;
		assertEquals(expected, actual, 0.0000001f);
	}
	
	/**
	 * for {@link LowCondEntropyConjecture#calcParadigmCellEntropy(InflectionClassSystem)}.
	 */
	@Test
	public void test_calcParadigmCellEntropy() {
		LowCondEntropyConjecture lcec = new LowCondEntropyConjecture();
		Map<Integer, Float> entropies = lcec.calcParadigmCellEntropy(system);
		
		float[] expected = {1.5f, 2f, 1f};
		for (int feature : entropies.keySet()) {
			assertEquals(expected[feature], entropies.get(feature), 0.0000001f);
		}		
	}
	
	/**
	 * for {@link LowCondEntropyConjecture#calcConditonalEntropy(InflectionClassSystem)}
	 * @throws IOException 
	 */
	@Test
	public void test_calcConditonalEntropy() throws IOException {
		String loc = "src/main/resources/p3.txt";
		ICSLoader loader = new ICSLoader(loc, true, true);
		InflectionClassSystem ics = loader.getICS("Finnish");
		
		LowCondEntropyConjecture lcec = new LowCondEntropyConjecture();
		float[][] matrix = lcec.calcConditonalEntropy(ics);
		
		List<String> labels = loader.getFeatureLables();
		for (String label : labels)
			System.out.print("\t"+label);
		System.out.print("\n");
		for (int row = 0; row < matrix.length; row++) {
			System.out.print(labels.get(row));
			for (float cell : matrix[row])
				System.out.print("\t"+cell);
			System.out.print("\n");
		}
	}
	
	/**
	 * for {@link LowCondEntropyConjecture#calcCondEntropyBetweenTwoCells(String[], String[])}
	 */
	@Test
	public void test_calcCondEntropyBetweenTwoCells() {
		String[] nomSg = {"i", "i", "i", "i", "e", "e"};
		String[] genSg = {"en", "en", "+en", "in", "en", "een"};
		
		LowCondEntropyConjecture lcec = new LowCondEntropyConjecture();
		float h_genSg = lcec.calcCondEntropyBetweenTwoCells(nomSg, genSg);
		float h_nomSg = lcec.calcCondEntropyBetweenTwoCells(genSg, nomSg);
		
		assertEquals(1.333, h_genSg, 0.001);
		assertEquals(0.459, h_nomSg, 0.001);
	}

}
