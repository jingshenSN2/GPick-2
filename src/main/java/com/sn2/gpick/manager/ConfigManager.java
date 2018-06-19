package com.sn2.gpick.manager;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigManager {
	private static Configuration config;

    private static Logger logger;
    
    public static boolean antiCheat;
    
    public ConfigManager(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();
        load();
    }

    public static void load()
    {
        logger.info("Started loading config. ");
        String comment;

        comment = "Enable anti-cheat mode(NOT FINISH YET)";
        antiCheat = config.get(Configuration.CATEGORY_GENERAL, "antiCheat", true, comment).getBoolean();

        config.save();
        logger.info("Finished loading config. ");
    }

    public static Logger logger()
    {
        return logger;
    }
}
