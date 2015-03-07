package com.austinv11.collectiveframework.books.elements;

import com.austinv11.collectiveframework.books.client.gui.GuiBook;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Interface for an element to be drawn to a page
 */
public interface IElement {
	
	/**
	 * Draws the element to the screen
	 * @param bookScreen The GuiScreen for the book
	 */
	@SideOnly(Side.CLIENT)
	public void draw(GuiBook bookScreen);
}
