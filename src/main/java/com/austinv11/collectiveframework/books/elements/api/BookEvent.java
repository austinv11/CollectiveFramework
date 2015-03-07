package com.austinv11.collectiveframework.books.elements.api;

import com.austinv11.collectiveframework.books.client.gui.GuiBook;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A collection of objects which describe various actions which occur in the book gui
 */
@SideOnly(Side.CLIENT)
public class BookEvent {
	
	public GuiBook bookScreen;
	
	public BookEvent(GuiBook bookScreen) {
		this.bookScreen = bookScreen;
	}
	
	/**
	 * Does the same thing as {@link cpw.mods.fml.common.eventhandler.SubscribeEvent} except for these custom events
	 * You also do not need to register the class to any event bus
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(java.lang.annotation.ElementType.METHOD)
	public static @interface SubscribeBookEvent {}
	
	/**
	 * This event is called during the initGui phase of the book gui initialization
	 * Only called with Button and TextField element types
	 */
	public static class InitGuiEvent extends BookEvent {
		
		public InitGuiEvent(GuiBook bookScreen) {
			super(bookScreen);
		}
	}
	
	/**
	 * This event is called when a mouse button is clicked
	 * Called with all ElementTypes
	 */
	public static class MouseClickedEvent extends BookEvent {
		
		public int x, y, mouseEvent;
		
		public MouseClickedEvent(GuiBook bookScreen) {
			super(bookScreen);
		}
		
		public MouseClickedEvent(GuiBook bookScreen, int x, int y, int mouseEvent) {
			this(bookScreen);
			this.x = x;
			this.y = y;
			this.mouseEvent = mouseEvent;
		}
	}
	
	/**
	 * This is called when the screen is updated from the main game loop
	 * Only called with the interactive ElementType
	 */
	public static class UpdateScreenEvent extends BookEvent {
		
		public UpdateScreenEvent(GuiBook bookScreen) {
			super(bookScreen);
		}
	}
	
	/**
	 * This called when a key is pressed
	 * Called with all ElementTypes
	 */
	public static class KeyTypedEvent extends BookEvent {
		
		public char eventChar;
		public int eventKey;
		
		public KeyTypedEvent(GuiBook bookScreen) {
			super(bookScreen);
		}
		
		public KeyTypedEvent(GuiBook bookScreen, char eventChar, int eventKey) {
			this(bookScreen);
			this.eventChar = eventChar;
			this.eventKey = eventKey;
		}
	}
}
