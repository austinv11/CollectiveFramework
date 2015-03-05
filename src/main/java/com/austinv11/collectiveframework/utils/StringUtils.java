package com.austinv11.collectiveframework.utils;

import java.util.List;

/**
 * A class for manipulating Strings
 */
public class StringUtils {
	
	/**
	 * Converts (any) list into a string, useful with the WebUtils.readURL method
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
}
