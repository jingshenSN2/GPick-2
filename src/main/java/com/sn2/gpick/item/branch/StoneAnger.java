package com.sn2.gpick.item.branch;

import java.awt.List;
import java.util.ArrayList;
import java.util.Set;

import com.google.common.collect.Sets;
import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.item.ItemManager;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StoneAnger extends ItemTool {

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND,
			Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND,
			Blocks.GRASS_PATH, Blocks.CONCRETE_POWDER, Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE,
			Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB,
			Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE,
			Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK,
			Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE,
			Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);
	
	public static Set<Block> canMine = Sets.newHashSet(Blocks.STONE, Blocks.SAND, Blocks.DIRT, Blocks.GRAVEL);
	
	public StoneAnger() {
		super(1.0F, -2.8F, MaterialManager.ONLYSTONE, EFFECTIVE_ON);
		this.setUnlocalizedName("gpick(only for stone)");
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		float baseSpeed = super.getDestroySpeed(stack, state);
		if (canMine.contains(state.getBlock()))
			baseSpeed *= 40;
		return baseSpeed;
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
