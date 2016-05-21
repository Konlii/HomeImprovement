package com.flashoverride.homeimprovement.item;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

import com.flashoverride.homeimprovement.HomeImprovement;
import com.flashoverride.homeimprovement.block.BlockSheet;
import com.flashoverride.homeimprovement.block.BlockHIContainer;
import com.flashoverride.homeimprovement.tileentity.TileEntitySheet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPrybar extends Item{
    protected Item.ToolMaterial toolMaterial;
    protected TileEntitySheet te = null;
    protected boolean flag = false;
    protected Block targetBlock = null;
    protected float hitX = 0;
    protected float hitY = 0;
    protected float hitZ = 0;
    protected int x = 0;
    protected int y = 0;
    protected int z = 0;
    protected int side = 0;

	public ItemPrybar(String unlocalizedName, ToolMaterial toolMaterial) {
		super();
        this.maxStackSize = 1;
        this.setMaxDamage(toolMaterial.getMaxUses());
        this.toolMaterial = toolMaterial;
		this.setUnlocalizedName(unlocalizedName);
		this.setTextureName(HomeImprovement.MODID + ":tools/" + unlocalizedName);
		setCreativeTab(HomeImprovement.HITab);
	}
	
   @SideOnly(Side.CLIENT)
   public boolean isFull3D()
   {
       return true;
   }

   public Item.ToolMaterial func_150913_i()
   {
       return this.toolMaterial;
   }
   
   public int getItemEnchantability()
   {
       return this.toolMaterial.getEnchantability();
   }

   public String getToolMaterialName()
   {
       return this.toolMaterial.toString();
   }

   public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
   {
       ItemStack mat = this.toolMaterial.getRepairItemStack();
       if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, p_82789_2_, false)) return true;
       return super.getIsRepairable(p_82789_1_, p_82789_2_);
   }
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float px, float py, float pz)
    {
		hitX = px;
		hitY = py;
		hitZ = pz;
		this.x = x;
		this.y = y;
		this.z = z;
		this.side = side;

		if (!world.blockExists(x, y, z)) return false;
		
		targetBlock = world.getBlock(x, y, z);
		if (targetBlock instanceof BlockSheet)
			{
            te = (TileEntitySheet)world.getTileEntity(x, y, z);
//			MovingObjectPosition target = new MovingObjectPosition(x, y, z, side, entityPlayer.getLookVec());
//			EffectRenderer effectRenderer = new EffectRenderer(world, Minecraft.getMinecraft().getTextureManager());
//    		world.playSoundAtEntity(entityPlayer, "mob.sheep.step", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
//			world.playSoundAtEntity(entityPlayer, world.getBlock(x, y, z).stepSound.getBreakSound(), 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
			world.playSoundAtEntity(entityPlayer, "step.ladder", 0.5F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
//			world.spawnParticle("blockcrack_5_4", x + 0.5D, y + 0.5D, z + 0.5D, 1D, 1D, 1D);
//			world.func_147480_a(x, y, z, true);
//			world.getBlock(x, y, z).addHitEffects(world, target, effectRenderer);
			
			flag = true;
			entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
			}
        else if (world.getBlock(x, y, z) instanceof BlockHIContainer){
        	targetBlock = null;
        	flag = false;
        	return false;
        }
        else
			{
			entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
			flag = false;
		}

		if (!targetBlock.isAir(world, x, y, z) && !flag)
		{
//			MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
//			if(!world.isRemote && mop != null)
//			{
//				ItemStack is = world.getBlock(x, y, z).getPickBlock(mop, world, x, y, z, entityPlayer);
//				EntityItem entityitem = new EntityItem(world, (double)((float)x), (double)((float)y), (double)((float)z), is);
//				world.spawnEntityInWorld(entityitem);
				world.playSoundAtEntity(entityPlayer, world.getBlock(x, y, z).stepSound.getBreakSound(), 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
				world.playSoundAtEntity(entityPlayer, "step.ladder", 0.5F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
				entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
		}
    	
/*    	if(!world.isRemote)
    	{
    		EntityItem item = new EntityItem(world, x, y, z, new ItemStack(Item.getItemFromBlock(targetBlock)));
		
    		world.spawnEntityInWorld(item);
    	}*/
    	

    	return true;
    }

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		targetBlock = null;
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
       if (targetBlock != null) return EnumAction.bow;
       else return EnumAction.block;
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

    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer entityPlayer, int itemInUseCount)
    {
        int j = this.getMaxItemUseDuration(itemStack) - itemInUseCount;
        
/*        int x = Minecraft.getMinecraft().objectMouseOver.blockX;
        int y = Minecraft.getMinecraft().objectMouseOver.blockY;
        int z = Minecraft.getMinecraft().objectMouseOver.blockZ;*/
        
//        int sideHit = Minecraft.getMinecraft().objectMouseOver.sideHit;
//        side = sideHit;
        
            ArrowLooseEvent event = new ArrowLooseEvent(entityPlayer, itemStack, j);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled())
            {
                return;
            }

            j = event.charge;

            float f = (float)j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

//       if (flag)
//        	{
                if ((double)f < 0.4D)
                {
                    return;
                }

                if (f > 1.0F)
                {
                    f = 1.0F;
                }
        	
//                te = (TileEntitySheet)world.getTileEntity(x, y, z);
//        	}
        
		if (!world.isRemote && te != null){
		if (hitX == 0.00F || hitX == 1.00F || hitY == 0.00F || hitY == 1.00F || hitZ == 0.00F || hitZ == 1.00F)
		{
			if (te.EastExists() && hitX == 0.00F) te.toggleBySide(side, false);
			else if (te.WestExists() && hitX == 1.00F) te.toggleBySide(side, false);
			else if (te.BottomExists() && hitY == 0.00F) te.toggleBySide(side, false);
			else if (te.TopExists() && hitY == 1.00F) te.toggleBySide(side, false);
			else if (te.NorthExists() && hitZ == 0.00F) te.toggleBySide(side, false);
			else if (te.SouthExists() && hitZ == 1.00F) te.toggleBySide(side, false);
			
			else if (te.BottomExists() && 0.00F <  hitY && hitY < 0.1875F)
			{
				side = 0;
				te.toggleBySide(0, false);
			}
			else if (te.TopExists() && 0.8125F <  hitY && hitY < 1.00F)
			{
				side = 1;
				te.toggleBySide(1, false);
			}
			else if (te.NorthExists() && 0.00F < hitZ && hitZ < 0.1875F)
			{
				side = 2;
				te.toggleBySide(2, false);
			}
			else if (te.SouthExists() && 0.8125F < hitZ && hitZ < 1.00F)
			{
				side = 3;
				te.toggleBySide(3, false);
			}
			else if (te.EastExists() && 0.00F < hitX && hitX < 0.1875F)
			{
				side = 4;
				te.toggleBySide(4, false);
			}
			else if (te.WestExists() && 0.8125F < hitX && hitX < 1.00F)
			{
				side = 5;
				te.toggleBySide(5, false);
			}
			else
			{
			switch(side)
			
			{
			case 0:
				if (te.BottomExists())
				{
					te.toggleBySide(side, false);
				}
				break;
				
			case 1:
				if(te.TopExists())
				{
					te.toggleBySide(side, false);
				}
				break;

			case 2:
				if(te.NorthExists())
				{
					te.toggleBySide(side, false);
				}
				break;

			case 3:
				if(te.SouthExists())
				{
					te.toggleBySide(side, false);
				}
				break;

			case 4:
				if(te.EastExists())
				{
					te.toggleBySide(side, false);
				}
				break;

			case 5:
				if(te.WestExists())
				{
					te.toggleBySide(side, false);
				}
				break;
			}
			}
		}
		else
		{
			side = flipSide(side);
			te.toggleBySide(side, false);
		}
		}
        
        
        
        
        
        	


        
        if (flag)
        {
        	if (te != null)
        		{
        		itemStack.damageItem(1, entityPlayer);
        		}
        	
            if (!world.isRemote && te != null)
            {
                if (world.getBlock(x, y, z) instanceof BlockSheet)
                {
//                	TileEntitySheet te = (TileEntitySheet)world.getTileEntity(x, y, z);
    				world.playSoundAtEntity(entityPlayer, "step.ladder", 0.5F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
            		world.playSoundAtEntity(entityPlayer, world.getBlock(x, y, z).stepSound.getBreakSound(), 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.3F);
//                    int l = world.getBlockMetadata(x, y, z);
//                    world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(world.getBlock(x, y, z)) + (l << 12));
//                    System.out.println(world.getBlock(x, y, z).getDamageValue(world, x, y, z));
            		
//                    world.destroyBlockInWorldPartially(Block.getIdFromBlock(world.getBlock(x, y, z)), x, y, z, 1);

    				ItemStack stack = new ItemStack(HomeImprovement.Sheet, 1, te.materialTypes[side]);
    				EntityItem e = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, stack);
    				e.delayBeforeCanPickup = 5;
    				

    	            if (HomeImprovement.MATERIALS_ALL[te.materialTypes[side]].endsWith("crafting"))
    	            {
    				for (int i1 = 0; i1 < te.getSizeInventory() - 1; ++i1)
    	            {
    	                ItemStack itemstack = te.getStackInSlot(i1);

    	                if (itemstack != null)
    	                {
    	                    float f0 = world.rand.nextFloat() * 0.8F + 0.1F;
    	                    float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
    	                    float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

    	                    while (itemstack.stackSize > 0)
    	                    {
    	                        int j1 = world.rand.nextInt(21) + 10;

    	                        if (j1 > itemstack.stackSize)
    	                        {
    	                            j1 = itemstack.stackSize;
    	                        }

    	                        itemstack.stackSize -= j1;
    	                        EntityItem entityitem = new EntityItem(world, (double)((float)x + f0), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

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
    	            
    	            if (!te.BottomExists() && !te.TopExists() && !te.NorthExists() && !te.SouthExists() &&!te.EastExists() &&!te.WestExists())
    	            {
    	            	world.setBlockToAir(x, y, z);
    	            }
    				
   					world.spawnEntityInWorld(e);
    				
//        			System.out.println(HomeImprovement.MATERIALS_ALL[te.materialTypes[side]]);
                }
        		te.data.clear(side);

        		NBTTagCompound nbt = new NBTTagCompound();
        		nbt.setInteger("index", side);
        		nbt.setByte("meta", te.materialTypes[side]);
        		te.broadcastPacketInRange(te.createDataPacket(nbt));

                flag = false;
           }
        }
        
        
        else if (world.getBlock(x, y, z) instanceof BlockHIContainer) return;
        
        
        else if (world.getBlock(x, y, z) != null && world.blockExists(x, y, z))
        {
        	if (!world.isRemote)
        	{
			MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
			if(mop != null)
			{
				ItemStack is = world.getBlock(x, y, z).getPickBlock(mop, world, x, y, z, entityPlayer);
				if (is != null)
				{
        		
				EntityItem entityitem = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, is);
				world.playSoundAtEntity(entityPlayer, "step.ladder", 0.5F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
				int l = world.getBlockMetadata(x, y, z);
                world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(world.getBlock(x, y, z)) + (l << 12));
//        		world.playSoundAtEntity(entityPlayer, world.getBlock(x, y, z).stepSound.getBreakSound(), 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.3F);
        		entityitem.delayBeforeCanPickup = 5;
        		
        		world.getBlock(x, y, z).breakBlock(world, x, y, z, targetBlock, 0);
        		world.setBlockToAir(x, y, z);
 				world.spawnEntityInWorld(entityitem);
				}
			}
		  }
        		itemStack.damageItem(5, entityPlayer);
       }
		if (itemStack != null) if (itemStack.getItemDamage() == 0) entityPlayer.destroyCurrentEquippedItem();
    }

/*    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
    	World world = Minecraft.getMinecraft().theWorld;
    	MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
    	if(mop != null)
    	{
    	int i = mop.blockX;
    	int j = mop.blockX;
    	int k = mop.blockX;
   	    int sideHit = mop.sideHit;
   	
    	
    	if (world.getBlock(i, j, k) != targetBlock)
    		{
    			System.out.println(world.getBlock(i,j,k).getUnlocalizedName());
    		}

    	    Block blockLookingAt = worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ) ; 
      int l = worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
//      worldObj.playAuxSFX(2001, mop.blockX, mop.blockY, mop.blockZ, Block.getIdFromBlock(blockLookingAt) + (l << 12));
//      worldObj.spawnParticle("blockcrack_5_4", mop.blockX + 0.5D, mop.blockY + 0.5D, mop.blockZ + 0.5D, 1D, 1D, 1D);
//      worldObj.destroyBlockInWorldPartially(Block.getIdFromBlock(blockLookingAt), x, y, z, 100);
//      System.out.println(blockLookingAt.getDamageValue(worldObj, mop.blockX, mop.blockY, mop.blockZ));
      
      Minecraft.getMinecraft().effectRenderer.addBlockHitEffects(x, y, z, mop);
    	}
    }*/

}
