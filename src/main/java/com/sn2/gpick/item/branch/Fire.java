package com.sn2.gpick.item.branch;

import java.util.List;

import javax.annotation.Nullable;

import com.sn2.gpick.GPick;
import com.sn2.gpick.GPickWords;
import com.sn2.gpick.config.ConfigManager;
import com.sn2.gpick.item.BranchGPick;
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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Fire extends BranchGPick {

	private static int maxFuel = 64;

	public Fire() {
		super(MaterialManager.FIRE);
		this.setUnlocalizedName("gpick(fire)");
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(GPickWords.FLUE() + getFuel(stack) + " / " + maxFuel);
		tooltip.add(GPickWords.FIRECHANCE() + (getFortuneLevel(stack) / 2.0 + 1) * ConfigManager.fireSmeltChance + "%");
	}

	public int getFortuneLevel(ItemStack stack) {
		NBTTagList list = stack.getEnchantmentTagList();
		int level = 0;
		if (list.tagCount() == 0) 
			return 0;
		else {
			for (int i = 0; i < list.tagCount(); i++) {
				if (list.getCompoundTagAt(i).getInteger("id") == 35) {
					level = list.getCompoundTagAt(i).getInteger("lvl");
				}
			}
			return level;
		}
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
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return ((double) stack.getMaxDamage() - (double) stack.getItemDamage()) / (double) stack.getMaxDamage();
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
		stack.setTagInfo("fuel", new NBTTagInt(0));
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		return true;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		float baseSpeed = super.getDestroySpeed(stack, state);
		return (getFuel(stack) == 0)? 0.001F : baseSpeed;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			IInventory bag = player.inventory;
			for (int i = 0; i < bag.getSizeInventory(); i++) {
				ItemStack repair = bag.getStackInSlot(i);
				if (repair.getItem().equals(Items.COAL)) {
					int fuel = getFuel(stack);
					fuel += 8;
					if (fuel > maxFuel)
						fuel = maxFuel;
					stack.setTagInfo("fuel", new NBTTagInt(fuel));
					repair.setCount(repair.getCount() - 1);
					break;
				}
			}
		}
		return stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (getFuel(itemstack) != maxFuel) {
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
