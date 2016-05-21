package com.flashoverride.homeimprovement.handlers;

import com.flashoverride.homeimprovement.item.ItemHI;
import com.flashoverride.homeimprovement.item.ItemSaw;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingHandler
{
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent e)
	{
		Item item = e.crafting.getItem();
		IInventory craftMatrix = e.craftMatrix;

		if(craftMatrix != null)
		{
			if(item instanceof ItemHI)
			{
/*				if (e.player.capabilities.isCreativeMode)
					e.crafting.setItemDamage(0);
	*/			
				for(int i = 0; i < craftMatrix.getSizeInventory(); i++)
				{
					if(craftMatrix.getStackInSlot(i) == null)
						continue;

						if((craftMatrix.getStackInSlot(i).getItem() instanceof ItemSaw))
						{
							ItemStack saw = craftMatrix.getStackInSlot(i).copy();
							if(saw != null)
							{
								saw.damageItem(1, e.player);
								if(saw.getItemDamage() != 0 || e.player.capabilities.isCreativeMode)
								{
									craftMatrix.setInventorySlotContents(i, saw);
									craftMatrix.getStackInSlot(i).stackSize = 2;
								}
							}
					}
				}
			}
		}
	}

}