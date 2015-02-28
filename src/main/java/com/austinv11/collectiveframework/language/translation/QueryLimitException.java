package com.austinv11.collectiveframework.language.translation;

public class QueryLimitException extends Exception {
	
	public QueryLimitException(ITranslationProvider provider) {
		super("Unable to translate! Please tell the author that: "+provider.getProviderName()+" can no longer be queried!");
	}
}
