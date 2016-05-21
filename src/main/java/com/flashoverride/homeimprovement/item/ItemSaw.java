package com.flashoverride.homeimprovement.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.flashoverride.homeimprovement.HomeImprovement;

public class ItemSaw extends ItemAxe {

	public ItemSaw(String unlocalizedName, ToolMaterial toolMaterial) {
		super(toolMaterial);
		this.setUnlocalizedName(unlocalizedName);
		this.setTextureName(HomeImprovement.MODID + ":tools/" + unlocalizedName);
		setCreativeTab(HomeImprovement.HITab);
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000; // however long your item can be used, in ticks
    }
	
    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.block;
    }
	
}
