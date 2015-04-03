package com.austinv11.collectiveframework.utils;

/**
 * Simple class for figuring out how long things take to be done
 */
public class TimeProfiler {
	
	private long startTime;
	
	/**
	 * Starts a profiler at the current instant
	 */
	public TimeProfiler() {
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Gets the amount of time lapsed from instantiation to the method call
	 * @return The time (in ms)
	 */
	public long getTime() {
		return System.currentTimeMillis()-startTime;
	}
	
	/**
	 * Gets the time this object was instantiated at
	 * @return The time (in ms)
	 */
	public long getStartTime() {
		return startTime;
	}
}
