package com.austinv11.collectiveframework.minecraft.init;

import com.austinv11.collectiveframework.minecraft.reference.Config;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * This is an internal class only, holds this mod's keybindings
 */
@SideOnly(Side.CLIENT)
public class Keybindings {
	
	public static KeyBinding TIME_BACK;
	public static KeyBinding TIME_FORWARD;
	
	public static void init() {
		if (Config.enableButtonTimeChanging) {
			TIME_BACK = new KeyBinding("collectiveframework.keybindings.timeBack", Keyboard.KEY_F8, "collectiveframework.keybindings.category");
			TIME_FORWARD = new KeyBinding("collectiveframework.keybindings.timeForward", Keyboard.KEY_F9, "collectiveframework.keybindings.category");
			
			ClientRegistry.registerKeyBinding(TIME_BACK);
			ClientRegistry.registerKeyBinding(TIME_FORWARD);
		}
	}
}
