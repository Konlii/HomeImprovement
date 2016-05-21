package com.flashoverride.homeimprovement.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import com.flashoverride.homeimprovement.HomeImprovement;
import com.flashoverride.homeimprovement.tileentity.TileEntitySheet;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderSheet implements ISimpleBlockRenderingHandler
{
	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess access = renderblocks.blockAccess;
		TileEntitySheet te = (TileEntitySheet)access.getTileEntity(i, j, k);

		boolean breaking = false;
		if(renderblocks.overrideBlockTexture != null)
			breaking = true;

		double yMin = 0D;
		double yMax = 1D;
		double xMin = 0D;
		double xMax = 1D;
		double zMin = 0D;
		double zMax = 1D;

		double f0 = 0.0625;
		double f1 = 0.9375;

		if(te.BottomExists())
		{
			yMin = 0.0001D;
		}
		if(te.TopExists())
		{
			yMax = 0.9999D;
		}
		if(te.NorthExists())
		{
			zMin = 0.0001D;
		}
		if(te.SouthExists())
		{
			zMax = 0.9999D;
		}
		if(te.EastExists())
		{
			xMin = 0.0001D;
		}
		if(te.WestExists())
		{
			xMax = 0.9999D;
		}
		
		
		
		
		
		
		
		
		
		
		if(te.BottomExists())
		{
			renderblocks.setRenderBounds(xMin, 0, zMin, xMax, f0, zMax);
			if(!breaking) renderblocks.overrideBlockTexture = HomeImprovement.WoodSheet.getIcon(0, te.materialTypes[0]);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(te.TopExists())
		{
			renderblocks.setRenderBounds(xMin, f1, zMin, xMax, 1, zMax);
			if(!breaking) renderblocks.overrideBlockTexture = HomeImprovement.WoodSheet.getIcon(0, te.materialTypes[1]);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(te.NorthExists())
		{
			renderblocks.setRenderBounds(xMin, yMin, 0, xMax, yMax, f0);
			if(!breaking) renderblocks.overrideBlockTexture = HomeImprovement.WoodSheet.getIcon(0, te.materialTypes[2]);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(te.SouthExists())
		{
			renderblocks.setRenderBounds(xMin, yMin, f1, xMax, yMax, 1);
			if(!breaking) renderblocks.overrideBlockTexture = HomeImprovement.WoodSheet.getIcon(0, te.materialTypes[3]);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(te.EastExists())
		{
			renderblocks.setRenderBounds(0, yMin, zMin, f0, yMax, zMax);
			if(!breaking) renderblocks.overrideBlockTexture = HomeImprovement.WoodSheet.getIcon(0, te.materialTypes[4]);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(te.WestExists())
		{
			renderblocks.setRenderBounds(f1, yMin, zMin, 1, yMax, zMax);
			if(!breaking) renderblocks.overrideBlockTexture = HomeImprovement.WoodSheet.getIcon(0, te.materialTypes[5]);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		renderblocks.clearOverrideBlockTexture();

		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0F, 0F, 0F, 1F, 0.2F, 1f);
		renderInvBlock(block, metadata, renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return render(block, x, y, z, renderer);
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return false;
	}

	@Override
	public int getRenderId()
	{
		return 0;
	}

	public static void renderInvBlock(Block block, int m, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		int meta = m;
		if (meta >= 8)
			meta -= 8;
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
		var14.draw();
	}

}
