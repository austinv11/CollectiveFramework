package com.austinv11.collectiveframework.multithreading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Runnable class to ease the management of threads
 */
public abstract class SimpleRunnable implements Runnable {
	
	//Key of true = the thread is being used, vice versa
	private volatile static HashMap<Boolean,List<SimpleThread>> threadMap = new HashMap<Boolean,List<SimpleThread>>();
	
	private volatile boolean isCleaned = false;
	private volatile int thread = -1;
	
	static {
		threadMap.put(true, new ArrayList<SimpleThread>());
		threadMap.put(false, new ArrayList<SimpleThread>());
	}
	
	/**
	 * Disables this thread from running
	 * @param clean Whether to clean the thread running - This will prevent this runnable from being able to run
	 * @throws IllegalThreadStateException
	 */
	public synchronized final void disable(boolean clean) throws IllegalThreadStateException {
		if (isCleaned)
			throw new IllegalThreadStateException("Thread "+this.getName()+" is cleaned already!");
		getThread().isActive = false;
		if (clean) {
			isCleaned = true;
			threadMap.get(false).add(getThread());
			threadMap.get(true).remove(thread);
		}
	}
	
	/**
	 * Attempts to reenable this thread
	 * @throws IllegalThreadStateException
	 */
	public synchronized final void enable() throws IllegalThreadStateException {
		if (isCleaned)
			throw new IllegalThreadStateException("Thread "+this.getName()+" is cleaned already!");
		getThread().isActive = true;
	}
	
	/**
	 * Runs a thread with this runnable
	 */
	public synchronized final void start() {
		getThread().start();
	}
	
	/**
	 * Called by the thread to run this runnable
	 */
	@Override
	public abstract void run();
	
	/**
	 * Gets the name of the thread
	 * @return The name
	 */
	public abstract String getName();
	
	/**
	 * Delays the given thread from running
	 * @param delay The length of the delay (in milliseconds)
	 */
	public synchronized final void delay(int delay) throws InterruptedException {
		getThread().wait(delay);
	}
	
	/**
	 * Gets whether is runnable is cleaned
	 * @return IsCleaned
	 */
	public synchronized boolean isCleaned() {
		return isCleaned;
	}
	
	/**
	 * Gets whether the runnable is running
	 * @returnIsEnabled
	 */
	public synchronized boolean isEnabled() {
		return getThread().isActive;
	}
	
	private synchronized final SimpleThread getThread() {
		if (this.thread == -1) { //Assign a thread
			if (threadMap.get(false).size() < 1) {
				SimpleThread thread = new SimpleThread(this);
				threadMap.get(true).add(thread);
				this.thread = threadMap.get(true).indexOf(thread);
				return getThread();
			} else {
				SimpleThread thread = threadMap.get(false).iterator().next();
				threadMap.get(false).remove(thread);
				thread.isActive = true;
				thread.setName(getName());
				try {
					thread.setTarget(this);
					threadMap.get(true).add(thread);
					this.thread = threadMap.get(true).indexOf(thread);
					return getThread();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else { //Fetch the thread
			return threadMap.get(true).get(this.thread);
		}
		return null; //This should never be reached
	}
}
