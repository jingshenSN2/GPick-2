package com.sn2.gpick.item;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nullable;

import com.sn2.gpick.GPick;
import com.sn2.gpick.GPickWords;
import com.sn2.gpick.creative.CreativeTrunk;
import com.sn2.gpick.manager.ItemManager;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;

public class TrunkGPick extends ItemPickaxe {

	public int max;
	public Item repairs;
	

	public TrunkGPick(ToolMaterial material, int max, Item repair) {
		super(material);
		this.max = max;
		this.repairs = repair;
		this.setNoRepair();
		this.setCreativeTab(CreativeTrunk.trunk);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
		int id = enchantment.getEnchantmentID(enchantment);
		if (id == 34 || id == 70)
			return false;
		else
			return super.canApplyAtEnchantingTable(stack, enchantment);
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
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (worldIn != null)
			tooltip.add(GPickWords.MINECOUNT() + getMineCount(stack) + " / " + max);
	}

	private int getMineCount(ItemStack stack) {
		NBTTagCompound compound = stack.getTagCompound();
		if (compound != null)
			return compound.getInteger("minecount");
		else
			return 0;
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		stack.setTagInfo("minecount", new NBTTagInt(0));
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if ((double) state.getBlockHardness(worldIn, pos) != 0.0D) {
			stack.damageItem(1, entityLiving);
			int minecount = getMineCount(stack);
			stack.setTagInfo("minecount", new NBTTagInt(minecount + 1));
		}
		return true;
	}

	// TODO more fair speed
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		float baseSpeed = super.getDestroySpeed(stack, state);
		return baseSpeed * (max - getMineCount(stack)) / max;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			IInventory bag = player.inventory;
			for (int i = 0; i < bag.getSizeInventory(); i++) {
				ItemStack repair = bag.getStackInSlot(i);
				if (repair.getItem().equals(repairs)) {
					int need = getMineCount(stack);
					int damage = repair.getMaxDamage() - repair.getItemDamage();
					int result = 0;
					if (repair.getItem().equals(Items.GOLDEN_PICKAXE))
						need = (int) Math.ceil(need / 20.0);
					if (need >= damage) {
						result = need - damage;
						repair.setCount(0);
					} else {
						result = 0;
						repair.damageItem(need, entityLiving);
					}
					stack.setTagInfo("minecount", new NBTTagInt(Math.max(0, result)));
					break;
				}
			}
		}
		return stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (getMineCount(itemstack) != 0) {
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
