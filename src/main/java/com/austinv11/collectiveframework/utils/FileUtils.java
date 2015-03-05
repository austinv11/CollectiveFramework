package com.austinv11.collectiveframework.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
}
