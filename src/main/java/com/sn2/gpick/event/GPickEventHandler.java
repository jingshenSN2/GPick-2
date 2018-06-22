package com.sn2.gpick.event;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.realmsclient.dto.BackupList;
import com.sn2.gpick.GPick;
import com.sn2.gpick.GPickWords;
import com.sn2.gpick.config.ConfigManager;
import com.sn2.gpick.item.TrunkGPick;
import com.sn2.gpick.item.branch.AdvancedFire;
import com.sn2.gpick.item.branch.AdvancedStoneAnger;
import com.sn2.gpick.item.branch.Fire;
import com.sn2.gpick.item.branch.StoneAnger;
import com.sn2.gpick.manager.ItemManager;
import com.sn2.gpick.recipe.RecipeManager;

import ca.weblite.objc.Client;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.ChunkDataEvent.Load;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import scala.reflect.internal.Trees.New;

@EventBusSubscriber(modid = GPick.MODID)
public class GPickEventHandler {
	public static final EventBus EVENT_BUS = new EventBus();
	public static final int veinMax = 64;

	public static void init() {
		EVENT_BUS.register(GPickEventHandler.class);
	}
	
	@SubscribeEvent
	public static void checkForUpdates(PlayerLoggedInEvent event) {
		URL updateURL = null;
		BufferedReader in = null;
		String version = "";
		EntityPlayer player = event.player;
		try {
			updateURL = new URL("https://raw.githubusercontent.com/jingshenSN2/GPick-2/master/VERSION.txt");
			in = new BufferedReader(new InputStreamReader(updateURL.openStream()));
			version = in.readLine();
		} catch (Exception e) {
			player.sendMessage(new TextComponentString(GPickWords.NAME() + GPickWords.FAIL()));
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		if (!version.equals(GPick.VERSION)) {
			player.sendMessage(
					new TextComponentString(GPickWords.NAME() + GPickWords.NEW1() + version + GPickWords.NEW2()));
		} 
	}

	/**
	 * branch event for fire fired when mine a block which can be smelt chance
	 * equals to ( 0.5 * fortune level + 1 ) * baseFireChance
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public static void PlayerMiningWithFirePickaxe(HarvestDropsEvent event) {
		EntityPlayer player = event.getHarvester();
		if (player != null) {
			ItemStack hand = player.getHeldItemMainhand();
			if (hand.getItem() instanceof Fire) {
				Fire fire = (Fire) hand.getItem();
				int fuel = fire.getFuel(hand);
				if (fuel > 0) {
					hand.setTagInfo("fuel", new NBTTagInt(fuel - 1));
					for (int i = 0; i < event.getDrops().size(); i++) {
						ItemStack smelt = FurnaceRecipes.instance().getSmeltingResult(event.getDrops().get(i));
						if (smelt != ItemStack.EMPTY) {
							double random1 = Math.random();
							if (random1 < (event.getFortuneLevel() / 2.0 + 1) * ConfigManager.fireSmeltChance / 100.0) {
								event.getDrops().set(i, smelt);
								if (hand.getItemDamage() == hand.getMaxDamage())
									player.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemManager.branchFireCore));
								else
									hand.damageItem(1, player);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * branch event for advanced fire fired when mine a block which can be smelt
	 * chance equals to advanceFireChance
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public static void PlayerMiningWithAdFire(HarvestDropsEvent event) {
		EntityPlayer player = event.getHarvester();
		Block block = event.getState().getBlock();
		if (player != null) {
			ItemStack hand = player.getHeldItemMainhand();
			if (hand.getItem() instanceof AdvancedFire) {
				AdvancedFire advancedFire = (AdvancedFire) hand.getItem();
				int fuel = advancedFire.getFuel(hand);
				if (fuel > 0) {
					hand.setTagInfo("fuel", new NBTTagInt(fuel - 1));
					for (int i = 0; i < event.getDrops().size(); i++) {
						ItemStack drop = event.getDrops().get(i);
						ItemStack smelt = FurnaceRecipes.instance().getSmeltingResult(drop);
						if (smelt != ItemStack.EMPTY) {
							double random1 = Math.random();
							int fuel2 = advancedFire.getFuel(hand);
							if (fuel2 > 0) {
								if (random1 < (ConfigManager.adFireSmeltChance / 100.0)) {
									hand.setTagInfo("fuel", new NBTTagInt(fuel2 - 1));
									int fuel3 = advancedFire.getFuel(hand);
									if (fuel3 > 0) {
										ItemStack blockStack = new ItemStack(block);
										if (!blockStack.isEmpty()) {
											int[] ids = OreDictionary.getOreIDs(blockStack);
											if (ids.length != 0) {
												for (int id : ids) {
													String name = OreDictionary.getOreName(id);
													if (name.startsWith("ore")) {
														double random2 = Math.random();
														if (random2 < (ConfigManager.adFireDoubleChance / 100.0)) {
															event.getDrops().add(new ItemStack(smelt.getItem()));
															hand.setTagInfo("fuel", new NBTTagInt(fuel3 - 1));
														}
													}
												}
											}
										}
									}
									event.getDrops().set(i, smelt);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * branch event for stone anger fired when mine a stone block, eat the drops
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public static void StoneAngerMineAndEatStone(HarvestDropsEvent event) {
		EntityPlayer player = event.getHarvester();
		if (player != null) {
			ItemStack hand = player.getHeldItemMainhand();
			if (hand.getItem() instanceof StoneAnger)
				event.setDropChance(0.1F);
		}
	}

	/**
	 * branch event for advanced stone anger fired when mine a block, eat the drops
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public static void AdvancedStoneAngerMineAndEatStone(HarvestDropsEvent event) {
		EntityPlayer player = event.getHarvester();
		if (player != null) {
			ItemStack hand = player.getHeldItemMainhand();
			if (hand.getItem() instanceof AdvancedStoneAnger)
				event.setDropChance(0.1F);
		}
	}

	/**
	 * branch event for glass fired when mine a glass block, silk touch
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public static void BreakGlass(HarvestDropsEvent event) {
		EntityPlayer player = event.getHarvester();
		if (player != null) {
			ItemStack hand = player.getHeldItemMainhand();
			if (hand.getItem().equals(ItemManager.branchGlass)) {
				Block block = event.getState().getBlock();
				int[] ids = OreDictionary.getOreIDs(new ItemStack(block));
				if (ids.length != 0) {
					for (int id : ids) {
						String name = OreDictionary.getOreName(id);
						if (name.endsWith("Glass")) {
							event.getDrops().add(new ItemStack(block, 1, block.getMetaFromState(event.getState())));
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * branch event for vein mine fired when mine a vein, mine them all
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public static void veinMine(HarvestDropsEvent event) {
		EntityPlayer player = event.getHarvester();
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		if (player != null) {
			ItemStack hand = player.getHeldItemMainhand();
			if (hand.getItem().equals(ItemManager.branchVein)) {
				Block block = event.getState().getBlock();
				if (canVeinMine().contains(block)) {
					List<BlockPos> minePos = new ArrayList<>();
					minePos.add(pos);
					boolean startMine = false;
					while (!startMine) {
						int newAdd = 0;
						for (int i = 0; i < minePos.size(); i++) {
							BlockPos startPos = minePos.get(i);
							BlockPos pos000 = startPos.up().north().east();
							BlockPos pos222 = startPos.down().west().south();
							Iterable<BlockPos> test = BlockPos.getAllInBox(pos000, pos222);
							for (BlockPos testPos : test) {
								if (!minePos.contains(testPos)
										&& world.getBlockState(testPos).getBlock().equals(block)) {
									minePos.add(testPos);
									newAdd++;
								}
							}
						}
						if (newAdd == 0 || minePos.size() >= veinMax)
							startMine = true;
					}
					int totalNum = 0;
					int totalExp = 0;
					ItemStack drops = event.getDrops().get(0);
					for (BlockPos pos2 : minePos) {
						if (pos2.equals(pos))
							continue;
						IBlockState state = world.getBlockState(pos2);
						Block tempBlock = state.getBlock();
						int fortune = event.getFortuneLevel();
						Random random = new Random();
						totalNum += tempBlock.quantityDropped(state, fortune, random);
						totalExp += tempBlock.getExpDrop(state, world, pos, fortune);
						world.setBlockToAir(pos2);
					}
					for (int i = 0; i < totalNum / 64; i++)
						event.getDrops().add(new ItemStack(drops.getItem(), totalNum, drops.getMetadata()));
					event.getDrops().add(new ItemStack(drops.getItem(), totalNum % 64, drops.getMetadata()));
					block.dropXpOnBlockBreak(world, pos, totalExp);
					hand.setItemDamage(hand.getItemDamage() + minePos.size());
					minePos.clear();
				}
			}
		}
	}

	// reach
	@SubscribeEvent
	public static void fetchItem(HarvestDropsEvent event) {
		EntityPlayer player = event.getHarvester();
		World world = event.getWorld();
		if (player != null) {
			ItemStack hand = player.getHeldItemMainhand();
			if (hand.getItem().equals(ItemManager.branchReach)) {
				BlockPos pos = player.getPosition();
				List<ItemStack> drops = event.getDrops();
				for (int i = 0; i < drops.size(); i++) {
					double y = pos.getY() - 0.35D + (double) player.getEyeHeight();
					EntityItem temp = new EntityItem(world, pos.getX(), y, pos.getZ(), drops.get(i));
					temp.setPickupDelay(40);
					world.spawnEntity(temp);
				}
				drops.clear();
			}
		}
	}
	
	public static List<Block> canVeinMine() {
		List<Block> blocks = new ArrayList<>();
		blocks.add(Blocks.COAL_ORE);
		blocks.add(Blocks.DIAMOND_ORE);
		blocks.add(Blocks.EMERALD_ORE);
		blocks.add(Blocks.GOLD_ORE);
		blocks.add(Blocks.IRON_ORE);
		blocks.add(Blocks.LAPIS_ORE);
		blocks.add(Blocks.QUARTZ_ORE);
		blocks.add(Blocks.OBSIDIAN);
		blocks.add(Blocks.GLOWSTONE);
		return blocks;
	}
}
