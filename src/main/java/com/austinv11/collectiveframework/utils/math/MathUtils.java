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
	public boolean isWholeNumber(Number number) {
		return number.intValue() == number.longValue();
	}
}
