package com.austinv11.collectiveframework.utils.math;

/**
 * Class to simplify manipulation of numbers
 */
public class MathUtils {
	
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
}
