package com.austinv11.collectiveframework.minecraft.event.handler;

import com.austinv11.collectiveframework.minecraft.reference.Config;
import com.austinv11.collectiveframework.minecraft.utils.Colors;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class TooltipHandler {
	
	@SubscribeEvent
	public void onTooltip(ItemTooltipEvent event) {
		if (Config.debugTooltips && !event.getItemStack().isEmpty()) {
			event.getToolTip().addAll(getUnlocalisedName(event.getItemStack()));
			event.getToolTip().addAll(getOreDictTooltip(event.getItemStack()));
			event.getToolTip().addAll(getNBTTooltip(event.getItemStack()));
		}
	}
	
	private List<String> getNBTTooltip(ItemStack stack) {
		List<String> tooltip = new ArrayList<String>();
		if (stack.hasTagCompound()) {
			tooltip.add(Colors.UNDERLINE+"NBT Tags:");
			tooltip.add(stack.getTagCompound().toString());
		}
		return tooltip;
	}
	
	private List<String> getOreDictTooltip(ItemStack stack) {
		List<String> tooltip = new ArrayList<String>();
		if (OreDictionary.getOreIDs(stack).length > 0) {
			tooltip.add(Colors.UNDERLINE+"Ore Dictionary Entries:");
			for (int id : OreDictionary.getOreIDs(stack)) {
				tooltip.add("-"+OreDictionary.getOreName(id)+" (id: "+id+")");
			}
		}
		return tooltip;
	}
	
	private List<String> getUnlocalisedName(ItemStack stack) {
		List<String> tooltip = new ArrayList<String>();
		tooltip.add(Colors.UNDERLINE+"String Item ID:");
		Item item = stack.getItem();
		if (item instanceof ItemBlock) {
			tooltip.add(Block.getBlockFromItem(item).getUnlocalizedName());
		} else {
			tooltip.add(item.getUnlocalizedName());
		}
		return tooltip;
	}
}
