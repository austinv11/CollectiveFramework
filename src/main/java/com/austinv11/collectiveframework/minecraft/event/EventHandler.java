package com.austinv11.collectiveframework.minecraft.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation, use this to have Collective Framework automatically register the class to the forge/fml event bus(es)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventHandler {
	
	/**
	 * Use this if you want a specific instance to be registered or if the class doesn't use a default constructor
	 * @return The (static) field name
	 */
	public String instanceName() default "";
	
	/**
	 * Checked to determine if the class should be registered on the FML event bus
	 * @return True to be registered
	 */
	public boolean fmlBus() default true;
	
	/**
	 * Checked to determine if the class should be registered on the Forge event bus
	 * @return True to be registered
	 */
	public boolean forgeBus() default true;
}
