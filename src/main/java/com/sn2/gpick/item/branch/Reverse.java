package com.sn2.gpick.item.branch;

import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class Reverse extends BranchGPick {

	public Reverse() {
		super(MaterialManager.REVERSE);
		this.setUnlocalizedName("gpick(reverse)");
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		float hardness = state.getBlockHardness(null, null);
		return (float) (super.getDestroySpeed(stack, state) * hardness / (70 * Math.exp(-hardness / 17)));
	}
}
