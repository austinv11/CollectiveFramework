package com.austinv11.collectiveframework.minecraft.books.api;

import com.austinv11.collectiveframework.utils.math.TwoDimensionalVector;

/**
 * An Entry class which can be interacted with
 */
public abstract class InteractiveEntry extends Entry {
	
	/**
	 * Default constructor, feel free to use a different one
	 * @param coords The coords of the entry
	 * @param width The width of the entry
	 * @param height The height of the entry
	 */
	public InteractiveEntry(TwoDimensionalVector coords, int width, int height) {
		super(coords, width, height);
	}
	
	/**
	 * Called when a key is pressed/released in the gui
	 * @param eventKey The key pressed/released
	 * @param isDown True if the key is pressed, false if otherwise
	 */
	public abstract void onKeyboardEvent(char eventKey, boolean isDown);
	
	/**
	 * Called when the mouse is interacted with
	 * @param x The mouse x
	 * @param y The mouse y
	 * @param dx The mouse's change in x
	 * @param dy The mouse's change in y
	 * @param dWheel The mouse's change in the mouse wheel
	 * @param button The button pressed/released, -1 if no button touched, 0 for left, 1 for right
	 * @param buttonState True if the button was pressed, false if otherwise
	 */
	public abstract void onMouseEvent(int x, int y, int dx, int dy, int dWheel, int button, boolean buttonState);
}
