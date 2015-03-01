package com.austinv11.collectiveframework.dependencies.download;

import java.util.EnumSet;

/**
 * Class for a download provider
 */
public interface IDownloadProvider {
	
	/**
	 * Method to download a file
	 * @param url URL to download the file from
	 * @param downloadPath Path to download the file to
	 * @return If this was a successful operation
	 */
	public boolean downloadFile(String url, String downloadPath);
	
	/**
	 * This is used internally to determine which download provider to use
	 * @return The set of possible file types which could be downloaded
	 */
	public EnumSet<FileType> getCapabilities();
}
