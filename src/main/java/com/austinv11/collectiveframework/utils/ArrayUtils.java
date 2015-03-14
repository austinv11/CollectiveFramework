package com.austinv11.collectiveframework.utils;

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
}
