package com.austinv11.collectiveframework.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A class to simplify file I/O
 */
public class FileUtils {
	
	/**
	 * Simple method to write to a file
	 * @param file File to write to
	 * @param string String to write
	 * @throws IOException
	 */
	public static void safeWrite(File file, String string) throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write(string);
		writer.flush();
		writer.close();
	}
	
	/**
	 * Simple method to read all the contents of a file
	 * @param file File to read
	 * @return The contents
	 * @throws IOException
	 */
	public static List<String> readAll(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<String> lines = new ArrayList<String>();
		while (reader.ready())
			lines.add(reader.readLine());
		reader.close();
		return lines;
	}
	
	/**
	 * Unzips a given file to the set directory
	 * @param file The file to unzip
	 * @param toDirectory The directory to unzip to
	 * @throws IOException
	 */
	public static void unzip(File file, File toDirectory) throws IOException {
		byte[] buffer = new byte[1024];
		if (!toDirectory.exists())
			toDirectory.mkdir();
		ZipInputStream zips = new ZipInputStream(new FileInputStream(file));
		ZipEntry zip = zips.getNextEntry();
		while (zip != null) {
			String fileName = zip.getName();
			File newFile = new File(toDirectory.getPath()+File.separator+fileName);
			new File(newFile.getParent()).mkdirs();
			FileOutputStream fos = new FileOutputStream(newFile);
			int len;
			while ((len = zips.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			zip = zips.getNextEntry();
		}
		zips.closeEntry();
		zips.close();
	}
}
