package com.austinv11.collectiveframework.multithreading;

/**
 * Simple class for use with {@link HeavyCalculations}
 */
public interface ICalculations {
	
	/**
	 * Called when it is this object's turn to do calculations
	 */
	public abstract void doCalculation();
}
