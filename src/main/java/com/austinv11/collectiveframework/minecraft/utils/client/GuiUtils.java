package com.austinv11.collectiveframework.minecraft.utils.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

/**
 * A class for holding helper methods for guis
 */
@SideOnly(Side.CLIENT)
public class GuiUtils {
	
	/**
	 * Plays the button click sound
	 */
	public static void playButtonSound() {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	}
}
