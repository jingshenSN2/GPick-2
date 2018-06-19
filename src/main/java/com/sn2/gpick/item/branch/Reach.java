package com.sn2.gpick.item.branch;

import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Reach extends BranchGPick {

	public Reach() {
		super(MaterialManager.REACH);
		this.setUnlocalizedName("gpick(reach)");
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (isSelected)
			((EntityPlayer) entityIn).getEntityAttribute(EntityPlayer.REACH_DISTANCE).setBaseValue(25.0D);
		else
			((EntityPlayer) entityIn).getEntityAttribute(EntityPlayer.REACH_DISTANCE).setBaseValue(5.0D);
	}
}
