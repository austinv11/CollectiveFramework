package com.austinv11.collectiveframework.minecraft.books.elements.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Use these elements to allow the IElement to be called on special events
 */
public class ElementType {
	
	/**
	 * Represents a button
	 */
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Button {}
	
	/**
	 * Represents an interactive portion of the screen
	 */
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Interactive {}
	
	/**
	 * Represents a text field
	 */
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface TextField {}
}
