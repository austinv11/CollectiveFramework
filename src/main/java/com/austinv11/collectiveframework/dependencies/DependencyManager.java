package com.austinv11.collectiveframework.dependencies;

import com.austinv11.collectiveframework.dependencies.download.*;
import com.austinv11.collectiveframework.multithreading.SimpleRunnable;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A class for managing dependencies
 */
public class DependencyManager {
	
	private static final List<IDownloadProvider> providers = new ArrayList<IDownloadProvider>();
	
	private static volatile int numOfDownloaders = 0;
	
	static {
		registerDownloadProvider(new PlainTextProvider());
		registerDownloadProvider(new BinaryProvider());
	}
	
	/**
	 * Register a download provider, note: the earlier the registration, the higher priority it has
	 * @param provider The download provider
	 */
	public static void registerDownloadProvider(IDownloadProvider provider) {
		providers.add(provider);
	}
	
	/**
	 * Attempts to load the specified library (must be a proper java archive!)
	 * @param file The file representing the library
	 * @throws MalformedURLException
	 */
	public static void loadLibrary(File file) throws MalformedURLException {
		URLClassLoader loader = new URLClassLoader(new URL[]{file.toURI().toURL()}, DependencyManager.class.getClassLoader());
	}
	
	/**
	 * Method to download a file of the given file type
	 * @param url URL to download the file from
	 * @param downloadPath Path to download the file to
	 * @param fileToDownload File type of the downlaod
	 * @return Whether it succeeded
	 * @throws NoProviderFoundException
	 */
	public static boolean downloadFile(String url, String downloadPath, FileType fileToDownload) throws NoProviderFoundException {
		IDownloadProvider provider = null;
		for (IDownloadProvider p : providers)
			for (FileType t : p.getCapabilities())
				if (t.toString().equals(fileToDownload.toString())) {
					provider = p;
					break;
				}
		if (provider == null)
			throw new NoProviderFoundException(fileToDownload);
		return provider.downloadFile(url, downloadPath);
	}
	
	/**
	 * An asynchronous method to download a file of the given file type
	 * @param url URL to download the file from
	 * @param downloadPath Path to download the file to
	 * @param fileToDownload File type of the downlaod
	 * @return Future object representing the future result
	 * @throws NoProviderFoundException
	 */
	public static Future<Boolean> downloadFileAsync(String url, String downloadPath, FileType fileToDownload) throws NoProviderFoundException {
		IDownloadProvider provider = null;
		for (IDownloadProvider p : providers)
			for (FileType t : p.getCapabilities())
				if (t.toString().equals(fileToDownload.toString())) {
					provider = p;
					break;
				}
		if (provider == null)
			throw new NoProviderFoundException(fileToDownload);
		return new DownloadFuture(url, downloadPath, provider);
	}
	
	protected static class DownloadFuture implements Future<Boolean> {
		
		public DownloadRunnable runnable;
		private boolean isCancelled = false;
		
		protected DownloadFuture(String url, String downloadPath, IDownloadProvider provider) {
			runnable = new DownloadRunnable(url, downloadPath, provider);
			runnable.start();
		}
		
		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			runnable.disable(true);
			isCancelled = true;
			return true;
		}
		
		@Override
		public boolean isCancelled() {
			return isCancelled;
		}
		
		@Override
		public boolean isDone() {
			return runnable.isDone;
		}
		
		@Override
		public Boolean get() throws InterruptedException, ExecutionException {
			return runnable.didDownload;
		}
		
		@Override
		public Boolean get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
			return runnable.didDownload;
		}
		
		protected static class DownloadRunnable extends SimpleRunnable {
			
			public boolean isDone = false;
			public boolean didDownload = false;
			
			private String url, downloadPath;
			private IDownloadProvider provider;
			private int downloadNum;
			
			public DownloadRunnable(String url, String downloadPath, IDownloadProvider provider) {
				this.url = url;
				this.downloadPath = downloadPath;
				this.provider = provider;
				downloadNum = numOfDownloaders++;
			}
			
			@Override
			public void run() {
				didDownload = provider.downloadFile(url, downloadPath);
				isDone = true;
			}
			
			@Override
			public String getName() {
				return "CollectiveFramework Downloader #"+numOfDownloaders;
			}
		}
	}
}
