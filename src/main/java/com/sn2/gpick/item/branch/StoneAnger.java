package com.sn2.gpick.item.branch;

import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.manager.ItemManager;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StoneAnger extends BranchGPick {

	public StoneAnger() {
		super(MaterialManager.ONLYSTONE);
		this.setUnlocalizedName("gpick(only for stone)");
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		float baseSpeed = super.getDestroySpeed(stack, state);
		return state.getBlock().equals(Blocks.STONE) ? baseSpeed : 0.0F;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (stack.getItemDamage() == stack.getMaxDamage()) {
			entityLiving.renderBrokenItemStack(stack);
			stack.shrink(1);
			((EntityPlayer) entityLiving).setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemManager.trunkIronCore));
		} else
			super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
		return true;
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ, EnumHand hand) {
		ItemStack stonePick = player.getHeldItemMainhand();
		IInventory bag = player.inventory;
		EnumActionResult result = EnumActionResult.PASS;
		for (int i = 0; i < bag.getSizeInventory(); i++) {
			ItemStack torch = bag.getStackInSlot(i);
			if (torch.getItem().equals(new ItemStack(Blocks.TORCH).getItem())) {
				if (side.equals(EnumFacing.UP) && world.getBlockState(pos).getBlock().canPlaceTorchOnTop(world.getBlockState(pos), world, pos)) {
					if (world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
						world.setBlockState(pos.up(), Block.getStateById(Block.getIdFromBlock(Blocks.TORCH)));
						bag.setInventorySlotContents(i, new ItemStack(Blocks.TORCH, torch.getCount() - 1));
						stonePick.damageItem(1, player);
						result = EnumActionResult.SUCCESS;
						break;
					}
				}
			}
		}
		return result;
	}

}
