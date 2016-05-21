package com.flashoverride.homeimprovement.container;

import com.flashoverride.homeimprovement.tileentity.TileEntitySheet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class ContainerCraftingSheet extends Container
{
	  protected TileEntitySheet bench;
	  public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	  protected boolean init = false;
	  
	  public ContainerCraftingSheet(InventoryPlayer inv, TileEntitySheet tile)
	  {
	    this.bench = tile;
	    for (int i = 0; i < 9; i++) {
	      this.craftMatrix.setInventorySlotContents(i, this.bench.getStackInSlot(i));
	    }
	    this.init = true;
	    addSlotToContainer(new SlotCrafting(inv.player, this.craftMatrix, this.bench, 9, 124, 35));
	    addCraftingGrid(this.craftMatrix, 0, 30, 17, 3, 3);
	    
	    addPlayerInventory(inv);
	    
	    onCraftMatrixChanged(this.craftMatrix);
	  }
	  
	  public void onCraftMatrixChanged(IInventory inv)
	  {
	    this.bench.setInventorySlotContents(9, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.bench.getWorldObj()));
	    if (this.init) {
	      updateTile();
	    }
	  }
	  
	  public void onContainerClosed(EntityPlayer player)
	  {
	    updateTile();
	  }
	  
	  protected void updateTile()
	  {
	    for (int i = 0; i < 9; i++) {
	      this.bench.setInventorySlotContents(i, this.craftMatrix.getStackInSlot(i));
	    }
	  }
	  
	  public boolean canInteractWith(EntityPlayer p_75145_1_)
	  {
	    return true;
	  }
	  
	  public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
	  {
	    ItemStack itemstack = null;
	    Slot slot = (Slot)this.inventorySlots.get(slotNum);
	    if ((slot != null) && (slot.getHasStack()))
	    {
	      ItemStack itemstack1 = slot.getStack();
	      itemstack = itemstack1.copy();
	      if (slotNum == 0)
	      {
	        if (!mergeItemStack(itemstack1, 10, 46, true)) {
	          return null;
	        }
	        slot.onSlotChange(itemstack1, itemstack);
	      }
	      else if ((slotNum >= 10) && (slotNum < 37))
	      {
	        if (!mergeItemStack(itemstack1, 1, 10, false) && !mergeItemStack(itemstack1, 37, 46, false)) {
	          return null;
	        }
	      }
	      else if ((slotNum >= 37) && (slotNum < 46))
	      {
	        if (!mergeItemStack(itemstack1, 1, 36, false)) {
	          return null;
	        }
	      }
	      else if (!mergeItemStack(itemstack1, 10, 46, false))
	      {
	        return null;
	      }
	      if (itemstack1.stackSize == 0) {
	        slot.putStack((ItemStack)null);
	      } else {
	        slot.onSlotChanged();
	      }
	      if (itemstack1.stackSize == itemstack.stackSize) {
	        return null;
	      }
	      slot.onPickupFromSlot(player, itemstack1);
	      updateTile();
	    }
	    return itemstack;
	  }
	  
	  public void addPlayerInventory(InventoryPlayer inv)
	  {
	    addPlayerInventory(inv, 8, 84);
	  }
	  
	  public void addPlayerInventory(InventoryPlayer inv, int x, int y)
	  {
	    for (int i = 0; i < 3; i++) {
	      for (int j = 0; j < 9; j++) {
	        addSlotToContainer(new Slot(inv, j + (i + 1) * 9, x + j * 18, y + i * 18));
	      }
	    }
	    for (int i = 0; i < 9; i++) {
	      addSlotToContainer(new Slot(inv, i, 8 + i * 18, y + 58));
	    }
	  }
	  
	  public void addCraftingGrid(IInventory inventory, int startSlot, int x, int y, int width, int height)
	  {
	    int i = 0;
	    for (int h = 0; h < height; h++) {
	      for (int w = 0; w < width; w++) {
	        addSlotToContainer(new Slot(inventory, startSlot + i++, x + w * 18, y + h * 18));
	      }
	    }
	  }
	}
