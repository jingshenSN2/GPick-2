package com.sn2.gpick.item.branch;

import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Glass extends BranchGPick {

	public Glass() {
		super(MaterialManager.GLASS);
		this.setUnlocalizedName("gpick(glass)");
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		float baseSpeed = super.getDestroySpeed(stack, state);
		Block block = state.getBlock();
		int[] ids = OreDictionary.getOreIDs(new ItemStack(block));
		boolean flag = false;
		if (ids.length != 0) {
			for (int id : ids) {
				String name = OreDictionary.getOreName(id);
				if (name.endsWith("Glass"))
					flag = true;
			}
		}
		return flag ? baseSpeed : 0.0F;
	}

}
