package com.austinv11.collectiveframework.minecraft.client.gui;

import com.austinv11.collectiveframework.minecraft.reference.Config;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedDeque;

@SideOnly(Side.CLIENT)
public class KeyOverlay {
	
	public static final ConcurrentLinkedDeque<Object[]> keys = new ConcurrentLinkedDeque<Object[]>();
	public static final int KEY_LIFE_LENGTH = 90;
	
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		if (Config.keyOverlay) {
			if (/*event.isCanceled() || */event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR)
				return;
			int x = 1;
			ConcurrentLinkedDeque<Object[]> newKeys = new ConcurrentLinkedDeque<Object[]>();
			while (!keys.isEmpty()) {
				Object[] key = keys.pop();
				String s = (String)key[0];
				int ticksLived = (Integer)key[1];
				Color color = Color.WHITE;
				for (int i = 0; i < KEY_LIFE_LENGTH/(ticksLived*2); i++)
					color = color.darker();
				Minecraft.getMinecraft().fontRenderer.drawString(s, x, 1, color.getRGB());
				x += Minecraft.getMinecraft().fontRenderer.getStringWidth(s)+3;
				if (ticksLived > 1)
					newKeys.add(new Object[]{s, --ticksLived});
			}
			keys.addAll(newKeys);
		}
	}
}
