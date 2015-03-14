package com.austinv11.collectiveframework.minecraft.books.elements.api;

import com.austinv11.collectiveframework.minecraft.books.client.gui.GuiBook;
import net.minecraft.util.ResourceLocation;

/**
 * Interface for a page
 */
public abstract class Page implements IElement {
	
	@Override
	public void draw(GuiBook bookScreen) {
		bookScreen.mc.getTextureManager().bindTexture(getBackgroundImage());
		int x = (bookScreen.width - getWidth()) / 2;
		int y = (bookScreen.height - getHeight()) / 2;
		bookScreen.drawTexturedModalRect(x, y, 0, 0, getWidth(), getHeight());
	}
	
	/**
	 * Gets the width of the page
	 * @return The width
	 */
	public abstract int getWidth();
	
	/**
	 * Gets the height of the page
	 * @return The height
	 */
	public abstract int getHeight();
	
	/**
	 * Gets the image for the page
	 * @return The location of the image
	 */
	public abstract ResourceLocation getBackgroundImage();
}
