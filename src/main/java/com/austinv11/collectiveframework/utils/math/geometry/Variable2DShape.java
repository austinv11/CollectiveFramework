package com.austinv11.collectiveframework.utils.math.geometry;

import com.austinv11.collectiveframework.utils.ArrayUtils;
import com.austinv11.collectiveframework.utils.math.MathUtils;
import com.austinv11.collectiveframework.utils.math.TwoDimensionalVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class which represents a 2D shape with an indefinite amount of sides (until instantiation)
 */
public class Variable2DShape {
	
	private Line[] sides;
	
	/**
	 * Constructor for a 2D shape
	 * @param vectors The vectors to form the shape with
	 * @throws IncompatibleDimensionsException
	 */
	public Variable2DShape(TwoDimensionalVector... vectors) throws IncompatibleDimensionsException {
		if (vectors.length < 3)
			throw new IncompatibleDimensionsException("Not enough vectors!");
		Arrays.sort(vectors);
		Line[] lines = new Line[vectors.length];
		for (int i = 0; i < vectors.length; i++)
			lines[i] = new Line(vectors[i], ArrayUtils.wrappedRetrieve(vectors, i+1));
		sides = lines;
		init();
	}
	
	/**
	 * Constructor for a 2D shape
	 * @param lines The lines to form the shape with
	 * @throws IncompatibleDimensionsException
	 */
	public Variable2DShape(Line... lines) throws IncompatibleDimensionsException {
		if (lines.length < 3)
			throw new IncompatibleDimensionsException("Not enough sides!");
		sides = lines;
		init();
	}
	
	private void init() throws IncompatibleDimensionsException { //This ensures that a proper polygon was actually formed
		int numOfSides = getNumberOfSides();
		TwoDimensionalVector[] nonRepeatingVectors = getVertices();
		if (!(nonRepeatingVectors.length == numOfSides))
			throw new IncompatibleDimensionsException("The dimensions do not connect to form a polygon!");
	}
	
	/**
	 * Gets the number of sides for this shape
	 * @return The number of sides
	 */
	public int getNumberOfSides() {
		return sides.length;
	}
	
	/**
	 * Retrieves all points representing vertices on the shape
	 * @return The vertices
	 */
	public TwoDimensionalVector[] getVertices() {
		List<TwoDimensionalVector> vectors = new ArrayList<TwoDimensionalVector>();
		for (Line line : sides) {
			vectors.add(line.get2DStart());
			vectors.add(line.get2DEnd());
		}
		TwoDimensionalVector[] vectorArray = vectors.toArray(new TwoDimensionalVector[vectors.size()]);
		return ArrayUtils.removeRepeats(vectorArray);
	}
	
	/**
	 * Gets the perimeter of the shape
	 * @return The perimeter
	 */
	public double getPerimeter() {
		double perimeter = 0;
		for (Line line : sides)
			perimeter += line.getLength();
		return perimeter;
	}
	
	/**
	 * Calculates the area of the shape
	 * @return The area
	 */
	public double getArea() {
		double area = 0;
		TwoDimensionalVector[] vertices = getVertices();
		for (int i = 0; i < vertices.length; i++) {
			TwoDimensionalVector vertex1 = ArrayUtils.wrappedRetrieve(vertices, i);
			TwoDimensionalVector vertex2 = ArrayUtils.wrappedRetrieve(vertices, i+1);
			area += (vertex1.x * vertex2.y) - (vertex1.y * vertex2.x);
		}
		area = Math.abs(area / 2);
		return area;
	}
	
	/**
	 * Creates an array of sides from the shape where the side touches the given coord
	 * This should only ever return 0-2 lines
	 * @param coord The coordinate point
	 * @return The sides
	 */
	public Line[] findSidesWithPoint(TwoDimensionalVector coord) {
		List<Line> lines = new ArrayList<Line>();
		for (Line line : sides)
			if (line.isPointValid(coord))
				lines.add(line);
		return lines.toArray(new Line[lines.size()]);
	}
	
	/**
	 * Finds the (internal) angle of the given vertex
	 * @param vertex The vertex
	 * @return The angle
	 * @throws IncompatibleDimensionsException
	 */
	public float getAngleForVertex(TwoDimensionalVector vertex) throws IncompatibleDimensionsException {
		Line[] sides = findSidesWithPoint(vertex);
		if (sides.length != 2)
			throw new IncompatibleDimensionsException(vertex.toString()+" is not a valid vertex!");
		double slope1 = Double.isNaN(sides[0].get2DSlope()) ? Double.MAX_VALUE : sides[0].get2DSlope();
		double slope2 = Double.isNaN(sides[1].get2DSlope()) ? Double.MAX_VALUE : sides[1].get2DSlope();
		return (float) Math.atan(Math.abs((slope1-slope2)/(1+(slope1*slope2))));
	}
	
	/**
	 * Gets the lines representing the sides of the shape
	 * @return The sides
	 */
	public Line[] getSides() {
		return sides;
	}
	
	/**
	 * Calculates the approximate center of the polygon
	 * @return The center coordinates
	 */
	public TwoDimensionalVector getCentroid() {
		TwoDimensionalVector[] vertices = getVertices();
		double centerX = 0;
		double centerY = 0;
		for (TwoDimensionalVector vertex : vertices) {
			centerX += vertex.x;
			centerY += vertex.y;
		}
		centerX /= getNumberOfSides();
		centerY /= getNumberOfSides();
		return new TwoDimensionalVector(centerX, centerY);
	}
	
	/**
	 * Translates the whole shape to make a new shape with a matching centroid
	 * @param centroid New centroid
	 * @return Modified shape
	 */
	public Variable2DShape setCentroid(TwoDimensionalVector centroid) {
		TwoDimensionalVector oldCentroid = getCentroid();
		double xDiff = centroid.x - oldCentroid.x;
		double yDiff = centroid.y - oldCentroid.y;
		TwoDimensionalVector toAdd = new TwoDimensionalVector(xDiff, yDiff);
		TwoDimensionalVector[] vertices = getVertices();
		List<TwoDimensionalVector> newVertices = new ArrayList<TwoDimensionalVector>();
		for (TwoDimensionalVector vertex : vertices)
			newVertices.add(vertex.add(toAdd));
		try {
			return new Variable2DShape(newVertices.toArray(new TwoDimensionalVector[vertices.length]));
		} catch (IncompatibleDimensionsException e) {
			e.printStackTrace();
		}
		return null; //This should never be reached
	}
	
	/**
	 * Rotates the shape around a point
	 * @param point The point to rotate around
	 * @param angle The angle to rotate by
	 * @return The new shape
	 */
	public Variable2DShape rotate(TwoDimensionalVector point, double angle) {
		TwoDimensionalVector[] vertices = getVertices();
		List<TwoDimensionalVector> newVertices = new ArrayList<TwoDimensionalVector>();
		for (TwoDimensionalVector vertex : vertices)
			newVertices.add(vertex.rotate(point, angle));
		try {
			return new Variable2DShape(newVertices.toArray(new TwoDimensionalVector[vertices.length]));
		} catch (IncompatibleDimensionsException e) {
			e.printStackTrace();
		}
		return null; //This should never be reached
	}
	
	/**
	 * Rotates the shape about its centroid
	 * @param angle The angle to rotate by
	 * @return The new shape
	 */
	public Variable2DShape rotate(double angle) {
		return rotate(getCentroid(), angle);
	}
	
	/**
	 * Checks if the provided point is on the shape's perimeter
	 * @param coord The point
	 * @return If the point is on the perimeter
	 */
	public boolean isPointOnPerimeter(TwoDimensionalVector coord) {
		for (Line l : sides)
			if (l.isPointValid(coord))
				return true;
		return false;
	}
	
	/**
	 * Checks if a point is inside the shape. 
	 * DO NOT TRUST THIS- it is not always accurate, it is only 100% accurate with rectangles
	 * @param coord The point
	 * @return If the point is in the shape
	 */
	@Deprecated
	public boolean isPointValid(TwoDimensionalVector coord) { //FIXME
		if (isPointOnPerimeter(coord))
			return true;
		TwoDimensionalVector[] vertices = getVertices();
		double[] xVals = new double[vertices.length];
		double[] yVals = new double[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			xVals[i] = vertices[i].x;
			yVals[i] = vertices[i].y;
		}
		double minX, maxX, minY, maxY;
		minX = MathUtils.getMin(xVals);
		maxX = MathUtils.getMax(xVals);
		minY = MathUtils.getMin(yVals);
		maxY = MathUtils.getMax(yVals);
		return MathUtils.isBetween(minX, maxX, coord.x, true) && MathUtils.isBetween(minY, maxY, coord.y, true);
	}
	
	/**
	 * Gets all the points in the shape (with whole number coords)
	 * WARNING- Deprecated because it uses isPointValid
	 * @return The points
	 */
	@Deprecated
	public TwoDimensionalVector[] getAllPoints() {
		List<TwoDimensionalVector> points = new ArrayList<TwoDimensionalVector>();
		TwoDimensionalVector[] vertices = getVertices();
		double[] xVals = new double[vertices.length];
		double[] yVals = new double[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			xVals[i] = vertices[i].x;
			yVals[i] = vertices[i].y;
		}
		double minX, maxX, minY, maxY;
		minX = MathUtils.getMin(xVals);
		maxX = MathUtils.getMax(xVals);
		minY = MathUtils.getMin(yVals);
		maxY = MathUtils.getMax(yVals);
		for (int x = (int) minX; x <= maxX; x++)
			for (int y = (int)minY; y <= maxY; y++)
				if (isPointValid(new TwoDimensionalVector(x, y)))
					points.add(new TwoDimensionalVector(x, y));
		return points.toArray(new TwoDimensionalVector[points.size()]);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Variable2DShape)
			return ((Variable2DShape) other).getNumberOfSides() == getNumberOfSides() &&
					((Variable2DShape) other).getArea() == getArea() &&
					((Variable2DShape) other).getPerimeter() == getPerimeter();
		return false;
	}
	
	@Override
	public String toString() {
		return "Variable2DShape(Sides:"+getNumberOfSides()+")";
	}
}
