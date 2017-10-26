package com.miltos.phd.owasp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jdom.Element;

import com.opencsv.CSVReader;

public class SubsetConstructor {
	
	public static String srcPath = "C:\\Users\\siavvasm.ITI-THERMI.000\\Desktop\\WorkSpace";
	public static String destPath = "C:\\Users\\siavvasm.ITI-THERMI.000\\Desktop\\WorkSpace_vuln_600";
	public static String csvPath = "C:\\Users\\siavvasm.ITI-THERMI.000\\Desktop\\vuln_600.csv";
	
	public static void main(String[] args) throws IOException {
		
		// 0. Create the appropriate files 
		File srcDir = new File(srcPath);
		File destDir = new File(destPath);
		destDir.mkdir();
		
		// 1. Parse the CSV with the analysis results
		CSVReader csvReader = new CSVReader(new FileReader(csvPath));
		List content = csvReader.readAll();
		
		// 2. Print the names of the selected projects
		String[] row = null;
		content.remove(0);
		String str = "";
		for (Object object: content) {
			
			// Parse the next row
			row = (String[]) object;
			str = row[0];
			System.out.println(str);
			List<String> strList = Arrays.asList(str.split(";"));
			System.out.println(strList.get(0));
			
			// Copy the directory
			System.out.println("From: " + new File(srcDir.getAbsolutePath() + "/" + strList.get(0)).getAbsolutePath() + " To: " + new File(destDir.getAbsolutePath() + "/" + strList.get(0)).getAbsolutePath());
			FileUtils.copyDirectory(new File(srcDir.getAbsolutePath() + "/" + strList.get(0)), new File(destDir.getAbsolutePath() + "/" + strList.get(0)));
		}
	}

}
