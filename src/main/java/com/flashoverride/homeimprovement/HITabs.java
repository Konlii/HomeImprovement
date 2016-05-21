package com.flashoverride.homeimprovement;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HITabs extends CreativeTabs
{
	public HITabs(int id, String unlocalizedName) {
		 
        super(id, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return HomeImprovement.SinglePlank;
	}
}