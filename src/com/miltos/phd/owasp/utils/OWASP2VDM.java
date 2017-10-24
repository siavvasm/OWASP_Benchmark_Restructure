package com.miltos.phd.owasp.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class OWASP2VDM {
	
	// The different paths that are required for the execution of the tool
	public static String srcPath = "C:\\Users\\Miltos\\Desktop\\OWASP_Benchmark\\src";
	public static String binPath = "C:\\Users\\Miltos\\Desktop\\OWASP_Benchmark\\bin";
	public static String vulnDBPath = "C:\\Users\\Miltos\\Desktop\\Vulnerabil_Database";
	public static String destPath_clean = vulnDBPath + "\\clean";
	public static String destPath_vuln = vulnDBPath + "\\vuln";
	public static String xmlPath = "C:\\Users\\Miltos\\Desktop\\owasp_bench_classes_metadata.xml";
	
	// Global variables required for the execution of the tool
	public static File srcFolder = null;
	public static File binFolder = null;
	public static File vulnDBFolder = null;
	public static File cleanFolder = null;
	public static File vulnFolder = null;
	
	/**
	 * This method is responsible for initializing the required 
	 * parameters.
	 */
	private static void intialize() {
		
		// 1. Create the folder that will contain the final data set
		srcFolder = new File(srcPath);
		binFolder = new File(binPath);
		vulnDBFolder = new File(vulnDBPath);
		cleanFolder = new File(destPath_clean);
		vulnFolder = new File(destPath_vuln);
		
		// 2. Create the final folders
		vulnDBFolder.mkdir();
		cleanFolder.mkdir();
		vulnFolder.mkdir();
	}
	
	
	public static void main(String[] args) throws IOException, JDOMException {
		
		// 0. Initialization
		intialize();
		
		// 1. Get the names of the .java and .class files of the test cases
		File[] classes = srcFolder.listFiles();
		File[] binnaries = binFolder.listFiles();
		
		// 1. Parse the XML file with the metadata of the OWASP test cases
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new File(xmlPath));
		Element root = (Element) doc.getRootElement();
		
		// List all the class elements
		List<Element> classElements = root.getChildren();
		
		boolean isVulnerable = true;
		String folderName = "";
		
		int i = 0;
		int j = 0;
		
		// For each class element ...
		for (Element el: classElements) {
			
			// 1. Retrieve the vulnerability status of the test case
			isVulnerable = Boolean.parseBoolean(el.getChildText("vuln"));
			
			// 2. Construct the name of the folder that will contain the files of this test case
			String index = el.getChildText("name").replace("BenchmarkTest", "");
			folderName = "CWE_" + el.getChildText("cwe") + "_" + el.getChildText("category") + "_" + index;

			// 3. Check if the test case corresponds to a vulnerable file
			if (isVulnerable) {
				
				// 3.1 Create the folder that corresponds to this test case
				File folder = new File(vulnFolder.getAbsolutePath() + "/" + folderName);
				folder.mkdir();
				
				// 3.2 Copy the .java file of the test case to the corresponding folder
				FileUtils.copyFile(classes[i].getAbsoluteFile(), new File(vulnFolder.getAbsolutePath() + "/" + folderName + "/" + classes[i].getName()));
				
				// 3.3 Copy all the .class files associated to this test case to the corresponding folder
				String javaName = classes[i].getName();
				javaName = javaName.replace(".java", "");
				
				for (int k = 0; k < 11; k++) {	
					
					// TODO: Remove this print
					System.out.println("Couple: " + javaName + " " + binnaries[j].getName());
					
					FileUtils.copyFile(binnaries[j].getAbsoluteFile(), new File(vulnFolder.getAbsolutePath() + "/" + folderName + "/" + binnaries[j].getName()));
					j++;
					String className = binnaries[j].getName();
					className = className.replace(".class", "");
					if (!className.contains(javaName)) {
						System.out.println("break");
						break; 
					}
				}
				
				
			} else {
				
				// 3.1 Create the folder that corresponds to this test case
				File folder = new File(cleanFolder.getAbsolutePath() + "/" + folderName);
				folder.mkdir();
				
				// 3.2 Copy the .java file of the test case to the corresponding folder
				FileUtils.copyFile(classes[i].getAbsoluteFile(), new File(cleanFolder.getAbsolutePath() + "/" + folderName + "/" + classes[i].getName()));
				
				// 3.3 Copy all the .class files associated to this test case to the corresponding folder
				String javaName = classes[i].getName();
				javaName = javaName.replace(".java", "");
				
				for (int k = 0; k < 11; k++) {	
					System.out.println("Couple: " + javaName + " " + binnaries[j].getName());
					FileUtils.copyFile(binnaries[j].getAbsoluteFile(), new File(cleanFolder.getAbsolutePath() + "/" + folderName + "/" + binnaries[j].getName()));
					j++;
					String className = binnaries[j].getName();
					className = className.replace(".class", "");
					if (!className.contains(javaName)) {
						System.out.println("break");
						break; 
					}
				}
			}
			
			// 4. Move to the next Element (i.e. test case)
			i++;
		}
		
	}


}
