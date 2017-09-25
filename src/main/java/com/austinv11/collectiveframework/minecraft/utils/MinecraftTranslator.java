package com.austinv11.collectiveframework.minecraft.utils;

import com.austinv11.collectiveframework.language.TranslationManager;
import com.austinv11.collectiveframework.language.translation.TranslationException;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

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
		if (I18n.canTranslate(text)) {
			return I18n.translateToLocal(text);
		}
		String toTranslate = I18n.translateToFallback(text);
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
		if (I18n.canTranslate(text)) {
			return I18n.translateToLocal(text);
		}
		String toTranslate = I18n.translateToFallback(text);
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
				if (!I18n.canTranslate(event.getItemStack().getUnlocalizedName()) && getFallback().isKeyTranslated(event.getItemStack().getUnlocalizedName()))
					if (I18n.translateToFallback(event.getItemStack().getUnlocalizedName()).equals(event.getItemStack().getDisplayName())) {
						String toTranslate = event.getItemStack().getDisplayName();
						event.getItemStack().setStackDisplayName(translateToLocal(toTranslate, "en"));
					}
			} catch (Exception ignore) {}
		
	}
	
	@SubscribeEvent
	public void onChatEvent(ClientChatReceivedEvent event) {
		if (Config.translateChat)
			if (!event.isCanceled())
				try {
					String message = getFallback().isKeyTranslated(event.getMessage().getUnformattedText()) ?
							I18n.translateToFallback(event.getMessage().getUnformattedText()) :
							event.getMessage().getUnformattedText();
					event.setMessage(new TextComponentString(translateToLocal(message, "en")));
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
	private static LanguageMap getFallback() throws IllegalAccessException, NoSuchFieldException {
		if (fallback == null)
			fallback = ReflectionHelper.getPrivateValue(I18n.class, null, "fallbackTranslator",
					"field_150828_b");
		return (LanguageMap) fallback.get(null);
	}
}
