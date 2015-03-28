package com.austinv11.collectiveframework.minecraft.books.elements;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.minecraft.books.client.gui.GuiBook;
import com.austinv11.collectiveframework.minecraft.books.elements.api.BookEvent;
import com.austinv11.collectiveframework.minecraft.books.elements.api.ElementType;
import com.austinv11.collectiveframework.minecraft.books.elements.api.IElement;
import com.austinv11.collectiveframework.utils.math.TwoDimensionalVector;
import com.austinv11.collectiveframework.utils.math.geometry.IncompatibleDimensionsException;
import com.austinv11.collectiveframework.utils.math.geometry.Variable2DShape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An element for displaying strings, also allows for complex string management
 */
@ElementType.Interactive
public abstract class Textbox implements IElement {
	
	private TwoDimensionalVector startCoords, endCoords;
	private Alignment alignment;
	private Variable2DShape box;
	
	private FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
	private List<Word> wordList = new ArrayList<Word>();
	private String text;
	
	private static int NEW_LINE_INCREMENT = 4;
	
	/**
	 * Constructor for a textbox
	 * @param startCoords The coords for the top left corner of the box
	 * @param endCoords The coords for the bottom right corner of the box
	 * @param alignment The alignment for the text
	 */
	public Textbox(TwoDimensionalVector startCoords, TwoDimensionalVector endCoords, Alignment alignment) {
		this.startCoords = startCoords;
		this.endCoords = endCoords;
		this.alignment = alignment;
		try {
			this.box = new Variable2DShape(startCoords, new TwoDimensionalVector(endCoords.x, startCoords.y), endCoords, new TwoDimensionalVector(startCoords.x, endCoords.y));
		} catch (IncompatibleDimensionsException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor for a textbox with a left alignment
	 * @param startCoords The coords for the top left corner of the box
	 * @param endCoords The coords for the bottom right corner of the box
	 */
	public Textbox(TwoDimensionalVector startCoords, TwoDimensionalVector endCoords) {
		this(startCoords, endCoords, Alignment.LEFT);
	}
	
	/**
	 * Gets the text for the textbox
	 * @return The text stored
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Sets the text content of the textbox
	 * @param text The text
	 */
	public void setText(String text) {
		this.text = text;
		wordList.clear();
		int x = startCoords.getRoundedX();
		int y = startCoords.getRoundedY();
		int maxX = endCoords.getRoundedX();
		int maxY = endCoords.getRoundedY();
		int maxWidth = x - maxX;
		
		if (alignment == Alignment.LEFT || alignment == Alignment.RIGHT) {
			List<String> strings = renderer.listFormattedStringToWidth(text, maxWidth);
			int currentY = y;
			int i = 0;
			while (i < strings.size() && currentY <= maxY) {
				Word w = new Word();
				w.string = strings.get(i);
				w.coords = new TwoDimensionalVector(Double.NaN, currentY);
				wordList.add(w);
				currentY += NEW_LINE_INCREMENT;
			}
		} else {
			int widthLeft = maxWidth;
			String[] words = text.split(" ");
			for (String word : words) {
				Word w = new Word();
				w.string = word;
				switch (alignment) {
					case CENTER:
						
						
						break;
					case JUSTIFY:
						
						
						break;
				}
			}
		}
	}
	
	/**
	 * Sets the font renderer for the textbox
	 * @param renderer The renderer
	 */
	public void setFontRenderer(FontRenderer renderer) {
		this.renderer = renderer;
	}
	
	@BookEvent.SubscribeBookEvent
	public final void onMouseClick(BookEvent.MouseClickedEvent event) {
		String word;
//		onWordClick(event.bookScreen, word, event.x, event.y, event.mouseEvent);
	}
	
	@BookEvent.SubscribeBookEvent
	public final void onUpdate(BookEvent.UpdateScreenEvent event) {
		onUpdate(event.bookScreen);
		CollectiveFramework.LOGGER.info("Go");
	}
	
	@BookEvent.SubscribeBookEvent
	public final void onKeyTyped(BookEvent.KeyTypedEvent event) {
		onKeyTyped(event.bookScreen, event.eventChar, event.eventKey);
	}
	
	/**
	 * Called when a word is clicked
	 * @param bookScreen The book gui screen this is called from
	 * @param word The word clicked
	 * @param x The x coordinate of the word
	 * @param y The y coordinate of the word
	 * @param mouseButton The mouse button pressed
	 */
	public abstract void onWordClick(GuiBook bookScreen, String word, int x, int y, int mouseButton);
	
	/**
	 * Called to update the textbox
	 * @param bookScreen The book gui screen this is called from
	 */
	public abstract void onUpdate(GuiBook bookScreen);
	
	/**
	 * Called when a key is typed in a book
	 * @param bookScreen The book gui screen this is called from
	 * @param keyChar The character pressed
	 * @param keyNum The key pressed
	 */
	public abstract void onKeyTyped(GuiBook bookScreen, char keyChar, int keyNum);
	
	@Override
	public void draw(GuiBook bookScreen) {
		if (alignment == Alignment.RIGHT)
			renderer.setBidiFlag(true);
		for (Word w : wordList)
			renderer.renderStringAligned(w.string, w.coords.getRoundedX(), w.coords.getRoundedY(), 0, w.color.getRGB(), w.doShadow);
		if (renderer.getBidiFlag())
			renderer.setBidiFlag(false);
	}
	
	/**
	 * Enum for text alignment
	 */
	public static enum Alignment {
		LEFT, CENTER, RIGHT, JUSTIFY
	}
	
	/**
	 * Class for holding words to aid in alignment computations
	 */
	public static class Word {
		public String string;
		public TwoDimensionalVector coords;
		public Color color = Color.WHITE;
		public boolean doShadow = false;
	}
}
