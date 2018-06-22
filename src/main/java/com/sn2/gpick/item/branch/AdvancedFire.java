package com.sn2.gpick.item.branch;

import java.util.List;
import java.util.Timer;

import javax.annotation.Nullable;

import com.sn2.gpick.GPick;
import com.sn2.gpick.GPickWords;
import com.sn2.gpick.config.ConfigManager;
import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.manager.ItemManager;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AdvancedFire extends BranchGPick {

	private static int maxAdFuel = 1000;

	public AdvancedFire() {
		super(MaterialManager.ADFIRE);
		this.setUnlocalizedName("gpick(advanced fire)");
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(GPickWords.FLUE() + getFuel(stack) + " / " + maxAdFuel);
		tooltip.add(GPickWords.FIRECHANCE() + ConfigManager.adFireSmeltChance + "%");
		tooltip.add(GPickWords.ADFIRECHANCE() + ConfigManager.adFireDoubleChance + "%");
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (stack.getItemDamage() == stack.getMaxDamage()) {
			entityLiving.renderBrokenItemStack(stack);
			stack.shrink(1);
			((EntityPlayer) entityLiving).setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemManager.trunkDiamondCore));
		} else
			super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
		return true;
	}
	
	public int getFuel(ItemStack stack) {
		NBTTagCompound compound = stack.getTagCompound();
		if (compound != null)
			return compound.getInteger("fuel");
		else {
			return 0;
		}
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
		stack.setTagInfo("fuel", new NBTTagInt(0));
    }
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		float baseSpeed = super.getDestroySpeed(stack, state);
		return (getFuel(stack) == 0) ? 0.001F : baseSpeed;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			IInventory bag = player.inventory;
			for (int i = 0; i < bag.getSizeInventory(); i++) {
				ItemStack repair = bag.getStackInSlot(i);
				if (repair.getItem().equals(Items.LAVA_BUCKET)) {
					stack.setTagInfo("fuel", new NBTTagInt(1000));
					bag.setInventorySlotContents(i, new ItemStack(Items.BUCKET));
					break;
				}
			}
		}
		return stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (getFuel(itemstack) != maxAdFuel) {
			playerIn.setActiveHand(handIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		} else {
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
}
