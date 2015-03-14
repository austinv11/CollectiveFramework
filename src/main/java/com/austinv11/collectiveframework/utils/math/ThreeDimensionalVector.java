package com.austinv11.collectiveframework.utils.math;

/**
 * Class for manipulating and holding coords on a 3D plane
 * Remember, in Minecraft the y and z axises are switched
 */
public class ThreeDimensionalVector {
	
	public double x,y,z;
	
	/**
	 * Default constructor
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param z Z coordinate
	 */
	public ThreeDimensionalVector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Sets the coordinates
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param z Z coordinate
	 */
	public void setComponents(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	 * Returns the z coord as an int
	 * @return The rounded z coord
	 */
	public int getRoundedZ() {
		return (int)z;
	}
	
	/**
	 * Returns a new vector with the result of the specified vector minus this.
	 * @param other The vector to subtract from
	 * @return New vector
	 */
	public ThreeDimensionalVector subtract(ThreeDimensionalVector other) {
		return new ThreeDimensionalVector(other.x - x, other.y - y, other.z - z);
	}
	
	/**
	 * Returns a new vector with the result of the specified vector plus this.
	 * @param other The vector to add to
	 * @return New vector
	 */
	public ThreeDimensionalVector add(ThreeDimensionalVector other) {
		return new ThreeDimensionalVector(other.x + x, other.y + y, other.z + z);
	}
	
	/**
	 * Normalizes the vector to a length of 1 (all coords add up to one)
	 * @return Normalized vector
	 */
	public ThreeDimensionalVector normalize() {
		double distance = Math.sqrt(dotProduct());
		return distance < 1.0E-4D ? new ThreeDimensionalVector(0, 0, 0) : new ThreeDimensionalVector(x/distance, y/distance, y/distance);
	}
	
	/**
	 * Finds the dot product of the current vector and itself
	 * @return The dot product
	 */
	public double dotProduct() {
		return x*x + y*y + z*z;
	}
	
	/**
	 * Finds the dot product of the current vector and the other specified vector
	 * @param other The other vector
	 * @return The dot product
	 */
	public double dotProduct(ThreeDimensionalVector other) {
		return x*other.x + y*other.y + z*other.z;
	}
	
	/**
	 * Finds the cross product of the current vector and the other specified vector
	 * @param other The other vector
	 * @return The cross product
	 */
	public ThreeDimensionalVector crossProduct(ThreeDimensionalVector other) {
		return new ThreeDimensionalVector(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
	}
	
	private double squareDistanceTo(ThreeDimensionalVector other) {
		return Math.pow(x-other.x, 2) + Math.pow(y-other.y, 2);
	}
	
	/**
	 * Finds the distance between two vectors
	 * @param other The other vector
	 * @return The distance
	 */
	public double distanceTo(ThreeDimensionalVector other) {
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
	public ThreeDimensionalVector midpoint(ThreeDimensionalVector other) {
		return new ThreeDimensionalVector((other.x + x)/2, (other.y + y)/2, (other.z + z)/2);
	}
	
	/**
	 * Rotates the vector around the X-axis
	 * @param center The coordinate to rotate around
	 * @param angle The angle to rotate by
	 * @return The rotated vector
	 */
	public ThreeDimensionalVector rotateX(ThreeDimensionalVector center, double angle) {
		return new ThreeDimensionalVector(x, 
				Math.cos(Math.toRadians(angle))*(y-center.y)-Math.sin(Math.toRadians(angle))*(z-center.z)+center.z,
				Math.sin(Math.toRadians(angle))*(y-center.y)+Math.cos(Math.toRadians(angle))*(z-center.z)+center.z);
	}
	
	/**
	 * Rotates the vector around the X-axis about the origin
	 * @param angle The angle to rotate by
	 * @return The rotated vector
	 */
	public ThreeDimensionalVector rotateX(double angle) {
		return rotateX(new ThreeDimensionalVector(0, 0, 0), angle);
	}
	
	/**
	 * Rotates the vector around the Y-axis
	 * @param center The coordinate to rotate around
	 * @param angle The angle to rotate by
	 * @return The rotated vector
	 */
	public ThreeDimensionalVector rotateY(ThreeDimensionalVector center, double angle) {
		return new ThreeDimensionalVector(Math.cos(Math.toRadians(angle))*(z-center.z)-Math.sin(Math.toRadians(angle))*(x-center.x)+center.x, 
				y,
				Math.sin(Math.toRadians(angle))*(z-center.z)+Math.cos(Math.toRadians(angle))*(x-center.x)+center.x);
	}
	
	/**
	 * Rotates the vector around the Y-axis about the origin
	 * @param angle The angle to rotate by
	 * @return The rotated vector
	 */
	public ThreeDimensionalVector rotateY(double angle) {
		return rotateY(new ThreeDimensionalVector(0, 0, 0), angle);
	}
	
	/**
	 * Rotates the vector around the Z-axis
	 * @param center The coordinate to rotate around
	 * @param angle The angle to rotate by
	 * @return The rotated vector
	 */
	public ThreeDimensionalVector rotateZ(ThreeDimensionalVector center, double angle) {
		return new ThreeDimensionalVector(Math.cos(Math.toRadians(angle))*(x-center.x)-Math.sin(Math.toRadians(angle))*(y-center.y)+center.x,
				Math.sin(Math.toRadians(angle))*(x-center.x)+Math.cos(Math.toRadians(angle))*(y-center.y)+center.y, 
				z);
	}
	
	/**
	 * Rotates the vector around the Z-axis about the origin
	 * @param angle The angle to rotate by
	 * @return The rotated vector
	 */
	public ThreeDimensionalVector rotateZ(double angle) {
		return rotateZ(new ThreeDimensionalVector(0, 0, 0), angle);
	}
	
	/**
	 * Converts this vector from a 3D plane to a 2D plane
	 * @return The converted vector
	 */
	public TwoDimensionalVector to2D() {
		return new TwoDimensionalVector(x, y);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof ThreeDimensionalVector)
			return ((ThreeDimensionalVector) other).x == x && ((ThreeDimensionalVector) other).y == y && ((ThreeDimensionalVector) other).z == z;
		return false;
	}
}
