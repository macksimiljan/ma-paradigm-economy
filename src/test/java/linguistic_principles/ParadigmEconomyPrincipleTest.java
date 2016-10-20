package linguistic_principles;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import representations.InflectionClass;
import representations.InflectionClassSystem;

/**
 * Tests for {@link ParadigmEconomyPrinciple}.
 * @author MM
 *
 */
public class ParadigmEconomyPrincipleTest {

	/** for {@link ParadigmEconomyPrinciple#checkICS(representations.InflectionClassSystem)}*/
	@Test
	public void test_checkICS() {
		ParadigmEconomyPrinciple pep = new ParadigmEconomyPrinciple();
		
		String[] exponents1 = {"-n", "-e", "-bum"};
		String[] exponents2 = {"-en", "-a", "-bum"};
		String[] exponents3 = {"-n", "-u", "-bim"};
		String[] exponents4 = {"-en", "-o", "-bum"};
		
		Set<InflectionClass> classes = new HashSet<InflectionClass>(2);
		classes.add(new InflectionClass("class 1", exponents1));
		classes.add(new InflectionClass("class 2", exponents2));
		classes.add(new InflectionClass("class 3", exponents3));
		classes.add(new InflectionClass("class 4", exponents4));
		
		InflectionClassSystem system = new InflectionClassSystem("Test System", classes);
		assertTrue(pep.checkICS(system));
		
		String[] exponents5 = {"-en", "-o", "-bim"};
		classes.add(new InflectionClass("class 5", exponents5));
		system = new InflectionClassSystem("Test System 2", classes);
		assertFalse(pep.checkICS(system));
	}

}
