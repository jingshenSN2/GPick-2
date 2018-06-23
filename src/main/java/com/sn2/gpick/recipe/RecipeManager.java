package com.sn2.gpick.recipe;

import com.sn2.gpick.GPick;
import com.sn2.gpick.config.ConfigManager;
import com.sn2.gpick.item.ItemManager;

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

		if (ConfigManager.allowFire) {
			addRecipe("branchFire", ItemManager.branchFire, Items.FLINT_AND_STEEL, Blocks.COAL_BLOCK, ItemManager.stickIron, 
					ItemManager.trunkIronCore);
		}
		if (ConfigManager.allowAdFire) {
			addRecipe("branchAdFire", ItemManager.branchAdFire, Items.BLAZE_ROD, Blocks.MAGMA, Items.BLAZE_ROD, 
					ItemManager.branchFireCore);
		}
		if (ConfigManager.allowStoneAnger) {
			addRecipe("branchStoneAnger", stone, Items.GUNPOWDER, Blocks.STONE, ItemManager.stickIron, ItemManager.trunkIronCore );
		}
		if (ConfigManager.allowReverse) {
			addRecipe("branchReverse", reverse, ItemManager.stickIron, Items.WATER_BUCKET, ItemManager.stickIron, ItemManager.trunkIronCore);
		}
		if (ConfigManager.allowGlass) {
			addRecipe("branchGlass", glass, Blocks.GLASS_PANE, Blocks.GLASS, Items.STICK, ItemManager.trunkWoodCore);
		}
		if (ConfigManager.allowVein) {
			addRecipe("branchVein", ItemManager.branchVein, Blocks.REDSTONE_TORCH, Blocks.REDSTONE_BLOCK, ItemManager.stickGold, 
					ItemManager.trunkGoldCore);
		}
		if (ConfigManager.allowReach) {
			addRecipe("branchReach", ItemManager.branchReach, Blocks.PISTON, Blocks.STICKY_PISTON, ItemManager.stickGold, 
					ItemManager.trunkGoldCore);
		}
		if (ConfigManager.allowJump) {
			addRecipe("branchJump", ItemManager.branchJump, ItemManager.stickGold, Blocks.SLIME_BLOCK, ItemManager.stickGold, 
					ItemManager.trunkGoldCore);
		}
		if (ConfigManager.allowFly) {
			addRecipe("branchFly", ItemManager.branchFly, Items.ELYTRA, Items.FEATHER, ItemManager.stickDiamond, 
					ItemManager.trunkDiamondCore);
		}
		if (ConfigManager.allowHeart) {
			addRecipe("branchHeart", ItemManager.branchHeart, Items.COMPASS, Items.ENDER_EYE, ItemManager.stickIron, 
					ItemManager.trunkIronCore);
		}

	}

	public static void addRecipe(String name, Item out, Object a, Object b, Item stick, Item core) {
		GameRegistry.addShapedRecipe(new ResourceLocation(GPick.MODID, name), group, new ItemStack(out), 
				"ABA",
				" C ",
				" D ", 'C', a, 'A', b, 'B', core, 'D', stick);
	}

	public static void addRecipe(String name, ItemStack out, Object a, Object b, Item stick, Item core) {
		GameRegistry.addShapedRecipe(new ResourceLocation(GPick.MODID, name), group, out, 
				"ABA",
				" C ",
				" D ", 'C', a, 'A', b, 'B', core, 'D', stick);
	}
}
