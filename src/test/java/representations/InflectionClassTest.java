package representations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import representations.InflectionClass;

/**
 * Tests for {@link InflectionClass}.
 * @author MM
 *
 */
public class InflectionClassTest {

	/**
	 * Tests {@link InflectionClass#toString()}.
	 */
	@Test
	public void test_toString() {
		String[] exponents = {"-n", "-e", "-bum"};
		InflectionClass inflClass = new InflectionClass("class label", exponents);
		
		assertEquals("InflectionClass [label=class label, exponents=[-n, -e, -bum]]", inflClass.toString());
	}
	
	/**
	 * Tests {@link InflectionClass#equals(Object)}.
	 */
	@Test
	public void test_equals() {
		String[] exponents1 = {"-n", "-e", "-bum"};
		String[] exponents2 = {"-en", "-a", "-bum"};
		
		InflectionClass inflClass1 = new InflectionClass("class label1", exponents1);
		InflectionClass inflClass2 = new InflectionClass("class label1", exponents1);
		InflectionClass inflClass3 = new InflectionClass("class label2", exponents1);
		InflectionClass inflClass4 = new InflectionClass("class label2", exponents2);
		
		assertTrue(inflClass1.equals(inflClass2));
		assertTrue(inflClass1.equals(inflClass3));
		assertFalse(inflClass1.equals(inflClass4));
		assertFalse(inflClass3.equals(inflClass4));
	}

}
