package com.sn2.gpick.item.branch;

import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Fly extends BranchGPick {

	public Fly() {
		super(MaterialManager.FLY);
		this.setUnlocalizedName("gpick(fly)");
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		EntityPlayer player = (EntityPlayer) entityIn;
		if (isSelected)
			player.capabilities.allowFlying = true;
		else {
			player.capabilities.allowFlying = false;
			player.capabilities.isFlying = false;
			player.fallDistance = 0.0F;
		}
	}
}
