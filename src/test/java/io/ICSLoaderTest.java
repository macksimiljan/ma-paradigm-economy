package io;

import static org.junit.Assert.*;

import org.junit.Test;

import representations.InflectionClassSystem;

public class ICSLoaderTest {

	@Test
	public void test_getICS() {
		
		String loc1 = "./src/test/resources/loaderTest1.tsv";
		String loc2 = "./src/test/resources/loaderTest2.tsv";
		String loc3 = "./src/test/resources/loaderTest3.tsv";
		String loc4 = "./src/test/resources/loaderTest4.tsv";
		
		InflectionClassSystem ics1 = null, ics2 = null, ics3 = null, ics4 = null;
		try {
			System.out.println("loading ICS 1 ... ");
			ICSLoader loader = new ICSLoader(loc1, true, true);
			ics1 = loader.getICS("ICS 1");
			System.out.println(ics1);
			
			System.out.println("loading ICS 2 ... ");
			loader = new ICSLoader(loc2, true, false);
			ics2 = loader.getICS("ICS 2");
			System.out.println(ics2);
			
			System.out.println("loading ICS 3 ... ");
			loader = new ICSLoader(loc3, false, true);
			ics3 = loader.getICS("ICS 3");
			System.out.println(ics3);
			
			System.out.println("loading ICS 4 ... ");
			loader = new ICSLoader(loc4, false, false);
			ics4 = loader.getICS("ICS 4");
			System.out.println(ics4);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(ics1.equals(ics2) && ics1.equals(ics3) && ics1.equals(ics4));
	}

}
