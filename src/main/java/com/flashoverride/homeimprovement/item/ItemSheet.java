package com.flashoverride.homeimprovement.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.flashoverride.homeimprovement.HomeImprovement;
import com.flashoverride.homeimprovement.block.BlockSheet;
import com.flashoverride.homeimprovement.tileentity.TileEntitySheet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSheet extends ItemHI
{
	protected int[][] sidesMap = new int[][]{{0,-1,0},{0,1,0},{0,0,-1},{0,0,+1},{-1,0,0},{1,0,0}};

	public ItemSheet()
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.setCreativeTab(HomeImprovement.HITab);
		setFolder("");
		this.MetaNames = HomeImprovement.MATERIALS_ALL.clone();
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		boolean isSuccessful = false;
		if(!world.isRemote)
		{
			if(itemstack.hasTagCompound())
				return false;
			TileEntitySheet te = null;
			int[] sides = sidesMap[side];
			Block currentBlock = world.getBlock(sides[0] + x, sides[1] + y, sides[2] + z);
			int newSide = side;
			boolean isNewCraftingSheet = false;
		
			if(world.getBlock(x, y, z) instanceof BlockSheet)
			{
				te = (TileEntitySheet)world.getTileEntity(x, y, z);
				
				if (hitX == 0.00F || hitX == 1.00F || hitY == 0.00F || hitY == 1.00F || hitZ == 0.00F || hitZ == 1.00F)
				{
					isSuccessful = makeCraftingSheet(itemstack, entityplayer, world, x, y, z, side, hitX, hitY, hitZ);
					isNewCraftingSheet = true;
				}
				else if (!te.BottomExists() && 0.00F <  hitY && hitY < 0.1875F)
				{
					newSide = 0;
					te.toggleBySide(newSide, true);
					isSuccessful = true;
				}
				else if (!te.TopExists() && 0.8125F <  hitY && hitY < 1.00F)
				{
					newSide = 1;
					te.toggleBySide(newSide, true);
					isSuccessful = true;
				}
				else if (!te.NorthExists() && 0.00F < hitZ && hitZ < 0.1875F)
				{
					newSide = 2;
					te.toggleBySide(newSide, true);
					isSuccessful = true;
				}
				else if (!te.SouthExists() && 0.8125F < hitZ && hitZ < 1.00F)
				{
					newSide = 3;
					te.toggleBySide(newSide, true);
					isSuccessful = true;
				}
				else if (!te.EastExists() && 0.00F < hitX && hitX < 0.1875F)
				{
					newSide = 4;
					te.toggleBySide(newSide, true);
					isSuccessful = true;
				}
				else if (!te.WestExists() && 0.8125F < hitX && hitX < 1.00F)
				{
					newSide = 5;
					te.toggleBySide(newSide, true);
					isSuccessful = true;
				}

				else
				{
				switch(side)
				{
				case 0:
					if (!te.BottomExists())
					{
						te.toggleBySide(side, true);
						isSuccessful = true;
					}
					else if (!te.TopExists())
					{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						isSuccessful = true;
					}
					break;
					
				case 1:
					if(!te.TopExists())
					{
						te.toggleBySide(side, true);
						isSuccessful = true;
					}
					else if (!te.BottomExists())
					{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						isSuccessful = true;
					}
					break;

				case 2:
					if(!te.NorthExists())
					{
						te.toggleBySide(side, true);
						isSuccessful = true;
					}
					else if (!te.SouthExists())
					{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						isSuccessful = true;
					}
					break;

				case 3:
					if(!te.SouthExists())
					{
						te.toggleBySide(side, true);
						isSuccessful = true;
					}
					else if (!te.NorthExists())
					{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						isSuccessful = true;
					}
					break;

				case 4:
					if(!te.EastExists())
					{
						te.toggleBySide(side, true);
						isSuccessful = true;
					}
					else if (!te.WestExists())
					{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						isSuccessful = true;
					}
					break;

				case 5:
					if(!te.WestExists())
					{
						te.toggleBySide(side, true);
						isSuccessful = true;
					}
					else if (!te.EastExists())
					{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						isSuccessful = true;
					}
					break;
				}
				}
/*				te.data.set(side);
				te.materialTypes[side] = (byte) itemstack.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", side);
				nbt.setByte("meta", (byte) itemstack.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
				
				System.out.println(side);*/
				if (!isNewCraftingSheet && isSuccessful)
				{
				te.data.set(newSide);
				te.materialTypes[newSide] = (byte) itemstack.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", newSide);
				nbt.setByte("meta", (byte) itemstack.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
				
				
//				System.out.println(newSide);
				}
			}
			else if(isValid(world, sides[0] + x, sides[1] + y, sides[2] + z))
			{
				if(!(world.getBlock(x, y, z) instanceof BlockSheet))
				{
					if (!(world.getBlock(sides[0] + x, sides[1] + y, sides[2] + z) instanceof BlockSheet))
					{
						if (itemstack.getUnlocalizedName().contains(".wood."))
						{
							world.setBlock( sides[0] + x, sides[1] + y, sides[2] + z, HomeImprovement.WoodSheet);
						}
						else if (itemstack.getUnlocalizedName().contains(".metal."))
						{
							world.setBlock( sides[0] + x, sides[1] + y, sides[2] + z, HomeImprovement.MetalSheet);
						}
						else if (itemstack.getUnlocalizedName().contains(".glass."))
						{
							world.setBlock( sides[0] + x, sides[1] + y, sides[2] + z, HomeImprovement.GlassSheet);
						}
					}
					te = (TileEntitySheet)world.getTileEntity( sides[0] + x, sides[1] + y, sides[2] + z);
					
					te.sheetStack = itemstack.copy();
					te.sheetStack.stackSize = 1;
					
					if (!te.BottomExists() && 0.00F <  hitY && hitY < 0.1875F)
					{
						newSide = 0;
						te.toggleBySide(newSide, true);
					}
					else if (!te.TopExists() && 0.8125F <  hitY && hitY < 1.00F)
					{
						newSide = 1;
						te.toggleBySide(newSide, true);
					}
					else if (!te.NorthExists() && 0.00F < hitZ && hitZ < 0.1875F)
					{
						newSide = 2;
						te.toggleBySide(newSide, true);
					}
					else if (!te.SouthExists() && 0.8125F < hitZ && hitZ < 1.00F)
					{
						newSide = 3;
						te.toggleBySide(newSide, true);
					}
					else if (!te.EastExists() && 0.00F < hitX && hitX < 0.1875F)
					{
						newSide = 4;
						te.toggleBySide(newSide, true);
					}
					else if (!te.WestExists() && 0.8125F < hitX && hitX < 1.00F)
					{
						newSide = 5;
						te.toggleBySide(newSide, true);
					}
					else
						{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						}
					
					isSuccessful = true;
				}
/*				te.data.set(side);
				te.materialTypes[side] = (byte) itemstack.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", side);
				nbt.setByte("meta", (byte) itemstack.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
				
				System.out.println(side);*/

				if (isSuccessful)
				{
				te.data.set(newSide);
				te.materialTypes[newSide] = (byte) itemstack.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", newSide);
				nbt.setByte("meta", (byte) itemstack.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
				}
				
//				System.out.println(newSide);

			}
			else
			{
				isSuccessful = false;
			}

			
			if(isSuccessful)
			{
/*				int d = 8;
				int dd = d*d;
				int dd2 = dd*2;

				float div = 1f / d;

				int i = (int) (hitX / div);
				int j = (int) (hitY / div);
				int k = (int) (hitZ / div);

				int index = side;

				te.data.set(newSide);
				te.materialTypes[newSide] = (byte) itemstack.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", newSide);
				nbt.setByte("meta", (byte) itemstack.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
				
				
				System.out.println(newSide);*/
				
				if (itemstack.getUnlocalizedName().contains(".wood."))
				{
					world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), HomeImprovement.WoodSheet.stepSound.func_150496_b(), (HomeImprovement.WoodSheet.stepSound.getVolume() + 1.0F) / 2.0F, HomeImprovement.WoodSheet.stepSound.getPitch() * 0.8F);
				}
				else if (itemstack.getUnlocalizedName().contains(".metal."))
				{
					world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), HomeImprovement.MetalSheet.stepSound.func_150496_b(), (HomeImprovement.MetalSheet.stepSound.getVolume() + 1.0F) / 2.0F, HomeImprovement.MetalSheet.stepSound.getPitch() * 0.8F);
				}
				else if (itemstack.getUnlocalizedName().contains(".glass."))
				{
					world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), HomeImprovement.GlassSheet.stepSound.func_150496_b(), (HomeImprovement.GlassSheet.stepSound.getVolume() + 1.0F) / 2.0F, HomeImprovement.GlassSheet.stepSound.getPitch() * 0.8F);
				}
				itemstack.stackSize--;
			}
/*			
			System.out.println(x + ", " + y + ", " + z);
			System.out.println("hitX: " + hitX  + ", hitY: " + hitY + ", hitZ: " + hitZ);
			System.out.println("side: " + side);
			System.out.println("block: " + world.getBlock(x, + y, + z).getLocalizedName());
*/			
		}
		return isSuccessful;
	}
	public int flipSide(int side)
	{
		switch(side)
		{
		case 0: return 1;
		case 1: return 0;
		case 2: return 3;
		case 3: return 2;
		case 4: return 5;
		case 5: return 4;
		}
		return 0;
	}

	public boolean isValid(World world, int i, int j, int k)
	{
		Block bid = world.getBlock(i, j, k);
		if (bid.isReplaceable(world, i, j, k))
			return true;
		if(bid instanceof BlockSheet)
//		if(bid instanceof BlockHIContainer)
		{
//			TileEntitySheet te = (TileEntitySheet)world.getTileEntity(i, j, k);
//			if(te.metalID == this.metalID)
				return true;
		}
		return false;
	}
	
	public boolean makeCraftingSheet (ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		boolean isSuccessful = false;
		if(!world.isRemote)
		{
//			System.out.println("makecraftingsheet");
			if(itemstack.hasTagCompound())
				return false;
			TileEntitySheet te = null;
			int[] sides = sidesMap[side];
			int newSide = side;
		
			if(world.getBlock(sides[0] + x, sides[1] + y, sides[2] + z) instanceof BlockSheet)
			{
				te = (TileEntitySheet)world.getTileEntity(sides[0] + x, sides[1] + y, sides[2] + z);
				
				if (!te.BottomExists() && 0.00F <  hitY && hitY < 0.1875F)
				{
					newSide = 0;
					te.toggleBySide(0, true);
					isSuccessful = true;
				}
				else if (!te.TopExists() && 0.8125F <  hitY && hitY < 1.00F)
				{
					newSide = 1;
					te.toggleBySide(1, true);
					isSuccessful = true;
				}
				else if (!te.NorthExists() && 0.00F < hitZ && hitZ < 0.1875F)
				{
					newSide = 2;
					te.toggleBySide(2, true);
					isSuccessful = true;
				}
				else if (!te.SouthExists() && 0.8125F < hitZ && hitZ < 1.00F)
				{
					newSide = 3;
					te.toggleBySide(3, true);
					isSuccessful = true;
				}
				else if (!te.EastExists() && 0.00F < hitX && hitX < 0.1875F)
				{
					newSide = 4;
					te.toggleBySide(4, true);
					isSuccessful = true;
				}
				else if (!te.WestExists() && 0.8125F < hitX && hitX < 1.00F)
				{
					newSide = 5;
					te.toggleBySide(5, true);
					isSuccessful = true;
				}
				else
				{
				switch(flipSide(side))
				
				{
				case 0:
					if (!te.BottomExists())
					{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						isSuccessful = true;
					}
					break;
					
				case 1:
					if(!te.TopExists())
					{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						isSuccessful = true;
					}
					break;

				case 2:
					if(!te.NorthExists())
					{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						isSuccessful = true;
					}
					break;

				case 3:
					if(!te.SouthExists())
					{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						isSuccessful = true;
					}
					break;

				case 4:
					if(!te.EastExists())
					{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						isSuccessful = true;
					}
					break;

				case 5:
					if(!te.WestExists())
					{
						newSide = flipSide(side);
						te.toggleBySide(flipSide(side), true);
						isSuccessful = true;
					}
					break;
				}
				}
			}
			else if(isValid(world, sides[0] + x, sides[1] + y, sides[2] + z))
			{
				if (!(world.getBlock(sides[0] + x, sides[1] + y, sides[2] + z) instanceof BlockSheet))
				{
					if (itemstack.getUnlocalizedName().contains(".wood."))
					{
						world.setBlock( sides[0] + x, sides[1] + y, sides[2] + z, HomeImprovement.WoodSheet);
					}
					else if (itemstack.getUnlocalizedName().contains(".metal."))
					{
						world.setBlock( sides[0] + x, sides[1] + y, sides[2] + z, HomeImprovement.MetalSheet);
					}
					else if (itemstack.getUnlocalizedName().contains(".glass."))
					{
						world.setBlock( sides[0] + x, sides[1] + y, sides[2] + z, HomeImprovement.GlassSheet);
					}
				}
					te = (TileEntitySheet)world.getTileEntity( sides[0] + x, sides[1] + y, sides[2] + z);
					te.sheetStack = itemstack.copy();
					te.sheetStack.stackSize = 1;
					
					if (!te.BottomExists() && 0.00F <  hitY && hitY < 0.1875F)
					{
						newSide = 0;
						te.toggleBySide(0, true);
					}
					else if (!te.TopExists() && 0.8125F <  hitY && hitY < 1.00F)
					{
						newSide = 1;
						te.toggleBySide(1, true);
					}
					else if (!te.NorthExists() && 0.00F < hitZ && hitZ < 0.1875F)
					{
						newSide = 2;
						te.toggleBySide(2, true);
					}
					else if (!te.SouthExists() && 0.8125F < hitZ && hitZ < 1.00F)
					{
						newSide = 3;
						te.toggleBySide(3, true);
					}
					else if (!te.EastExists() && 0.00F < hitX && hitX < 0.1875F)
					{
						newSide = 4;
						te.toggleBySide(4, true);
					}
					else if (!te.WestExists() && 0.8125F < hitX && hitX < 1.00F)
					{
						newSide = 5;
						te.toggleBySide(5, true);
					}
					else
					{
						newSide = flipSide(side);
					te.toggleBySide(flipSide(side), true);
					}
					isSuccessful = true;
			}
			else
			{
				isSuccessful = false;
			}
			
			if (isSuccessful)
			{
				te.data.set(newSide);
				te.materialTypes[newSide] = (byte) itemstack.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", newSide);
				nbt.setByte("meta", (byte) itemstack.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
				
				
//				System.out.println(newSide);

			}
		}
		return isSuccessful;
	}
	
	@Override
	public int getMetadata(int i) 
	{
		return i;
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}

	IIcon[] icons = new IIcon[HomeImprovement.MATERIALS_ALL.length];
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < HomeImprovement.MATERIALS_ALL.length; i++) {
			if (HomeImprovement.MATERIALS_ALL[i].startsWith("wood"))
			{
				icons[i] = registerer.registerIcon(HomeImprovement.MATERIALS_ALL[i].replace("wood.", HomeImprovement.MODID + ":wood/").replace("metal.", HomeImprovement.MODID + ":metal/") + "_sheet");
			}
			else if (HomeImprovement.MATERIALS_ALL[i].startsWith("metal"))
			{
				icons[i] = registerer.registerIcon(HomeImprovement.MATERIALS_ALL[i].replace("metal.", HomeImprovement.MODID + ":metal/") + "_sheet");
			}
			else if (HomeImprovement.MATERIALS_ALL[i].startsWith("glass"))
			{
				icons[i] = registerer.registerIcon(HomeImprovement.MATERIALS_ALL[i].replace("glass.", HomeImprovement.MODID + ":glass/") + "_glass_sheet");
			}
		}
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < HomeImprovement.MATERIALS_ALL.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
