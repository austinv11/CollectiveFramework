package com.austinv11.collectiveframework.minecraft.utils.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(
				new SoundEvent(new ResourceLocation("ui.button.click")), 1));
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
		return new ScaledResolution(Minecraft.getMinecraft());
	}
}
