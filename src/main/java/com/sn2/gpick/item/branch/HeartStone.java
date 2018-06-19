package com.sn2.gpick.item.branch;

import java.util.List;

import javax.annotation.Nullable;

import com.sn2.gpick.GPick;
import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class HeartStone extends BranchGPick {
	public HeartStone() {
		super(MaterialManager.HEARTSTONE);
		this.setUnlocalizedName("gpick(heartstone)");
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (worldIn != null)
			tooltip.add(GPick.POS + getPos(stack));
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setDouble("homeX", playerIn.posX);
		compound.setDouble("homeY", playerIn.posY);
		compound.setDouble("homeZ", playerIn.posZ);
		stack.setTagCompound(compound);
    }
	
	public String getPos(ItemStack stack) {
		NBTTagCompound compound = stack.getTagCompound();
		if (compound != null) {
			double x = compound.getDouble("homeX");
			double y = compound.getDouble("homeY");
			double z = compound.getDouble("homeZ");
			return (int)x + "/" + (int)y + "/" + (int)y;
		}
		else {
			return "NaN/NaN/NaN";
		}
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			if (!player.isSneaking()) {
				NBTTagCompound compound = stack.getTagCompound();
				double x = compound.getDouble("homeX");
				double y = compound.getDouble("homeY");
				double z = compound.getDouble("homeZ");
				double x1 = player.posX;
				double y1 = player.posY;
				double z1 = player.posZ;
				player.setPosition(x, y, z);
				compound.setDouble("homeX", x1);
				compound.setDouble("homeY", y1);
				compound.setDouble("homeZ", z1);
				stack.damageItem(50, player);
			}
			else {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setDouble("homeX", player.posX);
				compound.setDouble("homeY", player.posY);
				compound.setDouble("homeZ", player.posZ);
				stack.setTagCompound(compound);
			}
		}
		return stack;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (!getPos(itemstack).equals("NaN/NaN/NaN")) {
			playerIn.setActiveHand(handIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		} else {
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		}
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.EAT;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 224;
	}
}
