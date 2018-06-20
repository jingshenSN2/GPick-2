package com.sn2.gpick;

import org.apache.logging.log4j.Logger;

import com.sn2.gpick.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import scala.annotation.meta.param;

@Mod(modid = GPick.MODID, name = GPick.NAME, version = GPick.VERSION)
public class GPick {
	public static final String MODID = "gpick";
	public static final String NAME = "Gpick2";
	public static final String VERSION = "3.2a";
	public static final String MINECOUNT = "MineSoul: ";
	public static final String FLUE = "Fuel: ";
	public static final String POS = "Last position: ";

	@SidedProxy(clientSide = "com.sn2.gpick.proxy.ClientProxy", serverSide = "com.sn2.gpick.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Instance(MODID)
	public static GPick instance;

	private Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	public Logger getLogger() {
		return logger;
	}
}
