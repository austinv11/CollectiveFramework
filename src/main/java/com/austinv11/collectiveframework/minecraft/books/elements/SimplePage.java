package com.austinv11.collectiveframework.minecraft.books.elements;

import com.austinv11.collectiveframework.minecraft.books.elements.api.Page;
import net.minecraft.util.ResourceLocation;

/**
 * Simple page implementation, use this as an example
 */
public class SimplePage extends Page {
	
	@Override
	public int getWidth() {
		return 190;
	}
	
	@Override
	public int getHeight() {
		return 190;
	}
	
	@Override
	public ResourceLocation getBackgroundImage() {
		return new ResourceLocation("minecraft:textures/gui/book.png");
	}
}
