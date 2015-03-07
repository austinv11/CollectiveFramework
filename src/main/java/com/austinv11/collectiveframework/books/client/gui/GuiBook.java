package com.austinv11.collectiveframework.books.client.gui;

import com.austinv11.collectiveframework.books.Book;
import com.austinv11.collectiveframework.books.BookFactory;
import com.austinv11.collectiveframework.books.elements.BookEvent;
import com.austinv11.collectiveframework.books.elements.ElementType;
import com.austinv11.collectiveframework.books.elements.IElement;
import com.austinv11.collectiveframework.books.elements.Page;
import com.austinv11.collectiveframework.utils.ReflectionUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Object for the actual Gui of the book
 */
@SideOnly(Side.CLIENT)
public class GuiBook extends GuiScreen {
	
	public static HashMap<Book, Integer> CACHED_PAGES = new HashMap<Book,Integer>();
	
	private HashMap<Integer, List<IElement>> pageElements = new HashMap<Integer, List<IElement>>();
	private HashMap<Integer, Page> page = new HashMap<Integer, Page>();
	
	private HashMap<Integer, List<IElement>> buttonElements = new HashMap<Integer, List<IElement>>();
	private HashMap<Integer, List<IElement>> interactiveElements = new HashMap<Integer, List<IElement>>();
	private HashMap<Integer, List<IElement>> textFieldElements = new HashMap<Integer, List<IElement>>();
	
	private int currentPage;
	
	private Book book;
	
	/**
	 * Constructor for the gui, the player MUST be holding a Book
	 * @param player The player
	 */
	public GuiBook(EntityPlayer player) {
		super();
		Book book = (Book) player.getHeldItem().getItem();
		this.book = book;
		HashMap<Integer, List<IElement>> elements = BookFactory.bookRegistry.get(book);
		for (Integer i : elements.keySet()) {
			for (IElement e : elements.get(i)) {
				if (e instanceof Page)
					page.put(i, (Page) e);
				else { //Sorting elements
					if (!pageElements.containsKey(i))
						pageElements.put(i, new ArrayList<IElement>());
					List<IElement> otherElements = pageElements.get(i);
					otherElements.add(e);
					pageElements.put(i, otherElements);
					if (e.getClass().isAnnotationPresent(ElementType.Button.class)) {
						if (!buttonElements.containsKey(i))
							buttonElements.put(i, new ArrayList<IElement>());
						List<IElement> buttons = buttonElements.get(i);
						buttons.add(e);
						buttonElements.put(i, buttons);
					}
					if (e.getClass().isAnnotationPresent(ElementType.Interactive.class)) {
						if (!interactiveElements.containsKey(i))
							interactiveElements.put(i, new ArrayList<IElement>());
						List<IElement> widgets = interactiveElements.get(i);
						widgets.add(e);
						interactiveElements.put(i, widgets);
					}
					if (e.getClass().isAnnotationPresent(ElementType.TextField.class)) {
						if (!textFieldElements.containsKey(i))
							textFieldElements.put(i, new ArrayList<IElement>());
						List<IElement> textFields = textFieldElements.get(i);
						textFields.add(e);
						textFieldElements.put(i, textFields);
					}
				}
			}
		}
		if (CACHED_PAGES.containsKey(book))
			setCurrentPageNumber(CACHED_PAGES.get(book));
		else
			setCurrentPageNumber(0);
	}
	
	/**
	 * Sets the current page of the book
	 * @param currentPage The page to be set to
	 */
	public void setCurrentPageNumber(int currentPage) {
		this.currentPage = currentPage;
		CACHED_PAGES.put(book, currentPage);
		if (!page.containsKey(currentPage))
			page.put(currentPage, page.get(0));
		if (!pageElements.containsKey(currentPage))
			pageElements.put(currentPage, new ArrayList<IElement>());
		if (!buttonElements.containsKey(currentPage))
			buttonElements.put(currentPage, new ArrayList<IElement>());
		if (!interactiveElements.containsKey(currentPage))
			interactiveElements.put(currentPage, new ArrayList<IElement>());
		if (!textFieldElements.containsKey(currentPage))
			textFieldElements.put(currentPage, new ArrayList<IElement>());
	}
	
	/**
	 * Gets the current page of the book
	 * @return The current page
	 */
	public int getCurrentPageNumber() {
		return currentPage;
	}
	
	/**
	 * Gets the current page object of the book
	 * @return The page
	 */
	public Page getCurrentPage() {
		return page.get(getCurrentPageNumber());
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		getCurrentPage().draw(this);
		for (IElement e : pageElements.get(getCurrentPageNumber()))
			e.draw(this);
		super.drawScreen(mouseX, mouseY, renderPartialTicks);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		for (IElement button : buttonElements.get(getCurrentPageNumber()))
			callSubscribedMethods(button, new BookEvent.InitGuiEvent(this));
		for (IElement textField : textFieldElements.get(getCurrentPageNumber()))
			callSubscribedMethods(textField, new BookEvent.InitGuiEvent(this));
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
	
	@Override
	protected void mouseClicked(int x, int y, int mouseEvent) {
		super.mouseClicked(x, y, mouseEvent);
		for (IElement button : buttonElements.get(getCurrentPageNumber()))
			callSubscribedMethods(button, new BookEvent.MouseClickedEvent(this, x, y, mouseEvent));
		for (IElement textField : textFieldElements.get(getCurrentPageNumber()))
			callSubscribedMethods(textField, new BookEvent.MouseClickedEvent(this, x, y, mouseEvent));
		for (IElement interactive : interactiveElements.get(getCurrentPageNumber()))
			callSubscribedMethods(interactive, new BookEvent.MouseClickedEvent(this, x, y, mouseEvent));
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		for (IElement interactive : interactiveElements.get(getCurrentPageNumber()))
			callSubscribedMethods(interactive, new BookEvent.UpdateScreenEvent(this));
	}
	
	@Override
	protected void keyTyped(char eventChar, int eventKey) {
		super.keyTyped(eventChar, eventKey);
		for (IElement button : buttonElements.get(getCurrentPageNumber()))
			callSubscribedMethods(button, new BookEvent.KeyTypedEvent(this, eventChar,  eventKey));
		for (IElement textField : textFieldElements.get(getCurrentPageNumber()))
			callSubscribedMethods(textField, new BookEvent.KeyTypedEvent(this, eventChar,  eventKey));
		for (IElement interactive : interactiveElements.get(getCurrentPageNumber()))
			callSubscribedMethods(interactive, new BookEvent.KeyTypedEvent(this, eventChar,  eventKey));
	}
	
	/**
	 * Calls subscribed methods in the given element
	 * @param element The element to call methods from
	 * @param event The event
	 */
	public void callSubscribedMethods(IElement element, BookEvent event) {
		List<Method> methods = ReflectionUtils.getDeclaredMethodsWithAnnotation(BookEvent.SubscribeBookEvent.class, element.getClass());
		for (Method m : methods)
			if (ReflectionUtils.paramsMatch(m, event.getClass())) {
				m.setAccessible(true);
				try {
					m.invoke(element, event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}
}
