package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import representations.InflectionClass;
import representations.InflectionClassSystem;

public class ICSLoader {
	
	private final File file;
	
	private final boolean hasHeader;
	private final boolean hasClassLabels;
	
	private InflectionClassSystem ics = null;
	private List<String> features;
	
	/**
	 * Constructs a new loader for an inflection class system.
	 * @param loc Location of the inflection class system in the file system.
	 * @param hasHeader 'true' iff file has a header.
	 * @param hasClassLabels 'true' iff the first column in the file represents class labels.
	 */
	public ICSLoader(String loc, boolean hasHeader, boolean hasClassLabels) {
		this.file = new File(loc);
		this.hasHeader = hasHeader;
		this.hasClassLabels = hasClassLabels;
		if (!file.exists())
			throw new IllegalArgumentException("The file '"+loc+"' does not exist!");
		
		features = new ArrayList<String>();
	}
	
	
	/**
	 * Loads inflection class system from a file.
	 * @param labelICS Label of the inflection class system.
	 * @return
	 * @throws IOException
	 */
	public InflectionClassSystem getICS(String labelICS) throws IOException {
		if (ics == null)
			loadICS(labelICS);		
		
		return ics;
	}
	
	public List<String> getFeatureLables() {
		return features;
	}
	
	/**
	 * 
	 * @param labelICS
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void loadICS(String labelICS) throws FileNotFoundException, IOException {
		boolean readHeader = false;
		try (BufferedReader r = new BufferedReader(new FileReader(file))) {
			int id = 1;
			Set<InflectionClass> inflClasses = new LinkedHashSet<InflectionClass>();
			
			String line;
			while ( (line = r.readLine()) != null) {
				if (line.length() == 0)
					continue;
				
				String[] cells = line.split("\t");
				if (hasHeader && !readHeader) {
					if(hasClassLabels) {
						for (int i = 1; i < cells.length; i++)
							features.add(cells[i]);
					} else {
						for (int i = 0; i < cells.length; i++)
							features.add(cells[i]);
					}
					readHeader = true;
				} else {
					if (hasClassLabels) {
						String label = cells[0];
						String[] exponents = new String[cells.length - 1];
						for (int i = 1; i < cells.length; i++)
							exponents[i - 1] = cells[i];
						inflClasses.add(new InflectionClass(label, exponents));					
					} else {
						String label = "class "+id;
						id++;
						String[] exponents = new String[cells.length];
						for (int i = 0; i < cells.length; i++)
							exponents[i] = cells[i];
						inflClasses.add(new InflectionClass(label, exponents));		
					}
				}
			}
			ics = new InflectionClassSystem(labelICS, inflClasses);
		} 
	}

}
