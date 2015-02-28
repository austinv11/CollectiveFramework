package com.austinv11.collectiveframework.language;

import com.austinv11.collectiveframework.language.translation.ITranslationProvider;
import com.austinv11.collectiveframework.language.translation.QueryLimitException;
import com.austinv11.collectiveframework.language.translation.TranslationException;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A class for translating text
 */
public class TranslationManager {
	
	private static List<ITranslationProvider> translators = new ArrayList<ITranslationProvider>();
	
	//The cost of using free APIs
	private static List<String> unusableProviders = new ArrayList<String>();
	
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
	 * @throws QueryLimitException
	 */
	public static String translate(String text, String toLang) throws TranslationException, IOException, QueryLimitException {
		if (StatCollector.canTranslate(text)) {
			return StatCollector.translateToLocal(text);
		}
		String toTranslate = StatCollector.translateToFallback(text);
		ITranslationProvider provider;
		int i = 0;
		while (unusableProviders.contains(translators.get(i).getProviderName()) || !translators.get(i).canDetectLanguage()) {
			i++;
			if (!translators.contains(i))
				throw new TranslationException(TranslationException.ErrorTypes.NO_VALID_PROVIDERS);
		}
		provider = translators.get(i);
		return provider.translate(toTranslate, toLang);
	}
	
	/**
	 * Translates a given text (either from the unlocalized key or from standard text)
	 * @param text String to translate
	 * @param fromLang Language the string is from
	 * @param toLang Language to translate to
	 * @return The translated text
	 * @throws TranslationException
	 * @throws IOException
	 * @throws QueryLimitException
	 */
	public static String translate(String text, String fromLang, String toLang) throws TranslationException, IOException, QueryLimitException {
		if (StatCollector.canTranslate(text)) {
			return StatCollector.translateToLocal(text);
		}
		String toTranslate = StatCollector.translateToFallback(text);
		ITranslationProvider provider;
		int i = 0;
		while (unusableProviders.contains(translators.get(i).getProviderName())) {
			i++;
			if (!translators.contains(i))
				throw new TranslationException(TranslationException.ErrorTypes.NO_VALID_PROVIDERS);
		}
		provider = translators.get(i);
		return provider.translate(toTranslate, fromLang, toLang);
	}
	
	/**
	 * Simplified method to translate a string to the local language for Minecraft
	 * @param text String to translate
	 * @param fromLang Language of the string to translate
	 * @return The translated string
	 * @throws TranslationException
	 * @throws QueryLimitException
	 * @throws IOException
	 */
	public static String translateToLocal(String text, String fromLang) throws TranslationException, QueryLimitException, IOException {
		return translate(text, fromLang, langToUsable());
	}
	
	/**
	 * Simplified method to translate a string to the local language for Minecraft
	 * @param text String to translate
	 * @return The translated string
	 * @throws TranslationException
	 * @throws QueryLimitException
	 * @throws IOException
	 */
	public static String translateToLocal(String text) throws TranslationException, QueryLimitException, IOException {
		return translate(text, langToUsable());
	}
	
	/**
	 * Method which attempts to detect the language of the given string
	 * @param text String to detect the language for
	 * @return Language code
	 * @throws TranslationException
	 * @throws IOException
	 * @throws QueryLimitException
	 */
	public static String detectLanguage(String text) throws TranslationException, IOException, QueryLimitException {
		ITranslationProvider provider;
		int i = 0;
		while (unusableProviders.contains(translators.get(i).getProviderName())) {
			i++;
			if (!translators.contains(i))
				throw new TranslationException(TranslationException.ErrorTypes.NO_VALID_PROVIDERS);
		}
		provider = translators.get(i);
		return provider.detectLangauge(text);
	}
	
	/**
	 * Gets the usable language key for the local language
	 * @return The key
	 */
	public static String langToUsable() {
		return mcLangCodesToUsable(Minecraft.getMinecraft().gameSettings.language);
	}
	
	/**
	 * Gets the usable language key for the given Minecraft language code
	 * @param code The Minecraft language code
	 * @return The usable key
	 */
	public static String mcLangCodesToUsable(String code) {
		String[] langInfo = code.split("_");
		Locale loc = new Locale(langInfo[0], langInfo[1]);
		return loc.getLanguage();
	}
	
	public static String usableToMCLangCodes(String code) {
		Locale locale = Locale.forLanguageTag(code);
		return locale.getLanguage().toLowerCase()+"_"+locale.getCountry().toUpperCase();
	}
}
