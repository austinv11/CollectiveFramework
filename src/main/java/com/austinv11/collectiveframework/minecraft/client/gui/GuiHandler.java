package com.austinv11.collectiveframework.minecraft.client.gui;

import com.austinv11.collectiveframework.minecraft.books.core.GuiBook;
import com.austinv11.collectiveframework.minecraft.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Reference.Guis.BOOK.ordinal())
			return new GuiBook(player);
		return null;
	}
}
