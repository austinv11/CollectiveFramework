package com.austinv11.collectiveframework.minecraft.books.simple;

import com.austinv11.collectiveframework.minecraft.books.api.Entry;
import com.austinv11.collectiveframework.utils.math.TwoDimensionalVector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an easy-to-use entry implementation for text
 */
public class TextEntry extends Entry {
	
	/**
	 * Using unicode font makes everything a little less minecrafty
	 */
	public boolean useUnicodeFont = true;
	/**
	 * Adds a shadow to the text, makes it more visible in most cases
	 */
	public boolean useFontShadow = true;
	/**
	 * The text alignment
	 */
	public Alignment alignment = Alignment.LEFT;
	private String toRender;
	@SideOnly(Side.CLIENT)
	private FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
	
	public TextEntry(TwoDimensionalVector coords, int width, int height, String toRender) {
		this(coords, width, height);
		this.toRender = toRender;
	}
	
	private TextEntry(TwoDimensionalVector coords, int width, int height) {
		super(coords, width, height);
	}
	
	@SideOnly(Side.CLIENT)
	public List<String> fitString() {
		List<String> strings = new ArrayList<String>();
		String[] lines = toRender.split("\n");
		for (String line : lines) {
			String currentString = "";
			String[] split = line.split(" ");
			for (String s : split) {
				if (renderer.getStringWidth(currentString+" "+s) > width) {
					strings.add(currentString);
					currentString = s;
				} else
					currentString = currentString+" "+s;
			}
			strings.add(currentString);
		}
		return strings;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onRender(int dt) {
		List<String> lines = fitString();
		boolean unicode = renderer.getUnicodeFlag();
		renderer.setUnicodeFlag(useUnicodeFont);
		int currentY = getCoords().getRoundedY();
		if (alignment == Alignment.LEFT) {
			for (String s : lines) {
				renderer.drawString(s, getCoords().getRoundedX(), currentY, Color.WHITE.getRGB(), useFontShadow);
				currentY += renderer.FONT_HEIGHT;
				if (currentY >= getCoords().getRoundedY()+height)
					break;
			}
		} else if (alignment == Alignment.CENTER) {
			for (String s : lines) {
				renderer.drawString(s, getCoords().getRoundedX() + (width - renderer.getStringWidth(s)) / 2, currentY, 
						Color.WHITE.getRGB(), useFontShadow);
				currentY += renderer.FONT_HEIGHT;
				if (currentY >= getCoords().getRoundedY()+height)
					break;
			}
		} else if (alignment == Alignment.RIGHT) {
			for (String s : lines) {
				renderer.drawString(s, getCoords().getRoundedX()+(width-renderer.getStringWidth(s)), currentY, 
						Color.WHITE.getRGB(), useFontShadow);
				currentY += renderer.FONT_HEIGHT;
				if (currentY >= getCoords().getRoundedY()+height)
					break;
			}
		} else {
			for (String s : lines) {
				int spacesToAdd = (width-renderer.getStringWidth(s))/renderer.getCharWidth(' ');
				String[] words = s.split(" ");
				spacesToAdd += words.length;
				String justified = "";
				inner: while (spacesToAdd > 0) {
					for (int i = 0; i < words.length; i++) {
						if (spacesToAdd == 0)
							break inner;
						words[i] = words[i]+" ";
						spacesToAdd--;
					}
				}
				for (int i = 0; i < words.length; i++)
					justified = justified+words[i];
				renderer.drawString(justified, getCoords().getRoundedX(), currentY, Color.WHITE.getRGB(), useFontShadow);
				currentY += renderer.FONT_HEIGHT;
				if (currentY >= getCoords().getRoundedY()+height)
					break;
			}
		}
		renderer.setUnicodeFlag(unicode);
	}
	
	/**
	 * This represents the possible text alignments
	 */
	public static enum Alignment {
		LEFT, CENTER, RIGHT, JUSTIFY
	}
}
