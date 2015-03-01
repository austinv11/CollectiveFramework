package com.austinv11.collectiveframework.multithreading;

import java.lang.reflect.Field;

/**
 * Simple thread instance for use alongside the {@link com.austinv11.collectiveframework.multithreading.SimpleRunnable}
 */
public class SimpleThread extends Thread {
	
	public boolean isActive = true;
	
	protected SimpleThread() {
		super();
	}
	
	protected SimpleThread(Runnable target) {
		super(target);
	}
	
	protected SimpleThread(ThreadGroup group, Runnable target) {
		super(group, target);
	}
	
	protected SimpleThread(String name) {
		super(name);
	}
	
	protected SimpleThread(ThreadGroup group, String name) {
		super(group, name);
	}
	
	protected SimpleThread(Runnable target, String name) {
		super(target, name);
	}
	
	protected SimpleThread(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
	}
	
	protected SimpleThread(ThreadGroup group, Runnable target, String name, long stackSize) {
		super(group, target, name, stackSize);
	}
	
	@Override
	public void run() {
		if (isActive)
			super.run();
	}
	
	/**
	 * Sets the runnable that the thread executes
	 * @param runnable The runnable to execute
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public void setTarget(Runnable runnable) throws NoSuchFieldException, IllegalAccessException {
		Field field = Thread.class.getField("target");
		field.setAccessible(true);
		field.set(this, runnable);
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof SimpleThread && getName().equals(((SimpleThread) other).getName());
	}
}
