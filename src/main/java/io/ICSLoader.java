package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import representations.InflectionClass;
import representations.InflectionClassSystem;

public class ICSLoader {
	
	private final File file;
	
	private final boolean hasHeader;
	private final boolean hasClassLabels;
	
	public ICSLoader(String loc, boolean hasHeader, boolean hasClassLabels) {
		this.file = new File(loc);
		this.hasHeader = hasHeader;
		this.hasClassLabels = hasClassLabels;
		if (!file.exists())
			throw new IllegalArgumentException("The file '"+loc+"' does not exist!");
	}
	
	public InflectionClassSystem getICS(String labelICS) throws IOException {
		InflectionClassSystem ics = null;
		
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
		
		return ics;
	}

}
