package com.austinv11.collectiveframework.minecraft.books.api;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This is the base class for any rendered pages in a book
 */
public abstract class Page {
	
	/**
	 * Used to get the background image
	 * @return The resource location of the background, or null if there aren't any
	 */
	public abstract ResourceLocation getBackground();
	
	/**
	 * Used to retrieve all the entries on the page
	 * @return The entries
	 */
	public abstract Entry[] getEntries();
	
	/**
	 * Called when the page is rendered
	 * @param dt The time since the last render of the current pass in milliseconds
	 */
	@SideOnly(Side.CLIENT)
	public abstract void onRender(int dt);
}
