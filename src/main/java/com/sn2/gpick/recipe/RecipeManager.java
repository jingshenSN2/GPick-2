package com.sn2.gpick.recipe;

import com.sn2.gpick.GPick;
import com.sn2.gpick.manager.ItemManager;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeManager {

	public static final int fireFlue = 40;
	public static ResourceLocation group = new ResourceLocation(GPick.MODID, "group");

	public static void init() {

		GameRegistry.addShapedRecipe(new ResourceLocation(GPick.MODID, "trunkWood"), group,
				new ItemStack(ItemManager.trunkWood), "AAA", " B ", " C ", 'C', Items.STICK, 'A', Blocks.LOG, 'B',
				new ItemStack(Items.WOODEN_PICKAXE, 1, 1));

		ItemStack stone = new ItemStack(ItemManager.branchStone);
		ItemStack reverse = new ItemStack(ItemManager.branchReverse);
		ItemStack glass = new ItemStack(ItemManager.branchGlass);

		stone.addEnchantment(Enchantment.getEnchantmentByLocation("efficiency"), 4);
		reverse.addEnchantment(Enchantment.getEnchantmentByLocation("efficiency"), 4);
		glass.addEnchantment(Enchantment.getEnchantmentByLocation("efficiency"), 2);

		addRecipe("trunkStone", ItemManager.trunkStone, Items.STICK, Blocks.COBBLESTONE, ItemManager.trunkWoodCore);
		addRecipe("trunkIron", ItemManager.trunkIron, Items.STICK, Blocks.IRON_BLOCK, ItemManager.trunkStoneCore);
		addRecipe("trunkGold", ItemManager.trunkGold, Items.STICK, Blocks.GOLD_BLOCK, ItemManager.trunkIronCore);
		addRecipe("trunkDiamond", ItemManager.trunkDiamond, Items.STICK, Blocks.DIAMOND_BLOCK, ItemManager.trunkGoldCore);

		addRecipe("branchFire", ItemManager.branchFire, Items.FLINT_AND_STEEL, Blocks.COAL_BLOCK,ItemManager.trunkIronCore);
		addRecipe("branchAdFire", ItemManager.branchAdFire, Items.BLAZE_ROD, Blocks.MAGMA, ItemManager.branchFireCore);
		addRecipe("branchStoneAnger", stone, Items.GUNPOWDER, Blocks.STONE, ItemManager.trunkIronCore);
		addRecipe("branchReverse", reverse, Items.STICK, Items.WATER_BUCKET, ItemManager.trunkIronCore);
		addRecipe("branchGlass", glass, Blocks.GLASS_PANE, Blocks.GLASS, ItemManager.trunkWoodCore);
		addRecipe("branchVein", ItemManager.branchVein, Blocks.REDSTONE_TORCH, Blocks.REDSTONE_BLOCK, ItemManager.trunkGoldCore);
		addRecipe("branchReach", ItemManager.branchReach, Blocks.PISTON, Blocks.STICKY_PISTON, ItemManager.trunkGoldCore);
		addRecipe("branchJump", ItemManager.branchJump, Items.STICK, Blocks.SLIME_BLOCK, ItemManager.trunkGoldCore);
		addRecipe("branchFly", ItemManager.branchFly, Items.ELYTRA, Items.FEATHER,ItemManager.trunkDiamondCore);
		addRecipe("branchHeart", ItemManager.branchHeart, Items.COMPASS, Items.ENDER_EYE,ItemManager.trunkIronCore);

	}

	public static void addRecipe(String name, Item out, Object item, Object block, Item in) {
		GameRegistry.addShapedRecipe(new ResourceLocation(GPick.MODID, name), group, new ItemStack(out), "AAA", " B ",
				" C ", 'C', item, 'A', block, 'B', in);
	}

	public static void addRecipe(String name, ItemStack out, Object item, Object block, Item in) {
		GameRegistry.addShapedRecipe(new ResourceLocation(GPick.MODID, name), group, out, "AAA", " B ",
				" C ", 'C', item, 'A', block, 'B', in);
	}
}
