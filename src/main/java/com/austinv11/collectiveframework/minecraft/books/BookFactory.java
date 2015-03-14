package com.austinv11.collectiveframework.minecraft.books;

import com.austinv11.collectiveframework.minecraft.books.elements.api.IElement;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Factory for instantiating a Book {@link com.austinv11.collectiveframework.minecraft.books.Book}
 * Remember, you still need to register it with the {@link cpw.mods.fml.common.registry.GameRegistry}
 */
public class BookFactory {
	
	public static HashMap<Book, HashMap<Integer, List<IElement>>> bookRegistry = new HashMap<Book, HashMap<Integer, List<IElement>>>();
	
	private static int idTicker = 0;
	
	private CreativeTabs tab;
	private ResourceLocation icon;
	private String name;
	private HashMap<Integer, List<IElement>> elements;
	
	/**
	 * Default constructor
	 */
	public BookFactory() {
		elements = new HashMap<Integer, List<IElement>>();
	}
	
	private BookFactory(CreativeTabs tab, ResourceLocation icon, String name, HashMap<Integer, List<IElement>> elements) {
		this.tab = tab;
		this.icon = icon;
		this.name = name;
		this.elements = elements;
	}
	
	/**
	 * Sets the creative tab for the book
	 * @param tab The tab for the book item
	 * @return The modified Book Factory
	 */
	public BookFactory setCreativeTab(CreativeTabs tab) {
		return new BookFactory(tab, this.icon, this.name, this.elements);
	}
	
	/**
	 * Sets the icon for the book
	 * @param icon The icon for the book item
	 * @return The modified Book Factory
	 */
	public BookFactory setIcon(ResourceLocation icon) {
		return new BookFactory(this.tab, icon, this.name, this.elements);
	}
	
	/**
	 * Sets the name for the book (becomes unlocalized as item.book.<name>)
	 * @param name The name for the book
	 * @return The modified Book Factory
	 */
	public BookFactory setName(String name) {
		return new BookFactory(this.tab, this.icon, name, this.elements);
	}
	
	/**
	 * Adds an element to the given page
	 * @param page The page to add to
	 * @param element The element to add
	 * @return The modified Book Factory
	 */
	public BookFactory addElement(int page, IElement element) {
		HashMap<Integer, List<IElement>> elements = (HashMap<Integer,List<IElement>>) this.elements.clone();
		if (!elements.containsKey(page))
			elements.put(page, new ArrayList<IElement>());
		List<IElement> list = elements.get(page);
		list.add(element);
		elements.put(page, list);
		return new BookFactory(this.tab, this.icon, this.name, elements);
	}
	
	/**
	 * Adds multiple elements to the given page
	 * @param page The page to add to
	 * @param elements The elements to add
	 * @return The modified Book Factory
	 */
	public BookFactory addElements(int page, List<IElement> elements) {
		HashMap<Integer, List<IElement>> elements_ = (HashMap<Integer,List<IElement>>) this.elements.clone();
		if (!elements_.containsKey(page))
			elements_.put(page, new ArrayList<IElement>());
		List<IElement> list = elements_.get(page);
		list.addAll(elements);
		elements_.put(page, list);
		return new BookFactory(this.tab, this.icon, this.name, elements_);
	}
	
	/**
	 * Creates an actual book object
	 * @return The book
	 */
	public Book build() {
		Book book = new Book();
		book.id = idTicker++;
		book.tab = tab;
		book.icon = icon;
		book.name = name;
		bookRegistry.put(book, elements);
		return book;
	}
}
