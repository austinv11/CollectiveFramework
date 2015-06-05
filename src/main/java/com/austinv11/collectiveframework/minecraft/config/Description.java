package com.austinv11.collectiveframework.minecraft.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describes a config field
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Description {
	
	/**
	 * A short comment explaining what the config value does
	 * @return The comment
	 */
	String comment();
	
	/**
	 * The category the config value is under
	 * @return The category (capitalization matters!)
	 */
	String category() default "General";
	
	/**
	 * Determines whether a config option only effects the client
	 * @return True for the option to be client side only
	 */
	boolean clientSideOnly() default false;
}
