package com.miltos.phd.owasp.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.opencsv.CSVReader;

public class Csv2Xml {
	
	private static String csvPath = "C:\\Users\\Miltos\\Desktop\\owasp\\Benchmark-master\\expectedresults-1.2.csv";
	private static String destPath = "C:\\Users\\Miltos\\Desktop\\owasp_bench_classes_metadata_.xml";
	private static final ArrayList<String> xmlNames = new ArrayList<>();
	private static String exclusionFilter = "";
	
	public static void csv2Xml (String path) throws IOException {
		
		// 0. Create the root Element of the XML file
		Element root = new Element("classes");
		root.setName("classes");
		
		// 1. Load the CSV file that contains the description of the test cases
		CSVReader csvReader = new CSVReader(new FileReader(path));
		List content = csvReader.readAll();
		
		// 2. Iterate through its rows
		String[] row = null;
		content.remove(0);
		for (Object object: content) {
			
			// Parse the next row
			row = (String[]) object;
			
			// Filter the results
			if (exclusionFilter.equals(row[2])) continue;
			
			// Create a new class element
			Element clazz = new Element("class");
			
			for (int i = 0; i < xmlNames.size(); i++) {
				Element temp = new Element(xmlNames.get(i));
				temp.setText(row[i]);
				clazz.addContent(temp);
			}
			
			// Add the class element the root element of the XML file
			root.addContent(clazz);
		}
		
		// Export the XML file
		exportXML(root, destPath);
	}
	
	
	private static void exportXML(Element root, String dest) throws IOException {
		
		//Create an XML Outputter
		XMLOutputter outputter = new XMLOutputter();
		
		//Set the format of the outputed XML File
		Format format = Format.getPrettyFormat();
		outputter.setFormat(format);
		
		//Output the XML File to standard output and the desired file
		FileWriter filew = new FileWriter(dest);
		outputter.output(root, filew);
		
	}


	public static void main(String args[]) throws IOException {
		
		// Define the desired attribute names of the XML
		xmlNames.add("name");
		xmlNames.add("category");
		xmlNames.add("vuln");
		xmlNames.add("cwe");
		
		// Convert the CSV to XML
		csv2Xml(csvPath);
	}

}
