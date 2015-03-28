package com.austinv11.collectiveframework.minecraft.logging;

import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

/**
 * A simple wrapper class for easier logging
 */
public class Logger {
	
	private String label;
	
	/**
	 * Constructor for the logger
	 * @param label The label to use
	 */
	public Logger(String label) {
		this.label = label;
	}
	
	private void log(Level level, Object object){
		FMLLog.log(label, level, String.valueOf(object));
	}
	
	/**
	 * Logs a message at the ALL level
	 * @param object The object to log
	 */
	public void all(Object object){
		log(Level.ALL, object);
	}
	
	/**
	 * Logs a message at the DEBUG level
	 * @param object The object to log
	 */
	public void debug(Object object){
		log(Level.DEBUG, object);
	}
	
	/**
	 * Logs a message at the ERROR level
	 * @param object The object to log
	 */
	public void error(Object object){
		log(Level.ERROR, object);
	}
	
	/**
	 * Logs a message at the FATAL level
	 * @param object The object to log
	 */
	public void fatal(Object object){
		log(Level.FATAL, object);
	}
	
	/**
	 * Logs a message at the INFO level
	 * @param object The object to log
	 */
	public void info(Object object){
		log(Level.INFO, object);
	}
	
	/**
	 * Logs a message at the OFF level
	 * @param object The object to log
	 */
	public void off(Object object){
		log(Level.OFF, object);
	}
	
	/**
	 * Logs a message at the TRACE level
	 * @param object The object to log
	 */
	public void trace(Object object){
		log(Level.TRACE, object);
	}
	
	/**
	 * Logs a message at the WARN level
	 * @param object The object to log
	 */
	public void warn(Object object){
		log(Level.WARN, object);
	}
}
