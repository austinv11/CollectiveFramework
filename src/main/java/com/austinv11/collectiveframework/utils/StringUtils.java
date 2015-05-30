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
	
	/**
	 * Counts the number of occurrences of a string within another
	 * @param string The string to look in
	 * @param toFind The string to look for
	 * @param ignoreCase Whether to ignore case
	 * @return The amount of occurrences
	 */
	public static int countFor(String string, String toFind, boolean ignoreCase) {
		String searchString = string;
		String regex = toFind;
		if (ignoreCase) {
			searchString = searchString.toLowerCase();
			regex = regex.toLowerCase();
		}
		int i = 0;
		while (searchString.contains(regex)) {
			searchString = searchString.replaceFirst(regex, "");
			i++;
		}
		return i;
	}
	
	/**
	 * Counts the number of occurrences of a string within another,
	 * same as {@link #countFor(String,String,boolean)} with ignoreCase as true
	 * @param string The string to look in
	 * @param toFind The string to look for
	 * @return The amount of occurrences
	 */
	public static int countFor(String string, String toFind) {
		return countFor(string, toFind, false);
	}
	
	/**
	 * Replaces the first instance of a string with another, preserving the case for each character
	 * @param string The string to replace in
	 * @param toReplace The portion of the string to replace
	 * @param toReplaceWith The string to replace with
	 * @return The new string
	 */
	public static String replaceFirstPreservingCase(String string, String toReplace, String toReplaceWith) {
		String temp = string.toLowerCase();
		int index = temp.indexOf(toReplace.toLowerCase());
		if (index == -1)
			return string;
		String found = string.substring(index, toReplace.length());
		char[] casedToReplace = found.toCharArray();
		char[] toReplaceWithChars = toReplaceWith.toCharArray();
		for (int i = 0; i < casedToReplace.length; i++) {
			if (i == toReplaceWithChars.length)
				break;
			if (Character.isLowerCase(casedToReplace[i]))
				toReplaceWithChars[i] = Character.toLowerCase(toReplaceWithChars[i]);
			else
				toReplaceWithChars[i] = Character.toUpperCase(toReplaceWithChars[i]);
		}
		String newToReplaceWith = String.valueOf(toReplaceWithChars);
		return string.replaceFirst(found, newToReplaceWith);
	}
	
	/**
	 * Replaces all instances of a string with another, preserving the case for each character
	 * @param string The string to replace in
	 * @param toReplace The portion of the string to replace
	 * @param toReplaceWith The string to replace with
	 * @return The new string
	 */
	public static String replaceAllPreservingCase(String string, String toReplace, String toReplaceWith) {
		for (int i = 0; i < countFor(string, toReplace, true); i++) {
			string = replaceFirstPreservingCase(string, toReplace, toReplaceWith);
		}
		return string;
	}
}
