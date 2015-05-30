package com.austinv11.collectiveframework.minecraft.event.handler;

import com.austinv11.collectiveframework.minecraft.client.gui.KeyOverlay;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyHandler {
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (Config.keyOverlay) {
			if (Keyboard.getEventKeyState())
				try {
					KeyOverlay.keys.offer(new Object[]{Keyboard.getKeyName(Keyboard.getEventKey()).toUpperCase(), KeyOverlay.KEY_LIFE_LENGTH});
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
	
	@SubscribeEvent
	public void onMouseInput(InputEvent.MouseInputEvent event) {
		if (Config.keyOverlay) {
			if (Mouse.getEventButtonState())
				try {
					KeyOverlay.keys.offer(new Object[]{Mouse.getButtonName(Mouse.getEventButton()).toUpperCase(), KeyOverlay.KEY_LIFE_LENGTH});
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
}
