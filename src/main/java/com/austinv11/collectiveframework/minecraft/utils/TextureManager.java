package com.austinv11.collectiveframework.minecraft.utils;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to manage additional texture/sprite registrations, just implement {@link TextureRegistrar}
 */
@SideOnly(Side.CLIENT)
public class TextureManager {

	private static List<TextureRegistrar> textureRegistrars = new ArrayList<>();

	@SubscribeEvent
	public void onPreTextureStitch(TextureStitchEvent.Pre event) {
		registerTextures(event.getMap());
	}
	
	/**
	 * Registers an object which requires textures/sprites to be registered
	 * @param textureRegistrar The object
	 */
	public static void register(TextureRegistrar textureRegistrar) {
		textureRegistrars.add(textureRegistrar);
	}

	private void registerTextures(TextureMap map) {
		for (TextureRegistrar textureNeeded : textureRegistrars)
			textureNeeded.registerTextures(map);
	}

	/**
	 * Implement this to be able to register additional textures/sprites
	 */
	public interface TextureRegistrar {

		/**
		 * Called to register icons
		 * @param textureMap The texture map register to register textures/sprites with
		 */
		void registerTextures(TextureMap textureMap);
	}
}
