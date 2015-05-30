package com.austinv11.collectiveframework.multithreading;

import java.lang.reflect.Field;

/**
 * Simple thread instance for use alongside the {@link com.austinv11.collectiveframework.multithreading.SimpleRunnable}
 */
public class SimpleThread extends Thread {
	
	public volatile boolean isActive = true;
	public volatile long delay = -1;
	public volatile boolean started = false;
	
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
		do {
			if (started) {
				if (delay == -1) {
					if (isActive) {
						super.run();
					}
				} else {
					while (true) {
						if (isActive) {
							super.run();
							try {
								if (isActive && delay >= 0)
									sleep(delay);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} else {
							break;
						}
					}
				}
			}
		} while (SimpleRunnable.RESTRICT_THREAD_USAGE);
	}
	
	@Override
	public synchronized void start() {
		int status = -1;
		started = true;
		try {
			status = getStatus();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (status != 0) {
			isActive = true;
		} else {
			super.start();
		}
	}
	
	/**
	 * Sets the runnable that the thread executes
	 * @param runnable The runnable to execute
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public void setTarget(Runnable runnable) throws NoSuchFieldException, IllegalAccessException {
		Field field = Thread.class.getDeclaredField("target");
		field.setAccessible(true);
		field.set(this, runnable);
	}
	
	private Integer getStatus() throws NoSuchFieldException, IllegalAccessException {
		Field field = Thread.class.getDeclaredField("threadStatus");
		field.setAccessible(true);
		return (Integer) field.get(this);
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof SimpleThread && getName().equals(((SimpleThread) other).getName());
	}
	
	@Override
	public String toString() {
		return "SimpleThread(Name: "+getName()+" Is Active: "+isActive+")";
	}
}
