package com.sn2.gpick.proxy;

import com.sn2.gpick.item.ItemManager;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ItemManager.clientInit();
	}

	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
}
