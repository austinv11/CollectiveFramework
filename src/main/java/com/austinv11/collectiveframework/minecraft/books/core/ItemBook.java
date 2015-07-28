package com.austinv11.collectiveframework.minecraft.books.core;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.minecraft.books.api.Book;
import com.austinv11.collectiveframework.minecraft.items.ItemBase;
import com.austinv11.collectiveframework.minecraft.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * This class represents the physical item representing a book
 */
public abstract class ItemBook extends ItemBase {
	
	public ItemBook() {
		this.setMaxStackSize(1);
	}
	
	/**
	 * Used to retrieve the data for the book, the book will be instantiated on use
	 * @return The book class
	 */
	public abstract Class<? extends Book> getBook();
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		openBook(player);
		return stack;
	}
	
	/**
	 * Opens the book gui, they MUST be holding this book
	 * @param player The player to open the gui for
	 */
	public void openBook(EntityPlayer player) {
		player.openGui(CollectiveFramework.instance, Reference.Guis.BOOK.ordinal(), player.getEntityWorld(), 
				(int)player.posX, (int)player.posY, (int)player.posZ);
	}
}
