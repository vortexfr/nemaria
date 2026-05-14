package fr.nemaria.mods.future.items;

import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSoup;

public class ItemRabbitStew extends ItemSoup {

	public ItemRabbitStew() {
		super(10);
		setTextureName("rabbit_stew");
		setUnlocalizedName(Utils.getUnlocalisedName("rabbit_stew"));
		setCreativeTab(CreativeTabs.tabBrewing);
	}
}