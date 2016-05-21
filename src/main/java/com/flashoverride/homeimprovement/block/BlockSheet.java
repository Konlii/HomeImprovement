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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.flashoverride.homeimprovement.CollisionRayTraceStandard;
import com.flashoverride.homeimprovement.HomeImprovement;
import com.flashoverride.homeimprovement.interfaces.ICustomCollision;
import com.flashoverride.homeimprovement.tileentity.TileEntitySheet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSheet extends BlockHIContainer implements ICustomCollision
{
//	public IIcon[] icons;
	public BlockSheet(Material material)
	{
		super(material);
//		icons = new IIcon[sheetNames.length];
		this.setBlockBounds(0, 0, 0, 1, 1, 1);
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
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side)
	{
		return true;
	}

/*	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta) 
	{
		TileEntitySheet te = (TileEntitySheet)world.getTileEntity(i, j, k);
		int stack = 0;
		if(te.TopExists()) stack++;
		if(te.BottomExists()) stack++;
		if(te.NorthExists()) stack++;
		if(te.SouthExists()) stack++;
		if(te.EastExists()) stack++;
		if(te.WestExists()) stack++;
		te.sheetStack.stackSize = stack;
		EntityItem ei = new EntityItem(world, i, j, k, te.sheetStack);
		if (!Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode) world.spawnEntityInWorld(ei);
		
	    if (te != null) breakCraftingSheet(world, i, j, k);
	}*/
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		if(!world.isRemote && (TileEntitySheet)world.getTileEntity(x, y, z)!=null)
		{
			TileEntitySheet te = (TileEntitySheet)world.getTileEntity(x, y, z);
			ret = te.getDrops();
//			System.out.println("Drop Wood Sheets");
		}
		return ret;
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) 
	{
		TileEntitySheet te = (TileEntitySheet)world.getTileEntity(x, y, z);
		
		if (!player.capabilities.isCreativeMode) {
		ArrayList<ItemStack> out = this.getDrops(world, x, y, z, meta, 0);
		for(ItemStack is : out)
		{
			world.spawnEntityInWorld(new EntityItem(world, x+0.5, y+0.5, z+0.5, is));
		}
		}
		if (te != null) breakCraftingSheet(world, x, y, z);
	}

	@Override
	public void onBlockExploded(World world, int i, int j, int k, Explosion explosion)
	{
		TileEntitySheet te = (TileEntitySheet)world.getTileEntity(i, j, k);
		if ( te != null )
			te.clearSides();

		super.onBlockExploded(world, i, j, k, explosion);
	}

	@Override
	public int getRenderType()
	{
		return HomeImprovement.sheetRenderId;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

/*	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < icons.length; i++)
			icons[i] = registerer.registerIcon("minecraft:" + ""+sheetNames[i]);

		TFC_Textures.SheetBismuth = icons[0];
		TFC_Textures.SheetBismuthBronze = icons[1];
		TFC_Textures.SheetBlackBronze = icons[2];
		TFC_Textures.SheetBlackSteel = icons[3];
		TFC_Textures.SheetBlueSteel = icons[4];
		TFC_Textures.SheetBrass = icons[5];
		TFC_Textures.SheetBronze = icons[6];
		TFC_Textures.SheetCopper = icons[7];
		TFC_Textures.SheetGold = icons[8];
		TFC_Textures.SheetWroughtIron = icons[9];
		TFC_Textures.SheetLead = icons[10];
		TFC_Textures.SheetNickel = icons[11];
		TFC_Textures.SheetPigIron = icons[12];
		TFC_Textures.SheetPlatinum = icons[13];
		TFC_Textures.SheetRedSteel = icons[14];
		TFC_Textures.SheetRoseGold = icons[15];
		TFC_Textures.SheetSilver = icons[16];
		TFC_Textures.SheetSteel = icons[17];
		TFC_Textures.SheetSterlingSilver = icons[18];
		TFC_Textures.SheetTin = icons[19];
		TFC_Textures.SheetZinc = icons[20];
	}*/

/*	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta >= 0 && meta < icons.length)
			return icons[meta];
		return icons[19];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int i, int j, int k, int meta)
	{
		TileEntitySheet te = (TileEntitySheet) access.getTileEntity(i, j, k);
		if(te!= null)
			return icon[meta];
		else
			return icon[0];
	}*/
	
    @Override
	public void registerBlockIcons(IIconRegister iconRegister)
    {
        for (int i = 0; i < this.icon.length - 1; ++i)
        {
        	if (HomeImprovement.MATERIALS_ALL[i].startsWith("wood"))
        	{
        		this.icon[i] = iconRegister.registerIcon(HomeImprovement.MATERIALS_ALL[i].replace("wood.", "minecraft:planks_"));
        	}
        	else if (HomeImprovement.MATERIALS_ALL[i].startsWith("metal"))
        	{
        		this.icon[i] = iconRegister.registerIcon(HomeImprovement.MATERIALS_ALL[i].replace("metal.", "minecraft:") + "_block");
        	}
        	else if (HomeImprovement.MATERIALS_ALL[i].startsWith("glass"))
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
       this.icon[this.icon.length - 1] = iconRegister.registerIcon("minecraft:crafting_table_top");
    }

    @Override
    public IIcon getIcon(int i, int j)
    {
        return this.icon[j];
    }



	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntitySheet();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, List list)
	{
		TileEntitySheet te = (TileEntitySheet)world.getTileEntity(i, j, k);
		double f0 = 0.0625;
		double f1 = 0.9375;
		double yMax = 1;
		double yMin = 0;

		if(te.TopExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0, f1, 0.0, 1.0, 1.0, 1.0)});
		if(te.BottomExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0, 0, 0.0, 1.0, f0, 1.0)});
		if(te.NorthExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0, yMin, 0, 1, yMax, f0)});
		if(te.SouthExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0, yMin, f1, 1, yMax, 1)});
		if(te.EastExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0, yMin, 0, f0, yMax, 1)});
		if(te.WestExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(f1, yMin, 0, 1, yMax, 1)});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity)
	{
		ArrayList<Object[]> l = new ArrayList<Object[]>();
		addCollisionBoxesToList(world,i,j,k,l);
		for(Object[] o : l)
		{
			AxisAlignedBB a = (AxisAlignedBB)o[0];
			a.minX += i; a.maxX += i;
			a.minY += j; a.maxY += j;
			a.minZ += k; a.maxZ += k;
			if (a != null && aabb.intersectsWith(a))
				list.add(a);
		}
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view)
	{
		return CollisionRayTraceStandard.collisionRayTrace(this, world, x, y, z, player, view);
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntitySheet te = (TileEntitySheet)world.getTileEntity(x, y, z);
		switch(side)
		{
		case 0:return te.BottomExists();
		case 1:return te.TopExists();
		case 2:return te.NorthExists();
		case 3:return te.SouthExists();
		case 4:return te.EastExists();
		case 5:return te.WestExists();
		default: return false;
		}
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		TileEntitySheet te = (TileEntitySheet)world.getTileEntity(x, y, z);
		switch(side)
		{
		case DOWN:return te.BottomExists();
		case UP:return te.TopExists();
		case NORTH:return te.NorthExists();
		case SOUTH:return te.SouthExists();
		case WEST:return te.EastExists();
		case EAST:return te.WestExists();
		default: return false;
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ)
    {
		TileEntitySheet te = (TileEntitySheet)world.getTileEntity(x, y, z);
        boolean isCraftingSheet = false;
 /*       
        System.out.println(te.materialTypes[0]);
        System.out.println(te.materialTypes[1]);
        System.out.println(te.materialTypes[2]);
        System.out.println(te.materialTypes[3]);
        System.out.println(te.materialTypes[4]);
        System.out.println(te.materialTypes[5]);
        
        System.out.println(hitX + ", " + hitY + ", " + hitZ);
*/
        
        if ((hitY == 0.00F || hitY == 0.0625F) && te.BottomExists() && (te.materialTypes[0] == this.icon.length - 1)) isCraftingSheet = true;
        if ((hitY == 1.00F || hitY == 0.9375F) && te.TopExists() && (te.materialTypes[1] == this.icon.length - 1)) isCraftingSheet = true;
        if ((hitZ == 0.00F || hitZ == 0.0625F) && te.NorthExists() && (te.materialTypes[2] == this.icon.length - 1)) isCraftingSheet = true;
        if ((hitZ == 1.00F || hitZ == 0.9375F) && te.SouthExists() && (te.materialTypes[3] == this.icon.length - 1)) isCraftingSheet = true;
        if ((hitX == 0.00F || hitX == 0.0625F) && te.EastExists() && (te.materialTypes[4] == this.icon.length - 1)) isCraftingSheet = true;
        if ((hitX == 1.00F || hitX == 0.9375F) && te.WestExists() && (te.materialTypes[5] == this.icon.length - 1)) isCraftingSheet = true;
    	
        if (!world.isRemote && !player.isSneaking() && isCraftingSheet)
        {
        	player.openGui(HomeImprovement.instance, HomeImprovement.guiIDCraftingSheet, world, x, y, z);
        	return true;
        }
        else return false;
    }

	public void breakCraftingSheet(World world, int x, int y, int z)
	{
		TileEntitySheet te = (TileEntitySheet)world.getTileEntity(x, y, z);

        if (te != null)
        {
            for (int i1 = 0; i1 < te.getSizeInventory() - 1; ++i1)
            {
                ItemStack itemstack = te.getStackInSlot(i1);

                if (itemstack != null)
                {
                    float f = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0)
                    {
                        int j1 = world.rand.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize)
                        {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;        EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                        	entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }
            te.clearContents();
        }
	}
	
/*	@Override
	public int getDamageValue(World world, int x, int y, int z)
    {
		TileEntitySheet te = (TileEntitySheet)world.getTileEntity(x, y, z);

        return te.materialTypes[Minecraft.getMinecraft().objectMouseOver.sideHit];
    }*/
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
    {
		TileEntitySheet te = (TileEntitySheet)world.getTileEntity(x, y, z);

        if (te == null)
        {
            return null;
        }
        
        ArrayList<ItemStack> drops = te.getDrops();        
//        ItemStack[] newDrops = drops.toArray().clone();
        
        int wood = world.rand.nextInt(drops.size());
        return drops.get(wood);
//		System.out.println(drops.size());
		
/*		switch(wood)
		{
		case 0:
			if (te.BottomExists()) wood = te.materialTypes[0];
		case 1:
			if (te.TopExists()) wood = te.materialTypes[1];
		case 2:
			if (te.NorthExists()) wood = te.materialTypes[2];
		case 3:
			if (te.SouthExists()) wood = te.materialTypes[3];
		case 4:
			if (te.EastExists()) wood = te.materialTypes[4];
		case 5:
			if (te.WestExists()) wood = te.materialTypes[5];
			break;
		default: return null;
		}*/

      //return new ItemStack(HomeImprovement.CraftingSheet, 1, wood);
//		System.out.println(HomeImprovement.MATERIALS_ALL[wood]);
//		System.out.println(drops.get(wood).toString());
//		return null;
    }
}
