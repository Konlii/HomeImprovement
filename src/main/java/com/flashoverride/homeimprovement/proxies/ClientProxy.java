package com.flashoverride.homeimprovement.proxies;

import net.minecraftforge.common.MinecraftForge;

import com.flashoverride.homeimprovement.HomeImprovement;
import com.flashoverride.homeimprovement.handlers.PlankHighlightHandler;
import com.flashoverride.homeimprovement.handlers.SheetHighlightHandler;
import com.flashoverride.homeimprovement.render.RenderConstruct;
import com.flashoverride.homeimprovement.render.RenderSheet;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new PlankHighlightHandler());
		MinecraftForge.EVENT_BUS.register(new SheetHighlightHandler());
		RenderingRegistry.registerBlockHandler(HomeImprovement.constructRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderConstruct());
		RenderingRegistry.registerBlockHandler(HomeImprovement.sheetRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderSheet());
	}
}
