package com.austinv11.collectiveframework.language;

import com.austinv11.collectiveframework.language.translation.ITranslationProvider;
import com.austinv11.collectiveframework.language.translation.QueryLimitException;
import com.austinv11.collectiveframework.language.translation.TranslationException;
import com.austinv11.collectiveframework.language.translation.YandexProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class for translating text
 */
public class TranslationManager {
	
	private static final List<ITranslationProvider> translators = new ArrayList<ITranslationProvider>();
	
	//The cost of using free APIs
	private static final List<String> unusableProviders = new ArrayList<String>();
	
	//Helps reduce queries
	private static final HashMap<String, String> translationCache = new HashMap<String,String>();
	
	static {
		registerTranslationProvider(new YandexProvider());
	}
	
	/**
	 * Register a translation provider, note: the earlier the registration, the higher priority it has
	 * @param provider The translation provider
	 */
	public static void registerTranslationProvider(ITranslationProvider provider) {
		translators.add(provider);
	}
	
	/**
	 * Translates a given text (either from the unlocalized key or from standard text)
	 * @param text String to translate
	 * @param toLang Language to translate to
	 * @return The translated text
	 * @throws TranslationException
	 * @throws IOException
	 */
	public static String translate(String text, String toLang) throws TranslationException, IOException {
		if (translationCache.containsKey(text))
			return translationCache.get(text);
		ITranslationProvider provider;
		int i = 0;
		while (unusableProviders.contains(translators.get(i).getProviderName()) || !translators.get(i).canDetectLanguage()) {
			i++;
			if (!translators.contains(i))
				throw new TranslationException(TranslationException.ErrorTypes.NO_VALID_PROVIDERS);
		}
		provider = translators.get(i);
		try {
			String translation = provider.translate(text, toLang);
			translationCache.put(text, translation);
			return translation;
		} catch (QueryLimitException e) {
			unusableProviders.add(provider.getProviderName());
			return translate(text, toLang);
		}
	}
	
	/**
	 * Translates a given text (either from the unlocalized key or from standard text)
	 * @param text String to translate
	 * @param fromLang Language the string is from
	 * @param toLang Language to translate to
	 * @return The translated text
	 * @throws TranslationException
	 * @throws IOException
	 */
	public static String translate(String text, String fromLang, String toLang) throws TranslationException, IOException {
		if (translationCache.containsKey(text))
			return translationCache.get(text);
		ITranslationProvider provider;
		int i = 0;
		while (unusableProviders.contains(translators.get(i).getProviderName())) {
			i++;
			if (!translators.contains(i))
				throw new TranslationException(TranslationException.ErrorTypes.NO_VALID_PROVIDERS);
		}
		provider = translators.get(i);
		try {
			String translation = provider.translate(text, fromLang, toLang);
			translationCache.put(text, translation);
			return translation;
		} catch (QueryLimitException e) {
			unusableProviders.add(provider.getProviderName());
			return translate(text, fromLang, toLang);
		}
	}
	
	/**
	 * Method which attempts to detect the language of the given string
	 * @param text String to detect the language for
	 * @return Language code
	 * @throws TranslationException
	 * @throws IOException
	 */
	public static String detectLanguage(String text) throws TranslationException, IOException {
		ITranslationProvider provider;
		int i = 0;
		while (unusableProviders.contains(translators.get(i).getProviderName())) {
			i++;
			if (!translators.contains(i))
				throw new TranslationException(TranslationException.ErrorTypes.NO_VALID_PROVIDERS);
		}
		provider = translators.get(i);
		try {
			return provider.detectLangauge(text);
		} catch (QueryLimitException e) {
			unusableProviders.add(provider.getProviderName());
			return detectLanguage(text);
		}
	}
}
