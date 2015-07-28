package com.austinv11.collectiveframework.utils.math;

/**
 * This is a helper object to handle ratios (i.e. 3:1, 2:5, etc)
 */
public class Ratio {
	
	private double[] originalValues;
	private double[] reducedValues;
	private double lcd;
	
	/**
	 * Constructs a ratio
	 * @param values The ratio values
	 */
	public Ratio(double... values) {
		originalValues = values;
		reducedValues = new double[values.length];
		double lcd = MathUtils.findLowestCommonDenominator(values);
		this.lcd = lcd;
		if (Double.isNaN(lcd))
			reducedValues = originalValues;
		else {
			for (int i = 0; i < values.length; i++)
				reducedValues[i] = values[i]/lcd;
		}
	}
	
	/**
	 * Gets the provided ratio
	 * @return The provided ratio
	 */
	public double[] getOriginalRatio() {
		return originalValues;
	}
	
	/**
	 * Gets the ratio reduced by the lcd (if it exists)
	 * @return The reduced ratio
	 */
	public double[] getReducedRatio() {
		return reducedValues;
	}
	
	/**
	 * Retrieves the lcd for the ratio
	 * @return The lcd
	 */
	public double getLCD() {
		return lcd;
	}
}
