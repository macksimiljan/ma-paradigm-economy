package linguistic_principles;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import representations.InflectionClass;
import representations.InflectionClassSystem;

/**
 * Tests for {@link NoBlurPrinciple}.
 * @author MM
 *
 */
public class NoBlurPrincipleTest {

	/** for {@link NoBlurPrinciple#checkICS(representations.InflectionClassSystem)}*/
	@Test
	public void test() {
		String[] exponents1 = {"a1", "b1", "c1"};
		String[] exponents2 = {"a2", "b2", "c1"};
		String[] exponents3 = {"a3", "b3", "c1"};
		String[] exponents4 = {"a3", "b4", "c2"};
		
		Set<InflectionClass> inflClasses = new LinkedHashSet<InflectionClass>(2);
		inflClasses.add(new InflectionClass("class 1", exponents1));
		inflClasses.add(new InflectionClass("class 2", exponents2));
		inflClasses.add(new InflectionClass("class 3", exponents3));
		inflClasses.add(new InflectionClass("class 4", exponents4));
		
		InflectionClassSystem ics = new InflectionClassSystem("Test System", inflClasses);
		assertTrue(NoBlurPrinciple.checkICS(ics));
		
		String[] exponents5 = {"a3", "b1", "c2"};
		inflClasses.add(new InflectionClass("class 5", exponents5));
		ics = new InflectionClassSystem("Test System 2", inflClasses);
		System.out.println(ics);
		assertFalse(NoBlurPrinciple.checkICS(ics));
	}

}
