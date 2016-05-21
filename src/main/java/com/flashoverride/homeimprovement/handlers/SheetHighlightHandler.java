package com.flashoverride.homeimprovement.handlers;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import org.lwjgl.opengl.GL11;

import com.flashoverride.homeimprovement.block.BlockSheet;
import com.flashoverride.homeimprovement.item.ItemSheet;
import com.flashoverride.homeimprovement.tileentity.TileEntitySheet;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SheetHighlightHandler {
	protected int[][] sidesMap = new int[][]{{0,-1,0},{0,1,0},{0,0,-1},{0,0,+1},{-1,0,0},{1,0,0}};

	@SubscribeEvent
	public void DrawBlockHighlightEvent(DrawBlockHighlightEvent evt) 
	{
		World world = evt.player.worldObj;
		double var8 = evt.player.lastTickPosX + (evt.player.posX - evt.player.lastTickPosX) * (double)evt.partialTicks;
		double var10 = evt.player.lastTickPosY + (evt.player.posY - evt.player.lastTickPosY) * (double)evt.partialTicks;
		double var12 = evt.player.lastTickPosZ + (evt.player.posZ - evt.player.lastTickPosZ) * (double)evt.partialTicks;
		
		int side = evt.target.sideHit;
		int[] sides = sidesMap[side];

		if(evt.currentItem != null && evt.currentItem.getItem() instanceof ItemSheet)
		{
			TileEntitySheet te = null;

			GL11.glDisable(GL11.GL_TEXTURE_2D);

			boolean isConstruct = world.getBlock(evt.target.blockX, evt.target.blockY, evt.target.blockZ) instanceof BlockSheet;
			//float div = 1f / TileEntityWoodConstruct.PlankDetailLevel;
			//Get the hit location in local box coords
			double hitX = Math.round((evt.target.hitVec.xCoord - evt.target.blockX)*100)/100.0d;
			double hitY = Math.round((evt.target.hitVec.yCoord - evt.target.blockY)*100)/100.0d;
			double hitZ = Math.round((evt.target.hitVec.zCoord - evt.target.blockZ)*100)/100.0d;

			//get the targeted sub block coords
			double subX = (double)((int)((hitX)*1))/1;
			double subY = (double)((int)((hitY)*1))/1;
			double subZ = (double)((int)((hitZ)*1))/1;

			//create the box size
			double minX = evt.target.blockX + subX;
			double minY = evt.target.blockY + subY;
			double minZ = evt.target.blockZ + subZ;
			double maxX = minX + 0.0625;
			double maxY = minY + 0.0625;
			double maxZ = minZ + 0.0625;

			if(isConstruct && hitY != 0  && hitY != 1 && hitZ != 0  && hitZ != 1 && hitX != 0  && hitX != 1 && world.getTileEntity(evt.target.blockX, evt.target.blockY, evt.target.blockZ) != null)
			{
				te = (TileEntitySheet)world.getTileEntity(evt.target.blockX, evt.target.blockY, evt.target.blockZ);
				
				if(evt.target.sideHit == 0)
				{
					if (0.00F <  hitZ && hitZ < 0.1875F && !te.NorthExists())
					{
						minY = evt.target.blockY;
						maxY = evt.target.blockY + 1;
						maxX = minX + 1;
					}
					else if (0.8125F <  hitZ && hitZ < 1F && !te.SouthExists())
					{
						minY = evt.target.blockY;
						maxY = evt.target.blockY + 1;
						minZ = evt.target.blockZ + subZ + 1;
						maxZ = minZ - 0.0625;
						maxX = minX + 1;
					}
					else if (0.00F <  hitX && hitX < 0.1875F && !te.EastExists())
					{
						minY = evt.target.blockY;
						maxY = evt.target.blockY + 1;
						maxZ = minZ + 1;
					}
					else if (0.8125F <  hitX && hitX < 1F && !te.WestExists())
					{
						minY = evt.target.blockY;
						maxY = evt.target.blockY + 1;
						minX = evt.target.blockX + subX + 1;
						maxX = minX - 0.0625;
						maxZ = minZ + 1;
					}
					else if (!te.BottomExists())
					{
						minY = evt.target.blockY;
						maxY = minY + 0.0625;
						maxX = minX + 1;
						maxZ = minZ + 1;
					}
					else
					{
						minX = 0;
						minY = 0;
						minZ = 0;
						maxX = 0;
						maxY = 0;
						maxZ = 0;
					}
				}
				else if(evt.target.sideHit == 1)
				{
					if (0.00F <  hitZ && hitZ < 0.1875F && !te.NorthExists())
					{
						minY = evt.target.blockY;
						maxY = evt.target.blockY + 1;
						maxX = minX + 1;
					}
					else if (0.8125F <  hitZ && hitZ < 1F && !te.SouthExists())
					{
						minY = evt.target.blockY;
						maxY = evt.target.blockY + 1;
						minZ = evt.target.blockZ + subZ + 1;
						maxZ = minZ - 0.0625;
						maxX = minX + 1;
					}
					else if (0.00F <  hitX && hitX < 0.1875F && !te.EastExists())
					{
						minY = evt.target.blockY;
						maxY = evt.target.blockY + 1;
						maxZ = minZ + 1;
					}
					else if (0.8125F <  hitX && hitX < 1F && !te.WestExists())
					{
						minY = evt.target.blockY;
						maxY = evt.target.blockY + 1;
						minX = evt.target.blockX + subX + 1;
						maxX = minX - 0.0625;
						maxZ = minZ + 1;
					}
					else if (!te.TopExists())
					{
						minY = evt.target.blockY + 1;
						maxY = minY - 0.0625;
						maxX = minX + 1;
						maxZ = minZ + 1;
					}
					else
					{
						minX = 0;
						minY = 0;
						minZ = 0;
						maxX = 0;
						maxY = 0;
						maxZ = 0;
					}
				}
				else if(evt.target.sideHit == 2)
				{
					if (0.00F <  hitY && hitY < 0.1875F && !te.BottomExists())
					{
						minZ = evt.target.blockZ;
						maxZ = evt.target.blockZ + 1;
						maxX = minX + 1;
					}
					else if (0.8125F <  hitY && hitY < 1F && !te.TopExists())
					{
						minZ = evt.target.blockZ;
						maxZ = evt.target.blockZ + 1;
						minY = evt.target.blockY + subY + 1;
						maxY = minY - 0.0625;
						maxX = minX + 1;
					}
					else if (0.00F <  hitX && hitX < 0.1875F && !te.EastExists())
					{
						minZ = evt.target.blockZ;
						maxZ = evt.target.blockZ + 1;
						maxY = minY + 1;
					}
					else if (0.8125F <  hitX && hitX < 1F && !te.WestExists())
					{
						minZ = evt.target.blockZ;
						maxZ = evt.target.blockZ + 1;
						minX = evt.target.blockX + subX + 1;
						maxX = minX - 0.0625;
						maxY = minY + 1;
					}
					else if (!te.NorthExists())
					{
						minZ = evt.target.blockZ;
						maxZ = minZ + 0.0625;
						maxX = minX + 1;
						maxY = minY + 1;
					}
					else
					{
						minX = 0;
						minY = 0;
						minZ = 0;
						maxX = 0;
						maxY = 0;
						maxZ = 0;
					}
				}
				else if(evt.target.sideHit == 3)
				{
					if (0.00F <  hitY && hitY < 0.1875F && !te.BottomExists())
					{
						minZ = evt.target.blockZ;
						maxZ = evt.target.blockZ + 1;
						maxX = minX + 1;
					}
					else if (0.8125F <  hitY && hitY < 1F && !te.TopExists())
					{
						minZ = evt.target.blockZ;
						maxZ = evt.target.blockZ + 1;
						minY = evt.target.blockY + subY + 1;
						maxY = minY - 0.0625;
						maxX = minX + 1;
					}
					else if (0.00F <  hitX && hitX < 0.1875F && !te.EastExists())
					{
						minZ = evt.target.blockZ;
						maxZ = evt.target.blockZ + 1;
						maxY = minY + 1;
					}
					else if (0.8125F <  hitX && hitX < 1F && !te.WestExists())
					{
						minZ = evt.target.blockZ;
						maxZ = evt.target.blockZ + 1;
						minX = evt.target.blockX + subX + 1;
						maxX = minX - 0.0625;
						maxY = minY + 1;
					}
					else if (!te.SouthExists())
					{
						minZ = evt.target.blockZ + 1;
						maxZ = minZ - 0.0625;
						maxX = minX + 1;
						maxY = minY + 1;
					}
					else
					{
						minX = 0;
						minY = 0;
						minZ = 0;
						maxX = 0;
						maxY = 0;
						maxZ = 0;
					}
				}
				else if(evt.target.sideHit == 4)
				{
					if (0.00F <  hitY && hitY < 0.1875F && !te.BottomExists())
					{
						minX = evt.target.blockX;
						maxX = evt.target.blockX+1;
						maxZ = minZ + 1;
					}
					else if (0.8125F <  hitY && hitY < 1F && !te.TopExists())
					{
						minX = evt.target.blockX;
						maxX = evt.target.blockX+1;
						minY = evt.target.blockY + subY + 1;
						maxY = minY - 0.0625;
						maxZ = minZ + 1;
					}
					else if (0.00F <  hitZ && hitZ < 0.1875F && !te.NorthExists())
					{
						minX = evt.target.blockX;
						maxX = evt.target.blockX+1;
						maxY = minY + 1;
					}
					else if (0.8125F <  hitZ && hitZ < 1F && !te.SouthExists())
					{
						minX = evt.target.blockX;
						maxX = evt.target.blockX+1;
						minZ = evt.target.blockZ + subZ + 1;
						maxZ = minZ - 0.0625;
						maxY = minY + 1;
					}
					else if (!te.EastExists())
					{
						minX = evt.target.blockX;
						maxX = evt.target.blockX+0.0625;
						maxZ = minZ + 1;
						maxY = minY + 1;
					}
					else
					{
						minX = 0;
						minY = 0;
						minZ = 0;
						maxX = 0;
						maxY = 0;
						maxZ = 0;
					}
				}
				else if(evt.target.sideHit == 5)
				{
					if (0.00F <  hitY && hitY < 0.1875F && !te.BottomExists())
					{
						minX = evt.target.blockX;
						maxX = evt.target.blockX+1;
						maxZ = minZ + 1;
					}
					else if (0.8125F <  hitY && hitY < 1F && !te.TopExists())
					{
						minX = evt.target.blockX;
						maxX = evt.target.blockX+1;
						minY = evt.target.blockY + subY + 1;
						maxY = minY - 0.0625;
						maxZ = minZ + 1;
					}
					else if (0.00F <  hitZ && hitZ < 0.1875F && !te.NorthExists())
					{
						minX = evt.target.blockX;
						maxX = evt.target.blockX+1;
						maxY = minY + 1;
					}
					else if (0.8125F <  hitZ && hitZ < 1F && !te.SouthExists())
					{
						minX = evt.target.blockX;
						maxX = evt.target.blockX+1;
						minZ = evt.target.blockZ + subZ + 1;
						maxZ = minZ - 0.0625;
						maxY = minY + 1;
					}
					else if (!te.WestExists())
					{
						minX = evt.target.blockX + 1;
						maxX = minX - 0.0625;
						maxZ = minZ + 1;
						maxY = minY + 1;
					}
					else
					{
						minX = 0;
						minY = 0;
						minZ = 0;
						maxX = 0;
						maxY = 0;
						maxZ = 0;
					}
				}
			}
			else if (!world.isAirBlock(evt.target.blockX, evt.target.blockY, evt.target.blockZ))
			{
				if(evt.target.sideHit == 0)
				{
					if (world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ) != null
							&& world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ) instanceof TileEntitySheet)
					{
						te = (TileEntitySheet)world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ);
						if (0.00F <  hitZ && hitZ < 0.1875F && !te.NorthExists())
						{
							maxY = minY;
							minY = minY - 1;
							maxX = minX + 1;
						}
						else if (0.8125F <  hitZ && hitZ < 1F && !te.SouthExists())
						{
							maxY = minY;
							minY = minY - 1;
							minZ = evt.target.blockZ + subZ + 1;
							maxZ = minZ - 0.0625;
							maxX = minX + 1;
						}
						else if (0.00F <  hitX && hitX < 0.1875F && !te.EastExists())
						{
							maxY = minY;
							minY = minY - 1;
							maxZ = minZ + 1;
						}
						else if (0.8125F <  hitX && hitX < 1F && !te.WestExists())
						{
							maxY = minY;
							minY = minY - 1;
							minX = evt.target.blockX + subX + 1;
							maxX = minX - 0.0625;
							maxZ = minZ + 1;
						}
						else if (!te.BottomExists())
						{
							maxY = minY;
							minY = minY - 0.0625;
							maxX = minX + 1;
							maxZ = minZ + 1;
						}
						else
						{
							minX = 0;
							minY = 0;
							minZ = 0;
							maxX = 0;
							maxY = 0;
							maxZ = 0;
						}
					}
					else if (0.00F <  hitZ && hitZ < 0.1875F)
					{
						maxY = minY;
						minY = minY - 1;
						maxX = minX + 1;
					}
					else if (0.8125F <  hitZ && hitZ < 1F)
					{
						maxY = minY;
						minY = minY - 1;
						minZ = evt.target.blockZ + subZ + 1;
						maxZ = minZ - 0.0625;
						maxX = minX + 1;
					}
					else if (0.00F <  hitX && hitX < 0.1875F)
					{
						maxY = minY;
						minY = minY - 1;
						maxZ = minZ + 1;
					}
					else if (0.8125F <  hitX && hitX < 1F)
					{
						maxY = minY;
						minY = minY - 1;
						minX = evt.target.blockX + subX + 1;
						maxX = minX - 0.0625;
						maxZ = minZ + 1;
					}
					else
					{
						maxY = minY;
						minY = minY - 0.0625;
						maxX = minX + 1;
						maxZ = minZ + 1;
					}
				}
				else if(evt.target.sideHit == 1)
				{
					if (world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ) != null
							&& world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ) instanceof TileEntitySheet)
					{
						te = (TileEntitySheet)world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ);

						if (0.00F <  hitZ && hitZ < 0.1875F && !te.NorthExists())
						{
							maxY = minY + 1;
							maxX = minX + 1;
						}
						else if (0.8125F <  hitZ && hitZ < 1F && !te.SouthExists())
						{
							maxY = minY + 1;
							minZ = evt.target.blockZ + subZ + 1;
							maxZ = minZ - 0.0625;
							maxX = minX + 1;
						}
						else if (0.00F <  hitX && hitX < 0.1875F && !te.EastExists())
						{
							maxY = minY + 1;
							maxZ = minZ + 1;
						}
						else if (0.8125F <  hitX && hitX < 1F && !te.WestExists())
						{
							maxY = minY + 1;
							minX = evt.target.blockX + subX + 1;
							maxX = minX - 0.0625;
							maxZ = minZ + 1;
						}
						else if (!te.TopExists())
						{
							maxY = minY + 0.0625;
							maxX = minX + 1;
							maxZ = minZ + 1;
						}
						else
						{
							minX = 0;
							minY = 0;
							minZ = 0;
							maxX = 0;
							maxY = 0;
							maxZ = 0;
						}
					}
					else if (0.00F <  hitZ && hitZ < 0.1875F)
					{
						maxY = minY + 1;
						maxX = minX + 1;
					}
					else if (0.8125F <  hitZ && hitZ < 1F)
					{
						maxY = minY + 1;
						minZ = evt.target.blockZ + subZ + 1;
						maxZ = minZ - 0.0625;
						maxX = minX + 1;
					}
						else if (0.00F <  hitX && hitX < 0.1875F)
					{
						maxY = minY + 1;
						maxZ = minZ + 1;
					}
					else if (0.8125F <  hitX && hitX < 1F)
					{
						maxY = minY + 1;
						minX = evt.target.blockX + subX + 1;
						maxX = minX - 0.0625;
						maxZ = minZ + 1;
					}
					else
					{
						maxY = minY + 0.0625;
						maxX = minX + 1;
						maxZ = minZ + 1;
					}
				}
				else if(evt.target.sideHit == 2)
				{
					if (world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ) != null
							&& world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ) instanceof TileEntitySheet)
					{
						te = (TileEntitySheet)world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ);

						if (0.00F <  hitY && hitY < 0.1875F && !te.BottomExists())
						{
							maxZ = minZ;
							minZ = minZ - 1;
							maxX = minX + 1;
						}
						else if (0.8125F <  hitY && hitY < 1F && !te.TopExists())
						{
							maxZ = minZ;
							minZ = minZ - 1;
							minY = evt.target.blockY + subY + 1;
							maxY = minY - 0.0625;
							maxX = minX + 1;
						}
						else if (0.00F <  hitX && hitX < 0.1875F && !te.EastExists())
						{
							maxZ = minZ;
							minZ = minZ - 1;
							maxY = minY + 1;
						}
						else if (0.8125F <  hitX && hitX < 1F && !te.WestExists())
						{
							maxZ = minZ;
							minZ = minZ - 1;
							minX = evt.target.blockX + subX + 1;
							maxX = minX - 0.0625;
							maxY = minY + 1;
						}
						else if (!te.NorthExists())
						{
							minZ = evt.target.blockZ;
							maxZ = minZ + 0.0625;
							maxX = minX + 1;
							maxY = minY + 1;
						}
						else
						{
							minX = 0;
							minY = 0;
							minZ = 0;
							maxX = 0;
							maxY = 0;
							maxZ = 0;
						}
					}
					else if (0.00F <  hitY && hitY < 0.1875F)
					{
						maxZ = minZ;
						minZ = minZ - 1;
						maxX = minX + 1;
					}
					else if (0.8125F <  hitY && hitY < 1F)
					{
						maxZ = minZ;
						minZ = minZ - 1;
						minY = evt.target.blockY + subY + 1;
						maxY = minY - 0.0625;
						maxX = minX + 1;
					}
					else if (0.00F <  hitX && hitX < 0.1875F)
					{
						maxZ = minZ;
						minZ = minZ - 1;
						maxY = minY + 1;
					}
					else if (0.8125F <  hitX && hitX < 1F)
					{
						maxZ = minZ;
						minZ = minZ - 1;
						minX = evt.target.blockX + subX + 1;
						maxX = minX - 0.0625;
						maxY = minY + 1;
					}
					else
					{
						minZ = evt.target.blockZ - 0.0625;
						maxZ = minZ + 0.0625;
						maxX = minX + 1;
						maxY = minY + 1;
					}
				}
				else if(evt.target.sideHit == 3)
				{
					if (world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ) != null
							&& world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ) instanceof TileEntitySheet)
					{
						te = (TileEntitySheet)world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ);

						if (0.00F <  hitY && hitY < 0.1875F && !te.BottomExists())
						{
							maxZ = minZ + 1;
							maxX = minX + 1;
						}
						else if (0.8125F <  hitY && hitY < 1F && !te.TopExists())
						{
							maxZ = minZ + 1;
							minY = evt.target.blockY + subY + 1;
							maxY = minY - 0.0625;
							maxX = minX + 1;
						}
						else if (0.00F <  hitX && hitX < 0.1875F && !te.EastExists())
						{
							maxZ = minZ + 1;
							maxY = minY + 1;
						}
						else if (0.8125F <  hitX && hitX < 1F && !te.WestExists())
						{
							maxZ = minZ + 1;
							minX = evt.target.blockX + subX + 1;
							maxX = minX - 0.0625;
							maxY = minY + 1;
						}
						else if (!te.SouthExists())
						{
							minZ = evt.target.blockZ + 1;
							maxZ = minZ + 0.0625;
							maxX = minX + 1;
							maxY = minY + 1;
						}
						else
						{
							minX = 0;
							minY = 0;
							minZ = 0;
							maxX = 0;
							maxY = 0;
							maxZ = 0;
						}
					}
					else if (0.00F <  hitY && hitY < 0.1875F)
					{
						maxZ = minZ + 1;
						maxX = minX + 1;
					}
					else if (0.8125F <  hitY && hitY < 1F)
					{
						maxZ = minZ + 1;
						minY = evt.target.blockY + subY + 1;
						maxY = minY - 0.0625;
						maxX = minX + 1;
					}
					else if (0.00F <  hitX && hitX < 0.1875F)
					{
						maxZ = minZ + 1;
						maxY = minY + 1;
					}
					else if (0.8125F <  hitX && hitX < 1F)
					{
						maxZ = minZ + 1;
						minX = evt.target.blockX + subX + 1;
						maxX = minX - 0.0625;
						maxY = minY + 1;
					}
					else
					{
						maxZ = minZ + 0.0625;
						maxX = minX + 1;
						maxY = minY + 1;
					}
				}
				else if(evt.target.sideHit == 4)
				{
					if (world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ) != null
							&& world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ) instanceof TileEntitySheet)
					{
						te = (TileEntitySheet)world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ);

						if (0.00F <  hitY && hitY < 0.1875F && !te.BottomExists())
						{
							maxX = minX;
							minX = minX - 1;
							maxZ = minZ + 1;
						}
						else if (0.8125F <  hitY && hitY < 1F && !te.TopExists())
						{
							maxX = minX;
							minX = minX - 1;
							minY = evt.target.blockY + subY + 1;
							maxY = minY - 0.0625;
							maxZ = minZ + 1;
						}
						else if (0.00F <  hitZ && hitZ < 0.1875F && !te.NorthExists())
						{
							maxX = minX;
							minX = minX - 1;
							maxY = minY + 1;
						}
						else if (0.8125F <  hitZ && hitZ < 1F && !te.SouthExists())
						{
							maxX = minX;
							minX = minX - 1;
							minZ = evt.target.blockZ + subZ + 1;
							maxZ = minZ - 0.0625;
							maxY = minY + 1;
						}
						else if (!te.EastExists())
						{
							maxX = minX;
							minX = minX - 0.0625;
							maxZ = minZ + 1;
							maxY = minY + 1;
						}
						else
						{
							minX = 0;
							minY = 0;
							minZ = 0;
							maxX = 0;
							maxY = 0;
							maxZ = 0;
						}
					}
					else if (0.00F <  hitY && hitY < 0.1875F)
					{
						maxX = minX;
						minX = minX - 1;
						maxZ = minZ + 1;
					}
					else if (0.8125F <  hitY && hitY < 1F)
					{
						maxX = minX;
						minX = minX - 1;
						minY = evt.target.blockY + subY + 1;
						maxY = minY - 0.0625;
						maxZ = minZ + 1;
					}
					else if (0.00F <  hitZ && hitZ < 0.1875F)
					{
						maxX = minX;
						minX = minX - 1;
						maxY = minY + 1;
					}
					else if (0.8125F <  hitZ && hitZ < 1F)
					{
						maxX = minX;
						minX = minX - 1;
						minZ = evt.target.blockZ + subZ + 1;
						maxZ = minZ - 0.0625;
						maxY = minY + 1;
					}
					else
					{
						maxX = minX;
						minX = minX - 0.0625;
						maxZ = minZ + 1;
						maxY = minY + 1;
					}
				}
				else if(evt.target.sideHit == 5)
				{
					if (world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ) != null
							&& world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ) instanceof TileEntitySheet)
					{
						te = (TileEntitySheet)world.getTileEntity(sides[0] + evt.target.blockX, sides[1] + evt.target.blockY, sides[2] + evt.target.blockZ);

						if (0.00F <  hitY && hitY < 0.1875F && !te.BottomExists())
						{
							maxX = minX + 1;
							maxZ = minZ + 1;
						}
						else if (0.8125F <  hitY && hitY < 1F && !te.TopExists())
						{
							maxX = minX + 1;
							minY = evt.target.blockY + subY + 1;
							maxY = minY - 0.0625;
							maxZ = minZ + 1;
						}
						else if (0.00F <  hitZ && hitZ < 0.1875F && !te.NorthExists())
						{
							maxX = minX + 0.0625;
							maxY = minY + 1;
						}
						else if (0.8125F <  hitZ && hitZ < 1F && !te.SouthExists())
						{
							maxX = minX + 1;
							minZ = evt.target.blockZ + subZ + 1;
							maxZ = minZ - 0.0625;
							maxY = minY + 1;
						}
						else if (!te.WestExists())
						{
							minX = evt.target.blockX + 1;
							maxX = minX + 0.0625;
							maxZ = minZ + 1;
							maxY = minY + 1;
						}
						else
						{
							minX = 0;
							minY = 0;
							minZ = 0;
							maxX = 0;
							maxY = 0;
							maxZ = 0;
						}
					}
					if (0.00F <  hitY && hitY < 0.1875F)
					{
						maxX = minX + 1;
						maxZ = minZ + 1;
					}
					else if (0.8125F <  hitY && hitY < 1F)
					{
						maxX = minX + 1;
						minY = evt.target.blockY + subY + 1;
						maxY = minY - 0.0625;
						maxZ = minZ + 1;
					}
					else if (0.00F <  hitZ && hitZ < 0.1875F)
					{
						maxX = minX + 1;
						maxY = minY + 1;
					}
					else if (0.8125F <  hitZ && hitZ < 1F)
					{
						maxX = minX + 1;
						minZ = evt.target.blockZ + subZ + 1;
						maxZ = minZ - 0.0625;
						maxY = minY + 1;
					}
					else
					{
						maxX = minX + 0.0625;
						maxZ = minZ + 1;
						maxY = minY + 1;
					}
				}
			}
			else
			{
				minX = 0;
				minY = 0;
				minZ = 0;
				maxX = 0;
				maxY = 0;
				maxZ = 0;
			}

			//Setup GL for the depthbox
			GL11.glEnable(GL11.GL_BLEND);
			//Setup the GL stuff for the outline
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDepthMask(false);
			//Draw the mini Box
			drawBox(AxisAlignedBB.getBoundingBox(minX,minY,minZ,maxX,maxY,maxZ).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	void drawFaceUV(AxisAlignedBB par1AxisAlignedBB, int side)
	{
		Tessellator var2 = Tessellator.instance;

		var2.setColorRGBA_F(1, 1, 1, 1);
		//Top
		var2.startDrawing(GL11.GL_QUADS);

		if(side == 0)
		{
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, 0, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, 1, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ, 1, 1);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ, 0, 1);
		}
		else if(side == 1)
		{
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ, 0, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ, 1, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ, 1, 1);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ, 0, 1);
		}
		else if(side == 2)
		{
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, 0, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, 1, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ, 1, 1);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ, 0, 1);
		}
		else if(side == 3)
		{
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ, 0, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ, 1, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ, 1, 1);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ, 0, 1);
		}
		else if(side == 4 )
		{
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, 0, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ, 1, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ, 1, 1);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ, 0, 1);
		}
		else if( side == 5)
		{
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, 0, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ, 1, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ, 1, 1);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ, 0, 1);
		}
		var2.draw();
	}

	void drawFace(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;

		//Top
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}

	void drawBox(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;

		//Top
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();

		//Bottom
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.draw();

		//-x
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.draw();

		//+x
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();

		//-z
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();

		//+z
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}

	void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(GL11.GL_LINES);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}
}