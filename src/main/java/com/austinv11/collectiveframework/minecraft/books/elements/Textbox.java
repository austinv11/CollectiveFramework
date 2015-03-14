package com.austinv11.collectiveframework.minecraft.books.elements;

import com.austinv11.collectiveframework.minecraft.books.client.gui.GuiBook;
import com.austinv11.collectiveframework.minecraft.books.elements.api.ElementType;
import com.austinv11.collectiveframework.minecraft.books.elements.api.IElement;

/**
 * An element for displaying strings, also allows for complex string management
 */
@ElementType.Interactive
public class Textbox implements IElement {
	
	private Object[] listeners;
	
	
	
	@Override
	public void draw(GuiBook bookScreen) {
		
	}
	
	public static enum Alignmet {
		LEFT, CENTER, RIGHT, JUSTIFY
	}
}
