package com.austinv11.collectiveframework.minecraft.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Simple implementation of an item
 */
public abstract class ItemBase extends Item {
	
	public ItemBase() {
		super();
		this.setMaxStackSize(64);
		if (getTab() != null)
			this.setCreativeTab(getTab());
	}
	
	/**
	 * Gets the creative tab this item belongs to
	 * @return The tab (it can be null)
	 */
	public abstract CreativeTabs getTab();
	
	/**
	 * Gets the modid to which the block is associated to
	 * @return The modid
	 */
	public abstract String getModId();
	
	@Override
	public String getUnlocalizedName(){//Formats the name
		return String.format("item.%s%s", getModId().toLowerCase()+":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack item){//Formats the name
		return String.format("item.%s%s", getModId().toLowerCase()+":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister){//Sets the icon
		itemIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName){//Removes the "item." from the item name
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}
}
