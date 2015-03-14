package com.austinv11.collectiveframework.minecraft.books;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.minecraft.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Object for a custom manual, can only be instantiated through {@link com.austinv11.collectiveframework.minecraft.books.BookFactory}
 */
public final class Book extends Item {
	
	protected CreativeTabs tab;
	protected ResourceLocation icon;
	protected int id;
	protected String name;
	
	protected Book() {
		super();
		this.setMaxStackSize(1);
		if (tab != null)
			this.setCreativeTab(tab);
	}
	
	@Override
	public String getUnlocalizedName(){
		return "item.book."+getName();
	}
	
	@Override
	public String getUnlocalizedName(ItemStack item){
		return "item.book."+getName();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister){
		itemIcon = iconRegister.registerIcon(icon.toString());
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote)
			player.openGui(CollectiveFramework.instance, Reference.Guis.BOOK.ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
		return super.onItemRightClick(stack, world, player);
	}
	
	/**
	 * Gets the unique id for the book
	 * @return The id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the name for the book (becomes unlocalized as item.book.<name>)
	 * @return The name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof Book && ((Book) other).id == this.id;
	}
	
	@Override
	public String toString() {
		return "Book(Name:"+getName()+" ID:"+id+")";
	}
}
