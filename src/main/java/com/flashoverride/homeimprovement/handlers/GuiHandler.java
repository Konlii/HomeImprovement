package com.flashoverride.homeimprovement.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.flashoverride.homeimprovement.HomeImprovement;
import com.flashoverride.homeimprovement.container.ContainerCraftingSheet;
import com.flashoverride.homeimprovement.gui.GuiCraftingSheet;
import com.flashoverride.homeimprovement.tileentity.TileEntitySheet;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == HomeImprovement.guiIDCraftingSheet) {
//			System.out.println("server gui");
//			return new ContainerCraftingSheet(player.inventory, world, x, y, z);
			return new ContainerCraftingSheet(player.inventory, (TileEntitySheet)world.getTileEntity(x, y, z));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == HomeImprovement.guiIDCraftingSheet) {
//			System.out.println("client gui");
//			return new GuiCrafting(player.inventory, world, x, y, z);
			return new GuiCraftingSheet(player.inventory, (TileEntitySheet)world.getTileEntity(x, y, z));
		}
		return null;
	}

}
