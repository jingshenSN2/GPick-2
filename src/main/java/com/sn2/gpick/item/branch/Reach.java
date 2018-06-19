package com.sn2.gpick.item.branch;

import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.manager.ItemManager;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (stack.getItemDamage() == stack.getMaxDamage()) {
			entityLiving.renderBrokenItemStack(stack);
			stack.shrink(1);
			((EntityPlayer) entityLiving).setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemManager.trunkGoldCore));
		} else
			super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
		return true;
	}
}
