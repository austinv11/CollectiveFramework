package com.austinv11.collectiveframework.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class with helpful methods for handling arrays
 */
public class ArrayUtils {
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(Object[] array, Object key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(byte[] array, byte key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(short[] array, short key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(int[] array, int key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(long[] array, long key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(float[] array, float key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(double[] array, double key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(boolean[] array, boolean key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(char[] array, char key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @param startIndex The index to start searching from    
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(Object[] array, Object key, int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i].equals(key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @param startIndex The index to start searching from    
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(byte[] array, byte key, int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @param startIndex The index to start searching from    
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(short[] array, short key, int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @param startIndex The index to start searching from    
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(int[] array, int key, int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @param startIndex The index to start searching from    
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(long[] array, long key, int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @param startIndex The index to start searching from    
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(float[] array, float key, int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @param startIndex The index to start searching from    
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(double[] array, double key, int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @param startIndex The index to start searching from    
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(boolean[] array, boolean key, int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the specified key in an array
	 * @param array The array to search
	 * @param key The key to search for
	 * @param startIndex The index to start searching from    
	 * @return The index, or -1 if the key was not found
	 */
	public static int indexOf(char[] array, char key, int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == (key))
				return i;
		}
		return -1;
	}
	
	/**
	 * Gets the value from an array, prevents {@link java.lang.ArrayIndexOutOfBoundsException} by wrapping
	 * @param array The array to use
	 * @param index The index to get the value from
	 * @return The value
	 */
	public static <T> T wrappedRetrieve(T[] array, int index) {
		if (index < array.length)
			return array[index];
		while (index >= array.length)
			index -= array.length;
		return array[index];
	}
	
	/**
	 * Gets the value from an array, prevents {@link java.lang.ArrayIndexOutOfBoundsException} by wrapping
	 * @param array The array to use
	 * @param index The index to get the value from
	 * @return The value
	 */
	public static byte wrappedRetrieve(byte[] array, int index) {
		if (index < array.length)
			return array[index];
		while (index >= array.length)
			index -= array.length;
		return array[index];
	}
	
	/**
	 * Gets the value from an array, prevents {@link java.lang.ArrayIndexOutOfBoundsException} by wrapping
	 * @param array The array to use
	 * @param index The index to get the value from
	 * @return The value
	 */
	public static short wrappedRetrieve(short[] array, int index) {
		if (index < array.length)
			return array[index];
		while (index >= array.length)
			index -= array.length;
		return array[index];
	}
	
	/**
	 * Gets the value from an array, prevents {@link java.lang.ArrayIndexOutOfBoundsException} by wrapping
	 * @param array The array to use
	 * @param index The index to get the value from
	 * @return The value
	 */
	public static int wrappedRetrieve(int[] array, int index) {
		if (index < array.length)
			return array[index];
		while (index >= array.length)
			index -= array.length;
		return array[index];
	}
	
	/**
	 * Gets the value from an array, prevents {@link java.lang.ArrayIndexOutOfBoundsException} by wrapping
	 * @param array The array to use
	 * @param index The index to get the value from
	 * @return The value
	 */
	public static long wrappedRetrieve(long[] array, int index) {
		if (index < array.length)
			return array[index];
		while (index >= array.length)
			index -= array.length;
		return array[index];
	}
	
	/**
	 * Gets the value from an array, prevents {@link java.lang.ArrayIndexOutOfBoundsException} by wrapping
	 * @param array The array to use
	 * @param index The index to get the value from
	 * @return The value
	 */
	public static float wrappedRetrieve(float[] array, int index) {
		if (index < array.length)
			return array[index];
		while (index >= array.length)
			index -= array.length;
		return array[index];
	}
	
	/**
	 * Gets the value from an array, prevents {@link java.lang.ArrayIndexOutOfBoundsException} by wrapping
	 * @param array The array to use
	 * @param index The index to get the value from
	 * @return The value
	 */
	public static double wrappedRetrieve(double[] array, int index) {
		if (index < array.length)
			return array[index];
		while (index >= array.length)
			index -= array.length;
		return array[index];
	}
	
	/**
	 * Gets the value from an array, prevents {@link java.lang.ArrayIndexOutOfBoundsException} by wrapping
	 * @param array The array to use
	 * @param index The index to get the value from
	 * @return The value
	 */
	public static boolean wrappedRetrieve(boolean[] array, int index) {
		if (index < array.length)
			return array[index];
		while (index >= array.length)
			index -= array.length;
		return array[index];
	}
	
	/**
	 * Gets the value from an array, prevents {@link java.lang.ArrayIndexOutOfBoundsException} by wrapping
	 * @param array The array to use
	 * @param index The index to get the value from
	 * @return The value
	 */
	public static char wrappedRetrieve(char[] array, int index) {
		if (index < array.length)
			return array[index];
		while (index >= array.length)
			index -= array.length;
		return array[index];
	}
	
	/**
	 * Removes any repeating data from the array
	 * @param array The array to remove repeats from
	 * @return The new array
	 */
	public static <T> T[] removeRepeats(T[] array) {
		List<T> cache = new ArrayList<T>();
		for (T o : array)
			if (!cache.contains(o))
				cache.add(o);
		return cache.toArray(Arrays.copyOf(array, cache.size()));
	}
	
	/**
	 * Removes any repeating data from the array
	 * @param array The array to remove repeats from
	 * @return The new array
	 */
	public static byte[] removeRepeats(byte[] array) {
		List<Byte> cache = new ArrayList<Byte>();
		for (Byte o : array)
			if (!cache.contains(o))
				cache.add(o);
		Byte[] objArray = cache.toArray(new Byte[cache.size()]);
		byte[] newArray = new byte[objArray.length];
		for (int i = 0; i < objArray.length; i++)
			newArray[i] = objArray[i];
		return newArray;
	}
	
	/**
	 * Removes any repeating data from the array
	 * @param array The array to remove repeats from
	 * @return The new array
	 */
	public static short[] removeRepeats(short[] array) {
		List<Short> cache = new ArrayList<Short>();
		for (Short o : array)
			if (!cache.contains(o))
				cache.add(o);
		Short[] objArray = cache.toArray(new Short[cache.size()]);
		short[] newArray = new short[objArray.length];
		for (int i = 0; i < objArray.length; i++)
			newArray[i] = objArray[i];
		return newArray;
	}
	
	/**
	 * Removes any repeating data from the array
	 * @param array The array to remove repeats from
	 * @return The new array
	 */
	public static int[] removeRepeats(int[] array) {
		List<Integer> cache = new ArrayList<Integer>();
		for (Integer o : array)
			if (!cache.contains(o))
				cache.add(o);
		Integer[] objArray = cache.toArray(new Integer[cache.size()]);
		int[] newArray = new int[objArray.length];
		for (int i = 0; i < objArray.length; i++)
			newArray[i] = objArray[i];
		return newArray;
	}
	
	/**
	 * Removes any repeating data from the array
	 * @param array The array to remove repeats from
	 * @return The new array
	 */
	public static long[] removeRepeats(long[] array) {
		List<Long> cache = new ArrayList<Long>();
		for (Long o : array)
			if (!cache.contains(o))
				cache.add(o);
		Long[] objArray = cache.toArray(new Long[cache.size()]);
		long[] newArray = new long[objArray.length];
		for (int i = 0; i < objArray.length; i++)
			newArray[i] = objArray[i];
		return newArray;
	}
	
	/**
	 * Removes any repeating data from the array
	 * @param array The array to remove repeats from
	 * @return The new array
	 */
	public static float[] removeRepeats(float[] array) {
		List<Float> cache = new ArrayList<Float>();
		for (Float o : array)
			if (!cache.contains(o))
				cache.add(o);
		Float[] objArray = cache.toArray(new Float[cache.size()]);
		float[] newArray = new float[objArray.length];
		for (int i = 0; i < objArray.length; i++)
			newArray[i] = objArray[i];
		return newArray;
	}
	
	/**
	 * Removes any repeating data from the array
	 * @param array The array to remove repeats from
	 * @return The new array
	 */
	public static double[] removeRepeats(double[] array) {
		List<Double> cache = new ArrayList<Double>();
		for (Double o : array)
			if (!cache.contains(o))
				cache.add(o);
		Double[] objArray = cache.toArray(new Double[cache.size()]);
		double[] newArray = new double[objArray.length];
		for (int i = 0; i < objArray.length; i++)
			newArray[i] = objArray[i];
		return newArray;
	}
	
	/**
	 * Removes any repeating data from the array
	 * @param array The array to remove repeats from
	 * @return The new array
	 */
	public static boolean[] removeRepeats(boolean[] array) {
		List<Boolean> cache = new ArrayList<Boolean>();
		for (Boolean o : array)
			if (!cache.contains(o))
				cache.add(o);
		Boolean[] objArray = cache.toArray(new Boolean[cache.size()]);
		boolean[] newArray = new boolean[objArray.length];
		for (int i = 0; i < objArray.length; i++)
			newArray[i] = objArray[i];
		return newArray;
	}
	
	/**
	 * Removes any repeating data from the array
	 * @param array The array to remove repeats from
	 * @return The new array
	 */
	public static char[] removeRepeats(char[] array) {
		List<Character> cache = new ArrayList<Character>();
		for (Character o : array)
			if (!cache.contains(o))
				cache.add(o);
		Character[] objArray = cache.toArray(new Character[cache.size()]);
		char[] newArray = new char[objArray.length];
		for (int i = 0; i < objArray.length; i++)
			newArray[i] = objArray[i];
		return newArray;
	}
}
