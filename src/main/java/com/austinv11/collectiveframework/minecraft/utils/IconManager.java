package com.austinv11.collectiveframework.minecraft.utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to manage additional icon registrations, just implement {@link com.austinv11.collectiveframework.minecraft.utils.IconManager.IIconNeeded}
 */
@SideOnly(Side.CLIENT)
public class IconManager {

	private static List<IIconNeeded> texturesNeeded = new ArrayList<IIconNeeded>();

	@SubscribeEvent
	public void onPreTextureStitch(TextureStitchEvent.Pre event) {
		registerTextures(event.map);
	}
	
	/**
	 * Registers an object which requires icons to be registered
	 * @param iconNeeded The object
	 */
	public static void register(IIconNeeded iconNeeded) {
		texturesNeeded.add(iconNeeded);
	}

	private void registerTextures(TextureMap map) {
		for (IIconNeeded textureNeeded : texturesNeeded) {
			textureNeeded.registerIcons(map);
		}
	}
	
	/**
	 * Implement this to be able to register additional icons
	 */
	public static interface IIconNeeded {
		
		/**
		 * Called to register icons
		 * @param register The icon register to register icons with
		 */
		public void registerIcons(IIconRegister register);
	}
}
