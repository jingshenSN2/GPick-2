package com.sn2.gpick.creative;

import com.sn2.gpick.item.ItemManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTrunk extends CreativeTabs {
	public static CreativeTrunk trunk = new CreativeTrunk();

	public CreativeTrunk() {
		super("gpicktrunk");
	}

	public ItemStack getTabIconItem() {
		return new ItemStack(ItemManager.trunkWood);
	}
}
