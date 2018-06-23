package com.sn2.gpick.proxy;

import com.sn2.gpick.config.ConfigManager;
import com.sn2.gpick.item.ItemManager;
import com.sn2.gpick.recipe.RecipeManager;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ItemManager.init();
		new ConfigManager(event);
	}

	public void init(FMLInitializationEvent event) {
		RecipeManager.init();
	}

}
