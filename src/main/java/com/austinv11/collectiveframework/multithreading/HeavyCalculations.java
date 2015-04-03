package com.austinv11.collectiveframework.multithreading;

import com.austinv11.collectiveframework.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * This class is used to simplify multithreading for performance by delegating
 * multiple concurrent calculations across a variable amount of threads
 */
public class HeavyCalculations {
	
	private static int calculationNumber = 0;
	
	private List<CalculationThread> threads = new ArrayList<CalculationThread>();
	
	private ConcurrentHashMap<ICalculations, Future> futures = new ConcurrentHashMap<ICalculations, Future>();
	
	/**
	 * Constructor for HeavyCalculations
	 * @param numberOfThreads The number of threads to delegate calculations to
	 */
	public HeavyCalculations(int numberOfThreads) {
		for (int i = 0; i < numberOfThreads; i++)
			threads.add(new CalculationThread());
	}
	
	private void removeCalculation(ICalculations calculations) {
		for (CalculationThread thread : threads)
			if (thread.calculations.contains(calculations)) {
				thread.calculations.remove(calculations);
				return;
			}
	}
	
	private void delegate(ICalculations calculations) {
		int minCalculations = -1;
		CalculationThread thread = null;
		for (CalculationThread thread1 : threads) {
			if ((minCalculations == -1 || minCalculations > thread1.calculations.size())) {
				minCalculations = thread1.calculations.size();
				thread = thread1;
			}
		}
		thread.calculations.add(calculations);
	}
	
	/**
	 * Adds a calculation to the queues
	 * @param calculation The calculation it MUST implement {@link ICalculations}
	 * @return A future representing the eventual calculation {@link Future#get()} returns the object passed, 
	 * although after the calculations finished
	 */
	public <T> Future<T> addCalculation(T calculation) {
		ICalculations calculations = (ICalculations) calculation;
		delegate(calculations);
		Future<T> future = new FutureImpl<T>(calculations, calculation);
		futures.put(calculations, future);
		return future;
	}
	
	/**
	 * Adds a calculation to the queues by wrapping an object to implement {@link ICalculations}
	 * <b>This method is discouraged! It is meant for objects where it isn't possible to implement {@link ICalculations}</b>
	 * @param calculation The object representing the calculations
	 * @param methodToCalculate The method to call for calculations
	 * @param params The parameters for the method
	 * @return A future representing the eventual calculation {@link Future#get()} returns the object passed, 
	 * although after the calculations finished
	 */
	public <T> Future<T> addCalculation(T calculation, String methodToCalculate, Object... params) {
		ICalculations calculations = new ICalculationsWrapper(calculation, methodToCalculate, params);
		delegate(calculations);
		Future<T> future = new FutureImpl<T>(calculations, calculation);
		futures.put(calculations, future);
		return future;
	}
	
	/**
	 * Adds a calculation to the queues by wrapping a class to implement {@link ICalculations}, used for static methods
	 * when the class can't be instantiated
	 * <b>This method is discouraged! It is meant for objects where it isn't possible to implement {@link ICalculations}</b>
	 * @param calculationClass The class representing the calculations
	 * @param methodToCalculate The method to call for calculations
	 * @param params The parameters for the method
	 * @return A future representing the eventual calculation {@link Future#get()} returns null
	 */
	public Future<ICalculations> addCalculation(Class calculationClass, String methodToCalculate, Object... params) {
		ICalculations calculations = new ICalculationsWrapper(calculationClass, methodToCalculate, params);
		delegate(calculations);
		Future<ICalculations> future = new FutureImpl<ICalculations>(calculations, null);
		futures.put(calculations, future);
		return future;
	}
	
	private class CalculationThread extends SimpleRunnable {
		
		private int id;
		public ConcurrentLinkedDeque<ICalculations> calculations = new ConcurrentLinkedDeque<ICalculations>();
		
		public CalculationThread() {
			id = calculationNumber++;
			this.start();
		}
		
		@Override
		public void run() {
			if (!calculations.isEmpty()) {
				ICalculations calculation = calculations.pop();
				calculation.doCalculation();
				((FutureImpl)futures.get(calculation)).setDone();
			}
		}
		
		@Override
		public String getName() {
			return "Calculation Thread #"+id;
		}
	}
	
	private class ICalculationsWrapper implements ICalculations {
		
		private Object object;
		private String methodName;
		private Object[] params;
		
		public ICalculationsWrapper(Object object, String methodName, Object[] params) {
			this.object = object;
			this.methodName = methodName;
			this.params = params;
		}
		
		@Override
		public void doCalculation() {
			try {
				Method m;
				if (object instanceof Class)
					m = ReflectionUtils.getDeclaredOrNormalMethod(methodName, (Class) object);
				else
					m = ReflectionUtils.getDeclaredOrNormalMethod(methodName, object.getClass());
				m.invoke(object instanceof Class ? null : object, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private class FutureImpl<V> implements Future<V> {
		
		private volatile ICalculations calculations;
		private Object object;
		
		private boolean isCancelled = false;
		public volatile boolean isDone = false;
		
		public FutureImpl(ICalculations calculations, Object object) {
			this.calculations = calculations;
			this.object = object;
		}
		
		public void setDone() {
			isDone = true;
			futures.remove(calculations);
		}
		
		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			isCancelled = true;
			removeCalculation(calculations);
			return true;
		}
		
		@Override
		public boolean isCancelled() {
			return isCancelled;
		}
		
		@Override
		public boolean isDone() {
			return isDone;
		}
		
		@Override
		public V get() throws InterruptedException, ExecutionException {
			while (!isDone) {}
			if (object != null)
				return (V) object;
			return null;
		}
		
		@Override
		public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
			timeout = unit.toMillis(timeout);
			while (!isDone && timeout > 0) {
				timeout--;
				this.wait(1);
			}
			if (object != null)
				return (V) object;
			return null;
		}
	}
}
