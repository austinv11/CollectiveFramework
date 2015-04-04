package com.austinv11.collectiveframework.minecraft.utils;

import com.austinv11.collectiveframework.language.TranslationManager;
import com.austinv11.collectiveframework.language.translation.TranslationException;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Translation Manager for use in Minecraft
 */
public class MinecraftTranslator {
	
	private static Field fallback;
	
	/**
	 * Translates a given text (either from the unlocalized key or from standard text) (prefers Minecraft's translation)
	 * @param text String to translate
	 * @param toLang Language to translate to
	 * @return The translated text
	 * @throws TranslationException
	 * @throws IOException
	 */
	public static String translate(String text, String toLang) throws IOException, TranslationException {
		if (StatCollector.canTranslate(text)) {
			return StatCollector.translateToLocal(text);
		}
		String toTranslate = StatCollector.translateToFallback(text);
		return TranslationManager.translate(toTranslate, toLang);
	}
	
	/**
	 * Translates a given text (either from the unlocalized key or from standard text) (prefers Minecraft's translation)
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
		return TranslationManager.translate(toTranslate, fromLang, toLang);
	}
	
	/**
	 * Simplified method to translate a string to the local language for Minecraft
	 * @param text String to translate
	 * @param fromLang Language of the string to translate
	 * @return The translated string
	 * @throws com.austinv11.collectiveframework.language.translation.TranslationException
	 * @throws java.io.IOException
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
	 * Gets the usable language key for the local language from Minecraft
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
				if (!StatCollector.canTranslate(event.itemStack.getUnlocalizedName()) && getFallback().containsTranslateKey(event.itemStack.getUnlocalizedName()))
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
					String message = getFallback().containsTranslateKey(event.message.getUnformattedText()) ? StatCollector.translateToFallback(event.message.getUnformattedText()) :event.message.getUnformattedText();
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
}
