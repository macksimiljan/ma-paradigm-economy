package control;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import representations.AllomorphDistribution;
import representations.InflectionClass;
import simulation.Generator;

public class Test {

	public static void main(String[] args) throws IllegalAccessException {
		AllomorphDistribution alloDistr = new AllomorphDistribution();
		String[] exponents1 = {"a1", "a2"};
		String[] exponents2 = {"b1", "b2", "b3"};
		String[] exponents3 = {"c1"};
		alloDistr.addExponents("1", new HashSet<String>(Arrays.asList(exponents1)));
		alloDistr.addExponents("2", new HashSet<String>(Arrays.asList(exponents2)));
		alloDistr.addExponents("3", new HashSet<String>(Arrays.asList(exponents3)));
		
		System.out.println(alloDistr);
		
		Set<InflectionClass> classes = Generator.getAllInflectionClasses(alloDistr);
		
		
		
	}

}
