package com.sn2.gpick.creative;

import com.sn2.gpick.manager.ItemManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeBranch extends CreativeTabs {
	public static CreativeBranch branch = new CreativeBranch();
    
    public CreativeBranch()
    {
        super("gpickbranch");
    }

    public ItemStack getTabIconItem()
    {
        return new ItemStack(ItemManager.branchVein);
    }
}
