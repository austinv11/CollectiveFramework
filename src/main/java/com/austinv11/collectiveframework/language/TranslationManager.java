package com.austinv11.collectiveframework.language;

import com.austinv11.collectiveframework.language.translation.ITranslationProvider;
import com.austinv11.collectiveframework.language.translation.QueryLimitException;
import com.austinv11.collectiveframework.language.translation.TranslationException;
import com.austinv11.collectiveframework.reference.Config;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A class for translating text
 */
public class TranslationManager {
	
	private static final List<ITranslationProvider> translators = new ArrayList<ITranslationProvider>();
	
	//The cost of using free APIs
	private static final List<String> unusableProviders = new ArrayList<String>();
	
	//Helps reduce queries
	private static final HashMap<String, String> translationCache = new HashMap<String,String>();
	
	private static Field fallback;
	
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
		if (StatCollector.canTranslate(text)) {
			return StatCollector.translateToLocal(text);
		}
		String toTranslate = StatCollector.translateToFallback(text);
		if (translationCache.containsKey(toTranslate))
			return translationCache.get(toTranslate);
		ITranslationProvider provider;
		int i = 0;
		while (unusableProviders.contains(translators.get(i).getProviderName()) || !translators.get(i).canDetectLanguage()) {
			i++;
			if (!translators.contains(i))
				throw new TranslationException(TranslationException.ErrorTypes.NO_VALID_PROVIDERS);
		}
		provider = translators.get(i);
		try {
			String translation = provider.translate(toTranslate, toLang);
			translationCache.put(toTranslate, translation);
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
		if (StatCollector.canTranslate(text)) {
			return StatCollector.translateToLocal(text);
		}
		String toTranslate = StatCollector.translateToFallback(text);
		if (translationCache.containsKey(toTranslate))
			return translationCache.get(toTranslate);
		ITranslationProvider provider;
		int i = 0;
		while (unusableProviders.contains(translators.get(i).getProviderName())) {
			i++;
			if (!translators.contains(i))
				throw new TranslationException(TranslationException.ErrorTypes.NO_VALID_PROVIDERS);
		}
		provider = translators.get(i);
		try {
			String translation = provider.translate(toTranslate, fromLang, toLang);
			translationCache.put(toTranslate, translation);
			return translation;
		} catch (QueryLimitException e) {
			unusableProviders.add(provider.getProviderName());
			return translate(text, fromLang, toLang);
		}
	}
	
	/**
	 * Simplified method to translate a string to the local language for Minecraft
	 * @param text String to translate
	 * @param fromLang Language of the string to translate
	 * @return The translated string
	 * @throws TranslationException
	 * @throws IOException
	 */
	public static String translateToLocal(String text, String fromLang) throws TranslationException, IOException {
		return translate(text, fromLang, langToUsable());
	}
	
	/**
	 * Simplified method to translate a string to the local language for Minecraft
	 * @param text String to translate
	 * @return The translated string
	 * @throws TranslationException
	 * @throws IOException
	 */
	public static String translateToLocal(String text) throws TranslationException, IOException {
		return translate(text, langToUsable());
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
	
	@SubscribeEvent
	public void onTooltipEvent(ItemTooltipEvent event) {
		if (Config.translateItems)
			try {
				if (!StatCollector.canTranslate(event.itemStack.getUnlocalizedName()) && getFallback().isKeyTranslated(event.itemStack.getUnlocalizedName()))
					if (StatCollector.translateToFallback(event.itemStack.getUnlocalizedName()).equals(event.itemStack.getDisplayName())) {
						String toTranslate = event.itemStack.getDisplayName();
						event.itemStack.setStackDisplayName(translateToLocal(toTranslate, "en"));
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	@SubscribeEvent
	public void onChatEvent(ClientChatReceivedEvent event) {
		if (Config.translateChat)
			if (!event.isCanceled())
				try {
					String message = getFallback().isKeyTranslated(event.message.getUnformattedText()) ? StatCollector.translateToFallback(event.message.getUnformattedText()) :event.message.getUnformattedText();
					event.message = new ChatComponentText(translateToLocal(message, "en"));
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
	private static StringTranslate getFallback() throws IllegalAccessException, NoSuchFieldException {
		if (fallback == null) {
			fallback = StatCollector.class.getDeclaredField("fallbackTranslator");
			fallback.setAccessible(true);
		}
		return (StringTranslate) fallback.get(null);
	}
	
//	public static String usableToMCLangCodes(String code) {
//		Locale locale = Locale.forLanguageTag(code);
//		return locale.getLanguage().toLowerCase()+"_"+locale.getCountry().toUpperCase();
//	}
}
