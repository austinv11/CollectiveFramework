package com.austinv11.collectiveframework.minecraft.books.api;

import com.austinv11.collectiveframework.minecraft.books.core.GuiBook;
import com.austinv11.collectiveframework.minecraft.books.core.ItemBook;
import com.austinv11.collectiveframework.minecraft.books.core.LocalBookData;
import com.austinv11.collectiveframework.minecraft.utils.NBTHelper;
import com.austinv11.collectiveframework.minecraft.utils.client.GuiUtils;
import com.austinv11.collectiveframework.multithreading.SimpleRunnable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.io.IOException;

/**
 * This class is holds all required information to render and handle books
 */
public abstract class Book {
	
	private static final String GLOBAL_DIR = "./CFBookData/";
	private static volatile NBTTagCompound globalData; //A buffer for file I/O
	private int currentPage = 0;
	protected ItemBook bookItem;
	protected ItemStack bookStack;
	public EntityPlayer player;
	
	/**
	 * The gui rendering the book
	 */
	@SideOnly(Side.CLIENT)
	private GuiBook bookGui;
	
	/**
	 * Default Book constructor, this MUST be public
	 * @param bookItem The book item
	 * @param bookStack The book stack
	 */
	public Book(ItemBook bookItem, ItemStack bookStack) {
		this.bookItem = bookItem;
		this.bookStack = bookStack;
	}
	
	/**
	 * Used to determine how data will be stored in the book
	 * @return The storage type
	 */
	public abstract DataStorageType getDataStorageType();
	
	/**
	 * Gets the book's data
	 * @param stack The book stack (only necessary when {@link #getDataStorageType()} returns {@link DataStorageType#LOCAL}
	 * @return The data
	 */
	public NBTTagCompound getData(ItemStack stack) {
		if (getDataStorageType() == DataStorageType.GLOBAL) {
			if (globalData == null) {
				File storage = new File(GLOBAL_DIR+bookItem.getModId()+"/"+bookItem.getUnlocalizedName()+".dat");
				if (!storage.exists())
					return new NBTTagCompound();
				try {
					globalData = CompressedStreamTools.read(storage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return (NBTTagCompound) globalData.copy();
		} else if (getDataStorageType() == DataStorageType.LOCAL) {
			String key = bookItem.getModId()+":"+bookItem.getUnlocalizedName();
			NBTBase retrieved = LocalBookData.retrieveLocalData(key);
			if (retrieved == null)
				return new NBTTagCompound();
			return (NBTTagCompound) retrieved.copy();
		} else {
			return NBTHelper.getCompoundTag(stack, "CFBookData");
		}
	}
	
	/**
	 * Sets the book's data
	 * @param data The new data
	 * @param stack The book stack (only necessary when {@link #getDataStorageType()} returns {@link DataStorageType#LOCAL}
	 */
	public void setData(final NBTTagCompound data, final ItemStack stack) {
		final NBTTagCompound tag = getData(stack);
		if (getDataStorageType() == DataStorageType.GLOBAL) {
			globalData = data;
			new SimpleRunnable() { //Done to prevent speed bottlenecks from constant file modification
				
				@Override
				public void run() {
					tag.setTag(player.getGameProfile().getId().toString(), data);
					File storage = new File(GLOBAL_DIR+bookItem.getModId()+"/"+bookItem.getUnlocalizedName()+".dat");
					try {
						CompressedStreamTools.write(tag, storage);
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.disable(true);
				}
				
				@Override
				public String getName() {
					return "Book Data Serializer";
				}
			}.start();
		} else if (getDataStorageType() == DataStorageType.LOCAL) {
			String key = bookItem.getModId()+":"+bookItem.getUnlocalizedName();
			tag.setTag(player.getGameProfile().getId().toString(), data);
			LocalBookData.putLocalData(key, tag);
		} else {
			tag.setTag(player.getGameProfile().getId().toString(), data);
			NBTHelper.setTag(stack, "CFBookData", tag);
		}
	}
	
	/**
	 * Retrieves the current page of the book
	 * @return The current page
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	
	/**
	 * Attempts to set the page of the book
	 * @param newPage The new page
	 */
	public void setPage(int newPage) {
		if (newPage >= getPages().length)
			newPage = 0;
		else if (newPage < 0)
			newPage = getPages().length;
		if (onPageFlip(currentPage, newPage)) {
			currentPage = newPage;
			NBTTagCompound data = getData(bookStack);
			data.setInteger("page", currentPage);
			setData(data, bookStack);
		}
	}
	
	/**
	 * Do NOT touch this, it is required for communications between the actual gui and book
	 * @param gui The actual gui
	 */
	@SideOnly(Side.CLIENT)
	public final void handoffGui(GuiBook gui) {
		this.bookGui = gui;
	}
	
	/**
	 * Called when the book is opened
	 * @param player The player who opened the book
	 */
	public void onOpen(EntityPlayer player) {
		this.player = player;
		NBTTagCompound data = getData(bookStack);
		if (data.hasKey("page"))
			setPage(data.getInteger("page"));
	}
	
	/**
	 * Called when the book is closed
	 */
	public void onClose() {}
	
	/**
	 * Closes the book's gui
	 */
	@SideOnly(Side.CLIENT)
	public void closeBook() {
		GuiUtils.closeCurrentGui(GuiBook.class);
	}
	
	/**
	 * Same as {@link GuiScreen#doesGuiPauseGame()}
	 */
	public abstract boolean doesPauseGame();
	
	/**
	 * Used to retrieve the pages in the book
	 * @return All the pages in the book, THE ORDER MATTERS! Page at index 0 is the page displayed on the first page of 
	 * 		   the book
	 */
	public abstract Page[] getPages();
	
	/**
	 * Used to get the background image, basically the generic background
	 * @return The resource location of the background, or null if there aren't any
	 */
	public abstract ResourceLocation getBackground();
	
	/**
	 * Called when the page is rendered
	 * @param dt The time since the last render of the current pass in milliseconds
	 */
	@SideOnly(Side.CLIENT)
	public abstract void onRender(int dt);
	
	/**
	 * Called when the page is attempted to be changed
	 * @param oldPage The current page of the book
	 * @param newPage The new page of the book
	 * @return True to allow the page flip, false if otherwise
	 */
	public abstract boolean onPageFlip(int oldPage, int newPage);
	
	/**
	 * Represents where the persistence data is stored for this book:
	 * GLOBAL = All worlds share the same data
	 * LOCAL = All books in one world share the same data
	 * INSTANCE = The data stored is per book
	 */
	public static enum DataStorageType {
		GLOBAL, LOCAL, INSTANCE
	}
}
