package com.flashoverride.homeimprovement.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.flashoverride.homeimprovement.HomeImprovement;
import com.flashoverride.homeimprovement.block.BlockConstruct;
import com.flashoverride.homeimprovement.tileentity.TileEntityConstruct;

public class ItemPlank extends ItemHI {
	public ItemPlank() 
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		setCreativeTab(HomeImprovement.HITab);
		this.MetaNames = HomeImprovement.MATERIALS_ALL.clone();
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
	{
		boolean isConstruct = (world.getBlock(i, j, k) instanceof BlockConstruct);
		int offset = !isConstruct ? 1 : 0;
		boolean isAir = world.getBlock(i, j, k).isReplaceable(world, i, j, k);
		
		//System.out.println(isMetal);
//		System.out.println(isConstruct);

		if(!world.isRemote)
		{
//			System.out.println(is.getUnlocalizedName());
//			System.out.println(HomeImprovement.MetalConstruct.getIcon(0, is.getItemDamage()).getIconName());
			int d = TileEntityConstruct.PlankDetailLevel;
			int dd = d*d;
			int dd2 = dd*2;

			float div = 1f / d;

			int x = (int) (hitX / div);
			int y = (int) (hitY / div);
			int z = (int) (hitZ / div);

			hitX = Math.round(hitX*100)/100.0f;
			hitY = Math.round(hitY*100)/100.0f;
			hitZ = Math.round(hitZ*100)/100.0f;

			boolean isEdge = false;

			if(hitX == 0 || hitX == 1 || hitY == 0 || hitY == 1 || hitZ == 0 || hitZ == 1)
			{
				isEdge = true;
				isConstruct = true;
				offset = 1;
			}

			if(side == 0)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlock(i, j-offset, k).isReplaceable(world, i, j-offset, k))) {
					if (is.getUnlocalizedName().contains(".wood."))
					{
						world.setBlock(i, j-1, k, HomeImprovement.WoodConstruct);
					}
					else if (is.getUnlocalizedName().contains(".metal."))
					{
						world.setBlock(i, j-1, k, HomeImprovement.MetalConstruct);
					}
					else if (is.getUnlocalizedName().contains(".glass."))
					{
						world.setBlock(i, j-1, k, HomeImprovement.GlassConstruct);
					}
				}
				
				TileEntity tile = world.getTileEntity(i, j-offset, k);
				if((tile == null) || (!(tile instanceof TileEntityConstruct)))
					return false;
				int index = dd+(x+(z*d));
				TileEntityConstruct te = (TileEntityConstruct)tile;
				te.data.set(index);
				te.materialTypes[index] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", index);
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
//				System.out.println(te.materialTypes[is.getItemDamage()]);
			}
			else if(side == 1)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlock(i, j+offset, k).isReplaceable(world, i, j+offset, k))) {
					if (is.getUnlocalizedName().contains(".wood."))
					{
						world.setBlock(i, j+1, k, HomeImprovement.WoodConstruct);
					}
					else if (is.getUnlocalizedName().contains(".metal."))
					{
						world.setBlock(i, j+1, k, HomeImprovement.MetalConstruct);
					}
					else if (is.getUnlocalizedName().contains(".glass."))
					{
						world.setBlock(i, j+1, k, HomeImprovement.GlassConstruct);
					}
				}

				TileEntity tile = world.getTileEntity(i, j+offset, k);
				if((tile == null) || (!(tile instanceof TileEntityConstruct))) {
					return false;
				}
				int index = dd+(x+(z*d));
				TileEntityConstruct te = (TileEntityConstruct)tile;
				te.data.set(index);
				te.materialTypes[index] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", index);
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
//				System.out.println(te.materialTypes[is.getItemDamage()]);
			}
			else if(side == 2)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlock(i, j, k-offset).isReplaceable(world, i, j, k-offset))) {
					if (is.getUnlocalizedName().contains(".wood."))
					{
						world.setBlock(i, j, k-1, HomeImprovement.WoodConstruct);
					}
					else if (is.getUnlocalizedName().contains(".metal."))
					{
						world.setBlock(i, j, k-1, HomeImprovement.MetalConstruct);
					}
					else if (is.getUnlocalizedName().contains(".glass."))
					{
						world.setBlock(i, j, k-1, HomeImprovement.GlassConstruct);
					}
				}

				TileEntity tile = world.getTileEntity(i, j, k-offset);
				if((tile == null) || (!(tile instanceof TileEntityConstruct))) {
					return false;
				}
				int index = dd2+(x+(y*d));
				TileEntityConstruct te = (TileEntityConstruct)tile;
				te.data.set(index);
				te.materialTypes[index] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", index);
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
//				System.out.println(te.materialTypes[is.getItemDamage()]);
			}
			else if(side == 3)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlock(i, j, k+offset).isReplaceable(world, i, j, k+offset))) {
					if (is.getUnlocalizedName().contains(".wood."))
					{
						world.setBlock(i, j, k+1, HomeImprovement.WoodConstruct);
					}
					else if (is.getUnlocalizedName().contains(".metal."))
					{
						world.setBlock(i, j, k+1, HomeImprovement.MetalConstruct);
					}
					else if (is.getUnlocalizedName().contains(".glass."))
					{
						world.setBlock(i, j, k+1, HomeImprovement.GlassConstruct);
					}
				}

				TileEntity tile = world.getTileEntity(i, j, k+offset);
				if((tile == null) || (!(tile instanceof TileEntityConstruct))) {
					return false;
				}
				int index = dd2+(x+(y*d));
				TileEntityConstruct te = (TileEntityConstruct)tile;
				te.data.set(index);
				te.materialTypes[index] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", index);
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
//				System.out.println(te.materialTypes[is.getItemDamage()]);
			}
			else if(side == 4)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlock(i-offset, j, k).isReplaceable(world, i-offset, j, k))) {
					if (is.getUnlocalizedName().contains(".wood."))
					{
						world.setBlock(i-1, j, k, HomeImprovement.WoodConstruct);
					}
					else if (is.getUnlocalizedName().contains(".metal."))
					{
						world.setBlock(i-1, j, k, HomeImprovement.MetalConstruct);
					}
					else if (is.getUnlocalizedName().contains(".glass."))
					{
						world.setBlock(i-1, j, k, HomeImprovement.GlassConstruct);
					}
				}

				TileEntity tile = world.getTileEntity(i-offset, j, k);
				if((tile == null) || (!(tile instanceof TileEntityConstruct))) {
					return false;
				}
				int index = (y+(z*d));
				TileEntityConstruct te = (TileEntityConstruct)tile;
				te.data.set(index);
				te.materialTypes[index] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", index);
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
//				System.out.println(te.materialTypes[is.getItemDamage()]);
			}
			else if(side == 5)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlock(i+offset, j, k).isReplaceable(world, i+offset, j, k))) {
					if (is.getUnlocalizedName().contains(".wood."))
					{
						world.setBlock(i+1, j, k, HomeImprovement.WoodConstruct);
					}
					else if (is.getUnlocalizedName().contains(".metal."))
					{
						world.setBlock(i+1, j, k, HomeImprovement.MetalConstruct);
					}
					else if (is.getUnlocalizedName().contains(".glass."))
					{
						world.setBlock(i+1, j, k, HomeImprovement.GlassConstruct);
					}
				}

				TileEntity tile = world.getTileEntity(i+offset, j, k);
				if((tile == null) || (!(tile instanceof TileEntityConstruct))) {
					return false;
				}
				int index = (y+(z*d));
				TileEntityConstruct te = (TileEntityConstruct)tile;
				te.data.set(index);
				te.materialTypes[index] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", index);
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
//				System.out.println(te.materialTypes[is.getItemDamage()]);
			}
//            world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), HomeImprovement.WoodConstruct.stepSound.func_150496_b(), (HomeImprovement.WoodConstruct.stepSound.getVolume() + 1.0F) / 2.0F, HomeImprovement.WoodConstruct.stepSound.getPitch() * 0.8F);
            world.playSoundEffect((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), HomeImprovement.WoodConstruct.stepSound.func_150496_b(), (HomeImprovement.WoodConstruct.stepSound.getVolume() + 1.0F) / 2.0F, HomeImprovement.WoodConstruct.stepSound.getPitch() * 0.8F);
			is.stackSize--;
			return true;
		}
		return false;
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
		for(int i = 0; i < HomeImprovement.MATERIALS_ALL.length-1; i++) {
			if (HomeImprovement.MATERIALS_ALL[i].startsWith("wood"))
			{
				icons[i] = registerer.registerIcon(HomeImprovement.MATERIALS_ALL[i].replace("wood.", HomeImprovement.MODID + ":wood/") + "_plank");
			}
			else if (HomeImprovement.MATERIALS_ALL[i].startsWith("metal"))
			{
				icons[i] = registerer.registerIcon(HomeImprovement.MATERIALS_ALL[i].replace("metal.", HomeImprovement.MODID + ":metal/") + "_bar");
			}
			else if (HomeImprovement.MATERIALS_ALL[i].startsWith("glass"))
			{
				icons[i] = registerer.registerIcon(HomeImprovement.MATERIALS_ALL[i].replace("glass.", HomeImprovement.MODID + ":glass/") + "_glass_bar");
			}
		}
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < HomeImprovement.MATERIALS_ALL.length-1; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
