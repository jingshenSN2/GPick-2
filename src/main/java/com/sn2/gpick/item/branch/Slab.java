package com.sn2.gpick.item.branch;

import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.item.ItemManager;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import scala.noinline;
import scala.annotation.bridge;

public class Slab extends BranchGPick {
	public Slab() {
		super(MaterialManager.SLAB);
		this.setUnlocalizedName("gpick(slab)");
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (stack.getItemDamage() == stack.getMaxDamage()) {
			entityLiving.renderBrokenItemStack(stack);
			stack.shrink(1);
			((EntityPlayer) entityLiving).setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemManager.trunkStoneCore));
		}
		
		return true;
	}
	
}
