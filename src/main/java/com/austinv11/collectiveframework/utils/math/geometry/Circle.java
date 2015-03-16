package com.austinv11.collectiveframework.utils.math.geometry;

import com.austinv11.collectiveframework.utils.math.TwoDimensionalVector;

/**
 * A class which represents a 2D circle
 */
public class Circle {
	
	private TwoDimensionalVector center;
	private double radius;
	
	/**
	 * Constructor for a circle
	 * @param center The center of the circle
	 * @param radius The radius for the circle
	 * @throws IncompatibleDimensionsException
	 */
	public Circle(TwoDimensionalVector center, double radius) throws IncompatibleDimensionsException {
		if (radius <= 0)
			throw new IncompatibleDimensionsException("The radius must be greater than 0");
		this.center = center;
		this.radius = radius;
	}
	
	/**
	 * Constructor for a circle with the center at the origin
	 * @param radius The radius for the circle
	 * @throws IncompatibleDimensionsException
	 */
	public Circle(double radius) throws IncompatibleDimensionsException {
		this(new TwoDimensionalVector(0, 0), radius);
	}
	
	/**
	 * Gets the center of the circle
	 * @return The center of the circle
	 */
	public TwoDimensionalVector getCenter() {
		return center;
	}
	
	/**
	 * Gets the radius of the circle
	 * @return The radius
	 */
	public double getRadius() {
		return radius;
	}
	
	/**
	 * Sets the center of the circle
	 * @param newCenter The new center
	 * @return The modified circle
	 */
	public Circle setCenter(TwoDimensionalVector newCenter) {
		try {
			return new Circle(newCenter, radius);
		} catch (IncompatibleDimensionsException e) {
			e.printStackTrace();
		}
		return null; //This should never be reached
	}
	
	/**
	 * Sets the radius of the circle
	 * @param newRadius The new radius
	 * @return The modified circle
	 * @throws IncompatibleDimensionsException
	 */
	public Circle setRadius(double newRadius) throws IncompatibleDimensionsException {
		return new Circle(center, newRadius);
	}
	
	/**
	 * Gets the diameter of the circle
	 * @return The diameter
	 */
	public double getDiameter() {
		return 2*radius;
	}
	
	/**
	 * Gets the circumference of the circle
	 * @return The circumference
	 */
	public double getCircumference() {
		return getDiameter()*Math.PI;
	}
	
	/**
	 * Gets the area of the circle
	 * @return The area
	 */
	public double getArea() {
		return Math.PI*(Math.pow(radius, 2));
	}
	
	/**
	 * Checks if the specified point is on the circumference of the circle
	 * @param point The point
	 * @return If the point is on the circumference
	 */
	public boolean isPointOnCircumference(TwoDimensionalVector point) {
		return Math.sqrt(Math.pow((point.x - center.x), 2) + Math.pow((point.y - center.y), 2)) == radius;
	}
	
	/**
	 * Checks if the specified point is inside the circle
	 * @param point The point
	 * @return If the point is in the circle
	 */
	public boolean isPointInCircle(TwoDimensionalVector point) {
		return isPointOnCircumference(point) || point.distanceTo(center) < radius;
	}
}
