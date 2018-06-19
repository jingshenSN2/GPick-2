package com.sn2.gpick.item.branch;

import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Jump extends BranchGPick{

	public Jump() {
		super(MaterialManager.JUMP);
		this.setUnlocalizedName("gpick(jump)");
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (isSelected)
			((EntityPlayer) entityIn).addPotionEffect(new PotionEffect(Potion.getPotionById(8), 60, 4));
	}
}
