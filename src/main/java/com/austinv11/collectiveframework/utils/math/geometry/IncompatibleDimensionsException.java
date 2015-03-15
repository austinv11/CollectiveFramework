package com.austinv11.collectiveframework.utils.math.geometry;

/**
 * Represents an exception thrown when incompatible dimensions are used
 */
public class IncompatibleDimensionsException extends Exception {
	
	/**
	 * Constructor for the exception
	 * @param message The message
	 */
	public IncompatibleDimensionsException(String message) {
		super(message);
	}
}
