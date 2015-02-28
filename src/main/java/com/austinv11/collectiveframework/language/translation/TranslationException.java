package com.austinv11.collectiveframework.language.translation;

public class TranslationException extends Exception {
	
	public ErrorTypes errorType = ErrorTypes.UNKNOWN;
	
	public TranslationException(ErrorTypes error) {
		super("Problem translating: "+error.toString().replace("_", " ").toLowerCase()+"!");
		errorType = error;
	}
	
	public static enum ErrorTypes {
		TEXT_TOO_LONG, UNABLE_TO_TRANSLATE, LANGUAGE_NOT_SUPPORTED, NOT_ENOUGH_PARAMETERS, NO_VALID_PROVIDERS, UNKNOWN
	}
}
