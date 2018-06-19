package com.sn2.gpick.item;

import com.sn2.gpick.creative.CreativeBranch;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;

public class BranchGPick extends ItemPickaxe{
	public BranchGPick(ToolMaterial material) {
		super(material);
		this.setNoRepair().setCreativeTab(CreativeBranch.branch);
	}
}
