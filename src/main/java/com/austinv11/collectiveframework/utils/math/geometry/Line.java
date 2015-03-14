package com.austinv11.collectiveframework.utils.math.geometry;

import com.austinv11.collectiveframework.utils.math.ThreeDimensionalVector;
import com.austinv11.collectiveframework.utils.math.TwoDimensionalVector;

import java.util.ArrayList;
import java.util.List;

/**
 * Base unit for shapes
 */
public class Line {
	
	private ThreeDimensionalVector start, end;
	
	/**
	 * Two dimensional constructor for a line
	 * @param startCoord Start coord of the line
	 * @param endCoord End coord of the line
	 */
	public Line(TwoDimensionalVector startCoord, TwoDimensionalVector endCoord) {
		this(new ThreeDimensionalVector(startCoord.x, startCoord.y, 0), new ThreeDimensionalVector(endCoord.x, endCoord.y, 0));
	}
	
	/**
	 * Three dimensional constructor for a line
	 * @param startCoord Start coord of the line
	 * @param endCoord End coord of the line
	 */
	public Line(ThreeDimensionalVector startCoord, ThreeDimensionalVector endCoord) {
		start = startCoord;
		end = endCoord;
	}
	
	/**
	 * Gets the starting x coordinate of the line
	 * @return The x coordinate
	 */
	public double getStartX() {
		return start.x;
	}
	
	/**
	 * Gets the starting y coordinate of the line
	 * @return The y coordinate
	 */
	public double getStartY() {
		return start.y;
	}
	
	/**
	 * Gets the starting z coordinate of the line
	 * @return The z coordinate
	 */
	public double getStartZ() {
		return start.z;
	}
	
	/**
	 * Gets the ending x coordinate of the line
	 * @return The x coordinate
	 */
	public double getEndX() {
		return end.x;
	}
	
	/**
	 * Gets the ending y coordinate of the line
	 * @return The y coordinate
	 */
	public double getEndY() {
		return end.y;
	}
	
	/**
	 * Gets the ending z coordinate of the line
	 * @return The z coordinate
	 */
	public double getEndZ() {
		return end.z;
	}
	
	/**
	 * Gets the 2D slope for the line
	 * @return The slope
	 */
	public double get2DSlope() {
		return (end.y - start.y)/(end.x - start.x);
	}
	
	private TwoDimensionalVector find2DPoint(double x, double slope) {
		double yInt = start.y - (start.x * slope);
		return new TwoDimensionalVector(x, (x * slope) + yInt);
	}
	
	/**
	 * Plugs in the given x coordinate to the line to calculate a 2D point from its equation
	 * @param x The x coordinate
	 * @return The calculated coordinate
	 */
	public TwoDimensionalVector find2DPoint(double x) {
		return find2DPoint(x, get2DSlope());
	}
	
	/**
	 * Checks if the point is valid on a 2D plane
	 * @param coord The coord to check
	 * @return If it's on the line
	 */
	public boolean isPointValid(TwoDimensionalVector coord) {
		return ((coord.x <= start.x && coord.x >= end.x) || (coord.x >= start.x && coord.x <= end.x)) && //If point is within bounds of the line
				(find2DPoint(coord.x).y == coord.y); //If the point follows the rules of the line
	}
	
	/**
	 * Returns all points with whole number x values on the line
	 * @return The points
	 */
	public List<TwoDimensionalVector> getAll2DPoints() {
		List<TwoDimensionalVector> vectors = new ArrayList<TwoDimensionalVector>();
		TwoDimensionalVector start = getSorted2DCoords()[0];
		TwoDimensionalVector end = getSorted2DCoords()[1];
		int x = start.getRoundedX();
		if (isPointValid(new TwoDimensionalVector(start.getRoundedX(), start.getRoundedY())))
			vectors.add(new TwoDimensionalVector(start.getRoundedX(), start.getRoundedY()));
		while (!(x > end.getRoundedX())) {
			x++;
			vectors.add(find2DPoint(x));
		}
		return vectors;
	}
	
	private TwoDimensionalVector[] getSorted2DCoords() {
		TwoDimensionalVector[] array = new TwoDimensionalVector[2];
		if (start.x < end.x) {
			array[0] = start.to2D();
			array[1] = end.to2D();
		} else {
			array[0] = end.to2D();
			array[1] = start.to2D();
		}
		return array;
	}
	
	private double get2DSlopeForZ() {
		return (end.z - start.z)/(end.x - start.x);
	}
	
	/**
	 * Plugs in the given x coordinate to the line to calculate a 3D point from its equation
	 * @param x The x coordinate
	 * @return The calculated coordinate
	 */
	public ThreeDimensionalVector find3DPoint(double x) { //Basically does two 2D calculations for y, where the first calculation is actually y and the second z
		double y = find2DPoint(x, get2DSlope()).y;
		double z = find2DPoint(x, get2DSlopeForZ()).y;
		return new ThreeDimensionalVector(x, y, z);
	}
	
	/**
	 * Checks if the point is valid on a 3D plane
	 * @param coord The coord to check
	 * @return If it's on the line
	 */
	public boolean isPointValid(ThreeDimensionalVector coord) {
		return ((coord.x <= start.x && coord.x >= end.x) || (coord.x >= start.x && coord.x <= end.x)) && //If point is within bounds of the line
				(find3DPoint(coord.x).y == coord.y && find3DPoint(coord.x).z == coord.z); //If the point follows the rules of the line
	}
	
	/**
	 * Returns all points with whole number x values on the line
	 * @return The points
	 */
	public List<ThreeDimensionalVector> getAll3DPoints() {
		List<ThreeDimensionalVector> vectors = new ArrayList<ThreeDimensionalVector>();
		ThreeDimensionalVector start = getSorted3DCoords()[0];
		ThreeDimensionalVector end = getSorted3DCoords()[1];
		int x = start.getRoundedX();
		if (isPointValid(new ThreeDimensionalVector(start.getRoundedX(), start.getRoundedY(), start.getRoundedZ())))
			vectors.add(new ThreeDimensionalVector(start.getRoundedX(), start.getRoundedY(), start.getRoundedZ()));
		while (!(x > end.getRoundedX())) {
			x++;
			vectors.add(find3DPoint(x));
		}
		return vectors;
	}
	
	private ThreeDimensionalVector[] getSorted3DCoords() {
		ThreeDimensionalVector[] array = new ThreeDimensionalVector[2];
		if (start.x < end.x) {
			array[0] = start;
			array[1] = end;
		} else {
			array[0] = end;
			array[1] = start;
		}
		return array;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Line)
			return ((Line) other).start.equals(start) && ((Line) other).end.equals(end);
		return false;
	}
}
