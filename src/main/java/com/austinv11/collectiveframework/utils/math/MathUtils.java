package com.austinv11.collectiveframework.utils.math;

/**
 * Class to simplify manipulation of numbers
 */
public class MathUtils {
	
	/**
	 * The maximum number of passes for heavy calculations this can do
	 */
	public static int MAX_PASSES = 1024;
	
	/**
	 * Checks if the specified number is a whole number
	 * @param number Number to check
	 * @return If the number is a whole number
	 */
	public static boolean isWholeNumber(Number number) {
		return number.intValue() == number.longValue();
	}
	
	/**
	 * Finds the smallest number in a set of numbers
	 * @param numbers The numbers to search through
	 * @return The smallest number
	 */
	public static double getMin(double... numbers) {
		double min = numbers[0];
		for (double n : numbers)
			if (n < min)
				min = n;
		return min;
	}
	
	/**
	 * Finds the largest number in a set of numbers
	 * @param numbers The numbers to search through
	 * @return The largest number
	 */
	public static double getMax(double... numbers) {
		double max = numbers[0];
		for (double n : numbers)
			if (n > max)
				max = n;
		return max;
	}
	
	/**
	 * Checks if the specified value is between the provided range
	 * @param min The minimum of the range
	 * @param max The maximum of the range
	 * @param value The value to test
	 * @param inclusive Whether to include the min and max in calculations
	 * @return If the value is within the range
	 */
	public static boolean isBetween(double min, double max, double value, boolean inclusive) {
		if (inclusive) {
			return value <= max && value >= min;
		}
		return value < max && value > min;
	}
	
	/**
	 * Checks if the given string represents a number
	 * @param string The string
	 * @return True if the string is a number
	 */
	public static boolean isStringNumber(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Checks if the given number is even
	 * @param number The number
	 * @return True if the number is an even number
	 */
	public static boolean isEvenNumber(int number) {
		return number % 2 == 0;
	}
	
	/**
	 * Finds the lowest common (integer, despite return value) denominator
	 * @param values The values to find the LCD for
	 * @return The LCD or {@link Double#NaN} if none exist within {@link MathUtils#MAX_PASSES} number of passes
	 */
	public static double findLowestCommonDenominator(double... values) {
		outer: for (int i = 2; i < MAX_PASSES+2; i++) {
			for (double value : values) {
				if (value % i != 0)
					continue outer;
			}
			return i;
		}
		return Double.NaN;
	}
}
