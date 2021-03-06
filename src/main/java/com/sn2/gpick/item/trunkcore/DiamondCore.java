package com.sn2.gpick.item.trunkcore;

import java.util.List;

import javax.annotation.Nullable;

import com.sn2.gpick.GPickWords;
import com.sn2.gpick.item.TrunkCore;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class DiamondCore extends TrunkCore {
	public DiamondCore() {
		this.setUnlocalizedName("core(diamond)");
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (worldIn != null) {
			tooltip.add(GPickWords.COREINFO1() + I18n.translateToLocal("item.gpick(diamond).name") + GPickWords.COREINFO2());
		}
	}
}
