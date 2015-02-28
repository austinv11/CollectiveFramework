package com.austinv11.collectiveframework.language.translation;

import java.io.IOException;

/**
 * Class for a translation provider
 */
public interface ITranslationProvider {
	
	/**
	 * Method for receiving the name of the translation provider
	 * @return The name, like "Google Translate"
	 */
	public String getProviderName();
	
	/**
	 * Method to actually translate
	 * @param text Text to translate
	 * @param fromLang Language to translate from
	 * @param toLang The language to translate to
	 * @return The translated string
	 */
	public String translate(String text, String fromLang, String toLang) throws TranslationException, QueryLimitException, IOException;
	
	/**
	 * Method to actually translate, only used if canDetectLanguage() returns true
	 * @param text Text to translate
	 * @param toLang The language to translate to
	 * @return The translated string
	 */
	public String translate(String text, String toLang) throws TranslationException, QueryLimitException, IOException;
	
	/**
	 * Used by the TranslationManager internally
	 * @return Whether this translation provider can detect a language from text
	 */
	public boolean canDetectLanguage();
	
	/**
	 * Method to detect the language from a string, only used if canDetectLanguage() returns true
	 * @param text String to translate
	 * @return The language
	 */
	public String detectLangauge(String text) throws IOException, QueryLimitException;
}
