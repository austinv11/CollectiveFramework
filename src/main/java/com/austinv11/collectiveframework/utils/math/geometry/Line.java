package com.austinv11.collectiveframework.utils.math.geometry;

import com.austinv11.collectiveframework.utils.math.ThreeDimensionalVector;
import com.austinv11.collectiveframework.utils.math.TwoDimensionalVector;

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
	
	
}
