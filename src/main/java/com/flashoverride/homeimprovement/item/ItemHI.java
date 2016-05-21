package com.flashoverride.homeimprovement.item;

import java.util.List;

import com.flashoverride.homeimprovement.HomeImprovement;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemHI extends Item
{
	protected boolean stackable = true;

	public String[] MetaNames;
	public IIcon[] MetaIcons;
	public String textureFolder;

	private int craftingXP = 1;

	public ItemHI()
	{
		super();
//		this.setCreativeTab(HomeImprovement.homeImprovementTab);
		textureFolder = "";
//		setNoRepair();
	}

	public ItemHI setMetaNames(String[] metanames)
	{
		MetaNames = metanames;
		this.hasSubtypes = true;
		return this;
	}

	public ItemHI setCraftingXP(int m)
	{
		craftingXP = m;
		return this;
	}

	public int getCraftingXP()
	{
		return this.craftingXP;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		if(MetaNames != null)
		{
			for(int i = 0; i < MetaNames.length; i++)
			{
				list.add(new ItemStack(this, 1, i));
			}
		}
		else
		{
			list.add(new ItemStack(this, 1));
		}
	}

/*	@Override
	public int getItemStackLimit(ItemStack is)
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier <= 64 ? this.getSize(null).stackSize * getWeight(null).multiplier : 64;
			else
				return 1;
	}*/

	public ItemHI setFolder(String s)
	{
		this.textureFolder = s;
		return this;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		if(this.MetaNames == null)
		{
			if(this.iconString != null)
				this.itemIcon = registerer.registerIcon(HomeImprovement.MODID + ":" + this.textureFolder + this.getIconString());
			else
				this.itemIcon = registerer.registerIcon(HomeImprovement.MODID + ":" + this.textureFolder + this.getUnlocalizedName().replace("item.", ""));
		}
		else
		{
			MetaIcons = new IIcon[MetaNames.length];
			for(int i = 0; i < MetaNames.length; i++)
			{
				MetaIcons[i] = registerer.registerIcon(HomeImprovement.MODID + ":" + this.textureFolder + MetaNames[i]);
			}
			
			//This will prevent NullPointerException errors with other mods like NEI
			this.itemIcon = MetaIcons[0];
		}
	}

	@Override
	public IIcon getIconFromDamage(int i)
	{
		if(MetaNames != null && i < MetaNames.length)
			return MetaIcons[i];
		else
			return this.itemIcon;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		if(MetaNames != null && itemstack.getItemDamage() < MetaNames.length)
			return getUnlocalizedName().concat("." + MetaNames[itemstack.getItemDamage()]);
		return super.getUnlocalizedName(itemstack);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

/*	public static void addSizeInformation(ItemStack object, List<String> arraylist)
	{
		if(((ISize)object.getItem()).getSize(object)!= null && ((ISize)object.getItem()).getWeight(object) != null && ((ISize)object.getItem()).getReach(object)!= null)
			arraylist.add("\u2696" + StatCollector.translateToLocal("gui.Weight." + ((ISize)object.getItem()).getWeight(object).getName()) + " \u21F2" + 
					StatCollector.translateToLocal("gui.Size." + ((ISize)object.getItem()).getSize(object).getName().replace(" ", "")));
		if(object.getItem() instanceof IEquipable)
		{
			if(((IEquipable)object.getItem()).getEquipType(object) == IEquipable.EquipType.BACK)
			{
				arraylist.add(EnumChatFormatting.LIGHT_PURPLE.toString()+StatCollector.translateToLocal("gui.slot")+ 
						EnumChatFormatting.GRAY.toString()+": " + 
						EnumChatFormatting.WHITE.toString() + StatCollector.translateToLocal("gui.slot.back"));
			}
		}
	}*/

	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		//Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;
//		ItemHI.addSizeInformation(is, arraylist);

//		addHeatInformation(is, arraylist);

		if (is.hasTagCompound())
		{
			if(is.getTagCompound().hasKey("itemCraftingValue") && is.getTagCompound().getShort("itemCraftingValue") != 0)
				arraylist.add("This Item Has Been Worked");
		}

//		addItemInformation(is, player, arraylist);
		addExtraInformation(is, player, arraylist);
	}

/*	public void addItemInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if(	is.getItem() instanceof ItemIngot ||
				is.getItem() instanceof ItemMetalSheet ||
				is.getItem() instanceof ItemUnfinishedArmor ||
				is.getItem() instanceof ItemBloom ||
				is.getItem() == TFCItems.WroughtIronKnifeHead)
		{
			if(TFC_ItemHeat.HasTemp(is))
			{
				String s = "";
				if(HeatRegistry.getInstance().isTemperatureDanger(is))
				{
					s += EnumChatFormatting.WHITE + StatCollector.translateToLocal("gui.ingot.danger") + " | ";
				}

				if(HeatRegistry.getInstance().isTemperatureWeldable(is))
				{
					s += EnumChatFormatting.WHITE + StatCollector.translateToLocal("gui.ingot.weldable") + " | ";
				}

				if(HeatRegistry.getInstance().isTemperatureWorkable(is))
				{
					s += EnumChatFormatting.WHITE + StatCollector.translateToLocal("gui.ingot.workable");
				}

				if(!s.equals(""))
					arraylist.add(s);
			}
		}
	}*/

/*	public static void addHeatInformation(ItemStack is, List<String> arraylist)
	{
		if (is.hasTagCompound())
		{
			if(TFC_ItemHeat.HasTemp(is))
			{
				float temp = TFC_ItemHeat.GetTemp(is);
				float meltTemp = -1;
				HeatIndex hi = HeatRegistry.getInstance().findMatchingIndex(is);
				if(hi != null)
					meltTemp = hi.meltTemp;

				if(meltTemp != -1)
				{
					if(is.getItem() == TFCItems.Stick)
						arraylist.add(TFC_ItemHeat.getHeatColorTorch(temp, meltTemp));
					else
						arraylist.add(TFC_ItemHeat.getHeatColor(temp, meltTemp));
				}
			}
		}
	}*/

	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		return multimap;
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
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
        return EnumAction.block;
    }
}