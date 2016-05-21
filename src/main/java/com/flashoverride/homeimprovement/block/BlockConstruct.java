package com.flashoverride.homeimprovement.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.flashoverride.homeimprovement.CollisionRayTraceStandard;
import com.flashoverride.homeimprovement.HomeImprovement;
import com.flashoverride.homeimprovement.interfaces.ICustomCollision;
import com.flashoverride.homeimprovement.tileentity.TileEntitySheet;
import com.flashoverride.homeimprovement.tileentity.TileEntityConstruct;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConstruct extends BlockHIContainer implements ICustomCollision
{
	public BlockConstruct(Material material)
	{
		super(material);
		setBlockBounds(0.0F, 0.0F, 0.0f, 0.0f, 0.0F, 0.0F);
		if (material == Material.wood)
		{
			setHarvestLevel("axe", 0);
		}
		else if (material == Material.iron)
		{
			setHarvestLevel("pickaxe", 0);
		}
	}
	
    private IIcon[] icon = new IIcon[HomeImprovement.MATERIALS_ALL.length];


	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemById(0);
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		//TODO: Make the block hardness depend on the panels in this block.
		return this.blockHardness;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityConstruct();
	}

	@Override
	public int getRenderType()
	{
		return HomeImprovement.constructRenderId;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
	{
		return true;
	}

    @Override
	public void registerBlockIcons(IIconRegister iconRegister)
    {
        for (int i = 0; i < this.icon.length; ++i)
        {
        	if (HomeImprovement.MATERIALS_ALL[i].toString().endsWith("crafting"))
        	{
        		this.icon[i] = iconRegister.registerIcon("minecraft:crafting_table_top");
        	}
        	else if (HomeImprovement.MATERIALS_ALL[i].toString().startsWith("wood"))
        	{
        		this.icon[i] = iconRegister.registerIcon(HomeImprovement.MATERIALS_ALL[i].replace("wood.", "minecraft:planks_"));
        	}
        	else if (HomeImprovement.MATERIALS_ALL[i].toString().startsWith("metal"))
        	{
        		this.icon[i] = iconRegister.registerIcon(HomeImprovement.MATERIALS_ALL[i].replace("metal.", "minecraft:") + "_block");
        	}
        	else if (HomeImprovement.MATERIALS_ALL[i].toString().startsWith("glass"))
        	{
        		if (HomeImprovement.MATERIALS_ALL[i].toString().endsWith("glass"))
        		{
        			this.icon[i] = iconRegister.registerIcon(HomeImprovement.MATERIALS_ALL[i].replace("glass.", "minecraft:"));
        		}
        		else
        		{
        			this.icon[i] = iconRegister.registerIcon(HomeImprovement.MATERIALS_ALL[i].replace("glass.", "minecraft:glass_"));
        		}
        	}
       }
    }

    @Override
    public IIcon getIcon(int i, int j)
    {
        return this.icon[j];
    }

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		if(!world.isRemote && (TileEntityConstruct)world.getTileEntity(x, y, z)!=null)
		{
			TileEntityConstruct te = (TileEntityConstruct)world.getTileEntity(x, y, z);
			ret = te.getDrops();
		}
		return ret;
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) 
	{
		TileEntityConstruct te = (TileEntityConstruct)world.getTileEntity(x, y, z);
		
		if (!player.capabilities.isCreativeMode) {
		ArrayList<ItemStack> out = this.getDrops(world, x, y, z, meta, 0);
		for(ItemStack is : out)
		{
			world.spawnEntityInWorld(new EntityItem(world, x+0.5, y+0.5, z+0.5, is));
		}
		}
//		if (te != null) breakCraftingSheet(world, x, y, z);
	}

	@Override
	public boolean canDropFromExplosion(Explosion ex)
	{
		return true;
	}

/*	@Override
	public void onBlockExploded(World world, int i, int j, int k, Explosion explosion)
	{
		TileEntityConstruct te = (TileEntityConstruct)world.getTileEntity(i, j, k);
		if ( te != null )
			te.clearSides();

		super.onBlockExploded(world, i, j, k, explosion);
	}*/

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity)
	{
		ArrayList<Object[]> alist = new ArrayList<Object[]>();
		addCollisionBoxesToList(world, i, j, k, alist);
		for(Object[] obj : alist)
		{
			AxisAlignedBB plankAABB = (AxisAlignedBB)obj[0];
			plankAABB.minX += i; plankAABB.maxX += i;
			plankAABB.minY += j; plankAABB.maxY += j;
			plankAABB.minZ += k; plankAABB.maxZ += k;
			if (plankAABB != null && aabb.intersectsWith(plankAABB))
			{
				list.add(plankAABB);
			}
		}
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view)
	{
		return CollisionRayTraceStandard.collisionRayTrace(this, world, x, y, z, player, view);
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, List list) 
	{
		TileEntityConstruct te = (TileEntityConstruct) world.getTileEntity(i, j, k);

		int d = TileEntityConstruct.PlankDetailLevel;
		int dd = TileEntityConstruct.PlankDetailLevel * TileEntityConstruct.PlankDetailLevel;

		float div = 1f / d;

		for(int x = 0; x < dd; x++)
		{
			if(te.data.get(x))
			{
				float minX = 0;
				float maxX = 1;
				float minY = div * (x & 7);
				float maxY = minY + div;
				float minZ = div * (x >> 3);
				float maxZ = minZ + div;
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ)});
			}
		}

		for(int y = 0; y < dd; y++)
		{
			if(te.data.get(y+dd))
			{
				float minX = div * (y & 7);
				float maxX = minX + div;
				float minY = 0;
				float maxY = 1;
				float minZ = div * (y >> 3);
				float maxZ = minZ + div;
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ)});
			}
		}

		for(int z = 0; z < dd; z++)
		{
			if(te.data.get(z+(dd*2)))
			{
				float minX = div * (z & 7);
				float maxX = minX + div;
				float minY = div * (z >> 3);
				float maxY = minY + div;
				float minZ = 0;
				float maxZ = 1;
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ)});
			}
		}
	}

}
