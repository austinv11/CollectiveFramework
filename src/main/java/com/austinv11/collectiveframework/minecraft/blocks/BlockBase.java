package com.austinv11.collectiveframework.minecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public abstract class BlockBase extends Block {
	
	public BlockBase(Material material){
		super(material);
		this.setHardness(4f);
		if (getTab() != null)
			this.setCreativeTab(getTab());
	}
	
	public BlockBase(){
		this(Material.ROCK);
	}
	
	/**
	 * Gets the creative tab this block belongs to
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
		return String.format("tile.%s%s", getModId().toLowerCase()+":", getUnwrappedUnlocalizedName(getUnwrappedUnlocalizedName(super.getUnlocalizedName())));
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName){//Removes the "item." from the item name
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
}
