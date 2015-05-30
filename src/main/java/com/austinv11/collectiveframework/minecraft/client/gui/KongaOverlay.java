package com.austinv11.collectiveframework.minecraft.client.gui;

import com.austinv11.collectiveframework.minecraft.event.handler.HooksHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class KongaOverlay extends Gui {
	
	private final int ALPHA = 100;
	private float hue = 0;
	
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		if (HooksHandler.kongaTime) {
			if (/*event.isCanceled() || */event.type != RenderGameOverlayEvent.ElementType.HOTBAR)
				return;
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			drawRect(0, 0, event.resolution.getScaledWidth(), event.resolution.getScaledHeight(), getColor());
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}
	
	@SubscribeEvent
	public void renderGui(GuiScreenEvent.DrawScreenEvent.Post event) {
		if (HooksHandler.kongaTime) {
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			drawRect(0, 0, event.gui.width,  event.gui.height, getColor());
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}
	
	private int getColor() {
		Color color = Color.getHSBColor(hue, 1, 1);
		hue += 0.01F;
		if (hue > 1.0F)
			hue = 0;
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), ALPHA).getRGB();
	}
}
