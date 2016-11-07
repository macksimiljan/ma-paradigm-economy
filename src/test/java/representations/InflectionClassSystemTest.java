package representations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for {@link InflectionClassSystem}.
 * @author MM
 *
 */
public class InflectionClassSystemTest {

	
	/** for {@link InflectionClassSystem#isCorrect(AllomorphDistribution)} 
	 * @throws IllegalAccessException */
	@Test
	public void test_isCorrect1() throws IllegalAccessException {
		String[] exponents1 = {"-n", "-e", "-bum"};
		String[] exponents2 = {"-en", "-a", "-bum"};
		Set<InflectionClass> classes = new HashSet<InflectionClass>(2);
		classes.add(new InflectionClass("class 1", exponents1));
		classes.add(new InflectionClass("class 2", exponents2));		
		InflectionClassSystem system = new InflectionClassSystem("Test System", classes);
		
		AllomorphDistribution alloDistr1 = new AllomorphDistribution();
		String[] allos0 = { "-n", "-en" };
		String[] allos1 = { "-e", "-a" };
		String[] allos2 = { "-bum" };
		alloDistr1.addExponents("10", new HashSet<String>(Arrays.asList(allos0)));
		alloDistr1.addExponents("3", new HashSet<String>(Arrays.asList(allos1)));
		alloDistr1.addExponents("451", new HashSet<String>(Arrays.asList(allos2)));
		
		assertTrue(system.isCorrect(alloDistr1));
		
		AllomorphDistribution alloDistr2 = new AllomorphDistribution();
		String[] allos3 = { "-bum", "-bim" };
		alloDistr2.addExponents("0", new HashSet<String>(Arrays.asList(allos0)));
		alloDistr2.addExponents("1", new HashSet<String>(Arrays.asList(allos1)));
		alloDistr2.addExponents("2", new HashSet<String>(Arrays.asList(allos2)));
		alloDistr2.addExponents("3", new HashSet<String>(Arrays.asList(allos3)));
		
		assertFalse(system.isCorrect(alloDistr2));
		
		String[] exponents3 = {"-en", "-error", "-bum"};
		classes.add(new InflectionClass("class 3", exponents3));
		system = new InflectionClassSystem("Test System 2", classes);
		
		assertFalse(system.isCorrect(alloDistr1));
	}
	
	/** for {@link InflectionClassSystem#isCorrect(Set)} */
	@Test
	public void test_isCorrect2() {
		String[] exponents1 = {"-n", "-e", "-bum"};
		String[] exponents2 = {"-en", "-a", "-bum"};
		Set<InflectionClass> classes = new HashSet<InflectionClass>(2);
		classes.add(new InflectionClass("class 1", exponents1));
		classes.add(new InflectionClass("class 2", exponents2));		
		InflectionClassSystem system = new InflectionClassSystem("Test System", classes);
		
		Set<String> allomorphs = new HashSet<String>();
		String[] arr = {"-n", "-en", "-e", "-a", "-bum" };
		allomorphs.addAll(Arrays.asList(arr));
		
		assertTrue(system.isCorrect(allomorphs));
		
		allomorphs.add("-bim");
		
		assertFalse(system.isCorrect(allomorphs));
		
		String[] exponents3 = {"-en", "-error", "-bum"};
		classes.add(new InflectionClass("class 3", exponents3));
		system = new InflectionClassSystem("Test System 2", classes);
		allomorphs.remove("-bim");
		
		assertFalse(system.isCorrect(allomorphs));
	}
	
	
	
	/** for {@link InflectionClassSystem#toString()}*/
	@Test
	public void test_toString() {
		String[] exponents0 = {"-n"};
		String[] exponents1 = {"-n", "-e", "-bum"};
		String[] exponents2 = {"-en", "-a", "-bum"};		
		Set<InflectionClass> classes = new LinkedHashSet<InflectionClass>(2);
		classes.add(new InflectionClass("class 1", exponents1));
		classes.add(new InflectionClass("class 2", exponents2));
		
		InflectionClassSystem system = new InflectionClassSystem("Test System", classes);
		
		Set<InflectionClass> classes2 = new LinkedHashSet<InflectionClass>(3);
		classes2.addAll(classes);
		classes2.add(new InflectionClass("class 0", exponents0));
		try {
			@SuppressWarnings("unused")
			InflectionClassSystem system2 = new InflectionClassSystem("Test System 2", classes2);
			fail("Exception expected");
		} catch (Exception e) {
			assertTrue(true);
		}
		
		assertEquals("Inflection System: Test System\n"
				+ "\tclass 1:\t-n\t-e\t-bum\n"
				+ "\tclass 2:\t-en\t-a\t-bum\n", system.toString());
	}
	
	/** for {@link InflectionClassSystem#equals(Object)}*/
	@Test
	public void test_equals(){
		String[] exponents1 = {"-n", "-e", "-bum"};
		String[] exponents2 = {"-en", "-a", "-bum"};
		String[] exponents3 = {"-k", "-ol", "-e"};
		
		Set<InflectionClass> classes1 = new HashSet<InflectionClass>(2);
		classes1.add(new InflectionClass("class 1", exponents1));
		classes1.add(new InflectionClass("class 2", exponents2));
		InflectionClassSystem system1 = new InflectionClassSystem("Test System", classes1);
		
		Set<InflectionClass> classes2 = new HashSet<InflectionClass>(2);
		classes2.add(new InflectionClass("class 1", exponents1));
		classes2.add(new InflectionClass("class 2", exponents2));
		InflectionClassSystem system2 = new InflectionClassSystem("Test System", classes2);
		
		Set<InflectionClass> classes3 = new HashSet<InflectionClass>(2);
		classes3.add(new InflectionClass("class 1", exponents1));
		classes3.add(new InflectionClass("class 3", exponents3));
		InflectionClassSystem system3 = new InflectionClassSystem("Test System", classes3);
		
		Set<InflectionClass> classes4 = new HashSet<InflectionClass>(2);
		classes4.add(new InflectionClass("class 1", exponents1));
		classes4.add(new InflectionClass("class 2", exponents2));
		classes4.add(new InflectionClass("class 3", exponents3));
		InflectionClassSystem system4 = new InflectionClassSystem("Test System", classes4);
		
		assertTrue(system1.equals(system2));
		assertFalse(system1.equals(system3));
		assertFalse(system1.equals(system4));
	}
	
	/** for {@link InflectionClassSystem#getAllomorphs(int)}*/
	@Test
	public void test_getAllomorphs() {
		String[] exponents1 = {"-n", "-e", "-bum"};
		String[] exponents2 = {"-en", "-a", "-bum"};		
		Set<InflectionClass> classes = new HashSet<InflectionClass>(2);
		classes.add(new InflectionClass("class 1", exponents1));
		classes.add(new InflectionClass("class 2", exponents2));
		
		InflectionClassSystem system = new InflectionClassSystem("Test System", classes);
		Set<String> a0 = system.getAllomorphs(0);
		Set<String> a1 = system.getAllomorphs(1);
		Set<String> a2 = system.getAllomorphs(2);
		
		String[] e0 = {"-n", "-en"};
		String[] e1 = {"-e", "-a"};
		String[] e2 = {"-bum"};
		
		assertEquals(new HashSet<String>(Arrays.asList(e0)), a0);
		assertEquals(new HashSet<String>(Arrays.asList(e1)), a1);
		assertEquals(new HashSet<String>(Arrays.asList(e2)), a2);
		
	}
	
	/**
	 * for {@link InflectionClassSystem#reduceInflectionClasses(int, String, int, String)}
	 */
	@Test
	public void test() {
		String[] exponents1 = {"-n", "-e", "-bum"};
		String[] exponents2 = {"-en", "-a", "-bum"};
		String[] exponents3 = {"-k", "-ol", "-e"};
		String[] exponents4 = {"-n", "-e", "-e"};
		
		Set<InflectionClass> classes = new HashSet<InflectionClass>(2);
		classes.add(new InflectionClass("class 1", exponents1));
		classes.add(new InflectionClass("class 2", exponents2));
		classes.add(new InflectionClass("class 3", exponents3));
		classes.add(new InflectionClass("class 4", exponents4));
		InflectionClassSystem system = new InflectionClassSystem("Test System", classes);
		
		Set<InflectionClass> actual = system.reduceInflectionClasses(2, "-e", 1, "-e");
		System.out.println(actual);
	}

}
