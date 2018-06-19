package com.sn2.gpick.manager;

import com.sn2.gpick.item.BranchGPick;
import com.sn2.gpick.item.TrunkGPick;
import com.sn2.gpick.item.branch.AdvancedFire;
import com.sn2.gpick.item.branch.Fire;
import com.sn2.gpick.item.branch.Fly;
import com.sn2.gpick.item.branch.Glass;
import com.sn2.gpick.item.branch.HeartStone;
import com.sn2.gpick.item.branch.Jump;
import com.sn2.gpick.item.branch.Reach;
import com.sn2.gpick.item.branch.Reverse;
import com.sn2.gpick.item.branch.StoneAnger;
import com.sn2.gpick.item.branch.VeinMine;
import com.sn2.gpick.item.trunk.*;
import com.sn2.gpick.item.trunkcore.DiamondCore;
import com.sn2.gpick.item.trunkcore.FireCore;
import com.sn2.gpick.item.trunkcore.GoldCore;
import com.sn2.gpick.item.trunkcore.IronCore;
import com.sn2.gpick.item.trunkcore.StoneCore;
import com.sn2.gpick.item.trunkcore.WoodCore;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemManager {

	public static Item trunkWood = new Wood();
	public static Item trunkStone = new Stone();
	public static Item trunkIron = new Iron();
	public static Item trunkGold = new Gold();
	public static Item trunkDiamond = new Diamond();
	
	public static Item branchFire = new Fire();
	public static Item branchAdFire = new AdvancedFire();
	public static Item branchStone = new StoneAnger();
	public static Item branchReverse = new Reverse();
	public static Item branchGlass = new Glass();
	public static Item branchVein = new VeinMine();
	public static Item branchReach = new Reach();
	public static Item branchJump = new Jump();
	public static Item branchFly = new Fly();
	public static Item branchHeart = new HeartStone();
	
	public static Item trunkWoodCore = new WoodCore();
	public static Item trunkStoneCore = new StoneCore();
	public static Item trunkIronCore = new IronCore();
	public static Item trunkGoldCore = new GoldCore();
	public static Item trunkDiamondCore = new DiamondCore();
	public static Item branchFireCore = new FireCore();

	public static void init() {
		register(trunkWood.setRegistryName("gpick_w"));
		register(trunkStone.setRegistryName("gpick_s"));
		register(trunkIron.setRegistryName("gpick_i"));
		register(trunkGold.setRegistryName("gpick_g"));
		register(trunkDiamond.setRegistryName("gpick_d"));
		
		register(branchFire.setRegistryName("gpick_fire"));
		register(branchAdFire.setRegistryName("gpick_adfire"));
		register(branchStone.setRegistryName("gpick_stone"));
		register(branchReverse.setRegistryName("gpick_rev"));
		register(branchGlass.setRegistryName("gpick_glass"));
		register(branchVein.setRegistryName("gpick_vein"));
		register(branchReach.setRegistryName("gpick_reach"));
		register(branchJump.setRegistryName("gpick_jump"));
		register(branchFly.setRegistryName("gpick_fly"));
		register(branchHeart.setRegistryName("gpick_heart"));
		
		register(trunkWoodCore.setRegistryName("core_w"));
		register(trunkStoneCore.setRegistryName("core_s"));
		register(trunkIronCore.setRegistryName("core_i"));
		register(trunkGoldCore.setRegistryName("core_g"));
		register(trunkDiamondCore.setRegistryName("core_d"));
		register(branchFireCore.setRegistryName("core_fire"));
	}

	@SideOnly(Side.CLIENT)
	public static void clientInit() {
		model(trunkWood);
		model(trunkStone);
		model(trunkIron);
		model(trunkGold);
		model(trunkDiamond);
		
		model(branchFire);
		model(branchAdFire);
		model(branchStone);
		model(branchReverse);
		model(branchGlass);
		model(branchVein);
		model(branchReach);
		model(branchJump);
		model(branchFly);
		model(branchHeart);
		
		model(trunkWoodCore);
		model(trunkStoneCore);
		model(trunkIronCore);
		model(trunkGoldCore);
		model(trunkDiamondCore);
		model(branchFireCore);
	}

	public static void register(Item item) {
		ForgeRegistries.ITEMS.register(item);
	}

	public static void model(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0,
				new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

}
