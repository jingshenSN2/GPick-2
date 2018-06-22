package com.sn2.gpick.item.branch;

import java.util.List;

import javax.annotation.Nullable;

import com.sn2.gpick.GPick;
import com.sn2.gpick.GPickWords;
import com.sn2.gpick.config.ConfigManager;
import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.manager.ItemManager;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class HeartStone extends BranchGPick {
	public HeartStone() {
		super(MaterialManager.HEARTSTONE);
		this.setUnlocalizedName("gpick(heartstone)");
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (worldIn != null) {
			tooltip.add(GPickWords.POS() + getPos(stack));
		}
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
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setDouble("homeX", playerIn.posX);
		compound.setDouble("homeY", playerIn.posY);
		compound.setDouble("homeZ", playerIn.posZ);
		compound.setInteger("dimension", playerIn.dimension);
		stack.setTagCompound(compound);
    }
	
	public String getPos(ItemStack stack) {
		NBTTagCompound compound = stack.getTagCompound();
		if (compound != null) {
			double x = compound.getDouble("homeX");
			double y = compound.getDouble("homeY");
			double z = compound.getDouble("homeZ");
			return (int)x + GPickWords.POSSPLITER() + (int)y + GPickWords.POSSPLITER() + (int)y;
		}
		else {
			return "NaN" + GPickWords.POSSPLITER() + "NaN" + GPickWords.POSSPLITER() + "NaN";
		}
	}
	
	public int getWorld(ItemStack stack) {
		NBTTagCompound compound = stack.getTagCompound();
		if (compound != null) {
			int world = compound.getInteger("dimension");
			return world;
		}
		else {
			return 0;
		}
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			if (player.isSneaking()) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setDouble("homeX", player.posX);
				compound.setDouble("homeY", player.posY);
				compound.setDouble("homeZ", player.posZ);
				compound.setInteger("dimension", player.dimension);
				stack.setTagCompound(compound);
			} else if (player.dimension == getWorld(stack)) {
				NBTTagCompound compound = stack.getTagCompound();
				double x = compound.getDouble("homeX");
				double y = compound.getDouble("homeY");
				double z = compound.getDouble("homeZ");
				double x1 = player.posX;
				double y1 = player.posY;
				double z1 = player.posZ;
				if (player instanceof EntityPlayerMP)
                {
                    ((EntityPlayerMP)player).connection.setPlayerLocation(x, y, z, player.rotationYaw, player.rotationPitch);
                }
                else
                {
                    player.setLocationAndAngles(x, y, z, player.rotationYaw, player.rotationPitch);
                }
				compound.setDouble("homeX", x1);
				compound.setDouble("homeY", y1);
				compound.setDouble("homeZ", z1);
				int distance = (int) Math.sqrt((x - x1)*(x - x1) + (y - y1)*(y - y1) + (z - z1)*(z - z1));
				int damage = distance/10;
				if (worldIn.isRemote) {
					player.sendMessage(
							new TextComponentString(GPickWords.NAME() + GPickWords.TPSUCCES() + distance + GPickWords.UNIT()));
				}
				if (stack.getMaxDamage() - stack.getItemDamage() < damage) {
					entityLiving.renderBrokenItemStack(stack);
					stack.shrink(1);
					((EntityPlayer) entityLiving).setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemManager.trunkDiamondCore));
				} else
					stack.damageItem(damage, player);
			} else if (player.dimension != getWorld(stack) && worldIn.isRemote) {
				player.sendMessage(
						new TextComponentString(GPickWords.NAME() + GPickWords.TPFAIL()));
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
		return ConfigManager.tpTime * 20;
	}
}
