package com.sn2.gpick.creative;

import com.sn2.gpick.manager.ItemManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeCore extends CreativeTabs {
	public static CreativeCore trunkcore = new CreativeCore();

	public CreativeCore() {
		super("gpickcore");
	}

	public ItemStack getTabIconItem() {
		return new ItemStack(ItemManager.trunkGoldCore);
	}
}
