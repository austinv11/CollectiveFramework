package com.austinv11.collectiveframework.utils.math;

/**
 * Class for manipulating and holding coords on a 2D plane
 */
public class TwoDimensionalVector implements Comparable {
	
	public double x,y;
	
	/**
	 * Default constructor
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public TwoDimensionalVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets the coordinates
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public void setComponents(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Returns the x coord as an int
	 * @return The rounded x coord
	 */
	public int getRoundedX() {
		return (int)x;
	}
	
	/**
	 * Returns the y coord as an int
	 * @return The rounded y coord
	 */
	public int getRoundedY() {
		return (int)y;
	}
	
	/**
	 * Returns a new vector with the result of the specified vector minus this.
	 * @param other The vector to subtract from
	 * @return New vector
	 */
	public TwoDimensionalVector subtract(TwoDimensionalVector other) {
		return new TwoDimensionalVector(other.x - x, other.y - y);
	}
	
	/**
	 * Returns a new vector with the result of the specified vector plus this.
	 * @param other The vector to add to
	 * @return New vector
	 */
	public TwoDimensionalVector add(TwoDimensionalVector other) {
		return new TwoDimensionalVector(other.x + x, other.y + y);
	}
	
	/**
	 * Normalizes the vector to a length of 1 (all coords add up to one)
	 * @return Normalized vector
	 */
	public TwoDimensionalVector normalize() {
		double distance = Math.sqrt(dotProduct());
		return distance < 1.0E-4D ? new TwoDimensionalVector(0, 0) : new TwoDimensionalVector(x/distance, y/distance);
	}
	
	/**
	 * Finds the dot product of the current vector and itself
	 * @return The dot product
	 */
	public double dotProduct() {
		return x*x + y*y;
	}
	
	/**
	 * Finds the dot product of the current vector and the other specified vector
	 * @param other The other vector
	 * @return The dot product
	 */
	public double dotProduct(TwoDimensionalVector other) {
		return x*other.x + y*other.y;
	}
	
	//Cross product
	
	private double squareDistanceTo(TwoDimensionalVector other) {
		return Math.pow(x-other.x, 2) + Math.pow(y-other.y, 2);
	}
	
	/**
	 * Finds the distance between two vectors
	 * @param other The other vector
	 * @return The distance
	 */
	public double distanceTo(TwoDimensionalVector other) {
		return Math.sqrt(squareDistanceTo(other));
	}
	
	/**
	 * Finds the length of the vector
	 * @return The length
	 */
	public double length() {
		return Math.sqrt(dotProduct());
	}
	
	/**
	 * Returns a vector representing the midpoint between the current vector and another
	 * @param other The other vector
	 * @return The midpoint
	 */
	public TwoDimensionalVector midpoint(TwoDimensionalVector other) {
		return new TwoDimensionalVector((other.x + x)/2, (other.y + y)/2);
	}
	
	/**
	 * Rotates the vector around a given point
	 * @param center The point to rotate around
	 * @param angle The angle to rotate by
	 * @return The rotated vector
	 */
	public TwoDimensionalVector rotate(TwoDimensionalVector center, double angle) {
		return new TwoDimensionalVector(Math.cos(Math.toRadians(angle))*(x-center.x)-Math.sin(Math.toRadians(angle))*(y-center.y)+center.x,
				Math.sin(Math.toRadians(angle))*(x-center.x)+Math.cos(Math.toRadians(angle))*(y-center.y)+center.y);
	}
	
	/**
	 * Rotates the vector around the origin
	 * @param angle The angle to rotate by
	 * @return The rotated vector
	 */
	public TwoDimensionalVector rotate(double angle) {
		return rotate(new TwoDimensionalVector(0 , 0), angle);
	}
	
	/**
	 * Converts this vector from a 2D plane to a 3D plane (with a z of 0)
	 * @return The converted vector
	 */
	public ThreeDimensionalVector to3D() {
		return new ThreeDimensionalVector(x, y, 0);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof TwoDimensionalVector)
			return ((TwoDimensionalVector) other).x == x && ((TwoDimensionalVector) other).y == y;
		return false;
	}
	
	@Override
	public String toString() {
		return "TwoDimensionalVector(X:"+x+" Y:"+y+")";
	}
	
	@Override
	public int compareTo(Object o) {
		TwoDimensionalVector vector = (TwoDimensionalVector) o;
		if (vector.equals(this))
			return 0;
		if (vector.x == x)
			return vector.y > y ? -1 : 1;
		return vector.x > x ? -1 : 1;
	}
}
