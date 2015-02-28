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
}
