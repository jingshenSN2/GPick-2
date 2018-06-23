package com.sn2.gpick.item.trunk;

import com.sn2.gpick.item.ItemManager;
import com.sn2.gpick.item.TrunkGPick;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Stone extends TrunkGPick {

	public Stone() {
		super(MaterialManager.STONE, 100, Items.STONE_PICKAXE);
		this.setUnlocalizedName("gpick(stone)");
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (stack.getItemDamage() == stack.getMaxDamage()) {
			entityLiving.renderBrokenItemStack(stack);
			stack.shrink(1);
			((EntityPlayer) entityLiving).setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemManager.trunkStoneCore));
		} else
			super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
		return true;
	}
}
