package com.austinv11.collectiveframework.minecraft.books.elements;

import com.austinv11.collectiveframework.minecraft.books.client.gui.GuiBook;
import com.austinv11.collectiveframework.minecraft.books.elements.api.IElement;
import com.austinv11.collectiveframework.utils.math.TwoDimensionalVector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

/**
 * Simple element for rendering plain strings
 */
public class Label implements IElement {
	
	private String string;
	private TwoDimensionalVector coords;
	private Color color;
	private FontRenderer renderer;
	
	/**
	 * Constructor for a Label
	 * @param string String to draw
	 * @param coords Coordinates to draw onto
	 */
	public Label(String string, TwoDimensionalVector coords) {
		this(string, coords, Color.WHITE);
	}
	
	/**
	 * Constructor for a Label
	 * @param string String to draw
	 * @param coords Coordinates to draw onto
	 * @param color Color for the string
	 */
	public Label(String string, TwoDimensionalVector coords, Color color) {
		this(string, coords, color, Minecraft.getMinecraft().fontRenderer);
	}
	
	/**
	 * Constructor for a Label
	 * @param string String to draw
	 * @param coords Coordinates to draw onto
	 * @param color Color for the string
	 * @param renderer The renderer to draw with
	 */
	public Label(String string, TwoDimensionalVector coords, Color color, FontRenderer renderer) {
		this.string = string;
		this.coords = coords;
		this.color = color;
		this.renderer = renderer;
	}
	
	@Override
	public void draw(GuiBook bookScreen) {
		bookScreen.drawString(renderer, string, coords.getRoundedX(), coords.getRoundedY(), color.getRGB());
	}
}
