package com.flashoverride.homeimprovement.tileentity;

import java.util.ArrayList;
import java.util.BitSet;

import com.flashoverride.homeimprovement.HomeImprovement;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntitySheet extends NetworkTileEntity implements IInventory
{
	public ItemStack sheetStack;
	byte sides = 0;
	protected ItemStack[] slots;
	public BitSet data;
	public byte[] materialTypes = new byte[192];

	public TileEntitySheet()
	{
		this.slots = new ItemStack[10];
		data = new BitSet(192);
	}

	public void clearSides()
	{
		sides = 0;
	}

	public void clearContents()
	{
	    if (this.slots.length > 0)
	    {
	      for (int i = 0; i < this.slots.length; i++) {
	    	  this.slots[i] = null;
	      }
	    }
	}
	public boolean TopExists()
	{
		return (sides & 1) > 0;
	}

	public boolean BottomExists()
	{
		return (sides & 2) > 0;
	}

	public boolean NorthExists()
	{
		return (sides & 4) > 0;
	}

	public boolean SouthExists()
	{
		return (sides & 8) > 0;
	}

	public boolean EastExists()
	{
		return (sides & 16) > 0;
	}

	public boolean WestExists()
	{
		return (sides & 32) > 0;
	}
	
	public void toggleBySide(int side, boolean setOn)
	{
		switch(side)
		{
		case 0:toggleBottom(setOn);break;
		case 1:toggleTop(setOn);break;
		case 2:toggleNorth(setOn);break;
		case 3:toggleSouth(setOn);break;
		case 4:toggleEast(setOn);break;
		case 5:toggleWest(setOn);break;
		}
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void toggleTop(boolean setOn)
	{
		if(TopExists() && !setOn)
			sides -= 1;
		else
			sides += 1;
	}

	public void toggleBottom(boolean setOn)
	{
		if(BottomExists() && !setOn)
			sides -= 2;
		else
			sides += 2;
	}

	public void toggleNorth(boolean setOn)
	{
		if(NorthExists() && !setOn)
			sides -= 4;
		else
			sides += 4;
	}

	public void toggleSouth(boolean setOn)
	{
		if(SouthExists() && !setOn)
			sides -= 8;
		else
			sides += 8;
	}

	public void toggleEast(boolean setOn)
	{
		if(EastExists() && !setOn)
			sides -= 16;
		else
			sides += 16;
	}

	public void toggleWest(boolean setOn)
	{
		if(WestExists() && !setOn)
			sides -= 32;
		else
			sides += 32;
	}

	public boolean isEmpty()
	{
		if(!TopExists() && !BottomExists() && !NorthExists() && !SouthExists() && !EastExists() && !WestExists())
			return true;
		return false;
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		materialTypes = nbt.getByteArray("materialTypes");
		data = new BitSet(192);
		data.or(fromByteArray(nbt.getByteArray("data")));

		sheetStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("sheetType"));
		sides = nbt.getByte("sides");
		
	    if (nbt.hasKey("Contents"))
	    {
	      NBTTagList contents = nbt.getTagList("Contents", 10);
	      for (int i = 0; i < contents.tagCount(); i++)
	      {
	        NBTTagCompound tag = contents.getCompoundTagAt(i);
	        byte slot = tag.getByte("Slot");
	        if (slot < this.slots.length) {
	          this.slots[slot] = ItemStack.loadItemStackFromNBT(tag);
	        }
	      }
	    }
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setByteArray("materialTypes", materialTypes);
		nbt.setByteArray("data", toByteArray(data));

		nbt.setByte("sides", sides);
		NBTTagCompound st = new NBTTagCompound();
		if(sheetStack != null)
			sheetStack.writeToNBT(st);
		nbt.setTag("sheetType", st);
		
		
		
		
	    if (this.slots.length > 0)
	    {
	      NBTTagList contents = new NBTTagList();
	      for (int i = 0; i < this.slots.length; i++) {
	        if (this.slots[i] != null)
	        {
	          ItemStack stack = this.slots[i];
	          NBTTagCompound tag = new NBTTagCompound();
	          tag.setByte("Slot", (byte)i);
	          stack.writeToNBT(tag);
	          contents.appendTag(tag);
	        }
	      }
	      nbt.setTag("Contents", contents);
	    }
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		sides = nbt.getByte("sides");
		materialTypes = nbt.getByteArray("materialTypes");
		data = new BitSet(192);
		data.or(fromByteArray(nbt.getByteArray("data")));
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) {
		int index = nbt.getInteger("index");
		byte meta = nbt.getByte("meta");
		this.data.flip(index);
		materialTypes[index] = meta;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) {
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setByte("sides", this.sides);
		nbt.setByteArray("materialTypes", materialTypes);
		nbt.setByteArray("data", toByteArray(data));
	}

	
	
	
	
	
	
	
	public void ejectContents()
	{
		for(int i = 0; i < 192; i++)
		{
			if(data.get(i))
			{
				data.clear(i);
				ItemStack stack = new ItemStack(HomeImprovement.Sheet, 1, materialTypes[i]);
				EntityItem e = new EntityItem(worldObj, xCoord, yCoord, zCoord, stack);
				e.delayBeforeCanPickup = 5;
				worldObj.spawnEntityInWorld(e);
			}
		}
	}

	public ArrayList<ItemStack> getDrops()
	{
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		for(int i = 0; i < 192; i++)
		{
			if(data.get(i))
			{
				ItemStack stack = new ItemStack(HomeImprovement.Sheet, 1, materialTypes[i]);
				list.add(stack);
//				System.out.println(materialTypes[i]);
			}
		}
		return list;
	}

	public static BitSet fromByteArray(byte[] bytes)
	{
		BitSet bits = new BitSet(192);
		for (int i = 0; i < bytes.length * 8; i++)
		{
			if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0)
				bits.set(i);
		}
		return bits;
	}

	public static byte[] toByteArray(BitSet bits)
	{
		byte[] bytes = new byte[bits.length() / 8 + 1];
		for (int i=0; i < bits.length(); i++)
		{
			if (bits.get(i))
				bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
		}
		return bytes;
	}
	
	
	

	  

	  
	  public int getSizeInventory()
	  {
	    return this.slots.length;
	  }
	  
	  public ItemStack getStackInSlot(int slot)
	  {
	    return this.slots[slot];
	  }
	  
	  public ItemStack decrStackSize(int slot, int amt)
	  {
		    if (this.slots[slot] != null)
		    {
		      ItemStack newStack;
		      if (this.slots[slot].stackSize <= amt)
		      {
		        newStack = this.slots[slot];
		        this.slots[slot] = null;
		      }
		      else
		      {
		        newStack = this.slots[slot].splitStack(amt);
		        if (this.slots[slot].stackSize == 0) {
		          this.slots[slot] = null;
		        }
		      }
		      return newStack;
		    }
		    return null;
		  }
	  
	  public ItemStack getStackInSlotOnClosing(int slot)
	  {
	    if (this.slots[slot] != null)
	    {
	      ItemStack stack = this.slots[slot];
	      this.slots[slot] = null;
	      return stack;
	    }
	    return null;
	  }
	  
	  public void setInventorySlotContents(int slot, ItemStack stack)
	  {
	    this.slots[slot] = stack;
	    if ((stack != null) && (stack.stackSize > getInventoryStackLimit())) {
	      stack.stackSize = getInventoryStackLimit();
	    }
	  }
	  
	  public String getInventoryName()
	  {
	    return null;
	  }
	  
	  public boolean isInventoryNameLocalized()
	  {
	    return false;
	  }
	  
	  public int getInventoryStackLimit()
	  {
	    return 64;
	  }
	  
	  public boolean isUseableByPlayer(EntityPlayer player)
	  {
	    return (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this) && (player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) < 64.0D);
	  }
	  
	  
	  public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	  {
	    if (slot == 9) {
	      return false;
	    }
	    return true;
	  }

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}
}
