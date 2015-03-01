package com.austinv11.collectiveframework.dependencies.download;

public class NoProviderFoundException extends Exception {
	
	public NoProviderFoundException(FileType fileType) {
		super("No provider found for file type: "+fileType.toString());
	}
}
