package simulation;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import representations.AllomorphDistribution;
import representations.InflectionClass;
import representations.InflectionClassSystem;

/**
 * Tests for {@link Generator}.
 * @author MM
 *
 */
public class GeneratorTest {
	
	
	@Test
	public void test_calcICSs() throws IllegalAccessException {
		AllomorphDistribution alloDistr = new AllomorphDistribution();
		String[] exponents1 = {"a1", "a2", "a3"};
		String[] exponents2 = {"b1", "b2", "b3"};
		String[] exponents3 = {"c1"};
		alloDistr.addExponents("101", new HashSet<String>(Arrays.asList(exponents1)));
		alloDistr.addExponents("23", new HashSet<String>(Arrays.asList(exponents2)));
		alloDistr.addExponents("30", new HashSet<String>(Arrays.asList(exponents3)));
		
		Set<InflectionClass> classes = Generator.getAllInflectionClasses(alloDistr);
		System.out.println("|classes|: "+classes.size());
		
//		Set<InflectionClassSystem> systems = Generator.getInflectionClassSystems(classes, alloDistr, 4);
		
		Set<InflectionClassSystem> systems = Generator.getInflectionClassSystems(classes, alloDistr);

		System.out.println("|systems|: "+systems.size());
//		for (InflectionClassSystem ics : systems) {
//			System.out.println(ics);
//		}
	}
	

	/** for {@link Generator#generateAllInflectionClasses(AllomorphDistribution)}
	 * @throws IllegalAccessException */
	@Test
	public void test_generateAllInflectionClasses() throws IllegalAccessException {
		AllomorphDistribution alloDistr = new AllomorphDistribution();
		String[] exponents1 = {"a1", "a2"};
		String[] exponents2 = {"b1", "b2", "b3"};
		String[] exponents3 = {"c1", "c2"};
		alloDistr.addExponents("101", new HashSet<String>(Arrays.asList(exponents1)));
		alloDistr.addExponents("23", new HashSet<String>(Arrays.asList(exponents2)));
		alloDistr.addExponents("30", new HashSet<String>(Arrays.asList(exponents3)));
		
		Set<InflectionClass> classes = Generator.getAllInflectionClasses(alloDistr);
//		for (InflectionClass c : classes)
//			System.out.println(c);
		
		assertEquals(12, classes.size());
		System.out.println();
		
		Set<String> inventory = new HashSet<String>();
		inventory.add("a"); inventory.add("b"); inventory.add("c");
		classes = Generator.getAllInflectionClasses(inventory, 4);
//		for (InflectionClass c : classes)
//			System.out.println(c);
		
		assertEquals(81, classes.size());
	}
	
	
	
	

}
