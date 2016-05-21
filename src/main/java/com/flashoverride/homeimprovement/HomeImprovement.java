package com.flashoverride.homeimprovement;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.flashoverride.homeimprovement.block.BlockConstruct;
import com.flashoverride.homeimprovement.block.BlockSheet;
import com.flashoverride.homeimprovement.handlers.ConfigHandler;
import com.flashoverride.homeimprovement.handlers.CraftingHandler;
import com.flashoverride.homeimprovement.handlers.GuiHandler;
import com.flashoverride.homeimprovement.handlers.PacketPipeline;
import com.flashoverride.homeimprovement.item.ItemSheet;
import com.flashoverride.homeimprovement.item.ItemPlank;
import com.flashoverride.homeimprovement.item.ItemPrybar;
import com.flashoverride.homeimprovement.item.ItemSaw;
import com.flashoverride.homeimprovement.proxies.CommonProxy;
import com.flashoverride.homeimprovement.tileentity.TileEntityConstruct;
import com.flashoverride.homeimprovement.tileentity.TileEntitySheet;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = HomeImprovement.MODID, version = HomeImprovement.VERSION, name = HomeImprovement.NAME)

public class HomeImprovement {
    public static final String MODID = "homeimprovement";
    public static final String VERSION = "1.0";
    public static final String NAME = "Home Improvement";
	public static final String PROXY_LOCATION = "com.flashoverride.homeimprovement.proxies";
	public static final String CHANNEL = MODID;

	public static final String[] WOOD_ALL = {
		"oak","spruce","birch","jungle",
		"acacia","big_oak"
	};
	
	public static final String[] WOOD_1 = {
		"oak","spruce","birch","jungle"
	};
	
	public static final String[] WOOD_2 = {
		"acacia","big_oak"
	};
	
	public static final String[] METAL_ALL = {
		"iron","gold","lapis","redstone",
		"emerald","diamond"
	};
	
	public static final String[] CRAFTING_ALL = {
		"oak","spruce","birch","jungle",
		"acacia","big_oak","crafting"
	};
	
	public static final String[] GLASS_ALL = {
		"glass.glass","glass.white","glass.orange","glass.magenta",
		"glass.light_blue","glass.yellow","glass.lime","glass.pink",
		"glass.gray","glass.silver","glass.cyan","glass.purple",
		"glass.blue","glass.brown","glass.green","glass.red",
		"glass.black"
	};
	
	public static final String[] MATERIALS_ALL = {
		"wood.oak","wood.spruce","wood.birch","wood.jungle",
		"wood.acacia","wood.big_oak","metal.iron","metal.gold",
		"metal.lapis","metal.redstone","metal.emerald","metal.diamond",
		"glass.glass","glass.white","glass.orange","glass.magenta",
		"glass.light_blue","glass.yellow","glass.lime","glass.pink",
		"glass.gray","glass.silver","glass.cyan","glass.purple",
		"glass.blue","glass.brown","glass.green","glass.red",
		"glass.black","wood.crafting"
	};

	private static final ItemStack[] metalRecipeItems = new ItemStack[] {
		new ItemStack(Items.iron_ingot), 
		new ItemStack(Items.gold_ingot), 
		new ItemStack(Items.dye, 1, 4), 
		new ItemStack(Items.redstone), 
		new ItemStack(Items.emerald), 
		new ItemStack(Items.diamond)};
	
	public static int constructRenderId;
	public static int sheetRenderId;
	public static Block WoodConstruct = (new BlockConstruct(Material.wood)).setHardness(2F).setStepSound(Block.soundTypeWood).setBlockName("WoodConstruct");
	public static Block MetalConstruct = (new BlockConstruct(Material.iron)).setHardness(5F).setStepSound(Block.soundTypeMetal).setBlockName("MetalConstruct");
	public static Block GlassConstruct = (new BlockConstruct(Material.glass)).setHardness(0.5F).setStepSound(Block.soundTypeGlass).setBlockName("GlassConstruct");
	public static Block WoodSheet = (new BlockSheet(Material.wood)).setHardness(2F).setStepSound(Block.soundTypeWood).setBlockName("WoodSheet");
	public static Block MetalSheet = (new BlockSheet(Material.iron)).setHardness(5F).setStepSound(Block.soundTypeMetal).setBlockName("MetalSheet");
	public static Block GlassSheet = (new BlockSheet(Material.glass)).setHardness(0.5F).setStepSound(Block.soundTypeGlass).setBlockName("GlassSheet");

	public static TileEntity tileEntityConstruct;
	public static TileEntity tileEntityWoodConstruct;
	public static TileEntity tileEntityMetalConstruct;
	public static TileEntity tileEntityGlassConstruct;
	public static TileEntity TileEntityWoodSheet;
	public static TileEntity TileEntityMetalSheet;
	public static TileEntity TileEntityGlassSheet;

	public static CreativeTabs HITab = new HITabs(CreativeTabs.getNextID(), "HITab");

	public static Item SinglePlank = new ItemPlank().setUnlocalizedName("SinglePlank");
//	public static Item SingleBar = new ItemPlank().setUnlocalizedName("SingleBar");
	public static Item Sheet = new ItemSheet().setUnlocalizedName("Sheet");
	
	public static Item GoldSaw = new ItemSaw("gold_saw", ToolMaterial.GOLD);
	public static Item IronSaw = new ItemSaw("iron_saw", ToolMaterial.IRON);
	public static Item DiamondSaw = new ItemSaw("diamond_saw", ToolMaterial.EMERALD);
	
	public static Item GoldPrybar = new ItemPrybar("gold_prybar", ToolMaterial.GOLD);
	public static Item IronPrybar = new ItemPrybar("iron_prybar", ToolMaterial.IRON);
	public static Item DiamondPrybar = new ItemPrybar("diamond_prybar", ToolMaterial.EMERALD);

	public static final int guiIDCraftingSheet = 53;


	@Instance(HomeImprovement.MODID)
	public static HomeImprovement instance;
	
	@SidedProxy( clientSide = HomeImprovement.PROXY_LOCATION + ".ClientProxy", serverSide = HomeImprovement.PROXY_LOCATION + ".CommonProxy")
	public static CommonProxy proxy;
	
	// The packet pipeline
	public static final PacketPipeline packetPipeline = new PacketPipeline();


	
	/*   public static CreativeTabs homeImprovementTab = new CreativeTabs("HITab") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return SinglePlank;
        }
    };*/
    
	
	@EventHandler
	public static void preInit( FMLPreInitializationEvent event ) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());

		GameRegistry.registerBlock(WoodConstruct, "WoodConstruct");
		GameRegistry.registerBlock(MetalConstruct, "MetalConstruct");
		GameRegistry.registerBlock(GlassConstruct, "GlassConstruct");
		GameRegistry.registerBlock(WoodSheet, "WoodSheet");
		GameRegistry.registerBlock(MetalSheet, "MetalSheet");
		GameRegistry.registerBlock(GlassSheet, "GlassSheet");

		GameRegistry.registerItem(SinglePlank, SinglePlank.getUnlocalizedName());
//		GameRegistry.registerItem(SingleBar, SingleBar.getUnlocalizedName());
		GameRegistry.registerItem(Sheet, Sheet.getUnlocalizedName());

		GameRegistry.registerItem(GoldSaw, GoldSaw.getUnlocalizedName());
		GameRegistry.registerItem(IronSaw, IronSaw.getUnlocalizedName());
		GameRegistry.registerItem(DiamondSaw, DiamondSaw.getUnlocalizedName());
		
		GameRegistry.registerItem(GoldPrybar, GoldPrybar.getUnlocalizedName());
		GameRegistry.registerItem(IronPrybar, IronPrybar.getUnlocalizedName());
		GameRegistry.registerItem(DiamondPrybar, DiamondPrybar.getUnlocalizedName());

		GameRegistry.registerTileEntity(TileEntityConstruct.class, "TEConstruct");
		GameRegistry.registerTileEntity(TileEntitySheet.class, "TileEntitySheet");

		

		OreDictionary.registerOre("itemSaw", new ItemStack(GoldSaw, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("itemSaw", new ItemStack(IronSaw, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("itemSaw", new ItemStack(DiamondSaw, 1, OreDictionary.WILDCARD_VALUE));
		
		OreDictionary.registerOre("itemCrowbar", new ItemStack(GoldPrybar, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("itemCrowbar", new ItemStack(IronPrybar, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("itemCrowbar", new ItemStack(DiamondPrybar, 1, OreDictionary.WILDCARD_VALUE));


		OreDictionary.registerOre("woodLumber", new ItemStack(SinglePlank, 1, OreDictionary.WILDCARD_VALUE));
		
		proxy.registerHandlers();
	}
	
    @EventHandler
    public void init(FMLInitializationEvent event) {
		// Register Packet Handler
		packetPipeline.initalise();
		FMLCommonHandler.instance().bus().register(new CraftingHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
		
		
				// RECIPES
		// glass
		for (int i = 0; i < GLASS_ALL.length-1; i++) {
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Sheet, 1, 13+i), new Object[] {new ItemStack(Blocks.stained_glass_pane, 1, i)}));
		}
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Sheet, 1, 12), new Object[] {new ItemStack(Blocks.glass_pane)}));
		
		
		// reverse glass
		for (int i = 0; i < GLASS_ALL.length-1; i++) {
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.stained_glass_pane, 1, i), new Object[] {new ItemStack(Sheet, 1, 13+i)}));
		}
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.glass_pane), new Object[] {new ItemStack(Sheet, 1, 12)}));

		
		
		
		// wooden sheets
		for (int i = 0; i < MATERIALS_ALL.length - 1; i++) {
			GameRegistry.addRecipe(new ItemStack(Sheet, 1, i), new Object[] {"pp", "pp", 'p', new ItemStack(SinglePlank, 1, i)});
		}
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Sheet, 4, MATERIALS_ALL.length - 1), new Object[] {"itemSaw", new ItemStack(Blocks.crafting_table)}));

		
		// reverse wooden sheets
		for (int i = 0; i < MATERIALS_ALL.length - 1; i++) {
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(SinglePlank, 4, i), new Object[] {"itemSaw", new ItemStack(Sheet, 1, i)}));
		}
		GameRegistry.addRecipe(new ItemStack(Blocks.crafting_table), new Object[] {"ss", "ss", 's', new ItemStack(Sheet, 1, MATERIALS_ALL.length - 1)});

		
		
		
		// logs
		for (int i = 0; i < WOOD_1.length; i++) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(SinglePlank, 36, i), new Object[] {"itemSaw", new ItemStack(Blocks.log, 1, i)}));
		}
		
		for (int i = 0; i < WOOD_2.length; i++) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(SinglePlank, 36, i+4), new Object[] {"itemSaw", new ItemStack(Blocks.log2, 1, i)}));
		}
		
		
		// planks
		for (int i = 0; i < WOOD_ALL.length; i++) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(SinglePlank, 9, i), new Object[] {"itemSaw", new ItemStack(Blocks.planks, 1, i)}));
		}

		
		
		// reverse planks
		for (int i = 0; i < WOOD_ALL.length; i++) {
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 1, i), new Object[] {"ppp", "ppp", "ppp", 'p', new ItemStack(SinglePlank, 1, i)});
		}
		


		
		
		
		// bars
		for (int i = 0; i < metalRecipeItems.length; i++) {
		GameRegistry.addRecipe(new ItemStack(SinglePlank, 9, i+6), new Object[] {"#", "#", '#', metalRecipeItems[i]});
		}
		
		

		// saws
		GameRegistry.addRecipe(new ItemStack(GoldSaw), new Object[] {"sss", "sbb", 's', Items.stick, 'b', new ItemStack(SinglePlank, 1, 7)});
		GameRegistry.addRecipe(new ItemStack(GoldSaw), new Object[] {"sss", "bbs", 's', Items.stick, 'b', new ItemStack(SinglePlank, 1, 7)});

		GameRegistry.addRecipe(new ItemStack(IronSaw), new Object[] {"sss", "sbb", 's', Items.stick, 'b', new ItemStack(SinglePlank, 1, 6)});
		GameRegistry.addRecipe(new ItemStack(IronSaw), new Object[] {"sss", "bbs", 's', Items.stick, 'b', new ItemStack(SinglePlank, 1, 6)});

		GameRegistry.addRecipe(new ItemStack(DiamondSaw), new Object[] {"sss", "sbb", 's', Items.stick, 'b', new ItemStack(SinglePlank, 1, 11)});
		GameRegistry.addRecipe(new ItemStack(DiamondSaw), new Object[] {"sss", "bbs", 's', Items.stick, 'b', new ItemStack(SinglePlank, 1, 11)});

		
		

		
		// prybars
		GameRegistry.addRecipe(new ItemStack(GoldPrybar), new Object[] {"bb", " b ", "b ", 'b', new ItemStack(SinglePlank, 1, 7)});
		GameRegistry.addRecipe(new ItemStack(IronPrybar), new Object[] {"bb", " b ", "b ", 'b', new ItemStack(SinglePlank, 1, 6)});
		GameRegistry.addRecipe(new ItemStack(DiamondPrybar), new Object[] {"bb", " b ", "b ", 'b', new ItemStack(SinglePlank, 1, 11)});

		
		
		
		
		
    }
    
	@EventHandler
	public static void postInit( FMLPostInitializationEvent event ) {
		packetPipeline.postInitialise();
		Blocks.fire.setFireInfo(WoodConstruct, 5, 20);
		Blocks.fire.setFireInfo(MetalConstruct, 0, 0);
		Blocks.fire.setFireInfo(GlassConstruct, 0, 0);
		Blocks.fire.setFireInfo(WoodSheet, 5, 20);
		Blocks.fire.setFireInfo(MetalSheet, 0, 0);
		Blocks.fire.setFireInfo(GlassSheet, 0, 0);
	}
}
