package com.sn2.gpick.config;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigManager {
	private static Configuration config;

    private static Logger logger;
    
    public static boolean allowFire;
    public static boolean allowAdFire;
    public static boolean allowStoneAnger;
    public static boolean allowReverse;
    public static boolean allowGlass;
    public static boolean allowVein;
    public static boolean allowReach;
    public static boolean allowJump;
    public static boolean allowFly;
    public static boolean allowHeart;
    
    
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
        String crafting = "crafting";
        config.setCategoryComment(crafting, "enable craft branch gpicks");
        
        allowFire = config.get(crafting, "fire", true).getBoolean();
        allowAdFire = config.get(crafting, "advancedfire", true).getBoolean();
        allowStoneAnger = config.get(crafting, "stoneanger", true).getBoolean();
        allowReverse = config.get(crafting, "reverse", true).getBoolean();
        allowGlass = config.get(crafting, "glass", true).getBoolean();
        allowVein = config.get(crafting, "vein", true).getBoolean();
        allowReach = config.get(crafting, "reach", true).getBoolean();
        allowJump = config.get(crafting, "jump", true).getBoolean();
        allowFly = config.get(crafting, "fly", true).getBoolean();
        allowHeart = config.get(crafting, "heart", true).getBoolean();
        
        config.save();
        logger.info("Finished loading config. ");
    }

    public static Logger logger()
    {
        return logger;
    }
}
