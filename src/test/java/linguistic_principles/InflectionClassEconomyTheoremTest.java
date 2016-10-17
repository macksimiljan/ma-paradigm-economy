package linguistic_principles;

import static org.junit.Assert.*;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import representations.InflectionClass;
import representations.InflectionClassSystem;

/**
 * Tests for {@link InflectionClassEconomyTheorem}.
 * @author MM
 *
 */
public class InflectionClassEconomyTheoremTest {

	/** for {@link InflectionClassEconomyTheorem#checkICS(representations.InflectionClassSystem)}*/
	@Test
	public void test() {
		String[] exponents1 = {"a", "b", "c"};
		String[] exponents2 = {"a", "b", "a"};
		String[] exponents3 = {"a", "a", "a"};
		
		Set<InflectionClass> inflClasses = new LinkedHashSet<InflectionClass>(2);
		inflClasses.add(new InflectionClass("class 1", exponents1));
		inflClasses.add(new InflectionClass("class 2", exponents2));
		inflClasses.add(new InflectionClass("class 3", exponents3));
		InflectionClassSystem ics = new InflectionClassSystem("Test System", inflClasses);
		assertTrue(InflectionClassEconomyTheorem.checkICS(ics));
		
		String[] exponents4 = {"b", "b", "b"};
		String[] exponents5 = {"c", "c", "c"};
		String[] exponents6 = {"c", "c", "a"};
		inflClasses.add(new InflectionClass("class 4", exponents4));
		inflClasses.add(new InflectionClass("class 5", exponents5));
		inflClasses.add(new InflectionClass("class 6", exponents6));
		ics = new InflectionClassSystem("Test System 2", inflClasses);
		assertFalse(InflectionClassEconomyTheorem.checkICS(ics));
	}

}
