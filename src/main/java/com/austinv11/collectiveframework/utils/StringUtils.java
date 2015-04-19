package com.austinv11.collectiveframework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for manipulating Strings
 */
public class StringUtils {
	
	/**
	 * Converts (any) list into a string, useful with the {@link WebUtils#readURL(String)} method
	 * @param list The list
	 * @return The String
	 */
	public static String stringFromList(List list) {
		String val = "";
		for (Object o : list)
			val = val + String.valueOf(o) + "\n";
		return val;
	}
	
	/**
	 * Gets a string with the provided amount of the provided character
	 * @param character Character for the string
	 * @param amount Amount for the character to be repeated
	 * @return The String
	 */
	public static String repeatChar(char character, int amount) {
		String val = "";
		for (int i = 0; i < amount; i++)
			val = val + String.valueOf(character);
		return val;
	}
	
	/**
	 * Gets a string with the provided amount of the provided string
	 * @param string String to repeat
	 * @param amount Amount for the character to be repeated
	 * @return The String
	 */
	public static String repeatString(String string, int amount) {
		String val = "";
		for (int i = 0; i < amount; i++)
			val = val + String.valueOf(string);
		return val;
	}
	
	/**
	 * Creates a list from an input stream where each entry is due to a new line character
	 * @param stream The input stream
	 * @return The list of strings
	 * @throws IOException
	 */
	public static List<String> getStringsFromStream(InputStream stream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		String temp;
		List<String> read = new ArrayList<String>();
		while ((temp = in.readLine()) != null)
			read.add(temp);
		in.close();
		return read;
	}
}
