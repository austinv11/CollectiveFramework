package com.austinv11.collectiveframework.minecraft.utils.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;

/**
 * A class for holding helper methods for guis
 */
@SideOnly(Side.CLIENT)
public class GuiUtils {
	
	/**
	 * Plays the button click sound
	 */
	public static void playButtonSound() {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(
				new ResourceLocation("gui.button.press"), 1.0F));
	}
	
	/**
	 * Closes the currently opened gui for the player
	 */
	public static void closeCurrentGui() {
		if (Minecraft.getMinecraft().currentScreen != null) {
			Minecraft.getMinecraft().currentScreen.onGuiClosed();
			Minecraft.getMinecraft().currentScreen = null;
		}
	}
	
	/**
	 * Closes the currently opened gui for the player if it matches the filter
	 * @param filter The gui filter
	 */
	public static void closeCurrentGui(Class<? extends GuiScreen> filter) {
		if (Minecraft.getMinecraft().currentScreen != null && 
				filter.isAssignableFrom(Minecraft.getMinecraft().currentScreen.getClass())) {
			Minecraft.getMinecraft().currentScreen.onGuiClosed();
			Minecraft.getMinecraft().currentScreen = null;
		}
	}
	
	/**
	 * Retrieves an input stream for a resource location
	 * @param resourceLocation The resource location
	 * @return The input stream
	 * @throws IOException
	 */
	public static InputStream getResourceAsStream(ResourceLocation resourceLocation) throws IOException {
		return Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream();
	}
	
	/**
	 * Generates a scaled resolution object
	 * @return The resolution object
	 */
	public static ScaledResolution getCurrentResolution() {
		return new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, 
				Minecraft.getMinecraft().displayHeight);
	}
}
