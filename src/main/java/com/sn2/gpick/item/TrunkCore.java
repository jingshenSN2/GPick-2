package com.sn2.gpick.item;

import com.sn2.gpick.creative.CreativeTrunk;
import com.sn2.gpick.creative.CreativeCore;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;

public class TrunkCore extends Item {
	public TrunkCore() {
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeCore.trunkcore);
	}
}
