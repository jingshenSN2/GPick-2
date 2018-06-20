package com.sn2.gpick.item.trunkcore;

import java.util.List;

import javax.annotation.Nullable;

import com.sn2.gpick.GPickWords;
import com.sn2.gpick.item.TrunkCore;
import com.sn2.gpick.material.MaterialManager;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class WoodCore extends TrunkCore{
	public WoodCore() {
		this.setUnlocalizedName("core(wood)");
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (worldIn != null) {
			tooltip.add(GPickWords.COREINFO1() + I18n.translateToLocal("item.gpick(wood).name") + GPickWords.COREINFO2());
		}
	}
}
