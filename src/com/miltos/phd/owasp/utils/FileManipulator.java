package com.miltos.phd.owasp.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileManipulator {
	
	public static String srcPath = "C:\\Users\\Miltos\\Desktop\\arti-bench\\clean\\clean_2";
	public static String destPath = "C:\\Users\\Miltos\\Desktop\\dest";
	
	public static void main(String[] args) throws IOException {
		
		// 1. Read the contents of a folder
		File srcDir = new File(srcPath);
		File[] classes = srcDir.listFiles();
		
		// 2. Create the destination folder
		File destDir = new File(destPath);
		destDir.mkdir();
		
		for (File clazz: classes) {
			System.out.println(clazz.getAbsolutePath());
			FileUtils.copyFile(clazz, new File(destDir.getAbsolutePath() + "/" + clazz.getName()));
		}
	}

}
